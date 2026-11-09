package org.academy.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.academy.models.localization.LocalizationLanguageDto;

import java.util.List;

public class LocalizationLanguageClient extends BaseClient {

    private static final String ENDPOINT = "/LocalizationLanguage";

    public LocalizationLanguageClient(String baseAPIUrl) {
        super(baseAPIUrl);
    }

    public LocalizationLanguageClient(String baseAPIUrl, ContentType contentType) {
        super(baseAPIUrl, contentType);
    }

    public Response getLanguagesResponse() {
        return prepareRequest().get(ENDPOINT);
    }

    public List<LocalizationLanguageDto> getLanguages() {
        return getLanguagesResponse()
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("", LocalizationLanguageDto.class);
    }
}