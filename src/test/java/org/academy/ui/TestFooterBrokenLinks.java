package org.academy.ui;

import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.academy.ui.pages.BasePublicPage;
import org.academy.utils.testrunners.BaseUiTestRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class TestFooterBrokenLinks extends BaseUiTestRunner {

    private static final By FOOTER = By.cssSelector(".footer-container");
    private static final By FOOTER_LINKS = By.cssSelector(".footer-container a[href]");
    // marker of the SPA 404 page (NotFoundPage)
    private static final By NOT_FOUND_MESSAGE = By.cssSelector("[class*='NotFoundMessage_title']");

    // social networks often answer bots with these codes even if the link is alive
    private static final Set<Integer> BOT_PROTECTED_STATUSES = Set.of(401, 403, 429, 999);

    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    @Test(description = "Verify there are no broken links in the footer")
    @Issue("86")
    @Story("Footer")
    public void testFooterHasNoBrokenLinks() {
        new BasePublicPage(driver).getFooter();
        scrollToBottom();

        List<String> links = collectFooterLinks();
        Assert.assertFalse(links.isEmpty(), "No links were found in the footer");

        String baseHost = URI.create(testEnvProvider.getBaseUrl()).getHost();
        SoftAssert softAssert = new SoftAssert();

        for (String link : links) {
            String problem = baseHost.equals(URI.create(link).getHost())
                    ? checkInternalLink(link)
                    : checkExternalLink(link);
            softAssert.assertNull(problem, "Broken footer link: " + problem);
        }

        softAssert.assertAll();
    }

    private List<String> collectFooterLinks() {
        Set<String> links = new LinkedHashSet<>();
        for (WebElement element : driver.findElements(FOOTER_LINKS)) {
            String href = element.getAttribute("href");
            if (href != null && href.startsWith("http")) {
                links.add(href);
            }
        }
        return List.copyOf(links);
    }

    // returns null when the page opens normally, otherwise a description of the problem
    private String checkInternalLink(String link) {
        driver.get(link);
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.visibilityOfElementLocated(FOOTER));
        } catch (RuntimeException e) {
            return link + " -> the page did not load";
        }

        // findElements waits for the implicit timeout when nothing is found, so switch it off
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        try {
            return driver.findElements(NOT_FOUND_MESSAGE).isEmpty() ? null : link + " -> 404 page";
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(testEnvProvider.getImplicitWait()));
        }
    }

    private String checkExternalLink(String link) {
        HttpRequest request = HttpRequest.newBuilder(URI.create(link))
                .timeout(Duration.ofSeconds(15))
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) Chrome/130.0 Safari/537.36")
                .GET()
                .build();
        try {
            int status = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.discarding()).statusCode();
            boolean ok = status < 400 || BOT_PROTECTED_STATUSES.contains(status);
            return ok ? null : link + " -> HTTP " + status;
        } catch (IOException e) {
            return link + " -> " + e;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return link + " -> interrupted";
        }
    }

    private void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }
}