package org.academy.api;

import io.restassured.response.Response;
import org.academy.models.faq.FaqQuestionPageResponse;
import org.academy.models.faq.FaqQuestionResponse;
import org.academy.models.faq.FaqVisitorPageResponse;
import org.academy.models.faq.PublishedFaqQuestionResponse;

import java.util.List;

public class FaqClient extends BaseClient {
    private static final String ENDPOINT = "/Faq";

    public FaqClient(String baseApiUrl) {
        super(baseApiUrl);
    }

    public List<PublishedFaqQuestionResponse> getPublishedQuestions(String slug) {
        return prepareRequest().pathParam("slug", slug).get(ENDPOINT + "/published/{slug}")
                .then().statusCode(200).extract().jsonPath().getList("", PublishedFaqQuestionResponse.class);
    }

    public List<FaqVisitorPageResponse> getPages() {
        return getPagesResponse()
                .then().statusCode(200).extract().jsonPath().getList("", FaqVisitorPageResponse.class);
    }

    public Response getPagesResponse() {
        return prepareRequest().get(ENDPOINT + "/pages");
    }

    public FaqQuestionPageResponse getQuestions(int offset, int limit) {
        return getQuestionsResponse(offset, limit)
                .then().statusCode(200).extract().as(FaqQuestionPageResponse.class);
    }

    public Response getQuestionsResponse(int offset, int limit) {
        return prepareRequest()
                .queryParam("Offset", offset)
                .queryParam("Limit", limit)
                .get(ENDPOINT);
    }

    public FaqQuestionPageResponse getQuestionsByPage(int pageId, int limit) {
        return prepareRequest()
                .queryParam("PageId", pageId)
                .queryParam("Offset", 0)
                .queryParam("Limit", limit)
                .get(ENDPOINT)
                .then().statusCode(200).extract().as(FaqQuestionPageResponse.class);
    }

    public FaqQuestionPageResponse getQuestionsByStatus(int status, int limit) {
        return prepareRequest()
                .queryParam("Status", status)
                .queryParam("Offset", 0)
                .queryParam("Limit", limit)
                .get(ENDPOINT)
                .then().statusCode(200).extract().as(FaqQuestionPageResponse.class);
    }

    public FaqQuestionPageResponse getQuestionsByPageAndStatus(int pageId, int status, int limit) {
        return prepareRequest()
                .queryParam("PageId", pageId)
                .queryParam("Status", status)
                .queryParam("Offset", 0)
                .queryParam("Limit", limit)
                .get(ENDPOINT)
                .then().statusCode(200).extract().as(FaqQuestionPageResponse.class);
    }

    public Response getQuestion(int id) {
        return getQuestion(Integer.toString(id));
    }

    public FaqQuestionResponse getQuestionDetails(int id) {
        return getQuestion(id).then().statusCode(200).extract().as(FaqQuestionResponse.class);
    }

    public Response getQuestion(String id) {
        return prepareRequest().pathParam("id", id).get(ENDPOINT + "/{id}");
    }

    public FaqQuestionPageResponse searchQuestions(String query, int offset, int limit) {
        return searchQuestionsResponse(query, offset, limit)
                .then().statusCode(200).extract().as(FaqQuestionPageResponse.class);
    }

    public Response searchQuestionsResponse(String query, int offset, int limit) {
        return prepareRequest()
                .queryParam("SearchQuery", query)
                .queryParam("Offset", offset)
                .queryParam("Limit", limit)
                .get(ENDPOINT + "/search");
    }
}
