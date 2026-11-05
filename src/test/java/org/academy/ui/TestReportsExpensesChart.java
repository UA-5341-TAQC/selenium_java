package org.academy.ui;

import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.academy.ui.components.ExpensesBreakdownChartComponent;
import org.academy.ui.components.LanguageSwitcherComponent;
import org.academy.ui.pages.ReportsPage;
import org.academy.utils.testrunners.BaseUiTestRunner;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.json.Json;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.util.List;
import java.util.Map;

public class TestReportsExpensesChart extends BaseUiTestRunner {

    private static final String REPORTS_PATH = "/reports";
    // the request the Reports page uses to load its data (funding, expenses, programs)
    private static final String REPORT_API = "ReportFundsExpenditures";

    // "1 000 грн" or "1 000,50 грн"
    private static final String AMOUNT_FORMAT = "\\d{1,3}( \\d{3})*(,\\d{2})? грн";
    // "xx.x%"
    private static final String PERCENT_FORMAT = "\\d{1,3}([.,]\\d)?\\s?%";
    private static final double PERCENT_SUM_TOLERANCE = 1.0;
    private static final double PERCENT_TOLERANCE = 0.1;
    private static final BigDecimal AMOUNT_TOLERANCE = BigDecimal.ONE;

    private static final java.net.http.HttpClient HTTP_CLIENT = java.net.http.HttpClient.newHttpClient();

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
    @SuppressWarnings("unchecked")
    public void testExpensesChartShowsPercentageAndAmount() {
        ExpensesBreakdownChartComponent chart = reportsPage.getExpensesBreakdownChart();

        int segments = chart.getSegmentsCount();
        List<String> labels = chart.getChartLabels();
        List<String> percentTexts = chart.getChartPercentageTexts();
        List<Double> percentages = chart.getChartPercentages();
        List<String> amounts = chart.getChartAmounts().stream()
                .map(amount -> amount.replace('\u00A0', ' '))
                .toList();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(reportsPage.getHeader().getLanguageSwitcher().getCurrentLanguage()
                .toUpperCase().startsWith("U"), "Language should be Ukrainian");
        softAssert.assertTrue(chart.isChartDisplayed(), "Chart should be displayed");
        softAssert.assertTrue(segments >= 2, "Chart should have at least 2 segments, found: " + segments);
        softAssert.assertEquals(percentages.size(), segments, "Every segment should show a percentage");
        softAssert.assertEquals(amounts.size(), segments, "Every segment should show an absolute amount");

        for (String percentText : percentTexts) {
            softAssert.assertTrue(percentText.matches(PERCENT_FORMAT),
                    "Percentage should look like 'xx.x%', found: " + percentText);
        }
        for (String amount : amounts) {
            softAssert.assertTrue(amount.matches(AMOUNT_FORMAT),
                    "Amount should look like 'xxx xxx грн' or 'xxx xxx,xx грн', found: " + amount);
        }
        softAssert.assertEquals(chart.getChartPercentagesSum(), 100.0, PERCENT_SUM_TOLERANCE,
                "Percentages should sum up to 100%");

        // compare every segment with the report data (the data the admin panel manages)
        Map<String, Object> expenses = (Map<String, Object>) loadReportApiData().get("expenses");
        List<Map<String, Object>> items = (List<Map<String, Object>>) expenses.get("items");
        BigDecimal total = toBigDecimal(expenses.get("totalUah"));

        softAssert.assertEquals(segments, items.size(), "Chart should show every expense item of the report");
        if (labels.size() == percentages.size() && amounts.size() == percentages.size()
                && amounts.stream().allMatch(a -> a.matches(AMOUNT_FORMAT))) {
            for (int i = 0; i < items.size(); i++) {
                String expectedLabel = String.valueOf(items.get(i).get("label"));
                BigDecimal expectedAmount = toBigDecimal(items.get(i).get("amountUah"));
                int index = indexOfLabel(labels, expectedLabel, i);
                if (index < 0 || index >= percentages.size()) {
                    softAssert.fail("Expense category is not shown in the chart: " + expectedLabel);
                    continue;
                }
                softAssert.assertTrue(
                        parseAmount(amounts.get(index)).subtract(expectedAmount).abs()
                                .compareTo(AMOUNT_TOLERANCE) <= 0,
                        "Amount of '" + expectedLabel + "' should be " + expectedAmount
                                + ", found: " + amounts.get(index));
                double expectedPercent = expectedAmount.multiply(BigDecimal.valueOf(100))
                        .divide(total, 4, RoundingMode.HALF_UP).doubleValue();
                softAssert.assertEquals(percentages.get(index), expectedPercent, PERCENT_TOLERANCE,
                        "Percentage of '" + expectedLabel + "' should match the report data");
            }
        }
        softAssert.assertAll();
    }

    // the report data is loaded by the page itself: read the same request the browser has made
    @SuppressWarnings("unchecked")
    private Map<String, Object> loadReportApiData() {
        String url = (String) ((JavascriptExecutor) driver).executeScript(
                "const e = performance.getEntriesByType('resource')"
                        + ".filter(r => r.name.includes('" + REPORT_API + "'));"
                        + "return e.length ? e[0].name : null;");
        if (url == null) {
            throw new IllegalStateException("Report data request was not found on the page");
        }
        try {
            java.net.http.HttpResponse<String> response = HTTP_CLIENT.send(
                    java.net.http.HttpRequest.newBuilder(URI.create(url))
                            .header("Accept", "application/json").GET().build(),
                    java.net.http.HttpResponse.BodyHandlers.ofString());
            return new Json().toType(response.body(), Json.MAP_TYPE);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }

    // chart label may contain extra text, so compare by "contains"; fall back to the same position
    private static int indexOfLabel(List<String> labels, String expectedLabel, int fallbackIndex) {
        for (int i = 0; i < labels.size(); i++) {
            String label = labels.get(i).toLowerCase();
            String expected = expectedLabel.toLowerCase();
            if (label.contains(expected) || expected.contains(label)) {
                return i;
            }
        }
        return fallbackIndex;
    }

    private static BigDecimal toBigDecimal(Object number) {
        return new BigDecimal(String.valueOf(number));
    }

    // "1 249 854,99 грн" -> 1249854.99
    private static BigDecimal parseAmount(String text) {
        return new BigDecimal(text.replace("грн", "").replaceAll("\\s", "").replace(',', '.'));
    }
}