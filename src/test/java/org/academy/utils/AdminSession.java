package org.academy.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.academy.ui.pages.Admin.AdminPanelPage;
import org.academy.ui.pages.LoginPage;
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
import java.util.concurrent.atomic.AtomicReference;

/**
 * Logs in via API using REST Assured (with UI fallback), checks JWT 'exp' claim, and stubs refresh responses.
 * Intended for admin page tests, not authentication or refresh tests.
 */
public class AdminSession implements AutoCloseable {
    private static final String LOGIN_PATH = "/api/auth/login";
    private static final String ADMIN_PATH = "/admin-panel";
    private static final String ACCESS_TOKEN_FIELD = "accessToken";

    private record Session(String accessToken, URI refreshEndpoint) {
        boolean isExpired() {
            String payloadJson = new String(
                    Base64.getUrlDecoder().decode(accessToken.split("\\.")[1]),
                    StandardCharsets.UTF_8
            );
            Map<?, ?> payload = new Json().toType(payloadJson, Map.class);
            long exp = ((Number) payload.get("exp")).longValue();
            return Instant.now().getEpochSecond() >= exp;
        }
    }

    private static Session sharedSession;
    private final WebDriver driver;
    private final TestEnvProvider environment;
    private NetworkInterceptor interceptor;
    private final AtomicReference<Session> session = new AtomicReference<>();

    public AdminSession(WebDriver driver, TestEnvProvider environment) {
        this.driver = driver;
        this.environment = environment;
    }

    public AdminPanelPage login() {
        if (interceptor == null) {
            interceptor = interceptAuthentication();
            // NetworkInterceptor pauses all traffic by default; only auth needs interception.
            var devTools = ((HasDevTools) driver).getDevTools();
            var authPatterns = List.of(
                    Map.of("urlPattern", "*/api/auth/*", "requestStage", "Request"),
                    Map.of("urlPattern", "*/api/auth/*", "requestStage", "Response")
            );
            devTools.send(new Command<>(
                    "Fetch.enable",
                    Map.of("patterns", authPatterns)
            ));
        }
        if (session.get() == null || session.get().isExpired()) {
            session.set(getSession());
        }
        driver.get(URI.create(environment.getBaseUrl()).resolve(ADMIN_PATH).toString());
        return new AdminPanelPage(driver).waitUntilVisible();
    }

    private NetworkInterceptor interceptAuthentication() {
        URI site = URI.create(environment.getBaseUrl());
        String origin = site.getScheme() + "://" + site.getAuthority();
        return new NetworkInterceptor(driver, (Filter) next -> request -> {
            URI uri = URI.create(request.getUri());
            Session current = session.get();
            if (current != null && current.isExpired()) {
                current = getSession();
                session.set(current);
            }
            URI endpoint = current == null ? null : current.refreshEndpoint();
            if (endpoint != null && endpoint.getScheme().equals(uri.getScheme())
                    && endpoint.getAuthority().equals(uri.getAuthority())
                    && endpoint.getPath().equals(uri.getPath())
                    && ("POST".equals(request.getMethod().toString())
                    || "OPTIONS".equals(request.getMethod().toString()))) {
                return refreshResponse(current.accessToken(), origin);
            }
            if (!LOGIN_PATH.equals(uri.getPath())
                    || !"POST".equals(request.getMethod().toString())) {
                return NetworkInterceptor.PROCEED_WITH_REQUEST;
            }
            HttpResponse response = next.execute(request);
            if (response.getStatus() == 200) {
                Map<?, ?> body = new Json().toType(response.contentAsString(), Map.class);
                String token = (String) body.get(ACCESS_TOKEN_FIELD);
                if (token == null || token.isBlank()) {
                    throw new IllegalStateException("UI login did not return an access token");
                }
                session.set(new Session(token, uri.resolve("refresh-token")));
            }
            return response;
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
    private Session getSession() {
        synchronized (AdminSession.class) {
            if (sharedSession == null || sharedSession.isExpired()) {
                Session apiSession = loginThroughApi();
                if (apiSession != null) {
                    sharedSession = apiSession;
                    session.set(sharedSession);
                } else {
                    loginThroughUi();
                    Session captured = session.get();
                    if (captured == null) {
                        throw new IllegalStateException("UI login did not return an access token");
                    }
                    sharedSession = captured;
                }
            }
            return sharedSession;
        }
    }

    private Session loginThroughApi() {
        try {
            URI loginEndpoint = sharedSession != null
                    ? sharedSession.refreshEndpoint().resolve("login")
                    : resolveDefaultLoginEndpoint();
            Response response = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                    .body(Map.of(
                            "email", environment.getAdminEmail(),
                            "password", environment.getAdminPassword()
                    ))
                    .when()
                    .post(loginEndpoint);
            if (response.statusCode() == 200) {
                String token = response.jsonPath().getString(ACCESS_TOKEN_FIELD);
                if (token != null && !token.isBlank()) {
                    return new Session(token, loginEndpoint.resolve("refresh-token"));
                }
            }
        } catch (Exception ignored) {
            // Fall back to UI login if direct API endpoint is unreachable or differs
        }
        return null;
    }

    private URI resolveDefaultLoginEndpoint() {
        URI site = URI.create(environment.getBaseUrl());
        String host = site.getHost();
        String backendAuthority = (host != null && !host.startsWith("backend.") && !host.equals("localhost"))
                ? "backend." + site.getAuthority()
                : site.getAuthority();
        return URI.create(site.getScheme() + "://" + backendAuthority + LOGIN_PATH);
    }

    private void loginThroughUi() {
        session.set(null);
        driver.get(environment.getAdminLoginUrl());
        LoginPage page = new LoginPage(driver);
        page.enterEmail(environment.getAdminEmail());
        page.enterPassword(environment.getAdminPassword());
        page.clickLoginButton();
        new AdminPanelPage(driver).waitUntilVisible();
    }

    @Override
    public void close() {
        if (interceptor != null) {
            interceptor.close();
            interceptor = null;
        }
    }
}
