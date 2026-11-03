package org.academy.ui;

import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.academy.ui.pages.AboutUsPage;
import org.academy.ui.testrunners.BaseUiTestRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.net.URI;
import java.util.List;

public class TestFooterAfterScrollAndReload extends BaseUiTestRunner {

    private static final String ABOUT_US_PATH = "/about-us";

    private static final By FOOTER = By.cssSelector(".footer-container");

    @Test(description = "Verify footer after scrolling and page reload")
    @Issue("86")
    @Story("Footer")
    public void testFooterAfterScrollingAndReload() {
        driver.get(URI.create(testEnvProvider.getBaseUrl()).resolve(ABOUT_US_PATH).toString());
        AboutUsPage aboutUsPage = new AboutUsPage(driver);
        aboutUsPage.getFooter();

        for (int i = 0; i < 3; i++) {
            aboutUsPage.scrollToBottom();
            aboutUsPage.scrollToTop();
        }

        SoftAssert softAssert = new SoftAssert();
        aboutUsPage.scrollToBottom();
        assertFooterIsCorrect(softAssert, "before reload");

        driver.navigate().refresh();
        aboutUsPage = new AboutUsPage(driver);
        aboutUsPage.getFooter();
        aboutUsPage.scrollToBottom();
        assertFooterIsCorrect(softAssert, "after reload");

        softAssert.assertAll();
    }

    private void assertFooterIsCorrect(SoftAssert softAssert, String moment) {
        List<WebElement> footers = driver.findElements(FOOTER);
        softAssert.assertEquals(footers.size(), 1, "Footer should not be duplicated " + moment);
        if (footers.isEmpty()) {
            return;
        }

        softAssert.assertTrue(footers.getFirst().isDisplayed(), "Footer should be displayed " + moment);

        Object atBottom = ((JavascriptExecutor) driver).executeScript(
                "const f = document.querySelector('.footer-container');"
                        + "const bottom = f.getBoundingClientRect().bottom + window.scrollY;"
                        + "return Math.abs(bottom - document.documentElement.scrollHeight) <= 2;");
        softAssert.assertTrue(Boolean.TRUE.equals(atBottom),
                "Footer should be at the bottom of the page " + moment);

        // the element right before the footer (page content) must end above the footer start
        Object notOverlapping = ((JavascriptExecutor) driver).executeScript(
                "const f = document.querySelector('.footer-container');"
                        + "const prev = f.previousElementSibling;"
                        + "if (!prev) return true;"
                        + "return f.getBoundingClientRect().top >= prev.getBoundingClientRect().bottom - 1;");
        softAssert.assertTrue(Boolean.TRUE.equals(notOverlapping),
                "Footer should not overlap the page content " + moment);
    }
}