package org.academy.api;

import org.academy.models.hippotherapy.ProgramCategoryLocalizationDto;
import org.academy.models.hippotherapy.ProgramCategoryShortDto;
import org.academy.models.hippotherapy.ProgramDto;
import org.academy.utils.testrunners.BaseAPITestRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HippotherapyProgramCategoryTests extends BaseAPITestRunner {

    private HippotherapyProgramCategoryClient publicClient;
    private HippotherapyProgramCategoryClient adminClient;

    @BeforeClass
    public void beforeClass() {
        publicClient = new HippotherapyProgramCategoryClient(testEnvProvider.getBaseApiUrl());
        adminClient = new HippotherapyProgramCategoryClient(testEnvProvider.getBaseApiUrl());
        adminClient.setToken(getAccessToken());
    }

    @Test
    public void testGetCategoriesAuthenticated() {
        adminClient.getCategoriesResponse().then().statusCode(200);

        List<ProgramCategoryShortDto> categories = adminClient.getCategories();
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(categories, "Categories list should not be null");
        softAssert.assertFalse(categories.isEmpty(), "Categories list should not be empty");

        Set<Long> ids = new HashSet<>();
        for (ProgramCategoryShortDto category : categories) {
            softAssert.assertNotNull(category.getId(), "Category id should not be null");
            softAssert.assertNotNull(category.getName(), "Category name should not be null");
            softAssert.assertNotNull(category.getCreatedAt(), "Category createdAt should not be null");
            softAssert.assertNotNull(category.getPrograms(), "Category programs should not be null");
            softAssert.assertNotNull(category.getLocalizations(), "Category localizations should not be null");
            ids.add(category.getId());
        }
        softAssert.assertEquals(ids.size(), categories.size(), "Category ids should be unique");
        softAssert.assertAll();
    }

    @Test
    public void testProgramsInsideCategories() {
        List<ProgramCategoryShortDto> categories = adminClient.getCategories();
        SoftAssert softAssert = new SoftAssert();

        for (ProgramCategoryShortDto category : categories) {
            if (category.getPrograms() == null) {
                continue;
            }
            for (ProgramDto program : category.getPrograms()) {
                softAssert.assertNotNull(program.getId(), "Program id should not be null");
                softAssert.assertNotNull(program.getName(), "Program name should not be null");
                softAssert.assertNotNull(program.getSlug(), "Program slug should not be null");
            }
        }
        softAssert.assertAll();
    }

    @Test
    public void testCategoryLocalizations() {
        List<ProgramCategoryShortDto> categories = adminClient.getCategories();
        SoftAssert softAssert = new SoftAssert();

        for (ProgramCategoryShortDto category : categories) {
            if (category.getLocalizations() == null) {
                continue;
            }
            for (ProgramCategoryLocalizationDto localization : category.getLocalizations()) {
                softAssert.assertNotNull(localization.getName(), "Localized name should not be null");
                softAssert.assertNotNull(localization.getLocalizationInfoDto(), "Language info should not be null");
                if (localization.getLocalizationInfoDto() != null) {
                    softAssert.assertNotNull(localization.getLocalizationInfoDto().getCode(),
                            "Language code should not be null");
                }
            }
        }
        softAssert.assertAll();
    }

    @Test
    public void testGetCategoriesWithoutTokenIsUnauthorized() {
        publicClient.getCategoriesResponse().then().statusCode(401);
    }
}