package org.academy.ui;

import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import org.academy.ui.components.FooterComponent;
import org.academy.ui.pages.MainPage;
import org.academy.ui.pages.BasePublicPage;
import org.academy.ui.testrunners.BaseUiTestRunner;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Feature("Main Page - Footer Section")
public class TestFooter extends BaseUiTestRunner {

    private record FooterStructure(
            List<String> sectionTitles,
            List<String> menuLinks,
            List<String> aboutUsLinks,
            List<String> hippotherapyLinks,
            String email,
            String phone,
            List<String> socialLinks,
            String scrollingMotto
    ) {
    }

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

    @Test(description = "Verify footer is displayed with the same structure on every public page")
    public void testFooterIsConsistentAcrossPublicPages() {
        List<String> pagePaths = List.of(
                "/", "/about-us", "/reports", "/stories", "/support-us", "/contact-us",
                "/history", "/team", "/partners-page", "/events-and-news", "/hippotherapy", "/programs"
        );
        URI baseUri = URI.create(testEnvProvider.getBaseUrl());
        SoftAssert softAssert = new SoftAssert();
        FooterStructure expectedStructure = null;

        for (String pagePath : pagePaths) {
            driver.get(baseUri.resolve(pagePath).toString());
            FooterComponent footer = new MainPage(driver).getFooter().scrollIntoView();
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

            softAssert.assertTrue(footer.isDisplayed(),
                    "Footer must be displayed on " + pagePath);

            FooterStructure actualStructure = new FooterStructure(
                    footer.getSubtitlesTexts(),
                    footer.getMenuColumnLinkTexts(),
                    footer.getAboutUsColumnLinkTexts(),
                    footer.getHippotherapyColumnLinkTexts(),
                    footer.getEmailContactText(),
                    footer.getPhoneContactText(),
                    footer.getSocialMediaTexts(),
                    footer.getScrollingMottoText()
            );

            softAssert.assertFalse(actualStructure.menuLinks().isEmpty(),
                    "Footer navigation menu must be present on " + pagePath);
            softAssert.assertFalse(actualStructure.email().isBlank() || actualStructure.phone().isBlank(),
                    "Footer contact information must be present on " + pagePath);
            softAssert.assertFalse(actualStructure.socialLinks().isEmpty(),
                    "Footer social media links must be present on " + pagePath);
            softAssert.assertFalse(actualStructure.scrollingMotto().isBlank(),
                    "Footer moving text bar must be present on " + pagePath);

            if (expectedStructure == null) {
                expectedStructure = actualStructure;
            } else {
                softAssert.assertEquals(actualStructure, expectedStructure,
                        "Footer structure must match the home page on " + pagePath);
            }
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

    @Test(description = "Verify footer responsiveness at desktop, tablet, and mobile viewports")
    public void testFooterResponsiveLayout() {
        ChromeDriver chromeDriver = (ChromeDriver) driver;
        SoftAssert softAssert = new SoftAssert();

        try {
            for (int[] size : List.of(new int[]{1440, 900}, new int[]{768, 1024}, new int[]{390, 844})) {
                int width = size[0];
                int height = size[1];
                chromeDriver.executeCdpCommand("Emulation.setDeviceMetricsOverride",
                        Map.of("width", width, "height", height, "deviceScaleFactor", 1, "mobile", false));

                FooterComponent footer = new MainPage(driver).getFooter().scrollIntoView();
                chromeDriver.executeScript("window.scrollTo(0, document.documentElement.scrollHeight)");
                softAssert.assertEquals(((Number) chromeDriver.executeScript("return window.innerWidth")).intValue(),
                        width, "Viewport width must be set for " + width + "x" + height);
                softAssert.assertEquals(((Number) chromeDriver.executeScript("return window.innerHeight")).intValue(),
                        height, "Viewport height must be set for " + width + "x" + height);
                softAssert.assertTrue(footer.isLogoDisplayed(), "Footer logo must be visible at " + width + "px");
                softAssert.assertTrue(footer.isNewsletterDisplayed(),
                        "Footer newsletter must be visible at " + width + "px");
                softAssert.assertFalse(footer.getMenuColumnLinkTexts().isEmpty(),
                        "Footer menu must be visible at " + width + "px");
                softAssert.assertFalse(footer.getAboutUsColumnLinkTexts().isEmpty(),
                        "Footer about-us links must be visible at " + width + "px");
                softAssert.assertFalse(footer.getHippotherapyColumnLinkTexts().isEmpty(),
                        "Footer hippotherapy links must be visible at " + width + "px");
                softAssert.assertFalse(footer.getEmailContactText().isBlank(),
                        "Footer email must be visible at " + width + "px");
                softAssert.assertFalse(footer.getPhoneContactText().isBlank(),
                        "Footer phone must be visible at " + width + "px");
                softAssert.assertFalse(footer.getSocialMediaTexts().isEmpty(),
                        "Footer social links must be visible at " + width + "px");
                softAssert.assertFalse(footer.getScrollingMottoText().isBlank(),
                        "Footer motto must be visible at " + width + "px");

                softAssert.assertTrue(((Number) chromeDriver.executeScript(
                                "return document.documentElement.scrollWidth - document.documentElement.clientWidth"))
                                .intValue() <= 0,
                        "Page must not overflow horizontally at " + width + "px");
                softAssert.assertTrue(((Number) chromeDriver.executeScript(
                                "return arguments[0].scrollWidth - arguments[0].clientWidth",
                                driver.findElement(By.cssSelector(".footer-container"))))
                                .intValue() <= 0,
                        "Footer content must not overflow its container at " + width + "px");
                softAssert.assertTrue(Boolean.TRUE.equals(chromeDriver.executeScript(
                                "return [...arguments[0].querySelectorAll('.main-block, .newsletter-form, .main-contacts, .social-media')]" +
                                        ".filter(element => element.getClientRects().length > 0)" +
                                        ".every(element => element.scrollWidth <= element.clientWidth)",
                                driver.findElement(By.cssSelector(".footer-container")))),
                        "Footer content must not be clipped at " + width + "px");

                chromeDriver.executeScript("window.scrollTo(document.documentElement.scrollWidth, window.scrollY)");
                softAssert.assertEquals(((Number) chromeDriver.executeScript("return window.scrollX")).intValue(), 0,
                        "Page must not horizontally scroll at " + width + "px");
            }
        } finally {
            chromeDriver.executeCdpCommand("Emulation.clearDeviceMetricsOverride", Map.of());
        }
        softAssert.assertAll();
    }

    @Issue("840")
    @Test(description = "TC #840: Footer social media links open company profiles in new tabs")
    public void testFooterSocialMediaLinks() {
        Map<String, String> expectedProfiles = Map.of(
                "Facebook", "https://www.facebook.com/victorycenterua",
                "Telegram", "https://t.me/victorycenterua",
                "Instagram", "https://www.instagram.com/victorycenterua"
        );

        String originalWindow = driver.getWindowHandle();
        String originalUrl = driver.getCurrentUrl();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        SoftAssert softAssert = new SoftAssert();

        for (String name : List.of("Facebook", "Telegram", "Instagram")) {
            Set<String> previousWindows = Set.copyOf(driver.getWindowHandles());
            new MainPage(driver).getFooter().clickSocialMedia(name);

            // Очікуємо відкриття нової вкладки.
            wait.until(ExpectedConditions.numberOfWindowsToBe(previousWindows.size() + 1));

            String newWindow = driver.getWindowHandles().stream()
                    .filter(handle -> !previousWindows.contains(handle))
                    .findFirst()
                    .orElseThrow();

            driver.switchTo().window(newWindow);
            wait.until(currentDriver ->
                    !currentDriver.getCurrentUrl().equals("about:blank"));

            String actualUrl = normalizeProfileUrl(driver.getCurrentUrl());
            String expectedUrl = normalizeProfileUrl(expectedProfiles.get(name));

            boolean isExpectedProfile = actualUrl.equals(expectedUrl);

            // Для Instagram також допускаємо перенаправлення на сторінку входу.
            boolean isInstagramLogin = name.equals("Instagram")
                    && actualUrl.equals("www.instagram.com/accounts/login");

            softAssert.assertTrue(
                    isExpectedProfile || isInstagramLogin,
                    name + " opened an unexpected URL: " + actualUrl
            );
            driver.close();
            driver.switchTo().window(originalWindow);


            softAssert.assertEquals(
                    driver.getCurrentUrl(),
                    originalUrl,
                    "Original page must remain open after clicking " + name
            );
        }

        softAssert.assertAll();
    }

    // Повертає домен і шлях без кінцевих слешів, схеми, параметрів запиту та фрагмента.
    private String normalizeProfileUrl(String url) {
        URI uri = URI.create(url);
        return uri.getHost() + uri.getPath().replaceFirst("/+$", "");
    }
}
