package org.academy.ui;

import io.qameta.allure.Allure;
import org.academy.ui.pages.Admin.WhoWeAreAdminPage;
import org.academy.ui.testrunners.UITestRunnerWithAdmin;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TestWhoWeAreAdminPage extends UITestRunnerWithAdmin {

    private static final String NINE_CHARACTERS = "Тест коня";
    private static final String TEN_CHARACTERS = "Тест коней";
    private static final String MIN_LENGTH_ERROR = "Не менше 10 символів";
    private static final String REQUIRED_ERROR = "Поле обов'язкове";

    @Test(description = "Verify publish button state for every people card description")
    public void testPeopleCardDescriptionValidationAndPublishState() {
        WhoWeAreAdminPage page = new WhoWeAreAdminPage(driver).open(testEnvProvider.getBaseUrl());

        page.waitForLoaded();
        Assert.assertTrue(page.isPageDisplayed(), "Who We Are Admin page must be displayed");
        page.selectCategory("Люди");
        Assert.assertEquals(page.getCardsCount(), 4, "People section must contain four cards");
        Assert.assertFalse(page.isPublishButtonEnabled(), "Publish button must be disabled before edits");

        List<String> originalTexts = page.getCardDescriptionTexts();
        Map<String, String> originalCounters = new LinkedHashMap<>();
        for (String originalText : originalTexts) {
            originalCounters.put(originalText, page.getCardCharacterCounter(originalText));
        }

        for (int firstPosition = 0; firstPosition < originalTexts.size(); firstPosition++) {
            String firstText = originalTexts.get(firstPosition);
            String otherText = originalTexts.get((firstPosition + 1) % originalTexts.size());

            Allure.step("Validate card starting at " + firstText, () ->
                    runCardValidation(page, firstText, otherText, originalTexts, originalCounters));
        }
    }

    private void runCardValidation(WhoWeAreAdminPage page, String firstText, String otherText,
                                   List<String> originalTexts,
                                   Map<String, String> originalCounters) {
        Assert.assertFalse(page.isPublishButtonEnabled(), "Publish button must be disabled before the card edits");

        page.fillCardDescription(firstText, NINE_CHARACTERS);
        page.waitForValidationError(MIN_LENGTH_ERROR);
        Assert.assertTrue(page.hasValidationError(MIN_LENGTH_ERROR),
                "Nine-character description must show minimum-length error");
        Assert.assertFalse(page.isPublishButtonEnabled(),
                "Publish button must stay disabled with nine characters");

        page.fillCardDescription(NINE_CHARACTERS, TEN_CHARACTERS);
        Assert.assertTrue(page.isPublishButtonEnabled(),
                "Publish button must be enabled with ten valid characters");
        page.fillCardDescription(otherText, "");
        page.waitForValidationError(REQUIRED_ERROR);
        Assert.assertTrue(page.hasValidationError(REQUIRED_ERROR),
                "Cleared description must show the required-field error");
        Assert.assertFalse(page.isPublishButtonEnabled(),
                "Any required-field error must disable publishing");

        page.fillCardDescription("", TEN_CHARACTERS);
        Assert.assertTrue(page.isPublishButtonEnabled(),
                "Fixing the other description must enable publishing");

        page.refresh();
        assertReloadedState(page, originalTexts, originalCounters);

        page.fillCardDescriptionWithBoldSpace(firstText);
        page.waitForValidationError(REQUIRED_ERROR);
        Assert.assertTrue(page.hasValidationError(REQUIRED_ERROR),
                "A bold space must be treated as an empty field");
        Assert.assertFalse(page.isPublishButtonEnabled(), "Bold space must keep publishing disabled");

        page.refresh();
        assertReloadedState(page, originalTexts, originalCounters);

        String counterBefore = page.getCardCharacterCounter(firstText);
        page.addAndDeleteCardCharacter(firstText);
        Assert.assertEquals(page.getCardTextBox(firstText).getText().trim(), firstText,
                "Adding and deleting one character must restore the original description");
        Assert.assertEquals(page.getCardCharacterCounter(firstText), counterBefore,
                "Character counter must return to its original value");
        Assert.assertFalse(page.isPublishButtonEnabled(),
                "Returning text to its original value must disable publishing");

        page.refresh();
        assertReloadedState(page, originalTexts, originalCounters);
    }

    private void assertReloadedState(WhoWeAreAdminPage page,
                                     List<String> originalTexts,
                                     Map<String, String> originalCounters) {
        page.selectCategory("Люди");
        page.waitForPeopleCards(originalTexts.size());
        Assert.assertFalse(page.isPublishButtonEnabled(), "Reload must disable the publish button");

        List<String> currentTexts = page.getCardDescriptionTexts();
        List<String> currentCounters = page.getCardCharacterCounterTexts();
        Assert.assertEquals(currentTexts, originalTexts, "Reload must restore all original descriptions");
        Assert.assertEquals(currentCounters,
                originalTexts.stream().map(originalCounters::get).toList(),
                "Reload must restore all original character counters");
        }
    }
