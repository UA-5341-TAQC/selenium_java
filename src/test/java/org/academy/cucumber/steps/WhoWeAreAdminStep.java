package org.academy.cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.academy.cucumber.hooks.CucumberHook;
import org.academy.ui.components.admin.whoweare.WhoWeAreCardComponent;
import org.academy.ui.components.admin.whoweare.WhoWeAreDescriptionEditorComponent;
import org.academy.ui.modals.admin.ConfirmationModal;
import org.academy.ui.pages.Admin.WhoWeArePageAdmin;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.List;

public class WhoWeAreAdminStep {

    private static final String PUBLISH_MODAL_TITLE = "Опублікувати зміни?";

    private final CucumberHook cucumberHook;
    private WhoWeArePageAdmin whoWeArePageAdmin;
    private ConfirmationModal confirmationModal;

    public WhoWeAreAdminStep(CucumberHook cucumberHook) {
        this.cucumberHook = cucumberHook;
    }

    private int toZeroBasedIndex(int cardNumber) {
        if (cardNumber < 1) {
            throw new IllegalArgumentException("Card number must be >= 1, but got: " + cardNumber);
        }
        return cardNumber - 1;
    }

    @Given("the admin navigates to the \"Хто ми\" page")
    public void navigateToWhoWeArePage() {
        whoWeArePageAdmin = new WhoWeArePageAdmin(cucumberHook.getDriver());
        whoWeArePageAdmin.open(cucumberHook.getTestValueProvider().getBaseUrl());
    }

    @When("the admin selects the {string} tab")
    public void selectTab(String tabName) {
        whoWeArePageAdmin.selectTab(tabName);
    }

    @Then("the \"Опублікувати\" button is disabled")
    public void verifyPublishButtonIsDisabled() {
        Assert.assertFalse(whoWeArePageAdmin.isPublishButtonEnabled(),
                "Expected 'Опублікувати' button to be disabled, but it was enabled");
    }

    @Then("the \"Опублікувати\" button is enabled")
    public void verifyPublishButtonIsEnabled() {
        Assert.assertTrue(whoWeArePageAdmin.isPublishButtonEnabled(),
                "Expected 'Опублікувати' button to be enabled, but it was disabled");
    }

    @When("the admin clicks the \"Опублікувати\" button")
    public void clickPublishButton() {
        confirmationModal = whoWeArePageAdmin.clickPublishButton();
    }

    @Then("the title {string} is displayed above the cards")
    public void verifyCardsSectionTitle(String expectedTitle) {
        Assert.assertTrue(whoWeArePageAdmin.isCardsSectionTitleDisplayed(),
                "Cards section title should be displayed");
        Assert.assertTrue(whoWeArePageAdmin.getCardsSectionTitle().equalsIgnoreCase(expectedTitle),
                "Incorrect cards section title. Expected: " + expectedTitle + ", actual: " + whoWeArePageAdmin.getCardsSectionTitle());
    }

    @Then("exactly {int} cards are displayed")
    public void verifyCardsCount(int expectedCount) {
        Assert.assertEquals(whoWeArePageAdmin.getCardsCount(), expectedCount,
                "Cards count does not match expected value");
    }

    @Then("each card displays an image")
    public void verifyEachCardDisplaysImage() {
        List<WhoWeAreCardComponent> cards = whoWeArePageAdmin.getCards();
        for (int i = 0; i < cards.size(); i++) {
            Assert.assertTrue(cards.get(i).isImageDisplayed(),
                    "Image should be displayed and loaded in card " + (i + 1));
        }
    }

    @Then("each card displays an \"Опис\" field with a character counter up to {int}")
    public void verifyEachCardDisplaysDescriptionWithCounter(int maxLimit) {
        List<WhoWeAreCardComponent> cards = whoWeArePageAdmin.getCards();
        for (int i = 0; i < cards.size(); i++) {
            WhoWeAreCardComponent card = cards.get(i);
            Assert.assertTrue(card.isEditable(),
                    "Description should be editable in card " + (i + 1));
            Assert.assertFalse(card.getDescriptionText().isBlank(),
                    "Pre-uploaded description text should not be blank in card " + (i + 1));
            String counterText = card.getCounterText();
            Assert.assertTrue(counterText.contains("/" + maxLimit) || counterText.contains(String.valueOf(maxLimit)),
                    "Counter text in card " + (i + 1) + " should mention " + maxLimit + ", but got: " + counterText);
        }
    }

    @Then("a pre-uploaded image is displayed in the \"Команда\" block")
    public void verifyPreUploadedImageDisplayed() {
        Assert.assertTrue(whoWeArePageAdmin.isPreUploadedImageDisplayed(),
                "Pre-uploaded image should be present in 'Команда' block");
    }

