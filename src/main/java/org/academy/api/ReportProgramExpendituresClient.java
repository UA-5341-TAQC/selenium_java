package org.academy.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.academy.models.reportprogram.ReportProgramExpendituresRecordDto;
import org.academy.models.reportprogram.ReportProgramExpendituresSummaryDto;

import java.util.List;

public class ReportProgramExpendituresClient extends BaseClient {

    private static final String RECORDS_ENDPOINT = "/ReportProgramExpendituresRecords";
    private static final String SUMMARY_ENDPOINT = RECORDS_ENDPOINT + "/summary";
    private static final String CATEGORY_IDS_PARAM = "hippotherapyProgramCategoryIds";

    public ReportProgramExpendituresClient(String baseAPIUrl) {
        super(baseAPIUrl);
    }

    public ReportProgramExpendituresClient(String baseAPIUrl, ContentType contentType) {
        super(baseAPIUrl, contentType);
    }

    public Response getRecordsResponse() {
        return getRecordsResponse(null);
    }

    public Response getRecordsResponse(List<Long> categoryIds) {
        RequestSpecification req = prepareRequest();
        if (categoryIds != null && !categoryIds.isEmpty()) {
            req.queryParam(CATEGORY_IDS_PARAM, categoryIds);
        }
        return req.get(RECORDS_ENDPOINT);
    }

    public Response getRecordsResponseRaw(String rawCategoryId) {
        return prepareRequest().queryParam(CATEGORY_IDS_PARAM, rawCategoryId).get(RECORDS_ENDPOINT);
    }

    public List<ReportProgramExpendituresRecordDto> getRecords() {
        return getRecords(null);
    }

    public List<ReportProgramExpendituresRecordDto> getRecords(List<Long> categoryIds) {
        return getRecordsResponse(categoryIds)
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("", ReportProgramExpendituresRecordDto.class);
    }

    public Response getSummaryResponse() {
        return prepareRequest().get(SUMMARY_ENDPOINT);
    }

    public ReportProgramExpendituresSummaryDto getSummary() {
        return getSummaryResponse()
                .then()
                .statusCode(200)
                .extract()
                .as(ReportProgramExpendituresSummaryDto.class);
    }
}