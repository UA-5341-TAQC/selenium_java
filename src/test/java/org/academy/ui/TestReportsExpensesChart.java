package org.academy.ui;

import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.academy.ui.components.ExpensesBreakdownChartComponent;
import org.academy.ui.components.LanguageSwitcherComponent;
import org.academy.ui.pages.ReportsPage;
import org.academy.utils.testrunners.BaseUiTestRunner;
import org.academy.utils.ReportData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.net.URI;
import java.util.List;

public class TestReportsExpensesChart extends BaseUiTestRunner {

    private static final String REPORTS_PATH = "/reports";

    private ReportsPage reportsPage;

    @BeforeMethod
    public void openReportsPageInUkrainian() {
        driver.get(URI.create(testEnvProvider.getBaseUrl()).resolve(REPORTS_PATH).toString());
        reportsPage = new ReportsPage(driver);

        // precondition: the language drop-down is set to Ukrainian (UKR)
        LanguageSwitcherComponent languageSwitcher = reportsPage.getHeader().getLanguageSwitcher();
        if (!languageSwitcher.getCurrentLanguage().toUpperCase().startsWith("U")) {
            languageSwitcher.switchToUa();
        }
    }

    @Test(description = "Verify that the circular chart in the 'Основні витрати' block shows each expenditure "
            + "as both a percentage and an absolute amount")
    @Issue("882")
    @Story("Reports page: expenses breakdown chart")
    public void testExpensesChartShowsPercentageAndAmount() {
        ExpensesBreakdownChartComponent chart = reportsPage.getExpensesBreakdownChart();

        int segments = chart.getSegmentsCount();
        List<String> labels = chart.getChartLabels();
        List<String> percentTexts = chart.getChartPercentageTexts();
        List<Double> percentages = chart.getChartPercentages();
        List<String> amounts = chart.getChartAmounts().stream()
                .map(ReportData::normalizeSpaces)
                .toList();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(reportsPage.getHeader().getLanguageSwitcher().getCurrentLanguage()
                .toUpperCase().startsWith("U"), "Language should be Ukrainian");
        softAssert.assertTrue(chart.isChartDisplayed(), "Chart should be displayed");
        softAssert.assertTrue(segments >= 2, "Chart should have at least 2 segments, found: " + segments);
        softAssert.assertEquals(percentages.size(), segments, "Every segment should show a percentage");
        softAssert.assertEquals(amounts.size(), segments, "Every segment should show an absolute amount");

        for (String percentText : percentTexts) {
            softAssert.assertTrue(percentText.matches(ReportData.PERCENT_FORMAT),
                    "Percentage should look like 'xx.x%', found: " + percentText);
        }
        for (String amount : amounts) {
            softAssert.assertTrue(amount.matches(ReportData.AMOUNT_FORMAT),
                    "Amount should look like 'xxx xxx грн' or 'xxx xxx,xx грн', found: " + amount);
        }
        softAssert.assertEquals(chart.getChartPercentagesSum(), 100.0, ReportData.PERCENT_SUM_TOLERANCE,
                "Percentages should sum up to 100%");

        // compare every segment with the report data (the data the admin panel manages)
        for (String problem : ReportData.findExpenseMismatches(
                labels, percentages, amounts, ReportData.loadReportData(driver))) {
            softAssert.fail(problem);
        }
        softAssert.assertAll();
    }
}