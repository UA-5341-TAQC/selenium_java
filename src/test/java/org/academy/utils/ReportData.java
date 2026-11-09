package org.academy.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.json.Json;
import org.openqa.selenium.remote.http.Contents;
import org.openqa.selenium.remote.http.HttpHandler;
import org.openqa.selenium.remote.http.HttpRequest;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Data of the public Reports page: the formats of the shown values, the report data the page loads
 * from the server (read-only) and a way to substitute the funding block inside the test browser only.
 */
public final class ReportData {

    // the request the Reports page uses to load its data (funding, expenses, programs)
    public static final String REPORT_API = "ReportFundsExpenditures";

    public static final String COLLECTED_FUNDS_LABEL = "Зібрано коштів";

    // "1 000 грн" or "1 000,50 грн"
    public static final String AMOUNT_FORMAT = "\\d{1,3}( \\d{3})*(,\\d{2})? грн";
    // "xx.x%"
    public static final String PERCENT_FORMAT = "\\d{1,3}([.,]\\d)?\\s?%";
    // total collected funds: "1 249 854 грн" (no kopiykas)
    public static final String TOTAL_FORMAT = "\\d{1,3}( \\d{3})* грн";

    public static final double PERCENT_SUM_TOLERANCE = 1.0;
    public static final double PERCENT_TOLERANCE = 0.1;
    public static final BigDecimal AMOUNT_TOLERANCE = BigDecimal.ONE;

    private static final java.net.http.HttpClient HTTP_CLIENT = java.net.http.HttpClient.newHttpClient();

    private ReportData() {
    }

    // the site uses a non-breaking space between the digit groups
    public static String normalizeSpaces(String text) {
        return text.replace('\u00A0', ' ');
    }

    // "1 249 854,99 грн" -> 1249854.99
    public static BigDecimal parseAmount(String text) {
        return new BigDecimal(normalizeSpaces(text).replace("грн", "").replaceAll("\\s", "").replace(',', '.'));
    }

    public static BigDecimal toBigDecimal(Object number) {
        return new BigDecimal(String.valueOf(number));
    }

    // the report data is loaded by the page itself: read the same request the browser has made
    public static Map<String, Object> loadReportData(WebDriver driver) {
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

    // Compares every segment of the expenses chart with the report data.
    // Returns the list of found differences (empty when the chart matches the data).
    @SuppressWarnings("unchecked")
    public static List<String> findExpenseMismatches(List<String> labels, List<Double> percentages,
                                                     List<String> amounts, Map<String, Object> reportData) {
        List<String> problems = new ArrayList<>();
        Map<String, Object> expenses = (Map<String, Object>) reportData.get("expenses");
        List<Map<String, Object>> items = (List<Map<String, Object>>) expenses.get("items");
        BigDecimal total = toBigDecimal(expenses.get("totalUah"));

        if (percentages.size() != items.size()) {
            problems.add("Chart should show every expense item of the report: expected "
                    + items.size() + ", found " + percentages.size());
        }
        List<String> normalizedAmounts = amounts.stream().map(ReportData::normalizeSpaces).toList();
        if (labels.size() != percentages.size() || normalizedAmounts.size() != percentages.size()
                || !normalizedAmounts.stream().allMatch(amount -> amount.matches(AMOUNT_FORMAT))) {
            return problems;
        }

        // The chart can sort the categories differently from the report data and the names can be
        // written in different languages, so every item is matched with the segment that has the same amount.
        List<Integer> freeSegments = new ArrayList<>();
        for (int i = 0; i < percentages.size(); i++) {
            freeSegments.add(i);
        }
        for (Map<String, Object> item : items) {
            String expectedLabel = String.valueOf(item.get("label"));
            BigDecimal expectedAmount = toBigDecimal(item.get("amountUah"));

            Integer index = null;
            for (int segment : freeSegments) {
                if (parseAmount(normalizedAmounts.get(segment)).subtract(expectedAmount).abs()
                        .compareTo(AMOUNT_TOLERANCE) <= 0) {
                    index = segment;
                    break;
                }
            }
            if (index == null) {
                problems.add("No segment with the amount " + expectedAmount + " ('" + expectedLabel
                        + "') is shown in the chart, amounts in the chart: " + normalizedAmounts);
                continue;
            }
            freeSegments.remove(index);

            double expectedPercent = expectedAmount.multiply(BigDecimal.valueOf(100))
                    .divide(total, 4, RoundingMode.HALF_UP).doubleValue();
            if (Math.abs(percentages.get(index) - expectedPercent) > PERCENT_TOLERANCE) {
                problems.add("Percentage of '" + expectedLabel + "' (" + expectedAmount + ") should be "
                        + expectedPercent + ", found: " + percentages.get(index));
            }
        }
        return problems;
    }

    // The report data is substituted only inside the browser of the test: nothing is changed on the site.
    // Only the funding block is replaced, the rest of the real response is kept. Close the result to stop it.
    public static NetworkInterceptor stubFunding(WebDriver driver, String baseUrl,
                                                 Map<String, Double> sources) {
        HttpHandler handler = request -> fundingResponse(request, baseUrl, sources);
        return new NetworkInterceptor(driver,
                Route.matching(request -> request.getUri().contains(REPORT_API)).to(() -> handler));
    }

    @SuppressWarnings("unchecked")
    private static HttpResponse fundingResponse(HttpRequest request, String baseUrl, Map<String, Double> sources) {
        try {
            java.net.http.HttpResponse<String> real = HTTP_CLIENT.send(
                    java.net.http.HttpRequest.newBuilder(URI.create(request.getUri())).GET().build(),
                    java.net.http.HttpResponse.BodyHandlers.ofString());

            double totalUah = 0;
            List<Map<String, Object>> items = new ArrayList<>();
            for (Map.Entry<String, Double> source : sources.entrySet()) {
                totalUah += source.getValue();
                items.add(Map.of("label", source.getKey(), "amountUah", source.getValue(),
                        "amountUsd", source.getValue() / 40));
            }
            totalUah = BigDecimal.valueOf(totalUah).setScale(2, RoundingMode.HALF_UP).doubleValue();

            Map<String, Object> body = new LinkedHashMap<>(new Json().toType(real.body(), Json.MAP_TYPE));
            Map<String, Object> funding = new LinkedHashMap<>((Map<String, Object>) body.get("funding"));
            funding.put("items", items);
            funding.put("totalUah", totalUah);
            funding.put("totalUsd", Math.round(totalUah / 40 * 100) / 100.0);
            body.put("funding", funding);

            URI base = URI.create(baseUrl);
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
}