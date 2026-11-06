package org.academy.api;

import org.academy.models.CompanyProfile.CompanyProfileResponse;

public class CompanyProfileClient extends BaseClient{

    private static final String ENDPOINT = "/CompanyProfile";

    public CompanyProfileClient(String baseAPIUrl) {
        super(baseAPIUrl);
    }
    public CompanyProfileResponse getCompanyProfile() {
        return prepareRequest()
                .get(ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .as(CompanyProfileResponse.class);
    }
}
