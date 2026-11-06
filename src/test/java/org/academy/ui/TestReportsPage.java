package org.academy.ui;

import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.academy.ui.components.HeaderComponent;
import org.academy.ui.components.LanguageSwitcherComponent;
import org.academy.ui.pages.ReportsPage;
import org.academy.utils.testrunners.BaseUiTestRunner;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.net.URI;
import java.util.List;


public class TestReportsPage extends BaseUiTestRunner {

    private static final String REPORTS_PATH = "/reports";
    private static final String SHOW_MORE_BUTTON_UA = "Показати більше";
    private static final String SHOW_LESS_BUTTON_UA = "Показати менше";
    private static final int EXPECTED_ITEMS_COUNT = 5;
    private static final String USD_CURRENCY = "USD";
    private static final String UAH_CURRENCY = "грн";


    private ReportsPage reportsPage;

    @BeforeMethod
    public void openReportsPage() {
        String reportsUrl = URI.create(testEnvProvider.getBaseUrl()).resolve(REPORTS_PATH).toString();
        driver.get(reportsUrl);
        reportsPage = new ReportsPage(driver);
    }

    @Test(description = "Verify that the PDF-file list collapses after clicking the \"Показати менше\" button")
    @Issue("619")
    @Story("Reports Page: reports list")
    public void testShowLessReportsButton() {
        SoftAssert softAssert = new SoftAssert();

        List<String> defaultLabelItems = reportsPage.getReportFileLabels();
        int defaultFilesCount = reportsPage.getReportFilesCount();

        softAssert.assertEquals(defaultFilesCount, EXPECTED_ITEMS_COUNT,
                "The default count doesn't match expected items count");

        softAssert.assertTrue(reportsPage.isShowMoreReportsButtonDisplayed(),
                "The default count doesn't match expected items count");

        reportsPage.clickShowMoreReportsButton();
        String buttonText = reportsPage.getShowMoreReportsButtonText();
        int updatedFilesCount = reportsPage.getReportFilesCount();
        int differenceCount = updatedFilesCount - defaultFilesCount;

        softAssert.assertEquals(buttonText, SHOW_LESS_BUTTON_UA,
                "The text for less button is not available.");

        softAssert.assertTrue(differenceCount == EXPECTED_ITEMS_COUNT,
                "The difference count is not equal 5.");

        softAssert.assertTrue(updatedFilesCount > defaultFilesCount,
                "The default count is higher after then updated.");

        reportsPage.clickShowMoreReportsButton();
        int finalCount = reportsPage.getReportFilesCount();
        List<String> finalLabelItems = reportsPage.getReportFileLabels();

        softAssert.assertEquals(buttonText, SHOW_MORE_BUTTON_UA,
                "The text for less button is not available.");
        softAssert.assertTrue(finalCount == defaultFilesCount,
                "The files count didn't come back to default state.");
        softAssert.assertTrue(finalLabelItems == defaultLabelItems,
                "The order of displayed item is not correct.");

        softAssert.assertAll();

    }
    @Test(description = "Verify that all monetary values on the 'Звітність' page are shown in UAH" +
            " when the language drop-down is set to Ukrainian (UKR)")
    @Issue("627")
    @Story("Reports Page: monetary values check")
    public void testReportsNavigationPages() {
        SoftAssert softAssert = new SoftAssert();
        HeaderComponent headerComponent =reportsPage.getHeader();
        LanguageSwitcherComponent languageSwitcher = headerComponent.getLanguageSwitcher();
        languageSwitcher.switchToUa();

        String defaultMainStatValue = reportsPage.getMainStatValueTexts();
        List <String> defAmountValues = reportsPage.getAmountTexts();
        boolean hasUAH= defAmountValues.stream()
                        .anyMatch(text->text!= null && text.contains(UAH_CURRENCY));
        softAssert.assertTrue(defaultMainStatValue.contains(UAH_CURRENCY),
                "The main stat card doesn't display UAH currency.");
        softAssert.assertTrue(hasUAH,"The page doesn't display UAH currency.");

        languageSwitcher.switchToEn();

        String switchedMainStatValue = reportsPage.getMainStatValueTexts();
        List <String> switchedAmountValues = reportsPage.getAmountTexts();

        boolean hasUSD = switchedAmountValues.stream()
                .anyMatch(text->text!= null && text.contains(USD_CURRENCY));

        softAssert.assertTrue(switchedMainStatValue.contains(USD_CURRENCY),
                "The main stat card doesn't display USD currency.");
        softAssert.assertTrue(hasUSD,"The page doesn't display USD currency.");

        languageSwitcher.switchToUa();

        String finalMainStatValue = reportsPage.getMainStatValueTexts();
        List <String> finalAmountValues = reportsPage.getAmountTexts();
        boolean finalHasUAH = defAmountValues.stream()
                .anyMatch(text->text!= null && text.contains(UAH_CURRENCY));
        softAssert.assertTrue(finalMainStatValue.contains(UAH_CURRENCY),
                "The main stat card doesn't display UAH currency.");
        softAssert.assertTrue(finalHasUAH,"The page doesn't display UAH currency.");

        softAssert.assertAll();

    }
}
