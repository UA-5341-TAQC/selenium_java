package org.academy.utils;

import org.academy.ui.pages.BasePublicPage;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Set;

/**
 * Checks that a link is alive.
 * Both methods return null when the link is fine,
 * otherwise a short description of the problem.
 */
public final class LinkChecker {

    private static final Set<Integer> BOT_PROTECTED_STATUSES =
            Set.of(401, 403, 429, 999);

    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private LinkChecker() {
    }

    public static String checkInternalLink(WebDriver driver, String link) {
        driver.get(link);

        BasePublicPage page = new BasePublicPage(driver);

        if (!page.isFooterDisplayed()) {
            return link + " -> the page did not load";
        }

        return page.isNotFoundMessageDisplayed()
                ? link + " -> 404 page"
                : null;
    }

    public static String checkExternalLink(String link) {
        HttpRequest request = HttpRequest.newBuilder(URI.create(link))
                .timeout(Duration.ofSeconds(15))
                .header("User-Agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) Chrome/130.0 Safari/537.36")
                .GET()
                .build();

        try {
            int status = HTTP_CLIENT.send(
                    request,
                    HttpResponse.BodyHandlers.discarding()
            ).statusCode();

            boolean ok = status < 400 || BOT_PROTECTED_STATUSES.contains(status);

            return ok ? null : link + " -> HTTP " + status;

        } catch (IOException e) {
            return link + " -> " + e;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return link + " -> interrupted";
        }
    }
}
