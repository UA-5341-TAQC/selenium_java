package org.academy.api;

import org.academy.models.eventnews.AdminEventNewsCategoryDto;
import org.academy.models.eventnews.EventNewsCategoryLocalizationDto;
import org.academy.utils.testrunners.BaseAPITestRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class EventNewsCategoryTests extends BaseAPITestRunner {

    private EventNewsCategoryClient publicClient;
    private EventNewsCategoryClient adminClient;

    @BeforeClass
    public void beforeClass() {
        publicClient = new EventNewsCategoryClient(testEnvProvider.getBaseApiUrl());
        adminClient = new EventNewsCategoryClient(testEnvProvider.getBaseApiUrl());
        adminClient.setToken(getAccessToken());
    }

    @Test
    public void testGetCategoriesAuthenticated() {
        adminClient.getCategoriesResponse().then().statusCode(200);

        List<AdminEventNewsCategoryDto> categories = adminClient.getCategories();
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(categories, "Categories list should not be null");
        softAssert.assertFalse(categories.isEmpty(), "Categories list should not be empty");
        for (AdminEventNewsCategoryDto category : categories) {
            softAssert.assertNotNull(category.getId(), "Id should not be null");
            softAssert.assertNotNull(category.getName(), "Name should not be null");
            softAssert.assertNotNull(category.getCreatedAt(), "CreatedAt should not be null");
            softAssert.assertNotNull(category.getRelatedEventNewsCount(), "Related news count should not be null");
            if (category.getRelatedEventNewsCount() != null) {
                softAssert.assertTrue(category.getRelatedEventNewsCount() >= 0,
                        "Related news count should not be negative");
            }
            softAssert.assertNotNull(category.getLocalizations(), "Localizations should not be null");
        }
        softAssert.assertAll();
    }

    @Test
    public void testLocalizationsContainLanguageAndName() {
        List<AdminEventNewsCategoryDto> categories = adminClient.getCategories();
        SoftAssert softAssert = new SoftAssert();

        for (AdminEventNewsCategoryDto category : categories) {
            if (category.getLocalizations() == null) {
                continue;
            }
            for (EventNewsCategoryLocalizationDto localization : category.getLocalizations()) {
                softAssert.assertNotNull(localization.getLanguage(), "Language should not be null");
                if (localization.getLanguage() != null) {
                    softAssert.assertNotNull(localization.getLanguage().getCode(), "Language code should not be null");
                }
                softAssert.assertNotNull(localization.getName(), "Localized name should not be null");
            }
        }
        softAssert.assertAll();
    }

    @Test
    public void testGetCategoriesWithoutTokenIsUnauthorized() {
        publicClient.getCategoriesResponse().then().statusCode(401);
    }
}