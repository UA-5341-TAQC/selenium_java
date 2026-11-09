package org.academy.api;

import io.restassured.response.Response;
import org.academy.models.reportprogram.ProblemDetails;
import org.academy.models.reportprogram.ReportProgramExpendituresRecordDto;
import org.academy.models.reportprogram.ReportProgramExpendituresSummaryDto;
import org.academy.utils.testrunners.BaseAPITestRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.math.BigDecimal;
import java.util.List;

public class ReportProgramExpendituresTests extends BaseAPITestRunner {

    private ReportProgramExpendituresClient client;

    @BeforeClass
    public void beforeClass() {
        client = new ReportProgramExpendituresClient(testEnvProvider.getBaseApiUrl());
        client.setToken(getAccessToken());
    }

    @Test
    public void testGetRecordsWithoutTokenIsUnauthorized() {
        new ReportProgramExpendituresClient(testEnvProvider.getBaseApiUrl())
                .getRecordsResponse()
                .then()
                .statusCode(401);
    }

    @Test
    public void testGetRecords() {
        client.getRecordsResponse().then().statusCode(200);

        List<ReportProgramExpendituresRecordDto> records = client.getRecords();
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(records, "Records list should not be null");
        for (ReportProgramExpendituresRecordDto record : records) {
            softAssert.assertNotNull(record.getId(), "Id should not be null");
            softAssert.assertNotNull(record.getReportingYear(), "Reporting year should not be null");
            softAssert.assertNotNull(record.getHippotherapyProgramCategoryId(), "Category id should not be null");
            softAssert.assertNotNull(record.getAmountUah(), "Amount UAH should not be null");
            softAssert.assertNotNull(record.getAmountUsd(), "Amount USD should not be null");
        }
        softAssert.assertAll();
    }

    @Test
    public void testGetRecordsFilteredByCategory() {
        List<ReportProgramExpendituresRecordDto> all = client.getRecords();
        if (all.isEmpty()) {
            return;
        }
        Long categoryId = all.getFirst().getHippotherapyProgramCategoryId();

        List<ReportProgramExpendituresRecordDto> filtered = client.getRecords(List.of(categoryId));
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertFalse(filtered.isEmpty(), "Filtered list should not be empty");
        for (ReportProgramExpendituresRecordDto record : filtered) {
            softAssert.assertEquals(record.getHippotherapyProgramCategoryId(), categoryId,
                    "Every record should belong to the requested category");
        }
        softAssert.assertAll();
    }

    @Test
    public void testGetRecordsInvalidCategoryReturnsBadRequest() {
        Response response = client.getRecordsResponseRaw("abc");
        response.then().statusCode(400);

        ProblemDetails problem = response.as(ProblemDetails.class);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(problem.getTitle(), "Problem title should not be null");
        softAssert.assertTrue(problem.getStatus() != null && problem.getStatus() == 400,
                "Problem status should be 400, but was " + problem.getStatus());
        softAssert.assertAll();
    }

    @Test
    public void testGetSummary() {
        client.getSummaryResponse().then().statusCode(200);

        ReportProgramExpendituresSummaryDto summary = client.getSummary();
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(summary.getTotalAmountUah(), "Total UAH should not be null");
        softAssert.assertNotNull(summary.getTotalAmountUsd(), "Total USD should not be null");
        softAssert.assertTrue(summary.getTotalAmountUah().signum() >= 0, "Total UAH should not be negative");
        softAssert.assertTrue(summary.getTotalAmountUsd().signum() >= 0, "Total USD should not be negative");
        softAssert.assertAll();
    }

    @Test
    public void testSummaryMatchesRecords() {
        List<ReportProgramExpendituresRecordDto> records = client.getRecords();
        ReportProgramExpendituresSummaryDto summary = client.getSummary();

        BigDecimal sumUah = records.stream()
                .map(ReportProgramExpendituresRecordDto::getAmountUah)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal sumUsd = records.stream()
                .map(ReportProgramExpendituresRecordDto::getAmountUsd)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(summary.getTotalAmountUah().compareTo(sumUah), 0,
                "Total UAH should equal the sum of records, but was " + summary.getTotalAmountUah()
                        + " vs " + sumUah);
        softAssert.assertEquals(summary.getTotalAmountUsd().compareTo(sumUsd), 0,
                "Total USD should equal the sum of records, but was " + summary.getTotalAmountUsd()
                        + " vs " + sumUsd);
        softAssert.assertAll();
    }
}