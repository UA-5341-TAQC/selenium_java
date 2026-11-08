package org.academy.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.academy.models.hippotherapy.ProgramCategoryShortDto;

import java.util.List;

public class HippotherapyProgramCategoryClient extends BaseClient {

    private static final String ENDPOINT = "/HippotherapyProgramCategories";

    public HippotherapyProgramCategoryClient(String baseAPIUrl) {
        super(baseAPIUrl);
    }

    public HippotherapyProgramCategoryClient(String baseAPIUrl, ContentType contentType) {
        super(baseAPIUrl, contentType);
    }

    public Response getCategoriesResponse() {
        return prepareRequest().get(ENDPOINT);
    }

    public List<ProgramCategoryShortDto> getCategories() {
        return getCategoriesResponse()
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("", ProgramCategoryShortDto.class);
    }
}