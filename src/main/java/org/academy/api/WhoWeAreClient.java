package org.academy.api;

import io.restassured.response.Response;
import org.academy.models.whoweare.WhoWeArePreview;
import org.academy.models.whoweare.WhoWeAreSection;

import java.util.Arrays;
import java.util.List;

public class WhoWeAreClient extends BaseClient {
    private static final String ENDPOINT = "/WhoWeAre";

    public WhoWeAreClient(String baseApiUrl) {
        super(baseApiUrl);
    }

    public Response getSectionResponse(int sectionType) {
        return prepareRequest().get(ENDPOINT + "/" + sectionType);
    }

    public WhoWeAreSection getSection(int sectionType) {
        return getSectionResponse(sectionType)
                .then()
                .statusCode(200)
                .extract()
                .as(WhoWeAreSection.class);
    }

    public Response getPreviewsResponse() {
        return prepareRequest().get(ENDPOINT + "/previews");
    }

    public List<WhoWeArePreview> getPreviews() {
        WhoWeArePreview[] previews = getPreviewsResponse()
                .then()
                .statusCode(200)
                .extract()
                .as(WhoWeArePreview[].class);
        return Arrays.asList(previews);
    }

    public Response getPageResponse() {
        return prepareRequest().get("/WhoWeArePage");
    }

    public List<WhoWeAreSection> getPage() {
        WhoWeAreSection[] sections = getPageResponse()
                .then()
                .statusCode(200)
                .extract()
                .as(WhoWeAreSection[].class);
        return Arrays.asList(sections);
    }
}