    @When("the admin refreshes the page")
    public void refreshPage() {
        cucumberHook.getDriver().navigate().refresh();
        whoWeArePageAdmin = new WhoWeArePageAdmin(cucumberHook.getDriver());
    }

    @Then("the \"Опис\" field is editable")
    public void verifyDescriptionFieldIsEditable() {
        Assert.assertTrue(whoWeArePageAdmin.getPrimaryEditor().isEditable(),
                "'Опис' field should be editable");
    }

    @Then("the \"Опис\" field contains {string}")
    public void verifyDescriptionContains(String expectedText) {
        Assert.assertEquals(whoWeArePageAdmin.getPrimaryEditor().getDescriptionText(), expectedText,
                "'Опис' field text does not match expected value");
    }

    @Then("each card has an editable \"Опис\" field and a character counter")
    public void verifyEachCardHasEditableDescriptionAndCounter() {
        List<WhoWeAreCardComponent> cards = whoWeArePageAdmin.getCards();
        for (int i = 0; i < cards.size(); i++) {
            WhoWeAreCardComponent card = cards.get(i);
            Assert.assertTrue(card.isEditable(),
                    "Card " + (i + 1) + " description should be editable");
            Assert.assertFalse(card.getCounterText().isBlank(),
                    "Card " + (i + 1) + " should display a character counter");
        }
    }

    @When("the admin clears the \"Опис\" field of card {int}")
    public void clearDescriptionOfCard(int cardNumber) {
        whoWeArePageAdmin.getEditor(toZeroBasedIndex(cardNumber)).clear();
    }

    @When("the admin types {string} into the \"Опис\" field of card {int}")
    public void typeTextIntoDescriptionOfCard(String text, int cardNumber) {
        whoWeArePageAdmin.getEditor(toZeroBasedIndex(cardNumber)).typeText(text);
    }

    @When("the admin deletes {int} characters from the \"Опис\" field of card {int}")
    public void deleteCharactersFromCardDescription(int charCount, int cardNumber) {
        whoWeArePageAdmin.getEditor(toZeroBasedIndex(cardNumber)).deleteCharacters(charCount);
    }

    @Then("the character counter for card {int} matches its current text length out of {int}")
    public void verifyCharacterCounterMatchesCurrentLength(int cardNumber, int maxLimit) {
        WhoWeAreDescriptionEditorComponent editor = whoWeArePageAdmin.getEditor(toZeroBasedIndex(cardNumber));
        int actualLength = editor.getDescriptionText().length();
        String expectedCounter = actualLength + "/" + maxLimit;
        editor.waitForCounter(expectedCounter);
        Assert.assertEquals(editor.getCounterText(), expectedCounter,
                "Character counter does not match current text length");
    }

    @Then("the character counter for card {int} reads {string}")
    public void verifyCharacterCounterReads(int cardNumber, String expectedCounter) {
        WhoWeAreDescriptionEditorComponent editor = whoWeArePageAdmin.getEditor(toZeroBasedIndex(cardNumber));
        editor.waitForCounter(expectedCounter);
        Assert.assertEquals(editor.getCounterText(), expectedCounter,
                "Character counter text does not match expected value");
    }

    @When("the admin enters special characters {string} into the \"Опис\" field of card {int}")
    public void enterSpecialCharactersIntoCardDescription(String text, int cardNumber) {
        whoWeArePageAdmin.getEditor(toZeroBasedIndex(cardNumber)).typeText(text);
    }

    @When("the admin pastes text {string} into the \"Опис\" field of card {int}")
    public void pasteTextIntoCardDescription(String text, int cardNumber) {
        whoWeArePageAdmin.getEditor(toZeroBasedIndex(cardNumber)).pasteText(text);
    }

    @When("the admin clears the \"Опис\" field and enters {string}")
    public void clearAndEnterTextIntoPrimaryDescription(String text) {
        whoWeArePageAdmin.getPrimaryEditor().setText(text);
    }

    @When("the admin triggers validation by blurring the \"Опис\" field")
    public void blurPrimaryDescription() {
        whoWeArePageAdmin.getPrimaryEditor().blurEditor();
    }

    @Then("the validation message {string} is displayed under the field")
    public void verifyValidationMessageDisplayed(String expectedError) {
        WhoWeAreDescriptionEditorComponent editor = whoWeArePageAdmin.getPrimaryEditor();
        editor.waitForErrorMessage(expectedError);
        Assert.assertTrue(editor.getErrorMessage().contains(expectedError),
                "Expected error message '" + expectedError + "', but got: " + editor.getErrorMessage());
    }

