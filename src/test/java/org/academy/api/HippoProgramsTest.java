package org.academy.api;
import org.academy.models.HippotherapyPrograms.*;
import org.academy.utils.testrunners.BaseAPITestRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class HippoProgramsTest extends BaseAPITestRunner{
    private HippotherapyProgramsClient hippoProgramsClient;

    @BeforeClass
    public void beforeClass() {
        hippoProgramsClient = new HippotherapyProgramsClient(testEnvProvider.getBaseApiUrl());
        hippoProgramsClient.setToken(getAccessToken());
    }

    @Test
    public void testGetPublishedPrograms(){
        List<PublishedHippotherapyProgram> publishedPrograms = hippoProgramsClient.getPublishedHippoProgram();
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(publishedPrograms, "Published Programs response should not be null");
        softAssert.assertFalse(publishedPrograms.isEmpty(), "Published Programs list should not be empty");

        for (PublishedHippotherapyProgram publishedProgram : publishedPrograms) {
            // --- Basic Program Fields ---
            softAssert.assertTrue(publishedProgram.getId() > 0,
                    "Program ID should be greater than 0");

            softAssert.assertNotNull(publishedProgram.getName(),
                    "Program Name should not be null");
            softAssert.assertFalse(publishedProgram.getName().trim().isEmpty(),
                    "Program Name should not be empty");

            softAssert.assertNotNull(publishedProgram.getSlug(),
                    "Program Slug should not be null");
            softAssert.assertFalse(publishedProgram.getSlug().trim().isEmpty(),
                    "Program Slug should not be empty");

            softAssert.assertNotNull(publishedProgram.getDescription(),
                    "Program Description should not be null");
            softAssert.assertFalse(publishedProgram.getDescription().trim().isEmpty(),
                    "Program Description should not be empty");

            softAssert.assertNotNull(publishedProgram.getCreatedAt(),
                    "Program CreatedAt should not be null");

            // --- Preview Image ---
            softAssert.assertNotNull(publishedProgram.getPreviewImage(),
                    "PreviewImage object should not be null for program ID: " + publishedProgram.getId());

            PreviewImageDto previewImage = publishedProgram.getPreviewImage();
            softAssert.assertTrue(previewImage.getId() > 0,
                    "PreviewImage ID should be greater than 0");
            softAssert.assertNotNull(previewImage.getUrl(),
                    "PreviewImage URL should not be null");
            softAssert.assertFalse(previewImage.getUrl().trim().isEmpty(),
                    "PreviewImage URL should not be empty");

            // --- Categories ---
            softAssert.assertNotNull(publishedProgram.getCategories(),
                    "Categories list should not be null for program ID: " + publishedProgram.getId());

            softAssert.assertFalse(publishedProgram.getCategories().isEmpty(),
                    "Categories list should not be empty for program ID: " + publishedProgram.getId());

            for (CategoryDto category : publishedProgram.getCategories()) {
                softAssert.assertTrue(category.getId() > 0,
                        "Category ID should be greater than 0");
                softAssert.assertNotNull(category.getName(),
                        "Category Name should not be null");
                softAssert.assertFalse(category.getName().trim().isEmpty(),
                        "Category Name should not be empty");

                if (category.getLocalizations() != null) {
                    for (CategoryLocalizationDto catLoc : category.getLocalizations()) {
                        softAssert.assertTrue(catLoc.getEntityId() > 0,
                                "Category localization entityId should be greater than 0");
                        softAssert.assertNotNull(catLoc.getLocalizationInfoDto(),
                                "Category localizationInfoDto should not be null");
                    }
                }
            }

            // --- Program Localizations ---
            softAssert.assertNotNull(publishedProgram.getLocalizations(),
                    "Localizations list should not be null for program ID: " + publishedProgram.getId());

            //softAssert.assertFalse(publishedProgram.getLocalizations().isEmpty(),
            // "Localizations list should not be empty for program ID: " + publishedProgram.getId());

            for (LocalizationDto localization : publishedProgram.getLocalizations()) {
                softAssert.assertTrue(localization.getEntityId() > 0,
                        "Localization EntityId should be greater than 0");

                softAssert.assertNotNull(localization.getName(),
                        "Localization Name should not be null");
                softAssert.assertFalse(localization.getName().trim().isEmpty(),
                        "Localization Name should not be empty");

                softAssert.assertNotNull(localization.getDescription(),
                        "Localization Description should not be null");

                // Check localizationInfoDto
                softAssert.assertNotNull(localization.getLocalizationInfoDto(),
                        "LocalizationInfoDto should not be null for localization entity ID: " + localization.getEntityId());

                softAssert.assertTrue(localization.getLocalizationInfoDto().getId() > 0,
                        "LocalizationInfo ID should be greater than 0");
                softAssert.assertNotNull(localization.getLocalizationInfoDto().getCode(),
                        "LocalizationInfo Code should not be null");
            }
        }

        softAssert.assertAll();
    }

    @Test
    public void testGetProgramByID(){
        Long programId = 216L;
        HippotherapyProgramDto programById = hippoProgramsClient.getHippoProgramId(programId);
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(programById, "Published Programs response should not be null");


        softAssert.assertTrue(programById.getId() > 0,
                "Program ID should be greater than 0");
        softAssert.assertNotNull(programById.getName(),
                "Program Name should not be null");
        softAssert.assertFalse(programById.getName().trim().isEmpty(),
                "Program Name should not be empty");

        softAssert.assertNotNull(programById.getSlug(),
                "Program Slug should not be null");
        softAssert.assertFalse(programById.getSlug().trim().isEmpty(),
                "Program Slug should not be empty");

        softAssert.assertNotNull(programById.getDescription(),
                "Program Description should not be null");
        softAssert.assertFalse(programById.getDescription().trim().isEmpty(),
                "Program Description should not be empty");

        softAssert.assertNotNull(programById.getStatus(),
                "Program Status should not be null");
        softAssert.assertNotNull(programById.getLocation(),
                "Program Location should not be null");
        softAssert.assertNotNull(programById.getParticipantsCount(),
                "Program ParticipantsCount should not be null");
        softAssert.assertNotNull(programById.getMeetingsCount(),
                "Program MeetingsCount should not be null");


        // Categories
        softAssert.assertNotNull(programById.getCategories(), "Categories list should not be null");
        softAssert.assertFalse(programById.getCategories().isEmpty(), "Categories list should not be empty");

        for (CategoryDto category : programById.getCategories()) {
            softAssert.assertTrue(category.getId() > 0, "Category ID should be greater than 0");
            softAssert.assertNotNull(category.getName(), "Category Name should not be null");

            if (category.getLocalizations() != null) {
                for (CategoryLocalizationDto catLoc : category.getLocalizations()) {
                    softAssert.assertTrue(catLoc.getEntityId() > 0, "Category localization entityId > 0");
                    softAssert.assertNotNull(catLoc.getLocalizationInfoDto(), "Category localizationInfoDto should not be null");
                }
            }
        }

        // Sections & Contents
        softAssert.assertNotNull(programById.getSections(), "Sections list should not be null");
        softAssert.assertFalse(programById.getSections().isEmpty(), "Sections list should not be empty");
        for (SectionDto section : programById.getSections()) {
            softAssert.assertTrue(section.getId() > 0, "Section ID should be greater than 0");
            softAssert.assertNotNull(section.getTemplate(), "Section Template should not be null");
            softAssert.assertNotNull(section.getOrder(), "Section Order should not be null");

            if (section.getContents() != null) {
                for (SectionContentDto content : section.getContents()) {
                    softAssert.assertTrue(content.getId() > 0, "Content ID should be greater than 0");
                    softAssert.assertNotNull(content.getContentType(), "Content Type should not be null");
                    softAssert.assertNotNull(content.getOrder(), "Content Order should not be null");

                    // FAQ Question (if present)
                    if (content.getFaqQuestion() != null) {
                        softAssert.assertTrue(content.getFaqQuestion().getId() > 0, "FAQ Question ID > 0");
                        softAssert.assertNotNull(content.getFaqQuestion().getQuestionText(), "FAQ QuestionText should not be null");
                    }

                    // Content Localizations
                    if (content.getLocalizations() != null) {
                        for (ContentLocalizationDto contentLoc : content.getLocalizations()) {
                            softAssert.assertTrue(contentLoc.getEntityId() > 0, "Content localization entityId > 0");
                            softAssert.assertNotNull(contentLoc.getLocalizationInfoDto(), "Content localizationInfoDto should not be null");
                        }
                    }
                }
            }
        }

        softAssert.assertNotNull(programById.getLocalizations(), "Program Localizations list should not be null");

        softAssert.assertAll();
    }


}
