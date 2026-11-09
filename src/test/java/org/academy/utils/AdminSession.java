package org.academy.utils;

import org.academy.api.AuthClient;
import org.academy.models.auth.LoginData;
import org.academy.models.auth.LoginResponse;
import org.academy.ui.pages.Admin.AdminPanelPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.Command;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.json.Json;
import org.openqa.selenium.remote.http.Contents;
import org.openqa.selenium.remote.http.Filter;
import org.openqa.selenium.remote.http.HttpResponse;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * Logs in via AuthClient, renews expired JWT via refresh token, and stubs browser refresh calls.
 * Intended for admin page tests, not authentication or refresh tests.
 */
public class AdminSession implements AutoCloseable {
    private static final String REFRESH_PATH = "/api/auth/refresh-token";
    private static final String ADMIN_PATH = "/admin-panel";
    private static final String ACCESS_TOKEN_FIELD = "accessToken";
    private static final long EXPIRATION_BUFFER_SECONDS = 60;

    private record Session(String accessToken, String refreshToken) {
        boolean isExpired() {
            if (accessToken == null || accessToken.isBlank()) {
                return true;
            }
            try {
                String[] parts = accessToken.split("\\.");
                if (parts.length < 2) {
                    return true;
                }
                String payloadJson = new String(
                        Base64.getUrlDecoder().decode(parts[1]),
                        StandardCharsets.UTF_8
                );
                Map<?, ?> payload = new Json().toType(payloadJson, Map.class);
                Object expVal = payload.get("exp");
                if (!(expVal instanceof Number expNumber)) {
                    return true;
                }
                return Instant.now().getEpochSecond() >= (expNumber.longValue() - EXPIRATION_BUFFER_SECONDS);
            } catch (Exception e) {
                return true;
            }
        }
    }

    private static Session sharedSession;
    private final WebDriver driver;
    private final TestEnvProvider environment;
    private NetworkInterceptor interceptor;

    public AdminSession(WebDriver driver, TestEnvProvider environment) {
        this.driver = driver;
        this.environment = environment;
    }

    public AdminPanelPage login() {
        if (interceptor == null) {
            interceptor = interceptAuthentication();
            // NetworkInterceptor pauses all traffic by default; only refresh-token needs interception.
            var devTools = ((HasDevTools) driver).getDevTools();
            var authPatterns = List.of(
                    Map.of("urlPattern", "*" + REFRESH_PATH, "requestStage", "Request")
            );
            devTools.send(new Command<>(
                    "Fetch.enable",
                    Map.of("patterns", authPatterns)
            ));
        }
        getSession(environment);
        driver.get(URI.create(environment.getBaseUrl()).resolve(ADMIN_PATH).toString());
        return new AdminPanelPage(driver).waitUntilVisible();
    }

    private NetworkInterceptor interceptAuthentication() {
        URI site = URI.create(environment.getBaseUrl());
        String origin = site.getScheme() + "://" + site.getAuthority();
        return new NetworkInterceptor(driver, (Filter) next -> request -> {
            URI uri = URI.create(request.getUri());
            String method = request.getMethod().toString();
            if (REFRESH_PATH.equals(uri.getPath())
                    && ("POST".equals(method) || "OPTIONS".equals(method))) {
                Session current = getSession(environment);
                return refreshResponse(current.accessToken(), origin);
            }
            return NetworkInterceptor.PROCEED_WITH_REQUEST;
        });
    }

    private static HttpResponse refreshResponse(String token, String origin) {
        return new HttpResponse().setStatus(200)
                .addHeader("Content-Type", "application/json")
                .addHeader("Access-Control-Allow-Origin", origin)
                .addHeader("Access-Control-Allow-Credentials", "true")
                .addHeader("Access-Control-Allow-Methods", "POST, OPTIONS")
                .addHeader("Access-Control-Allow-Headers", "content-type,authorization")
                .setContent(Contents.utf8String(new Json().toJson(Map.of(ACCESS_TOKEN_FIELD, token))));
    }

    // Prevent parallel tests from logging in simultaneously and renew expired tokens automatically.
    public static Session getSession(TestEnvProvider environment) {
        synchronized (AdminSession.class) {
            if (sharedSession != null && sharedSession.isExpired()) {
                sharedSession = refreshThroughApi(sharedSession, environment);
            }
            if (sharedSession == null || sharedSession.isExpired()) {
                sharedSession = loginThroughApi(environment);
            }
            return sharedSession;
        }
    }

    public static String getSharedAccessToken(TestEnvProvider environment) {
        return getSession(environment).accessToken();
    }

    public static synchronized void invalidateSharedSession() {
        sharedSession = null;
    }

    private static Session refreshThroughApi(Session expiredSession, TestEnvProvider environment) {
        if (expiredSession.refreshToken() == null || expiredSession.refreshToken().isBlank()) {
            return null;
        }
        try {
            AuthClient authClient = new AuthClient(environment.getBaseApiUrl());
            authClient.setToken(expiredSession.accessToken());
            authClient.setRefreshToken(expiredSession.refreshToken());
            if (authClient.refreshToken().statusCode() == 200) {
                String token = authClient.getToken();
                if (token != null && !token.isBlank()) {
                    return new Session(token, authClient.getRefreshToken());
                }
            }
        } catch (Exception ignored) {
            // Fall back to full login if refresh token is expired or rejected
        }
        return null;
    }

    private static Session loginThroughApi(TestEnvProvider environment) {
        AuthClient authClient = new AuthClient(environment.getBaseApiUrl());
        LoginData loginData = new LoginData();
        loginData.setEmail(environment.getAdminEmail());
        loginData.setPassword(environment.getAdminPassword());
        LoginResponse response = authClient.login(loginData);
        String token = response.getAccessToken();
        if (token == null || token.isBlank()) {
            throw new IllegalStateException("API login did not return an access token");
        }
        return new Session(token, authClient.getRefreshToken());
    }

    @Override
    public void close() {
        if (interceptor != null) {
            interceptor.close();
            interceptor = null;
        }
    }
}
