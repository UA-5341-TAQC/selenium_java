package org.academy.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.academy.models.eventnews.AdminEventNewsCategoryDto;

import java.util.List;

public class EventNewsCategoryClient extends BaseClient {

    private static final String ENDPOINT = "/EventNewsCategories";

    public EventNewsCategoryClient(String baseAPIUrl) {
        super(baseAPIUrl);
    }

    public EventNewsCategoryClient(String baseAPIUrl, ContentType contentType) {
        super(baseAPIUrl, contentType);
    }

    public Response getCategoriesResponse() {
        return prepareRequest().get(ENDPOINT);
    }

    public List<AdminEventNewsCategoryDto> getCategories() {
        return getCategoriesResponse()
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("", AdminEventNewsCategoryDto.class);
    }
}