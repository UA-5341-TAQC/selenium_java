package org.academy.api.clients;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.academy.api.BaseClient;
import org.academy.models.partners.PartnersPageBannerDto;
import org.academy.models.partners.PartnersPageDto;
import org.academy.models.partners.PartnersSectionDto;

import java.util.List;

public class PartnersClient extends BaseClient {

    private static final String ENDPOINT = "/Partners";
    private static final String PAGE_ENDPOINT = ENDPOINT + "/page";
    private static final String SECTIONS_ENDPOINT = ENDPOINT + "/sections";
    private static final String BANNER_ENDPOINT = ENDPOINT + "/banner";

    public PartnersClient(String baseApiUrl) {
        super(baseApiUrl);
    }

    public PartnersClient(String baseApiUrl, ContentType contentType) {
        super(baseApiUrl, contentType);
    }

    public Response getPartnersPageResponse() {
        return prepareRequest().get(PAGE_ENDPOINT);
    }

    public PartnersPageDto getPartnersPage() {
        return getPartnersPageResponse()
                .then()
                .statusCode(200)
                .extract()
                .as(PartnersPageDto.class);
    }

    public Response getPartnersSectionsResponse() {
        return prepareRequest().get(SECTIONS_ENDPOINT);
    }

    public List<PartnersSectionDto> getPartnersSections() {
        return getPartnersSectionsResponse()
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("", PartnersSectionDto.class);
    }

    public Response getPartnersBannerResponse() {
        return prepareRequest().get(BANNER_ENDPOINT);
    }

    public PartnersPageBannerDto getPartnersBanner() {
        return getPartnersBannerResponse()
                .then()
                .statusCode(200)
                .extract()
                .as(PartnersPageBannerDto.class);
    }
}
