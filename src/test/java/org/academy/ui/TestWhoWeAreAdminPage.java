package org.academy.ui;

import io.qameta.allure.Allure;
import org.academy.ui.components.admin.whoweare.WhoWeAreDescriptionEditorComponent;
import org.academy.ui.pages.Admin.WhoWeArePageAdmin;
import org.academy.ui.testrunners.UITestRunnerWithAdmin;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class TestWhoWeAreAdminPage extends UITestRunnerWithAdmin {

    private static final String NINE_CHARACTERS = "Тест коня";
    private static final String TEN_CHARACTERS = "Тест коней";
    private static final String MIN_LENGTH_ERROR = "Не менше 10 символів";
    private static final String REQUIRED_ERROR = "Поле обов'язкове";
    private WhoWeArePageAdmin page;

    @BeforeMethod
    public void setUp() {
        page = new WhoWeArePageAdmin(driver).open(testEnvProvider.getBaseUrl());
        page.selectTab("Люди");
        page.waitForPeopleCards(4);
    }

    @Test(description = "Verify publish button state for every people card description")
    public void testPeopleCardDescriptionValidationAndPublishState() {
        Assert.assertEquals(page.getCardsCount(), 4, "People section must contain four cards");
        assertPublishState(false);

        List<String> originalTexts = page.getCards().stream()
                .map(WhoWeAreDescriptionEditorComponent::getDescriptionText)
                .toList();
        List<String> originalCounters = page.getCards().stream()
                .map(WhoWeAreDescriptionEditorComponent::getCounterText)
                .toList();

        Assert.assertEquals(originalTexts.size(), 4, "Every people card description must be captured");
        Assert.assertEquals(originalCounters.size(), 4, "Every people card counter must be captured");

        for (int firstCard = 0; firstCard < originalTexts.size(); firstCard++) {
            int finalFirstCard = firstCard;
            Allure.step("Validate publish state starting from card " + (firstCard + 1), () ->
                    runCardValidation(page, finalFirstCard, originalTexts, originalCounters));
        }
    }

    @Test(description = "Verify description validation messages for every people card")
    public void testPeopleCardDescriptionValidationMessages() {
        Assert.assertEquals(page.getCardsCount(), 4, "People section must contain four cards");

        for (int cardIndex = 0; cardIndex < page.getCardsCount(); cardIndex++) {
            if (cardIndex > 0) {
                reloadPage();
            }

            int currentCardIndex = cardIndex;
            Allure.step("Validate description messages on card " + (cardIndex + 1), () -> {
                WhoWeAreDescriptionEditorComponent editor = page.getEditor(currentCardIndex);

                editText(editor, NINE_CHARACTERS);
                assertNoValidationWhileFocused(editor);
                assertValidationAfterBlur(editor, MIN_LENGTH_ERROR);
                assertErrorIsShownOnlyForCard(currentCardIndex, MIN_LENGTH_ERROR);

                editText(editor, TEN_CHARACTERS);
                assertValidationAfterBlur(editor, "");
                assertErrorIsShownOnlyForCard(currentCardIndex, "");

                editor.clear();
                assertNoValidationWhileFocused(editor);
                assertValidationAfterBlur(editor, REQUIRED_ERROR);
                assertErrorIsShownOnlyForCard(currentCardIndex, REQUIRED_ERROR);

                editText(editor, "            ");
                assertValidationAfterBlur(editor, REQUIRED_ERROR);
                assertErrorIsShownOnlyForCard(currentCardIndex, REQUIRED_ERROR);
            });

            reloadPage();
        }
    }

    private void editText(WhoWeAreDescriptionEditorComponent editor, String text) {
        editor.clear().setText(text);
    }

    private void assertNoValidationWhileFocused(WhoWeAreDescriptionEditorComponent editor) {
        editor.waitForErrorMessageToDisappear();
        Assert.assertFalse(editor.hasErrorMessage(),
                "Validation message must not be shown while the field has focus");
    }

    private void assertValidationAfterBlur(WhoWeAreDescriptionEditorComponent editor, String expectedError) {
        editor.blurEditor();
        if (expectedError.isEmpty()) {
            editor.waitForErrorMessageToDisappear();
            Assert.assertFalse(editor.hasErrorMessage(),
                    "Valid description must not show a validation message");
        } else {
            editor.waitForErrorMessage(expectedError);
            Assert.assertTrue(editor.getErrorMessage().contains(expectedError),
                    "Expected validation message '" + expectedError + "', but got: " + editor.getErrorMessage());
        }
    }

    private void reloadPage() {
        page.refresh();
        page = new WhoWeArePageAdmin(driver);
        page.selectTab("Люди");
        page.waitForPeopleCards(4);
    }

    private void assertPublishState(boolean enabled) {
        page.waitForPublishButtonEnabled(enabled);
        Assert.assertEquals(page.isPublishButtonEnabled(), enabled,
                enabled ? "Publish button must be enabled" : "Publish button must be disabled");
    }

    private void assertErrorIsShownOnlyForCard(int cardIndex, String expectedError) {
        for (int index = 0; index < page.getCardsCount(); index++) {
            WhoWeAreDescriptionEditorComponent card = page.getEditor(index);
            if (index == cardIndex) {
                if (!expectedError.isEmpty()) {
                    Assert.assertTrue(card.getErrorMessage().contains(expectedError),
                            "Validation error must be shown under card " + (cardIndex + 1));
                } else {
                    Assert.assertFalse(card.hasErrorMessage(),
                            "Edited card must not have a validation error");
                }
            } else {
                Assert.assertFalse(card.hasErrorMessage(),
                        "Validation error must not be shown under card " + (index + 1));
            }
        }
    }

    private void runCardValidation(WhoWeArePageAdmin page, int firstCard,
                                   List<String> originalTexts, List<String> originalCounters) {
        reloadPage();
        page = this.page;
        assertPublishState(false);

        WhoWeAreDescriptionEditorComponent firstEditor = page.getEditor(firstCard);
        editText(firstEditor, NINE_CHARACTERS);
        assertValidationAfterBlur(firstEditor, MIN_LENGTH_ERROR);
        assertPublishState(false);

        editText(firstEditor, TEN_CHARACTERS);
        assertValidationAfterBlur(firstEditor, "");
        assertPublishState(true);

        int otherCard = (firstCard + 1) % 4;
        WhoWeAreDescriptionEditorComponent otherEditor = page.getEditor(otherCard);
        editText(otherEditor, "");
        assertValidationAfterBlur(otherEditor, REQUIRED_ERROR);
        assertPublishState(false);

        editText(otherEditor, TEN_CHARACTERS);
        assertValidationAfterBlur(otherEditor, "");
        assertPublishState(true);

        reloadPage();
        page = this.page;
        assertReloadedState(page, originalTexts, originalCounters);

        page.getEditor(firstCard).enterBoldSpace();
        WhoWeAreDescriptionEditorComponent boldEditor = page.getEditor(firstCard);
        boldEditor.waitForErrorMessage(REQUIRED_ERROR);
        Assert.assertTrue(boldEditor.getErrorMessage().contains(REQUIRED_ERROR),
                "A bold space must be treated as an empty description");
        assertPublishState(false);

        reloadPage();
        page = this.page;
        assertReloadedState(page, originalTexts, originalCounters);

        String originalText = originalTexts.get(firstCard);
        String counterBefore = originalCounters.get(firstCard);
        WhoWeAreDescriptionEditorComponent restoreEditor = page.getEditor(firstCard);
        restoreEditor.addAndDeleteCharacter("X");

        Assert.assertEquals(restoreEditor.getDescriptionText(), originalText,
                "Adding and deleting a character must restore the original description");
        Assert.assertEquals(restoreEditor.getCounterText(), counterBefore,
                "Adding and deleting a character must restore the counter");
        assertPublishState(false);

        reloadPage();
        page = this.page;
        assertReloadedState(page, originalTexts, originalCounters);
    }

    private void assertReloadedState(WhoWeArePageAdmin page,
                                     List<String> originalTexts,
                                     List<String> originalCounters) {
        page.waitForPeopleCards(4);
        assertPublishState(false);

        List<String> currentTexts = page.getCards().stream()
                .map(WhoWeAreDescriptionEditorComponent::getDescriptionText)
                .toList();
        List<String> currentCounters = page.getCards().stream()
                .map(WhoWeAreDescriptionEditorComponent::getCounterText)
                .toList();

        Assert.assertEquals(currentTexts, originalTexts, "Reload must restore all original descriptions");
        Assert.assertEquals(currentCounters, originalCounters, "Reload must restore all character counters");
    }
}
