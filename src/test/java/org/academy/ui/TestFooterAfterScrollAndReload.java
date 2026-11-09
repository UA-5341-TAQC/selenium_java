package org.academy.ui;

import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.academy.ui.pages.AboutUsPage;
import org.academy.utils.testrunners.BaseUiTestRunner;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.net.URI;

public class TestFooterAfterScrollAndReload extends BaseUiTestRunner {

    private static final String ABOUT_US_PATH = "/about-us";

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
        assertFooterIsCorrect(softAssert, aboutUsPage, "before reload");

        driver.navigate().refresh();
        aboutUsPage = new AboutUsPage(driver);
        aboutUsPage.getFooter();
        aboutUsPage.scrollToBottom();
        assertFooterIsCorrect(softAssert, aboutUsPage, "after reload");

        softAssert.assertAll();
    }

    private void assertFooterIsCorrect(SoftAssert softAssert, AboutUsPage page, String moment) {
        int footers = page.getFooterCount();
        softAssert.assertEquals(footers, 1, "Footer should not be duplicated " + moment);
        if (footers == 0) {
            return;
        }

        softAssert.assertTrue(page.getFooter().isDisplayed(), "Footer should be displayed " + moment);
        softAssert.assertTrue(page.isFooterAtPageBottom(), "Footer should be at the bottom of the page " + moment);
        softAssert.assertTrue(page.isFooterNotOverlappingContent(),
                "Footer should not overlap the page content " + moment);
    }
}