    @Then("no validation message is displayed under the field")
    public void verifyNoValidationMessageDisplayed() {
        WhoWeAreDescriptionEditorComponent editor = whoWeArePageAdmin.getPrimaryEditor();
        editor.waitForErrorMessageToDisappear();
        Assert.assertFalse(editor.hasErrorMessage(),
                "Expected no error message, but an error message was displayed: " + editor.getErrorMessage());
    }

    @When("the admin clears the \"Опис\" field")
    public void clearPrimaryDescription() {
        whoWeArePageAdmin.getPrimaryEditor().clear();
    }

    @When("the admin clears the \"Опис\" field and types {int} characters")
    public void clearAndTypeCharacters(int count) {
        String generated = "A".repeat(count);
        whoWeArePageAdmin.getPrimaryEditor().clear().typeText(generated);
    }

    @When("the admin clears the \"Опис\" field and pastes {int} characters")
    public void clearAndPasteCharacters(int count) {
        String generated = "B".repeat(count);
        whoWeArePageAdmin.getPrimaryEditor().clear().pasteText(generated);
    }

    @Then("the character counter reads {string}")
    public void verifyPrimaryCharacterCounterReads(String expectedCounter) {
        WhoWeAreDescriptionEditorComponent editor = whoWeArePageAdmin.getPrimaryEditor();
        editor.waitForCounter(expectedCounter);
        Assert.assertEquals(editor.getCounterText(), expectedCounter,
                "Character counter does not match");
    }

    @When("the admin attempts to type an additional character into the \"Опис\" field")
    public void attemptToTypeAdditionalCharacter() {
        whoWeArePageAdmin.getPrimaryEditor().typeText("Z");
    }

    @Then("the character counter remains {string}")
    public void verifyPrimaryCharacterCounterRemains(String expectedCounter) {
        Assert.assertEquals(whoWeArePageAdmin.getPrimaryEditor().getCounterText(), expectedCounter,
                "Character counter should remain at " + expectedCounter);
    }

    @Then("the \"Опис\" field length does not exceed {int} characters")
    public void verifyDescriptionFieldLengthDoesNotExceed(int maxLen) {
        Assert.assertTrue(whoWeArePageAdmin.getPrimaryEditor().getDescriptionText().length() <= maxLen,
                "Description text length exceeds " + maxLen);
    }

    @When("the admin modifies card {int} with valid text {string}")
    public void modifyCardWithValidText(int cardNumber, String text) {
        whoWeArePageAdmin.getEditor(toZeroBasedIndex(cardNumber))
                .setText(text)
                .blurEditor();
    }

    @Then("the \"Опублікувати зміни?\" confirmation pop-up is displayed")
    public void verifyConfirmationPopupDisplayed() {
        if (confirmationModal == null) {
            confirmationModal = whoWeArePageAdmin.getConfirmationModal();
        }
        Assert.assertEquals(confirmationModal.getTitle(), PUBLISH_MODAL_TITLE,
                "Incorrect confirmation modal title");
    }

    @Then("the confirmation pop-up has \"Так\" and \"Ні\" buttons")
    public void verifyConfirmationPopupButtons() {
        if (confirmationModal == null) {
            confirmationModal = whoWeArePageAdmin.getConfirmationModal();
        }
        Assert.assertTrue(confirmationModal.isConfirmEnabled(), "'Так' button should be enabled");
        Assert.assertTrue(confirmationModal.isCancelEnabled(), "'Ні' button should be enabled");
    }

    @When("the admin clicks \"Ні\" in the confirmation pop-up")
    public void clickCancelInConfirmationPopup() {
        if (confirmationModal == null) {
            confirmationModal = whoWeArePageAdmin.getConfirmationModal();
        }
        confirmationModal.cancel();
    }

    @Then("the confirmation pop-up is closed")
    public void verifyConfirmationPopupClosed() {
        Assert.assertTrue(whoWeArePageAdmin.isConfirmationModalClosed(),
                "Expected confirmation pop-up to be closed");
    }

    @Then("the public \"Хто ми\" page does not display {string}")
    public void verifyPublicWhoWeArePageDoesNotDisplay(String unpublishedText) {
        WebDriver driver = cucumberHook.getDriver();
        String baseUrl = cucumberHook.getTestValueProvider().getBaseUrl();
        String url = baseUrl.endsWith("/") ? baseUrl + "about-us" : baseUrl + "/about-us";
        driver.get(url);
        Assert.assertFalse(driver.getPageSource().contains(unpublishedText),
                "Unpublished text should not appear on the public 'Хто ми' page");
    }
}
