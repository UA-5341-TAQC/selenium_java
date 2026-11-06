package org.academy.api;

import io.restassured.response.Response;

public class FaqClient extends BaseClient {
    private static final String ENDPOINT = "/Faq";

    public FaqClient(String baseApiUrl) {
        super(baseApiUrl);
    }

    public Response getPublishedQuestions(String slug) {
        return prepareRequest().pathParam("slug", slug).get(ENDPOINT + "/published/{slug}");
    }

    public Response getPages() {
        return prepareRequest().get(ENDPOINT + "/pages");
    }

    public Response getQuestions(int offset, int limit) {
        return prepareRequest()
                .queryParam("Offset", offset)
                .queryParam("Limit", limit)
                .get(ENDPOINT);
    }

    public Response getQuestionsByPage(long pageId, int limit) {
        return prepareRequest()
                .queryParam("PageId", pageId)
                .queryParam("Offset", 0)
                .queryParam("Limit", limit)
                .get(ENDPOINT);
    }

    public Response getQuestionsByStatus(int status, int limit) {
        return prepareRequest()
                .queryParam("Status", status)
                .queryParam("Offset", 0)
                .queryParam("Limit", limit)
                .get(ENDPOINT);
    }

    public Response getQuestionsByPageAndStatus(long pageId, int status, int limit) {
        return prepareRequest()
                .queryParam("PageId", pageId)
                .queryParam("Status", status)
                .queryParam("Offset", 0)
                .queryParam("Limit", limit)
                .get(ENDPOINT);
    }

    public Response getQuestion(long id) {
        return getQuestion(Long.toString(id));
    }

    public Response getQuestion(String id) {
        return prepareRequest().pathParam("id", id).get(ENDPOINT + "/{id}");
    }

    public Response searchQuestions(String query, int offset, int limit) {
        return prepareRequest()
                .queryParam("SearchQuery", query)
                .queryParam("Offset", offset)
                .queryParam("Limit", limit)
                .get(ENDPOINT + "/search");
    }
}
