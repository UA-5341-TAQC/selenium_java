package org.academy.cucumber.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.academy.cucumber.hooks.CucumberHook;
import org.academy.ui.pages.MainPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.net.URI;
import java.time.Duration;
import java.util.Locale;
import java.util.Set;

/**
 * Additional footer.feature steps used together with HomePageStep and CucumberHook.
 * Browser lifecycle and shared navigation steps are provided by those classes.
 */
public class FooterSteps {
    private final CucumberHook cucumberHook;

    public FooterSteps(CucumberHook cucumberHook) {
        this.cucumberHook = cucumberHook;
    }

    private String originalWindow;
    private String originalUrl;
    private Set<String> previousWindows;
    private String socialMediaName;

    @Then("the page displays non-empty content")
    public void verifyNonEmptyContent() {
        WebDriver driver = cucumberHook.getDriver();
        String pageText = new MainPage(driver).waitForContentText();
        Assert.assertFalse(pageText.isBlank(),
                "Destination page must display content");
    }

    @Then("the page does not display a 404 or page-not-found message")
    public void verifyPageIsNotMissing() {
        WebDriver driver = cucumberHook.getDriver();
        String pageText = new MainPage(driver).waitForContentText();
        Assert.assertFalse(pageText.matches("(?s).*\\b404\\b.*"),
                "Page must not display a 404 message");
        Assert.assertFalse(pageText.toLowerCase(Locale.ROOT).contains("сторінку не знайдено"),
                "Page must not display a page-not-found message");
    }

    @Then("the footer displays the email {string}")
    public void verifyEmail(String expectedEmail) {
        WebDriver driver = cucumberHook.getDriver();
        String actualEmail = new MainPage(driver).getFooter().getEmailContactText();
        Assert.assertEquals(actualEmail, expectedEmail,
                "Footer email must match the expected value");
    }

    @Then("the footer displays the phone number {string}")
    public void verifyPhone(String expectedPhone) {
        WebDriver driver = cucumberHook.getDriver();
        String actualPhone = new MainPage(driver).getFooter().getPhoneContactText();
        Assert.assertEquals(actualPhone, expectedPhone,
                "Footer phone number must match the expected value");
    }

    @When("the visitor clicks {string} in the footer")
    public void clickSocialMedia(String name) {
        socialMediaName = name;
        WebDriver driver = cucumberHook.getDriver();
        originalWindow = driver.getWindowHandle();
        originalUrl = driver.getCurrentUrl();
        previousWindows = Set.copyOf(driver.getWindowHandles());
        new MainPage(driver).getFooter().clickSocialMedia(name);
    }

    @Then("a new tab opens with the profile {string}")
    public void verifyProfileInNewTab(String expectedUrl) {
        WebDriver driver = cucumberHook.getDriver();
        waitForBrowser().until(ExpectedConditions.numberOfWindowsToBe(previousWindows.size() + 1));
        String newWindow = driver.getWindowHandles().stream()
                .filter(handle -> !previousWindows.contains(handle))
                .findFirst().orElseThrow();
        driver.switchTo().window(newWindow);
        waitForBrowser().until(currentDriver ->
                !currentDriver.getCurrentUrl().equals("about:blank"));

        String actualProfile = normalizeProfileUrl(driver.getCurrentUrl());
        String expectedProfile = normalizeProfileUrl(expectedUrl);
        boolean isExpectedProfile = actualProfile.equals(expectedProfile);

        // Для Instagram також допускаємо перенаправлення на сторінку входу.
        boolean isInstagramLogin = "Instagram".equals(socialMediaName)
                && actualProfile.equals("www.instagram.com/accounts/login");

        Assert.assertTrue(
                isExpectedProfile || isInstagramLogin,
                socialMediaName + " opened an unexpected URL: " + actualProfile
        );
        driver.close();
        driver.switchTo().window(originalWindow);
    }

    @Then("the original page remains open at the same URL")
    public void verifyOriginalPage() {
        WebDriver driver = cucumberHook.getDriver();
        Assert.assertTrue(driver.getWindowHandles().contains(originalWindow),
                "Original tab must remain open");
        driver.switchTo().window(originalWindow);
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, originalUrl,
                "Original page URL must remain unchanged");
    }

    // Створює очікування до 20 секунд для браузера поточного сценарію.
    private WebDriverWait waitForBrowser() {
        WebDriver driver = cucumberHook.getDriver();
        return new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Returns host and path, omitting trailing slashes, scheme, query and fragment.
    // for example: www.instagram.com/victorycenterua
    private String normalizeProfileUrl(String url) {
        URI uri = URI.create(url);
        return uri.getHost() + uri.getPath().replaceFirst("/+$", "");
    }
}
