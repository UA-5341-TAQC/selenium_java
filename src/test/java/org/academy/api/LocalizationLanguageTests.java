package org.academy.api;

import org.academy.models.localization.LocalizationLanguageDto;
import org.academy.utils.testrunners.BaseAPITestRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LocalizationLanguageTests extends BaseAPITestRunner {

    private LocalizationLanguageClient client;

    @BeforeClass
    public void beforeClass() {
        client = new LocalizationLanguageClient(testEnvProvider.getBaseApiUrl());
    }

    @Test
    public void testGetLanguages() {
        client.getLanguagesResponse().then().statusCode(200);

        List<LocalizationLanguageDto> languages = client.getLanguages();
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(languages, "Languages list should not be null");
        softAssert.assertFalse(languages.isEmpty(), "Languages list should not be empty");
        for (LocalizationLanguageDto language : languages) {
            softAssert.assertNotNull(language.getId(), "Id should not be null");
            softAssert.assertNotNull(language.getCode(), "Code should not be null");
            softAssert.assertNotNull(language.getName(), "Name should not be null");
        }
        softAssert.assertAll();
    }

    @Test
    public void testLanguageIdsAndCodesAreUnique() {
        List<LocalizationLanguageDto> languages = client.getLanguages();
        Set<Integer> ids = new HashSet<>();
        Set<String> codes = new HashSet<>();
        languages.forEach(language -> {
            ids.add(language.getId());
            codes.add(language.getCode());
        });

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(ids.size(), languages.size(), "Language ids should be unique");
        softAssert.assertEquals(codes.size(), languages.size(), "Language codes should be unique");
        softAssert.assertAll();
    }

    @Test
    public void testContainsUkrainianAndEnglish() {
        Set<String> codes = new HashSet<>();
        client.getLanguages().forEach(language -> codes.add(language.getCode()));

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(codes.contains("uk"), "Languages should contain 'uk', but were " + codes);
        softAssert.assertTrue(codes.contains("en"), "Languages should contain 'en', but were " + codes);
        softAssert.assertAll();
    }
}