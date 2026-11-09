package org.academy.ui;

import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.academy.ui.components.FundingSourcesChartComponent;
import org.academy.ui.components.LanguageSwitcherComponent;
import org.academy.ui.pages.ReportsPage;
import org.academy.utils.testrunners.BaseUiTestRunner;
import org.academy.utils.ReportData;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestReportsTotalCollectedFunds extends BaseUiTestRunner {

    private static final String REPORTS_PATH = "/reports";

    // test data for the "not rounded up" check: sum 1 249 854,99 (more than 1 000, cents greater than 50)
    private static final double STUB_AMOUNT_A = 1000000.00;
    private static final double STUB_AMOUNT_B = 249854.99;
    private static final String STUB_EXPECTED_TOTAL = "1249854";

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

    @Test(description = "Verify that the total collected funds on the 'Звітність' page uses spaces as thousand "
            + "separators, shows no kopiykas and is not rounded up (current report data)")
    @Issue("882")
    @Story("Reports page: total collected funds")
    public void testTotalCollectedFundsFormat() {
        FundingSourcesChartComponent fundingSources = reportsPage.getFundingSourcesChart();
        BigDecimal expectedTotal = fundingSources.getTotalAmount().setScale(0, RoundingMode.DOWN);

        String totalText = reportsPage.getStatCardValueByLabel(ReportData.COLLECTED_FUNDS_LABEL);
        String normalized = ReportData.normalizeSpaces(totalText);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(reportsPage.getHeader().getLanguageSwitcher().getCurrentLanguage()
                .toUpperCase().startsWith("U"), "Language should be Ukrainian");
        softAssert.assertTrue(normalized.matches(ReportData.TOTAL_FORMAT),
                "Total should use spaces as thousand separators and have no decimals, found: " + totalText);

        String digits = normalized.replace("грн", "").replaceAll("\\s", "");
        softAssert.assertEquals(digits, expectedTotal.toPlainString(),
                "Total should equal the sum of all funding sources truncated (not rounded up)");
        softAssert.assertAll();
    }

    @Test(description = "Verify that the total collected funds with cents greater than 50 "
            + "(for example 1 249 854,99) is truncated to 1 249 854 and not rounded up")
    @Issue("882")
    @Story("Reports page: total collected funds")
    public void testTotalCollectedFundsIsNotRoundedUp() {
        Map<String, Double> sources = new LinkedHashMap<>();
        sources.put("Stub source A", STUB_AMOUNT_A);
        sources.put("Stub source B", STUB_AMOUNT_B);

        // The report data is substituted only inside this test browser: nothing is changed on the site.
        try (NetworkInterceptor ignored = ReportData.stubFunding(driver, testEnvProvider.getBaseUrl(), sources)) {
            driver.navigate().refresh();

            FundingSourcesChartComponent fundingSources = reportsPage.getFundingSourcesChart();
            BigDecimal barsSum = fundingSources.getTotalAmount();
            String totalText = reportsPage.getStatCardValueByLabel(ReportData.COLLECTED_FUNDS_LABEL);
            String normalized = ReportData.normalizeSpaces(totalText);

            SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(
                    barsSum.subtract(BigDecimal.valueOf(STUB_AMOUNT_A + STUB_AMOUNT_B)).abs()
                            .compareTo(ReportData.AMOUNT_TOLERANCE) <= 0,
                    "The page should show the prepared data, found sum: " + barsSum);
            softAssert.assertTrue(normalized.matches(ReportData.TOTAL_FORMAT),
                    "Total should use spaces as thousand separators and have no decimals, found: " + totalText);
            softAssert.assertEquals(normalized.replace("грн", "").replaceAll("\\s", ""), STUB_EXPECTED_TOTAL,
                    "Total should be truncated to 1 249 854, not rounded up to 1 249 855");
            softAssert.assertAll();
        }
    }
}