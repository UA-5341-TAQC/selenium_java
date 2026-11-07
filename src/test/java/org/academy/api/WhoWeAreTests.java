package org.academy.api;

import org.academy.models.whoweare.WhoWeAreContent;
import org.academy.models.whoweare.WhoWeArePreview;
import org.academy.models.whoweare.WhoWeAreSection;
import org.academy.models.whoweare.WhoWeAreTranslationStatus;
import org.academy.utils.testrunners.BaseAPITestRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class WhoWeAreTests extends BaseAPITestRunner {
    private WhoWeAreClient adminClient;

    @BeforeClass
    public void beforeClass() {
        adminClient = new WhoWeAreClient(testEnvProvider.getBaseApiUrl());
        adminClient.setToken(getAccessToken());
    }

    @Test
    public void testGetWhoWeAreSection() {
        int sectionType = 1;
        WhoWeAreSection section = adminClient.getSection(sectionType);
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(section, "Who We Are section should not be null");
        softAssert.assertEquals(section.getSectionType(), Integer.valueOf(sectionType),
                "Section type should match the requested section type");
        softAssert.assertNotNull(section.getTitle(), "Section title should not be null");
        softAssert.assertNotNull(section.getContents(), "Section contents should not be null");
        if (section.getContents() != null) {
            softAssert.assertFalse(section.getContents().isEmpty(),
                    "Section contents should not be empty");

            for (WhoWeAreContent content : section.getContents()) {
                softAssert.assertNotNull(content.getType(), "Content type should not be null");
                softAssert.assertTrue(content.getId() != null && content.getId() > 0,
                        "Content id should be positive");
                softAssert.assertTrue(content.getContentType() != null && content.getContentType() >= 0,
                        "Content type id should not be negative");
                softAssert.assertNotNull(content.getLocalizations(),
                        "Content localizations should not be null");
            }
        }

        softAssert.assertAll();
    }

    @Test
    public void testGetWhoWeArePreviewsAuthenticated() {
        List<WhoWeArePreview> previews = adminClient.getPreviews();
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(previews, "Who We Are previews should not be null");
        softAssert.assertFalse(previews.isEmpty(), "Who We Are previews should not be empty");
        softAssert.assertEquals(previews.size(), 5, "Who We Are previews size mismatch");

        for (WhoWeArePreview preview : previews) {
            softAssert.assertTrue(preview.getId() != null && preview.getId() > 0,
                    "Preview id should be positive");
            softAssert.assertNotNull(preview.getSectionType(), "Preview section type should not be null");
            softAssert.assertNotNull(preview.getTitle(), "Preview title should not be null");
            softAssert.assertNotNull(preview.getTranslationStatuses(),
                    "Preview translation statuses should not be null");

            for (WhoWeAreTranslationStatus status : preview.getTranslationStatuses()) {
                softAssert.assertTrue(status.getLanguageId() != null && status.getLanguageId() > 0,
                        "Language id should be positive");
                softAssert.assertTrue(status.getTranslationStatus() != null
                                && status.getTranslationStatus() >= 0,
                        "Translation status should not be negative");
            }
        }

        softAssert.assertAll();
    }

    @Test
    public void testGetWhoWeArePage() {
        List<WhoWeAreSection> sections = adminClient.getPage();
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(sections, "Who We Are page should not be null");
        softAssert.assertFalse(sections.isEmpty(), "Who We Are page should not be empty");
        softAssert.assertEquals(sections.size(), 5, "Who We Are page sections size mismatch");

        for (WhoWeAreSection section : sections) {
            softAssert.assertTrue(section.getSectionType() != null
                            && section.getSectionType() >= 0
                            && section.getSectionType() <= 4,
                    "Section type should be between 0 and 4");
            softAssert.assertNotNull(section.getContents(), "Section contents should not be null");
            softAssert.assertFalse(section.getContents().isEmpty(),
                    "Section contents should not be empty");

            for (WhoWeAreContent content : section.getContents()) {
                softAssert.assertTrue(content.getId() != null && content.getId() > 0,
                        "Content id should be positive");
                softAssert.assertNotNull(content.getType(), "Content type should not be null");
                softAssert.assertNotNull(content.getLocalizations(),
                        "Content localizations should not be null");
            }
        }

        softAssert.assertAll();
    }
}
