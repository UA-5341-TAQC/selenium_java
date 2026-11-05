package org.academy.ui;

import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.academy.ui.components.FundingSourcesChartComponent;
import org.academy.ui.components.LanguageSwitcherComponent;
import org.academy.ui.pages.ReportsPage;
import org.academy.ui.testrunners.BaseUiTestRunner;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.json.Json;
import org.openqa.selenium.remote.http.Contents;
import org.openqa.selenium.remote.http.HttpRequest;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TestReportsTotalCollectedFunds extends BaseUiTestRunner {

    private static final String REPORTS_PATH = "/reports";
    // the request the Reports page uses to load its data (funding, expenses, programs)
    private static final String REPORT_API = "ReportFundsExpenditures";
    private static final String COLLECTED_FUNDS_LABEL = "Зібрано коштів";

    // total collected funds: spaces as thousand separators, no kopiykas
    private static final String TOTAL_FORMAT = "\\d{1,3}( \\d{3})* грн";

    private static final BigDecimal AMOUNT_TOLERANCE = BigDecimal.ONE;

    // test data for the "not rounded up" check: sum 1 249 854,99 (more than 1 000, cents greater than 50)
    private static final double STUB_AMOUNT_A = 1000000.00;
    private static final double STUB_AMOUNT_B = 249854.99;
    private static final double STUB_TOTAL = 1249854.99;
    private static final String STUB_EXPECTED_TOTAL = "1249854";

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

    @Test(description = "Verify that the total collected funds on the 'Звітність' page uses spaces as thousand "
            + "separators, shows no kopiykas and is not rounded up (current report data)")
    @Issue("882")
    @Story("Reports page: total collected funds")
    public void testTotalCollectedFundsFormat() {
        FundingSourcesChartComponent fundingSources = reportsPage.getFundingSourcesChart();
        BigDecimal expectedTotal = fundingSources.getTotalAmount().setScale(0, RoundingMode.DOWN);

        String totalText = reportsPage.getStatCardValueByLabel(COLLECTED_FUNDS_LABEL);
        // the site uses a non-breaking space between the digit groups
        String normalized = totalText.replace('\u00A0', ' ');

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(reportsPage.getHeader().getLanguageSwitcher().getCurrentLanguage()
                .toUpperCase().startsWith("U"), "Language should be Ukrainian");
        softAssert.assertTrue(normalized.matches(TOTAL_FORMAT),
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
        // The report data is substituted only inside this test browser: nothing is changed on the site.
        try (NetworkInterceptor ignored = new NetworkInterceptor(driver,
                Route.matching(request -> request.getUri().contains(REPORT_API))
                        .to(() -> this::stubFundingResponse))) {
            driver.navigate().refresh();

            FundingSourcesChartComponent fundingSources = reportsPage.getFundingSourcesChart();
            BigDecimal barsSum = fundingSources.getTotalAmount();
            String totalText = reportsPage.getStatCardValueByLabel(COLLECTED_FUNDS_LABEL);
            String normalized = totalText.replace('\u00A0', ' ');

            SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(
                    barsSum.subtract(BigDecimal.valueOf(STUB_TOTAL)).abs().compareTo(AMOUNT_TOLERANCE) <= 0,
                    "The page should show the prepared data (sum " + STUB_TOTAL + "), found sum: " + barsSum);
            softAssert.assertTrue(normalized.matches(TOTAL_FORMAT),
                    "Total should use spaces as thousand separators and have no decimals, found: " + totalText);
            softAssert.assertEquals(normalized.replace("грн", "").replaceAll("\\s", ""), STUB_EXPECTED_TOTAL,
                    "Total should be truncated to 1 249 854, not rounded up to 1 249 855");
            softAssert.assertAll();
        }
    }

    // takes the real report data and replaces only the funding block with the prepared amounts
    @SuppressWarnings("unchecked")
    private HttpResponse stubFundingResponse(HttpRequest request) {
        try {
            java.net.http.HttpResponse<String> real = HTTP_CLIENT.send(
                    java.net.http.HttpRequest.newBuilder(URI.create(request.getUri())).GET().build(),
                    java.net.http.HttpResponse.BodyHandlers.ofString());

            Map<String, Object> body = new LinkedHashMap<>(new Json().toType(real.body(), Json.MAP_TYPE));
            Map<String, Object> funding = new LinkedHashMap<>((Map<String, Object>) body.get("funding"));
            funding.put("items", List.of(
                    fundingItem("Stub source A", STUB_AMOUNT_A),
                    fundingItem("Stub source B", STUB_AMOUNT_B)));
            funding.put("totalUah", STUB_TOTAL);
            funding.put("totalUsd", Math.round(STUB_TOTAL / 40 * 100) / 100.0);
            body.put("funding", funding);

            URI base = URI.create(testEnvProvider.getBaseUrl());
            return new HttpResponse()
                    .setStatus(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .addHeader("Access-Control-Allow-Origin", base.getScheme() + "://" + base.getAuthority())
                    .addHeader("Access-Control-Allow-Credentials", "true")
                    .setContent(Contents.utf8String(new Json().toJson(body)));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }

    private static Map<String, Object> fundingItem(String label, double amountUah) {
        return Map.of("label", label, "amountUah", amountUah, "amountUsd", amountUah / 40);
    }
}