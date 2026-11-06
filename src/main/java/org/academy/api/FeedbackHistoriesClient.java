package org.academy.api;

import io.restassured.response.Response;
import org.academy.models.FeedbackHistory.FeedbackHistoriesReorderRequest;
import org.academy.models.FeedbackHistory.FeedbackHistoriesSearchResponse;
import org.academy.models.FeedbackHistory.FeedbackHistoryRequest;
import org.academy.models.FeedbackHistory.FeedbackHistoryResponse;

import java.util.Arrays;
import java.util.List;

public class FeedbackHistoriesClient extends BaseClient {

    private static final String ENDPOINT = "/FeedbackHistories";

    public FeedbackHistoriesClient(String baseAPIUrl) {
        super(baseAPIUrl);
    }

    public List<FeedbackHistoryResponse> getPublishedFeedbackHistories() {
        return Arrays.asList(prepareRequest()
                .get(ENDPOINT + "/published")
                .then()
                .statusCode(200)
                .extract()
                .as(FeedbackHistoryResponse[].class));
    }

    public List<FeedbackHistoryResponse> getLatestPublishedFeedbackHistories() {
        return Arrays.asList(prepareRequest()
                .get(ENDPOINT + "/published/latest")
                .then()
                .statusCode(200)
                .extract()
                .as(FeedbackHistoryResponse[].class));
    }

    public List<FeedbackHistoryResponse> getFeedbackHistories(Integer translationStatusFilter) {
        var request = prepareRequest();

        if (translationStatusFilter != null) {
            request.queryParam("translationStatusFilter", translationStatusFilter);
        }

        return Arrays.asList(request
                .get(ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .as(FeedbackHistoryResponse[].class));
    }

    public FeedbackHistoryResponse createFeedbackHistory(FeedbackHistoryRequest request) {
        return prepareRequest()
                .body(request)
                .post(ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .as(FeedbackHistoryResponse.class);
    }

    public Response searchFeedbackHistories(
            String searchQuery, Integer offset, Integer limit) {

        var request = prepareRequest()
                .queryParam("Offset", offset)
                .queryParam("Limit", limit);

        if (searchQuery != null) {
            request.queryParam("SearchQuery", searchQuery);
        }

        return request.get(ENDPOINT + "/search");
    }

    public FeedbackHistoryResponse updateFeedbackHistory(
            Long id, FeedbackHistoryRequest request) {

        return prepareRequest()
                .body(request)
                .put(ENDPOINT + "/" + id)
                .then()
                .statusCode(200)
                .extract()
                .as(FeedbackHistoryResponse.class);
    }

    public Long deleteFeedbackHistory(Long id) {
        return prepareRequest()
                .delete(ENDPOINT + "/" + id)
                .then()
                .statusCode(200)
                .extract()
                .as(Long.class);
    }

    public void reorderFeedbackHistories(FeedbackHistoriesReorderRequest request) {
        prepareRequest()
                .body(request)
                .put(ENDPOINT + "/reorder")
                .then()
                .statusCode(200);
    }
}