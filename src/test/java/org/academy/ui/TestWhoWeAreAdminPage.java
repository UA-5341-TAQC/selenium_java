package org.academy.ui;

import io.qameta.allure.Allure;
import io.qameta.allure.Issue;
import org.academy.ui.components.admin.whoweare.WhoWeAreDescriptionEditorComponent;
import org.academy.ui.pages.Admin.WhoWeArePageAdmin;
import org.academy.utils.testrunners.UITestRunnerWithAdmin;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.time.Duration;
import java.nio.file.Paths;

public class TestWhoWeAreAdminPage extends UITestRunnerWithAdmin {

    private static final String NINE_CHARACTERS = "Тест коня";
    private static final String TEN_CHARACTERS = "Тест коней";
    private static final String MIN_LENGTH_ERROR = "Не менше 10 символів";
    private static final String REQUIRED_ERROR = "Поле обов'язкове";
    private static final int MAX_CHARACTERS = 60;
    private static final int BEFORE_MAX_CHARACTERS = 59;
    private static final String MAX_COUNTER = "60/60";
    private static final String COUNTER_59 = "59/60";
    private static final String COUNTER_60 = "60/60";
    private static final String SIXTY_CHARACTERS = "А".repeat(MAX_CHARACTERS);
    private static final String TEST_TEXT_59 = "А".repeat(BEFORE_MAX_CHARACTERS);
    private static final String PASTE_TEXT = "Б".repeat(MAX_CHARACTERS + 1);
    private static final String EXPECTED_PASTED_TEXT = PASTE_TEXT.substring(0, MAX_CHARACTERS);
    private WhoWeArePageAdmin page;

    @BeforeMethod
    public void setUp() {
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);

