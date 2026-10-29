package org.academy.ui;

import org.academy.ui.components.LanguageSwitcherComponent;
import org.academy.ui.pages.ContactUsPage;
import org.academy.ui.testrunners.BaseUiTestRunner;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.academy.ui.components.HeaderComponent;
import org.academy.ui.components.FooterComponent;
import org.academy.ui.pages.MainPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.net.URI;
import java.util.List;

public class TestMainPage  extends BaseUiTestRunner {

    private static final String SCROLLING_TEXT_EN = "VICTORY STARTS WITH YOU";
    private static final String MENU_EN = "Menu";
    private static final String REPORTS_EN = "Reports";
    private static final String HOW_TO_SUPPORT_EN = "How to support";
    private static final String VICTORY_STORIES_EN = "Victory stories";
    private static final String ABOUT_US_EN = "About us";
    private static final String OUR_HISTORY_EN = "Our history";
    private static final String OUR_TEAM_EN = "Our team";
    private static final String PARTNERS_EN = "Partners";
    private static final String EVENTS_AND_NEWS_EN = "Events and news";
    private static final String HIPPOTHERAPY_EN = "Hippotherapy";
    private static final String WHAT_IS_HIPPOTHERAPY_EN = "What is hippotherapy";
    private static final String PROGRAMS_EN = "Programs";


    private static final String FACEBOOK_NAME = "Facebook";
    private static final String TELEGRAM_NAME = "Telegram";
    private static final String INSTAGRAM_NAME = "Instagram";

    private MainPage mainPage;

    @BeforeMethod
    public void openMainPage() {
        mainPage = new MainPage(driver);
    }

    @Test(description = "Verify EN localization of the footer")
    @Issue("846")
    @Story("UI Elements on Main Page")
    public void testFooterEN(){
        HeaderComponent headerComponent = mainPage.getHeader();
        LanguageSwitcherComponent languageSwitcher = headerComponent.getLanguageSwitcher();
        languageSwitcher.switchToEn();

        FooterComponent footer=mainPage.getFooter();

        SoftAssert softAssert = new SoftAssert();

        List<String> subTitles = footer.getSubtitlesTexts();
        softAssert.assertTrue(subTitles .contains(MENU_EN), "The menu title text should match EN option.");
        softAssert.assertTrue(subTitles .contains(ABOUT_US_EN), "The about us title text should match EN option.");
        softAssert.assertTrue(subTitles .contains(HIPPOTHERAPY_EN), "The hippotherapy title text should match EN option.");

        List<String> menuNames = footer.getMenuColumnLinkTexts();

        softAssert.assertTrue(menuNames.contains(REPORTS_EN), "The reports text should match EN option.");
        softAssert.assertTrue(menuNames.contains(HOW_TO_SUPPORT_EN), "The how to support text should match EN option.");
        softAssert.assertTrue(menuNames.contains(VICTORY_STORIES_EN), "The victory stories text should match EN option.");

        List<String> aboutUsNames = footer.getAboutUsColumnLinkTexts();
        softAssert.assertTrue(aboutUsNames.contains(ABOUT_US_EN), "The about us text should match EN option.");
        softAssert.assertTrue(aboutUsNames.contains(OUR_HISTORY_EN), "The our history text should match EN option.");
        softAssert.assertTrue(aboutUsNames.contains(OUR_TEAM_EN), "The our team text should match EN option.");
        softAssert.assertTrue(aboutUsNames.contains(PARTNERS_EN), "The partners text should match EN option.");
        softAssert.assertTrue(aboutUsNames.contains(EVENTS_AND_NEWS_EN), "The event and news text should match EN option.");


        List <String> hippotherapyNames = footer.getHippotherapyColumnLinkTexts();
        softAssert.assertTrue(hippotherapyNames.contains(WHAT_IS_HIPPOTHERAPY_EN), "The what is hippotherapy text should match EN option.");
        softAssert.assertTrue(hippotherapyNames.contains(PROGRAMS_EN), "The programs text should match EN option.");


        List<String> socialNames = footer.getSocialMediaTexts();

        softAssert.assertTrue(socialNames.contains(FACEBOOK_NAME), "Social links should contain Facebook");
        softAssert.assertTrue(socialNames.contains(TELEGRAM_NAME), "Social links should contain Telegram");
        softAssert.assertTrue(socialNames.contains(INSTAGRAM_NAME), "Social links should contain Instagram");

        softAssert.assertFalse(footer.getEmailContactText().isBlank(),"The email should be visible.");
        softAssert.assertFalse(footer.getPhoneContactText().isBlank(),"The phone should be visible.");

        softAssert.assertEquals(footer.getScrollingMottoText(),SCROLLING_TEXT_EN,
                "The scrolling text should match EN option.");

        softAssert.assertAll();
    }
}
