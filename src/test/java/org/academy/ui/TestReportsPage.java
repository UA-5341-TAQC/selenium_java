package org.academy.ui;

import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.academy.ui.components.ExpensesBreakdownChartComponent;
import org.academy.ui.components.FundingSourcesChartComponent;
import org.academy.ui.components.LanguageSwitcherComponent;
import org.academy.ui.pages.ReportsPage;
import org.academy.ui.testrunners.BaseUiTestRunner;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.util.List;

public class TestReportsPage extends BaseUiTestRunner {

    private static final String REPORTS_PATH = "/reports";
    private static final String COLLECTED_FUNDS_LABEL = "Зібрано коштів";

    // "1 000 грн" or "1 000,50 грн"
    private static final String AMOUNT_FORMAT = "\\d{1,3}( \\d{3})*(,\\d{2})? грн";
    // "xx.x%"
    private static final String PERCENT_FORMAT = "\\d{1,3}([.,]\\d)?\\s?%";
    // total collected funds: spaces as thousand separators, no kopiykas
    private static final String TOTAL_FORMAT = "\\d{1,3}( \\d{3})* грн";

    private static final double PERCENT_SUM_TOLERANCE = 1.0;
    // percentages are rounded to 0.1 and amounts to whole hryvnias, so allow a small difference
    private static final double PERCENT_TO_AMOUNT_TOLERANCE = 0.5;
    private static final BigDecimal HALF = new BigDecimal("0.50");

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
        List<String> percentTexts = chart.getChartPercentageTexts();
        List<Double> percentages = chart.getChartPercentages();
        List<String> amounts = chart.getChartAmounts().stream()
                .map(amount -> amount.replace(' ', ' '))
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

        // each percentage must correspond to its amount: amount / total * 100
        if (amounts.size() == percentages.size() && amounts.stream().allMatch(a -> a.matches(AMOUNT_FORMAT))) {
            List<BigDecimal> values = amounts.stream().map(TestReportsPage::parseAmount).toList();
            BigDecimal total = values.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
            for (int i = 0; i < values.size(); i++) {
                double expected = values.get(i).multiply(BigDecimal.valueOf(100))
                        .divide(total, 4, RoundingMode.HALF_UP).doubleValue();
                softAssert.assertEquals(percentages.get(i), expected, PERCENT_TO_AMOUNT_TOLERANCE,
                        "Percentage of segment " + (i + 1) + " should match its amount " + amounts.get(i));
            }
        }
        softAssert.assertAll();
    }

    @Test(description = "Verify that the total collected funds on the 'Звітність' page uses spaces as thousand "
            + "separators, shows no kopiykas and is not rounded up")
    @Issue("882")
    @Story("Reports page: total collected funds")
    public void testTotalCollectedFundsFormat() {
        FundingSourcesChartComponent fundingSources = reportsPage.getFundingSourcesChart();
        BigDecimal sum = fundingSources.getTotalAmount();
        BigDecimal expectedTotal = sum.setScale(0, RoundingMode.DOWN);

        String totalText = reportsPage.getStatCardValueByLabel(COLLECTED_FUNDS_LABEL);
        // the site uses a non-breaking space between the digit groups
        String normalized = totalText.replace(' ', ' ');

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(reportsPage.getHeader().getLanguageSwitcher().getCurrentLanguage()
                .toUpperCase().startsWith("U"), "Language should be Ukrainian");
        softAssert.assertTrue(normalized.matches(TOTAL_FORMAT),
                "Total should use spaces as thousand separators and have no decimals, found: " + totalText);

        String digits = normalized.replace("грн", "").replaceAll("\\s", "");
        softAssert.assertEquals(digits, expectedTotal.toPlainString(),
                "Total should equal the sum of all funding sources truncated (not rounded up)");
        softAssert.assertAll();

        // precondition 4: the sum is above 1 000 and its cents are greater than 50,
        // otherwise rounding up cannot be distinguished from truncating
        BigDecimal cents = sum.subtract(expectedTotal);
        if (sum.compareTo(new BigDecimal("1000")) <= 0 || cents.compareTo(HALF) <= 0) {
            throw new SkipException("Precondition is not met: the sum of funding sources must be greater than "
                    + "1 000 and have cents greater than 50 (for example 1 249 854,99), current sum: " + sum);
        }
    }

    // "1 249 854,99 грн" -> 1249854.99
    private static BigDecimal parseAmount(String text) {
        return new BigDecimal(text.replace("грн", "").replaceAll("\\s", "").replace(',', '.'));
    }
}