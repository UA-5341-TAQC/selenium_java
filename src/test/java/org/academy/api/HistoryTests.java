package org.academy.api;

import org.academy.models.history.HistoryContent;
import org.academy.models.history.HistorySection;
import org.academy.models.history.HistoryLocalization;
import org.academy.models.history.HistoryLocalizationGroup;
import org.academy.utils.testrunners.BaseAPITestRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class HistoryTests extends BaseAPITestRunner {
    private HistoryClient historyClient;

    @BeforeClass
    public void beforeClass() {
        historyClient = new HistoryClient(testEnvProvider.getBaseApiUrl());
        historyClient.setToken(getAccessToken());
    }

    @Test
    public void testGetHistory() {
        List<HistorySection> sections = historyClient.getHistory();
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(sections, "History response should not be null");
        softAssert.assertFalse(sections.isEmpty(), "History response should not be empty");

        for (HistorySection section : sections) {
            softAssert.assertTrue(section.getId() > 0, "Section id should be positive");
            softAssert.assertTrue(section.getTemplate() > 0, "Section template should be positive");
            softAssert.assertTrue(section.getOrder() >= 0, "Section order should not be negative");
            softAssert.assertNotNull(section.getContents(), "Section contents should not be null");
            softAssert.assertFalse(section.getContents().isEmpty(), "Section contents should not be empty");

            for (HistoryContent content : section.getContents()) {
                softAssert.assertTrue(content.getId() > 0, "Content id should be positive");
                softAssert.assertTrue(content.getOrder() >= 0, "Content order should not be negative");
                softAssert.assertNotNull(content.getLocalizations(),
                        "Content localizations should not be null");

                for (HistoryLocalization localization : content.getLocalizations()) {
                    softAssert.assertTrue(localization.getEntityId() > 0,
                            "Localization entity id should be positive");
                    softAssert.assertNotNull(localization.getLocalizationInfoDto(),
                            "Localization info should not be null");
                    softAssert.assertNotNull(localization.getLocalizationInfoDto().getCode(),
                            "Localization language code should not be null");
                }
            }
        }

        boolean hasTextContent = sections.stream()
                .flatMap(section -> section.getContents().stream())
                .anyMatch(content -> content.getTitle() != null || content.getDescription() != null);
        softAssert.assertTrue(hasTextContent, "History should contain text content");

        softAssert.assertAll();
    }

    @Test
    public void testGetHistoryLocalizationsByEntityId() {
        long entityId = 210;
        List<HistoryLocalizationGroup> groups =
                historyClient.getLocalizationsByEntityId(entityId);
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(groups, "Localization response should not be null");
        softAssert.assertFalse(groups.isEmpty(), "Localization response should not be empty");

        for (HistoryLocalizationGroup group : groups) {
            softAssert.assertEquals(group.getEntityId(), entityId,
                    "Localization entity id should match the requested entity id");
            softAssert.assertNotNull(group.getContents(), "Localization contents should not be null");
            if (group.getContents() != null) {
                softAssert.assertFalse(group.getContents().isEmpty(),
                        "Localization contents should not be empty");
                for (HistoryLocalization localization : group.getContents()) {
                    assertLocalization(localization, softAssert);
                }
            }
        }

        softAssert.assertAll();
    }

    @Test
    public void testGetHistoryLocalizationsByLanguageId() {
        long languageId = 2;
        List<HistoryLocalizationGroup> groups =
                historyClient.getLocalizationsByLanguageId(languageId);
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(groups, "Localization response should not be null");
        softAssert.assertFalse(groups.isEmpty(), "Localization response should not be empty");

        for (HistoryLocalizationGroup group : groups) {
            softAssert.assertTrue(group.getEntityId() > 0, "Entity id should be positive");
            softAssert.assertNotNull(group.getContents(), "Localization contents should not be null");
            if (group.getContents() != null) {
                softAssert.assertFalse(group.getContents().isEmpty(),
                        "Localization contents should not be empty");
                for (HistoryLocalization localization : group.getContents()) {
                    assertLocalization(localization, softAssert);
                    softAssert.assertEquals(localization.getLanguageId(), languageId,
                            "Localization language id should match the requested language id");
                }
            }
        }

        softAssert.assertAll();
    }

    private void assertLocalization(HistoryLocalization localization, SoftAssert softAssert) {
        softAssert.assertTrue(localization.getEntityId() > 0, "Content entity id should be positive");
        softAssert.assertTrue(localization.getLanguageId() > 0, "Language id should be positive");
        softAssert.assertNotNull(localization.getLocalizationInfoDto(),
                "Localization info should not be null");
        softAssert.assertNotNull(localization.getLocalizationInfoDto().getCode(),
                "Localization language code should not be null");
        softAssert.assertTrue(localization.getTranslationStatus() >= 0,
                "Translation status should not be negative");
    }
}
