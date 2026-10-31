package org.academy.ui;

import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.academy.ui.pages.ReportsPage;
import org.academy.ui.testrunners.BaseUiTestRunner;
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

        softAssert.assertTrue(defaultFilesCount == EXPECTED_ITEMS_COUNT,
                "The default count is higher after then updated");

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

    }
}
