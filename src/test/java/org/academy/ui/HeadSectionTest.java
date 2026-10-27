package org.academy.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Owner;
import org.academy.ui.components.IntroSectionComponent;
import org.academy.ui.components.ProgramsIntroSectionComponent;
import org.academy.ui.pages.MainPage;
import org.academy.ui.pages.ProgramsPage;
import org.academy.ui.testrunners.BaseUiTestRunner;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Feature("Main Page - Head Section")
public class HeadSectionTest extends BaseUiTestRunner {

    @Issue("935")
    @Description("Verify that the Head section is displayed correctly")
    @Owner("Svitlana Kovalova")
    @Test
    public void testHeadSectionIsDisplayed() {

        IntroSectionComponent introSection = new MainPage(driver)
                .getIntroSection();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(introSection.isImageDisplayed(), "Head section image is not displayed");
        softAssert.assertFalse(introSection.getTitleText().isBlank(), "Head section title is empty");
        softAssert.assertTrue(introSection.isTitleDisplayed(), "Head section title is not displayed");
        softAssert.assertFalse(introSection.getDescriptionText().isBlank(), "Head section description is empty");
        softAssert.assertTrue(introSection.isDescriptionDisplayed(), "Head section description is not displayed");
        softAssert.assertTrue(introSection.isCtaButtonDisplayed(), "View Programs button is not displayed");
        softAssert.assertEquals(introSection.getCtaButtonText(), "Переглянути програми", "Incorrect button text");
        softAssert.assertFalse(introSection.getTitleText().matches("(?s).*<[^>]+>.*"), "Title contains HTML tags");
        softAssert.assertFalse(introSection.getDescriptionText().matches("(?s).*<[^>]+>.*"), "Description contains HTML tags");
        softAssert.assertAll();
    }

    @Issue("936")
    @Description("Verify that View Programs opens Programs page in a new tab")
    @Owner("Svitlana Kovalova")
    @Test
    public void testViewProgramsOpensNewTab() {

        MainPage mainPage = new MainPage(driver);

        mainPage.getIntroSection().clickCtaButton();
        mainPage.switchToOtherTab();

        ProgramsIntroSectionComponent programsIntro = new ProgramsPage(driver)
                .getIntroSection();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(driver.getWindowHandles().size(), 2, "New browser tab was not opened");
        softAssert.assertTrue(driver.getCurrentUrl().endsWith("/programs"), "Programs page URL is incorrect");
        softAssert.assertFalse(programsIntro.getTitleText().isBlank(), "Programs page title is empty");
        softAssert.assertFalse(programsIntro.getDescriptionTexts().isEmpty(), "Programs page descriptions are missing");
        softAssert.assertAll();
    }

    @Issue("937")
    @Description("Verify that Main page remains open after clicking View Programs")
    @Owner("Svitlana Kovalova")
    @Test
    public void testMainPageRemainsOpen() {

        MainPage mainPage = new MainPage(driver);
        String originalUrl = driver.getCurrentUrl();

        mainPage.getIntroSection().clickCtaButton();
        mainPage.switchToOtherTab();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(driver.getCurrentUrl().endsWith("/programs"), "Programs page is not opened");

        mainPage.switchToOtherTab();

        softAssert.assertEquals(driver.getCurrentUrl(), originalUrl, "Main page URL has changed");
        softAssert.assertTrue(mainPage.getIntroSection().isTitleDisplayed(), "Main page Head section is not displayed");
        softAssert.assertAll();
    }
}
