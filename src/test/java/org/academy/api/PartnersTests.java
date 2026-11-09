package org.academy.api;

import io.restassured.response.Response;
import org.academy.api.clients.PartnersClient;
import org.academy.models.partners.PartnersPageBannerDto;
import org.academy.models.partners.PartnersPageDto;
import org.academy.models.partners.PartnersSectionDto;
import org.academy.utils.testrunners.BaseAPITestRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class PartnersTests extends BaseAPITestRunner {

    private PartnersClient publicClient;
    private PartnersClient adminClient;

    @BeforeClass
    public void beforeClass() {
        publicClient = new PartnersClient(testEnvProvider.getBaseApiUrl());
        adminClient = new PartnersClient(testEnvProvider.getBaseApiUrl());
        adminClient.setToken(getAccessToken());
    }

    @Test
    public void testGetPartnersPage() {
        Response response = publicClient.getPartnersPageResponse();
        response.then().statusCode(200);

        PartnersPageDto page = response.as(PartnersPageDto.class);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(page, "Partners page should not be null");
        softAssert.assertNotNull(page.getBanner(), "Partners page banner should not be null");
        softAssert.assertNotNull(page.getSections(), "Partners sections should not be null");
        softAssert.assertFalse(page.getSections().isEmpty(), "Partners sections should not be empty");
        softAssert.assertAll();
    }

    @Test
    public void testGetPartnersPageAuthenticated() {
        Response response = adminClient.getPartnersPageResponse();
        response.then().statusCode(200);

        PartnersPageDto page = response.as(PartnersPageDto.class);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(page, "Authenticated Partners page should not be null");
        softAssert.assertNotNull(page.getBanner(), "Authenticated Partners banner should not be null");
        softAssert.assertNotNull(page.getSections(), "Authenticated Partners sections should not be null");
        softAssert.assertAll();
    }

    @Test
    public void testGetPartnersSections() {
        Response response = publicClient.getPartnersSectionsResponse();
        response.then().statusCode(200);

        List<PartnersSectionDto> sections = response.jsonPath().getList("", PartnersSectionDto.class);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(sections, "Partners sections should not be null");
        softAssert.assertFalse(sections.isEmpty(), "Partners sections should not be empty");
        if (sections != null) {
            for (PartnersSectionDto section : sections) {
                softAssert.assertNotNull(section.getId(), "Section id should not be null");
                softAssert.assertNotNull(section.getTitle(), "Section title should not be null");
                softAssert.assertNotNull(section.getDescription(), "Section description should not be null");
                softAssert.assertNotNull(section.getPartners(), "Section partners should not be null");
            }
        }
        softAssert.assertAll();
    }

    @Test
    public void testGetPartnersSectionsAuthenticated() {
        Response response = adminClient.getPartnersSectionsResponse();
        response.then().statusCode(200);

        List<PartnersSectionDto> sections = response.jsonPath().getList("", PartnersSectionDto.class);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(sections, "Authenticated Partners sections should not be null");
        softAssert.assertFalse(sections.isEmpty(), "Authenticated Partners sections should not be empty");
        softAssert.assertAll();
    }

    @Test
    public void testGetPartnersBanner() {
        Response response = publicClient.getPartnersBannerResponse();
        response.then().statusCode(200);

        PartnersPageBannerDto banner = response.as(PartnersPageBannerDto.class);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(banner, "Partners banner should not be null");
        softAssert.assertNotNull(banner.getId(), "Partners banner id should not be null");
        softAssert.assertNotNull(banner.getTitle(), "Partners banner title should not be null");
        softAssert.assertNotNull(banner.getDescription(), "Partners banner description should not be null");
        softAssert.assertNotNull(banner.getImage(), "Partners banner image should not be null");
        softAssert.assertAll();
    }

    @Test
    public void testGetPartnersBannerAuthenticated() {
        Response response = adminClient.getPartnersBannerResponse();
        response.then().statusCode(200);

        PartnersPageBannerDto banner = response.as(PartnersPageBannerDto.class);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(banner, "Authenticated Partners banner should not be null");
        softAssert.assertNotNull(banner.getId(), "Authenticated Partners banner id should not be null");
        softAssert.assertAll();
    }
}
