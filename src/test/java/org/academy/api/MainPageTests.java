package org.academy.api;

import io.restassured.response.Response;
import org.academy.models.mainpage.LocalizedMainPageDto;
import org.academy.models.mainpage.MainPageDto;
import org.academy.models.mainpage.ProblemDetails;
import org.academy.utils.testrunners.BaseAPITestRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class MainPageTests extends BaseAPITestRunner {

    private MainPageClient publicClient;
    private MainPageClient adminClient;

    @BeforeClass
    public void beforeClass() {
        publicClient = new MainPageClient(testEnvProvider.getBaseApiUrl());
        adminClient = new MainPageClient(testEnvProvider.getBaseApiUrl());
        adminClient.setToken(getAccessToken());
    }

    @Test
    public void testGetMainPage() {
        Response response = publicClient.getMainPageResponse();
        response.then().statusCode(200);

        MainPageDto mainPage = response.as(MainPageDto.class);
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(mainPage, "MainPageDto should not be null");
        softAssert.assertNotNull(mainPage.getId(), "MainPage ID should not be null");
        softAssert.assertNotNull(mainPage.getTitle(), "MainPage title should not be null");
        softAssert.assertNotNull(mainPage.getDescription(), "MainPage description should not be null");
        softAssert.assertNotNull(mainPage.getImage(), "MainPage image should not be null");
        softAssert.assertNotNull(mainPage.getMainAboutUs(), "MainAboutUs block should not be null");
        softAssert.assertNotNull(mainPage.getMainPartners(), "MainPartners block should not be null");
        softAssert.assertNotNull(mainPage.getMainDonations(), "MainDonations block should not be null");
        softAssert.assertNotNull(mainPage.getImpactStatistics(), "ImpactStatistics block should not be null");
        softAssert.assertNotNull(mainPage.getImpactStatistics().getMetrics(), "Metrics list should not be null");
        softAssert.assertFalse(mainPage.getImpactStatistics().getMetrics().isEmpty(), "Metrics list should not be empty");
        softAssert.assertNotNull(mainPage.getLocalizations(), "Localizations should not be null");

        softAssert.assertAll();
    }

    @Test
    public void testGetMainPageAuthenticated() {
        Response response = adminClient.getMainPageResponse();
        response.then().statusCode(200);

        MainPageDto mainPage = adminClient.getMainPage();
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(mainPage, "MainPageDto should not be null when authenticated");
        softAssert.assertNotNull(mainPage.getId(), "MainPage ID should not be null when authenticated");
        softAssert.assertNotNull(mainPage.getTitle(), "MainPage title should not be null when authenticated");

        softAssert.assertAll();
    }

    @Test
    public void testGetLocalizedMainPageWithoutLanguageId() {
        Response response = publicClient.getLocalizedMainPageResponse();
        response.then().statusCode(200);

        LocalizedMainPageDto localizedMainPage = publicClient.getLocalizedMainPage();
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(localizedMainPage, "LocalizedMainPageDto should not be null");
        softAssert.assertNotNull(localizedMainPage.getId(), "LocalizedMainPage ID should not be null");
        softAssert.assertNotNull(localizedMainPage.getTitle(), "LocalizedMainPage title should not be null");
        softAssert.assertNotNull(localizedMainPage.getMainAboutUs(), "LocalizedMainAboutUs block should not be null");
        softAssert.assertNotNull(localizedMainPage.getMainPartners(), "LocalizedMainPartners block should not be null");
        softAssert.assertNotNull(localizedMainPage.getMainDonations(), "LocalizedMainDonations block should not be null");
        softAssert.assertNotNull(localizedMainPage.getImpactStatistics(), "LocalizedImpactStatistics block should not be null");
        softAssert.assertNotNull(localizedMainPage.getImpactStatistics().getMetrics(), "LocalizedMetrics list should not be null");

        softAssert.assertAll();
    }

    @Test
    public void testGetLocalizedMainPageWithLanguageId() {
        MainPageDto mainPage = publicClient.getMainPage();
        Long languageId = 2L;
        if (mainPage.getLocalizations() != null && !mainPage.getLocalizations().isEmpty()) {
            languageId = mainPage.getLocalizations().get(0).getLocalizationInfoDto().getId();
        }

        Response response = publicClient.getLocalizedMainPageResponse(languageId);
        response.then().statusCode(200);

        LocalizedMainPageDto localizedMainPage = publicClient.getLocalizedMainPage(languageId);
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(localizedMainPage, "LocalizedMainPageDto should not be null");
        softAssert.assertEquals(localizedMainPage.getLanguageId(), languageId, "LanguageId mismatch");
        softAssert.assertNotNull(localizedMainPage.getTitle(), "Title should not be null");
        softAssert.assertNotNull(localizedMainPage.getMainAboutUs(), "MainAboutUs should not be null");
        softAssert.assertNotNull(localizedMainPage.getMainPartners(), "MainPartners should not be null");
        softAssert.assertNotNull(localizedMainPage.getMainDonations(), "MainDonations should not be null");
        softAssert.assertNotNull(localizedMainPage.getImpactStatistics(), "ImpactStatistics should not be null");

        softAssert.assertAll();
    }

    @Test
    public void testGetLocalizedMainPageWithNonExistentLanguageId() {
        Response response = publicClient.getLocalizedMainPageResponse(999999L);
        response.then().statusCode(200);

        LocalizedMainPageDto localizedMainPage = response.as(LocalizedMainPageDto.class);
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(localizedMainPage, "LocalizedMainPageDto should not be null on fallback");
        softAssert.assertNotNull(localizedMainPage.getTitle(), "Title should not be null on fallback");
        softAssert.assertNotNull(localizedMainPage.getMainAboutUs(), "MainAboutUs should not be null on fallback");

        softAssert.assertAll();
    }

    @Test
    public void testGetLocalizedMainPageWithInvalidLanguageIdReturnsProblemDetails() {
        Response response = publicClient.getLocalizedMainPageResponse("invalid_id");

        response.then().statusCode(400);

        ProblemDetails problemDetails = response.as(ProblemDetails.class);
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(problemDetails, "ProblemDetails should not be null");
        softAssert.assertEquals(problemDetails.getStatus(), Integer.valueOf(400), "Status code should be 400");
        softAssert.assertNotNull(problemDetails.getTitle(), "Title should not be null");

        softAssert.assertAll();
    }
}