        page = new WhoWeArePageAdmin(driver).open(testEnvProvider.getBaseUrl());
        page.selectTab("Люди");
        page.waitForPeopleCards(4);
    }

    @Issue("1000")
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

            if (firstCard < originalTexts.size() - 1) {
                reloadPage();
            }
        }
    }
    @Issue("999")
    @Test(description = "Verify description validation messages for every people card")
    public void testPeopleCardDescriptionValidationMessages() {
        Assert.assertEquals(page.getCardsCount(), 4, "People section must contain four cards");

        for (int cardIndex = 0; cardIndex < page.getCardsCount(); cardIndex++) {

            int currentCardIndex = cardIndex;
            Allure.step("Validate description messages on card " + (cardIndex + 1), () -> {
                WhoWeAreDescriptionEditorComponent editor = page.getEditor(currentCardIndex);

                editor.clear();
                editor.setText(NINE_CHARACTERS);

                assertNoValidationWhileFocused(editor);

                assertValidationAfterBlur(editor, MIN_LENGTH_ERROR);
                assertCardError(editor, MIN_LENGTH_ERROR, currentCardIndex);

                assertOtherCardsHaveNoErrors(currentCardIndex);

                editor.clear();
                editor.setText(TEN_CHARACTERS);

                assertValidationAfterBlur(editor, "");
                assertCardError(editor, "", currentCardIndex);

                editor.clear();

                assertNoValidationWhileFocused(editor);

                assertValidationAfterBlur(editor, REQUIRED_ERROR);
                assertCardError(editor, REQUIRED_ERROR, currentCardIndex);
            });

            if (cardIndex < page.getCardsCount() - 1) {
                reloadPage();
            }
        }
    }

    @Issue("998")
    @Test(description = "Verify that people card descriptions do not exceed 60 characters")
    public void testPeopleCardDescriptionMaxLength() {
        Assert.assertEquals(page.getCardsCount(), 4,
                "People section must contain four cards");

        List<String> originalTexts = page.getCards().stream()
                .map(WhoWeAreDescriptionEditorComponent::getDescriptionText)
                .toList();

        List<String> originalCounters = page.getCards().stream()
                .map(WhoWeAreDescriptionEditorComponent::getCounterText)
                .toList();

        Assert.assertEquals(originalTexts.size(), 4,
                "Every people card description must be captured");

        Assert.assertEquals(originalCounters.size(), 4,
                "Every people card counter must be captured");

        assertPublishState(false);

        for (int cardIndex = 0; cardIndex < 4; cardIndex++) {
            int currentCardIndex = cardIndex;

            Allure.step("Verify 60-character limit on card " + (cardIndex + 1), () -> {
                WhoWeAreDescriptionEditorComponent editor = page.getEditor(currentCardIndex);

                editor.clear();
                editor.typeText(SIXTY_CHARACTERS);

                Assert.assertEquals(editor.getDescriptionText(), SIXTY_CHARACTERS,
                        "The editor must contain exactly 60 characters");

                Assert.assertEquals(editor.getCounterText(), MAX_COUNTER,
                        "Counter must show 60/60");

                editor.typeText("Б");

                Assert.assertEquals(editor.getDescriptionText(), SIXTY_CHARACTERS,
                        "The 61st character must not appear in the editor");

                Assert.assertEquals(editor.getCounterText(), MAX_COUNTER,
                        "Counter must remain 60/60 after typing the 61st character");

                editor.blurEditor();

                Assert.assertEquals(editor.getErrorMessage(), "",
                        "No validation error must appear when the description contains 60 characters");

                editor.pasteText(PASTE_TEXT);

                Assert.assertEquals(editor.getDescriptionText(), EXPECTED_PASTED_TEXT,
                        "After pasting 61 characters, the editor must contain only 60 characters");

                Assert.assertEquals(editor.getCounterText(), MAX_COUNTER,
                        "Counter must not exceed 60/60 after paste");

                Assert.assertTrue(editor.getDescriptionText().length() <= MAX_CHARACTERS,
                        "Description must not exceed 60 characters after paste");

                editor.blurEditor();

                Assert.assertEquals(editor.getErrorMessage(), "",
                        "No validation error must appear after pasting 60 characters");

                assertPublishState(true);
            });

            Allure.step("Reload page and verify original description on card " + (cardIndex + 1), () -> {
                reloadPage();
                assertReloadedState(page, originalTexts, originalCounters);
            });
        }
    }

    @Issue("997")
    @Test(description = "Verify real-time validation and 60-character limit for every people card")
    public void testPeopleCardDescriptionRealTimeValidation() {
        Assert.assertEquals(page.getCardsCount(), 4,
                "People section must contain four cards");

        List<String> originalTexts = page.getCards().stream()
                .map(WhoWeAreDescriptionEditorComponent::getDescriptionText)
                .toList();

        List<String> originalCounters = page.getCards().stream()
                .map(WhoWeAreDescriptionEditorComponent::getCounterText)
                .toList();

        for (int cardIndex = 0; cardIndex < 4; cardIndex++) {
            int currentCardIndex = cardIndex;

            Allure.step("Verify real-time validation on card " + (cardIndex + 1), () -> {
                WhoWeAreDescriptionEditorComponent editor = page.getEditor(currentCardIndex);

                assertOtherCardsTextsUnchanged(currentCardIndex, originalTexts);

                editor.clear();

                Assert.assertEquals(editor.getDescriptionText(), "",
                        "Description must be empty after clearing");

                // Вводимо 59 символів по одному та перевіряємо лічильник після кожного
                for (int i = 0; i < TEST_TEXT_59.length(); i++) {
                    editor.typeText(String.valueOf(TEST_TEXT_59.charAt(i)));

                    String expectedCounter = (i + 1) + "/60";
                    editor.waitForCounter(expectedCounter);

                    Assert.assertEquals(editor.getCounterText(), expectedCounter,
                            "Counter must update after entering character " + (i + 1));
                }

                Assert.assertEquals(editor.getDescriptionText(), TEST_TEXT_59,
                        "Description must contain exactly 59 characters");

                Assert.assertEquals(editor.getCounterText(), COUNTER_59,
                        "Counter must show 59/60");

                Assert.assertEquals(editor.getErrorMessage(), "",
                        "No validation message must be displayed at 59 characters");

                editor.typeText("Б");
                editor.waitForCounter(COUNTER_60);

                Assert.assertEquals(editor.getDescriptionText(), TEST_TEXT_59 + "Б",
                        "Description must contain exactly 60 characters");

                Assert.assertEquals(editor.getCounterText(), COUNTER_60,
                        "Counter must show 60/60");

                Assert.assertEquals(editor.getErrorMessage(), "",
                        "No validation message must be displayed at 60 characters");

                assertOtherCardsTextsUnchanged(currentCardIndex, originalTexts);

                editor.selectAllAndMakeBold();

                Assert.assertEquals(editor.getDescriptionText(), TEST_TEXT_59 + "Б",
                        "Applying bold formatting must not change description text");

                Assert.assertEquals(editor.getCounterText(), COUNTER_60,
                        "Bold formatting must not change the character counter");

                Assert.assertEquals(editor.getErrorMessage(), "",
                        "No validation message must appear after applying bold formatting");

                assertOtherCardsTextsUnchanged(currentCardIndex, originalTexts);
            });

            Allure.step("Reload page and verify original descriptions after editing card "
                    + (cardIndex + 1), () -> {

                reloadPage();

                assertOriginalDescriptionsRestored(originalTexts, originalCounters);
            });
        }
    }

    @Issue("981")
    @Test(description = "Verify Publish button state after uploading valid and invalid People card images")
    public void testPeopleCardImageUploadAndPublishState() {

        String validImagePath = Paths.get(
                "src/test/resources/assets/size-360x430-exact.png"
        ).toAbsolutePath().toString();

        String invalidImagePath = Paths.get(
                "src/test/resources/assets/size-359x430-narrow.png"
        ).toAbsolutePath().toString();

        assertPublishState(false);

        for (int i = 0; i < 4; i++) {
            String previousSrc = page.getCard(i).getPreviewImageSrc();
            page.uploadImageToCard(i, validImagePath);
            page.getCard(i).waitForPreviewImageChanged(previousSrc);
        }

        assertPublishState(true);
        reloadPage();
        assertPublishState(false);

        String firstCardPreviousSrc = page.getCard(0).getPreviewImageSrc();
        String secondCardPreviousSrc = page.getCard(1).getPreviewImageSrc();

        page.uploadImageToCard(0, validImagePath);
        page.getCard(0).waitForPreviewImageChanged(firstCardPreviousSrc);

        page.uploadImageToCard(1, invalidImagePath);
        page.getCard(1).waitForErrorMessage(
                "Розмір зображення менший за рекомендований"
        );

        Assert.assertEquals(
                page.getCard(1).getPreviewImageSrc(),
                secondCardPreviousSrc,
                "Previous image must remain unchanged after invalid upload"
        );

        assertPublishState(false);
        reloadPage();
        assertPublishState(false);

        String thirdCardPreviousSrc = page.getCard(2).getPreviewImageSrc();
        String fourthCardPreviousSrc = page.getCard(3).getPreviewImageSrc();

        page.uploadImageToCard(2, invalidImagePath);
        page.getCard(2).waitForErrorMessage(
                "Розмір зображення менший за рекомендований"
        );

        page.uploadImageToCard(3, invalidImagePath);
        page.getCard(3).waitForErrorMessage(
                "Розмір зображення менший за рекомендований"
        );

        Assert.assertEquals(
                page.getCard(2).getPreviewImageSrc(),
                thirdCardPreviousSrc,
                "Third card image must remain unchanged after invalid upload"
        );

        Assert.assertEquals(
                page.getCard(3).getPreviewImageSrc(),
                fourthCardPreviousSrc,
                "Fourth card image must remain unchanged after invalid upload"
        );

        assertPublishState(false);
    }

    private void editText(WhoWeAreDescriptionEditorComponent editor, String text) {
        editor.clear().setText(text);
    }

    private void assertNoValidationWhileFocused(WhoWeAreDescriptionEditorComponent editor) {
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

    private void assertCardError(
            WhoWeAreDescriptionEditorComponent editor,
            String expectedError,
            int cardIndex) {

        String actualError = editor.getErrorMessage();

        Assert.assertEquals(
                actualError,
                expectedError,
                "Incorrect validation message under card " + (cardIndex + 1)
        );
    }

    private void assertOtherCardsHaveNoErrors(int currentCardIndex) {
        for (int i = 0; i < 4; i++) {
            if (i == currentCardIndex) {
                continue;
            }

            WhoWeAreDescriptionEditorComponent otherEditor = page.getEditor(i);

            Assert.assertFalse(
                    otherEditor.hasErrorMessage(),
                    "Validation error must not be shown under card " + (i + 1)
            );
        }
    }

    private void runCardValidation(WhoWeArePageAdmin page, int firstCard,
                                   List<String> originalTexts, List<String> originalCounters) {
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

    private void assertOtherCardsTextsUnchanged(
            int editedCardIndex,
            List<String> originalTexts) {

        for (int i = 0; i < 4; i++) {
            if (i == editedCardIndex) {
                continue;
            }

            String actualText = page.getEditor(i).getDescriptionText();

            Assert.assertEquals(actualText, originalTexts.get(i),
                    "Description of card " + (i + 1) + " must not change");
        }
    }

    private void assertOriginalDescriptionsRestored(
            List<String> originalTexts,
            List<String> originalCounters) {

        page.waitForPeopleCards(4);

        List<String> currentTexts = page.getCards().stream()
                .map(WhoWeAreDescriptionEditorComponent::getDescriptionText)
                .toList();

        List<String> currentCounters = page.getCards().stream()
                .map(WhoWeAreDescriptionEditorComponent::getCounterText)
                .toList();

        Assert.assertEquals(currentTexts, originalTexts,
                "Reload must restore all original descriptions");

        Assert.assertEquals(currentCounters, originalCounters,
                "Reload must restore all original counters");
    }
}
