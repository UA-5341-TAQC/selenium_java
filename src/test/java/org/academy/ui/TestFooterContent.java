package org.academy.ui;

import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.academy.ui.components.FooterComponent;
import org.academy.ui.pages.MainPage;
import org.academy.utils.testrunners.BaseUiTestRunner;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TestFooterContent extends BaseUiTestRunner {

    private static final String SCROLLING_TEXT_UA = "ПЕРЕМОГА ПОЧИНАЄТЬСЯ З ТЕБЕ";
    private static final String ANOTHER_PAGE_PATH = "/reports";

    private MainPage mainPage;

    @BeforeMethod
    public void openMainPage() {
        mainPage = new MainPage(driver);
    }

    @Test(description = "Verify moving text bar across the footer")
    @Issue("842")
    @Story("UI Elements on Main Page")
    public void testFooterMovingTextBar() {
        FooterComponent footer = mainPage.getFooter();

        Assert.assertTrue(footer.isScrollingMottoDisplayed(),
                "The moving text bar should be displayed in the footer.");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(footer.getScrollingMottoText(), SCROLLING_TEXT_UA,
                "The moving text bar text is wrong.");
        softAssert.assertTrue(footer.isScrollingMottoMoving(),
                "The text in the moving bar should move continuously.");
        softAssert.assertAll();
    }

    @Test(description = "Verify the newsletter subscription block is not displayed in the footer")
    @Issue("841")
    @Story("UI Elements on Main Page")
    public void testNewsletterBlockIsNotDisplayed() {
        SoftAssert softAssert = new SoftAssert();

        FooterComponent footer = mainPage.getFooter().scrollIntoView();
        checkFooterWithoutNewsletter(footer, "Home page", softAssert);

        footer.clickNavigationLink(ANOTHER_PAGE_PATH);
        FooterComponent anotherPageFooter = new MainPage(driver).getFooter().scrollIntoView();
        checkFooterWithoutNewsletter(anotherPageFooter, ANOTHER_PAGE_PATH, softAssert);

        softAssert.assertAll();
    }

    private void checkFooterWithoutNewsletter(FooterComponent footer, String page, SoftAssert softAssert) {
        softAssert.assertFalse(footer.isNewsletterDisplayed(),
                "[" + page + "] Newsletter subscription block should not be displayed.");
        softAssert.assertFalse(footer.getMenuColumnLinkTexts().isEmpty(),
                "[" + page + "] Footer menu links should be displayed.");
        softAssert.assertFalse(footer.getEmailContactText().isBlank(),
                "[" + page + "] Footer email contact should be displayed.");
    }
}
