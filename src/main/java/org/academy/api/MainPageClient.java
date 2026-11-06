package org.academy.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.academy.models.mainpage.LocalizedMainPageDto;
import org.academy.models.mainpage.MainPageDto;

public class MainPageClient extends BaseClient {

    private static final String ENDPOINT = "/MainPage";
    private static final String LOCALIZED_ENDPOINT = ENDPOINT + "/localized";

    public MainPageClient(String baseAPIUrl) {
        super(baseAPIUrl);
    }

    public MainPageClient(String baseAPIUrl, ContentType contentType) {
        super(baseAPIUrl, contentType);
    }

    public Response getMainPageResponse() {
        return prepareRequest().get(ENDPOINT);
    }

    public MainPageDto getMainPage() {
        return getMainPageResponse()
                .then()
                .statusCode(200)
                .extract()
                .as(MainPageDto.class);
    }

    public Response getLocalizedMainPageResponse() {
        return prepareRequest().get(LOCALIZED_ENDPOINT);
    }

    public Response getLocalizedMainPageResponse(Long languageId) {
        RequestSpecification request = prepareRequest();
        if (languageId != null) {
            request.queryParam("languageId", languageId);
        }
        return request.get(LOCALIZED_ENDPOINT);
    }

    public Response getLocalizedMainPageResponse(String languageId) {
        RequestSpecification request = prepareRequest();
        if (languageId != null) {
            request.queryParam("languageId", languageId);
        }
        return request.get(LOCALIZED_ENDPOINT);
    }

    public LocalizedMainPageDto getLocalizedMainPage() {
        return getLocalizedMainPageResponse()
                .then()
                .statusCode(200)
                .extract()
                .as(LocalizedMainPageDto.class);
    }

    public LocalizedMainPageDto getLocalizedMainPage(Long languageId) {
        return getLocalizedMainPageResponse(languageId)
                .then()
                .statusCode(200)
                .extract()
                .as(LocalizedMainPageDto.class);
    }
}
