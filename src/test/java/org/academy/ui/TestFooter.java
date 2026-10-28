package org.academy.ui;

import io.qameta.allure.Issue;
import org.academy.ui.components.FooterComponent;
import org.academy.ui.pages.MainPage;
import org.academy.ui.pages.BasePublicPage;
import org.academy.ui.testrunners.BaseUiTestRunner;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestFooter extends BaseUiTestRunner {

    @Issue("838")
    @Test(description = "TC #838: Footer navigation links open the corresponding pages")
    public void testFooterNavigationLinks() {
        List<String> expectedPaths = List.of(
                "/reports", "/support-us", "/stories", "/about-us", "/history",
                "/team", "/partners-page", "/events-and-news", "/hippotherapy", "/programs"
        );
        FooterComponent footer = new MainPage(driver).getFooter();
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(footer.getNavigationPaths(), expectedPaths,
                "Footer must contain all expected navigation links");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        for (String path : expectedPaths) {
            // Кожен перехід перевіряємо з головної сторінки.
            driver.get(URI.create(testEnvProvider.getBaseUrl()).resolve("/").toString());
            new MainPage(driver).getFooter().clickNavigationLink(path);
            String expectedUrl = URI.create(testEnvProvider.getBaseUrl()).resolve(path).toString();
            wait.until(ExpectedConditions.urlToBe(expectedUrl));
            String pageText = new BasePublicPage(driver).waitForContentText();
            softAssert.assertEquals(driver.getCurrentUrl(), expectedUrl,
                    "Incorrect destination for footer link " + path);
            softAssert.assertFalse(pageText.matches("(?s).*\\b404\\b.*"),
                    "Footer link must not open a 404 page: " + path);
            softAssert.assertFalse(pageText.toLowerCase(java.util.Locale.ROOT).contains("сторінку не знайдено"),
                    "Footer link must not open a missing page: " + path);
        }
        softAssert.assertAll();
    }

    @Issue("839")
    @Test(description = "TC #839: Footer displays the expected email and phone number")
    public void testFooterContactInformation() {
        FooterComponent footer = new MainPage(driver).getFooter();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(footer.getEmailContactText(), "victorycenter@gmail.com",
                "Footer email must match TC #839");
        softAssert.assertEquals(footer.getPhoneContactText(), "+380 50 334 4448",
                "Footer phone must match TC #839");
        softAssert.assertAll();
    }

    @Issue("840")
    @Test(description = "TC #840: Footer social media links open company profiles in new tabs")
    public void testFooterSocialMediaLinks() {
        // Задаємо очікувані профілі
        Map<String, String> expectedProfiles = Map.of(
                "Facebook", "https://www.facebook.com/victorycenterua",
                "Telegram", "https://t.me/victorycenterua",
                "Instagram", "https://www.instagram.com/victorycenterua"
        );
        String originalWindow = driver.getWindowHandle();
        String originalUrl = driver.getCurrentUrl();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        SoftAssert softAssert = new SoftAssert();

        // По черзі перевіряємо три соцмережі
        for (String name : List.of("Facebook", "Telegram", "Instagram")) {
            Set<String> previousWindows = Set.copyOf(driver.getWindowHandles());
            new MainPage(driver).getFooter().clickSocialMedia(name);
            // Повинна відкритися нова вкладка
            wait.until(ExpectedConditions.numberOfWindowsToBe(previousWindows.size() + 1));
            String newWindow = driver.getWindowHandles().stream()
                    .filter(handle -> !previousWindows.contains(handle))
                    .findFirst().orElseThrow();
            // Перемикаємо Selenium на нову вкладку
            driver.switchTo().window(newWindow);
            wait.until(currentDriver -> !currentDriver.getCurrentUrl().equals("about:blank"));
            softAssert.assertEquals(normalizeProfileUrl(driver.getCurrentUrl()),
                    normalizeProfileUrl(expectedProfiles.get(name)),
                    name + " must open the Victory Center profile");
            driver.close();
            // Перемикаємо Selenium на початкову
            driver.switchTo().window(originalWindow);
            softAssert.assertEquals(driver.getCurrentUrl(), originalUrl,
                    "Original page must remain open after clicking " + name);
        }
        softAssert.assertAll();
    }

    // Повертає домен і шлях без кінцевих слешів, схеми, параметрів запиту та фрагмента.
    private String normalizeProfileUrl(String url) {
        URI uri = URI.create(url);
        return uri.getHost() + uri.getPath().replaceFirst("/+$", "");
    }
}
