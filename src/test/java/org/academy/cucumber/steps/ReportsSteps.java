package org.academy.cucumber.steps;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.academy.cucumber.hooks.CucumberHook;
import org.academy.ui.components.ExpensesBreakdownChartComponent;
import org.academy.ui.components.FundingSourcesChartComponent;
import org.academy.ui.components.LanguageSwitcherComponent;
import org.academy.ui.pages.ReportsPage;
import org.academy.utils.ReportData;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Steps of reports.feature. The page is opened by the shared
 * "the visitor opens the {string} page at {string}" step.
 */
public class ReportsSteps {

    private final CucumberHook cucumberHook;
    private NetworkInterceptor interceptor;

    public ReportsSteps(CucumberHook cucumberHook) {
        this.cucumberHook = cucumberHook;
    }

    private ReportsPage getReportsPage() {
        return new ReportsPage(cucumberHook.getDriver());
    }

    // stops the substitution of the report data (it exists only in the browser of the scenario)
    @After(order = 20000)
    public void stopReportDataSubstitution() {
        if (interceptor != null) {
            try {
                interceptor.close();
            } catch (RuntimeException ignored) {
                // the browser can already be closed
            }
            interceptor = null;
        }
    }

    @Given("the website language is set to Ukrainian")
    public void setUkrainianLanguage() {
        LanguageSwitcherComponent languageSwitcher = getReportsPage().getHeader().getLanguageSwitcher();
        if (!languageSwitcher.getCurrentLanguage().toUpperCase().startsWith("U")) {
            languageSwitcher.switchToUa();
        }
    }

    @Given("the funding report is replaced with the sources {string} and {string}")
    public void replaceFundingReport(String firstAmount, String secondAmount) {
        Map<String, Double> sources = new LinkedHashMap<>();
        sources.put("Stub source A", Double.parseDouble(firstAmount));
        sources.put("Stub source B", Double.parseDouble(secondAmount));

        // the data is substituted only inside the browser of the scenario: nothing is changed on the site
        interceptor = ReportData.stubFunding(cucumberHook.getDriver(),
                cucumberHook.getTestValueProvider().getBaseUrl(), sources);
        cucumberHook.getDriver().navigate().refresh();
    }

    @Then("the total collected funds are shown with spaces as thousands separators and without kopiykas")
    public void verifyTotalFormat() {
        String totalText = getReportsPage().getStatCardValueByLabel(ReportData.COLLECTED_FUNDS_LABEL);
        Assert.assertTrue(ReportData.normalizeSpaces(totalText).matches(ReportData.TOTAL_FORMAT),
                "Total should use spaces as thousand separators and have no decimals, found: " + totalText);
    }

    @Then("the total collected funds equal the sum of all funding sources without kopiykas")
    public void verifyTotalIsTruncatedSum() {
        ReportsPage page = getReportsPage();
        FundingSourcesChartComponent fundingSources = page.getFundingSourcesChart();
        BigDecimal expectedTotal = fundingSources.getTotalAmount().setScale(0, RoundingMode.DOWN);

        String totalText = page.getStatCardValueByLabel(ReportData.COLLECTED_FUNDS_LABEL);
        String digits = ReportData.normalizeSpaces(totalText).replace("грн", "").replaceAll("\\s", "");
        Assert.assertEquals(digits, expectedTotal.toPlainString(),
                "Total should equal the sum of all funding sources truncated (not rounded up)");
    }

    @Then("the total collected funds are {string}")
    public void verifyTotal(String expectedTotal) {
        String totalText = getReportsPage().getStatCardValueByLabel(ReportData.COLLECTED_FUNDS_LABEL);
        Assert.assertEquals(ReportData.normalizeSpaces(totalText), expectedTotal,
                "Total should be truncated, not rounded up");
    }

    @Then("every segment of the expenses chart shows a percentage and an absolute amount")
    public void verifyChartSegments() {
        ExpensesBreakdownChartComponent chart = getReportsPage().getExpensesBreakdownChart();
        int segments = chart.getSegmentsCount();
        List<String> percentTexts = chart.getChartPercentageTexts();
        List<String> amounts = chart.getChartAmounts().stream().map(ReportData::normalizeSpaces).toList();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(chart.isChartDisplayed(), "Chart should be displayed");
        softAssert.assertTrue(segments >= 2, "Chart should have at least 2 segments, found: " + segments);
        softAssert.assertEquals(percentTexts.size(), segments, "Every segment should show a percentage");
        softAssert.assertEquals(amounts.size(), segments, "Every segment should show an absolute amount");
        for (String percentText : percentTexts) {
            softAssert.assertTrue(percentText.matches(ReportData.PERCENT_FORMAT),
                    "Percentage should look like 'xx.x%', found: " + percentText);
        }
        for (String amount : amounts) {
            softAssert.assertTrue(amount.matches(ReportData.AMOUNT_FORMAT),
                    "Amount should look like 'xxx xxx грн' or 'xxx xxx,xx грн', found: " + amount);
        }
        softAssert.assertAll();
    }

    @Then("the percentages of the expenses chart sum up to 100 percent")
    public void verifyPercentagesSum() {
        ExpensesBreakdownChartComponent chart = getReportsPage().getExpensesBreakdownChart();
        Assert.assertEquals(chart.getChartPercentagesSum(), 100.0, ReportData.PERCENT_SUM_TOLERANCE,
                "Percentages should sum up to 100%");
    }

    @Then("the expenses chart matches the report data")
    public void verifyChartMatchesReportData() {
        ExpensesBreakdownChartComponent chart = getReportsPage().getExpensesBreakdownChart();
        List<String> problems = ReportData.findExpenseMismatches(
                chart.getChartLabels(), chart.getChartPercentages(), chart.getChartAmounts(),
                ReportData.loadReportData(cucumberHook.getDriver()));
        Assert.assertTrue(problems.isEmpty(), "The chart differs from the report data: " + problems);
    }
}