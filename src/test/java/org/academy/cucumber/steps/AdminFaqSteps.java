package org.academy.cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.academy.cucumber.hooks.CucumberHook;
import org.academy.ui.modals.admin.ConfirmationModal;
import org.academy.ui.modals.admin.faq.FaqModal;
import org.academy.ui.pages.Admin.AdminFaqPage;
import org.testng.Assert;

import java.net.URI;
import java.util.List;

public class AdminFaqSteps {
    private static final String FAQ_PATH = "/admin-panel/faq";
    private static final String ADD_QUESTION_TITLE = "Додати питання";
    private static final String DISCARD_CONFIRMATION_TITLE = "Зміни будуть втрачені. Бажаєте продовжити?";
    private static final String CONFIRM_BUTTON_TEXT = "Так";
    private static final String CANCEL_BUTTON_TEXT = "Ні";
    private static final String WHITESPACE_REGEX = "\\s+";

    private final CucumberHook cucumberHook;
    private FaqModal modal;
    private ConfirmationModal discardConfirmation;
    private String enteredQuestion = "";
    private String enteredAnswer = "";
    private List<String> selectedPages = List.of();

    public AdminFaqSteps(CucumberHook cucumberHook) {
        this.cucumberHook = cucumberHook;
    }

    private AdminFaqPage getFaqPage() {
        return new AdminFaqPage(cucumberHook.getDriver());
    }

    @Given("the administrator opens the FAQ page")
    public void openFaqPage() {
        cucumberHook.getDriver().get(URI.create(cucumberHook.getTestValueProvider().getBaseUrl())
                .resolve(FAQ_PATH).toString());
        getFaqPage().waitUntilVisible();
    }

    @When("the administrator opens the Add Question modal")
    @When("the administrator reopens the Add Question modal")
    public void openAddQuestionModal() {
        modal = getFaqPage().openAddQuestionModal();
        Assert.assertEquals(modal.getTitle(), ADD_QUESTION_TITLE);
    }

    @Then("the FAQ page options should be:")
    public void verifyPageOptions(List<String> expectedPages) {
        Assert.assertEqualsNoOrder(modal.getAvailablePages().toArray(), expectedPages.toArray(), "Available FAQ pages");
    }

    @When("the administrator selects the following FAQ pages:")
    public void selectPages(List<String> pages) {
        selectedPages = List.copyOf(pages);
        modal.setPages(selectedPages);
    }

    @Then("the selected FAQ pages should be:")
    public void verifySelectedPages(List<String> expectedPages) {
        Assert.assertEqualsNoOrder(modal.getSelectedPages().toArray(), expectedPages.toArray(), "Selected FAQ pages");
    }

    @When("the administrator enters a FAQ question of {int} characters")
    public void enterQuestion(int length) {
        enteredQuestion = "a".repeat(length);
        modal.setQuestion(enteredQuestion);
        modal.blurQuestion();
    }

    @When("the administrator enters a FAQ answer of {int} characters")
    public void enterAnswer(int length) {
        enteredAnswer = "a".repeat(length);
        modal.setAnswer(enteredAnswer);
        modal.blurAnswer();
    }

    @Then("the FAQ question should contain {int} characters and its counter should be correct")
    public void verifyQuestionCounter(int length) {
        Assert.assertEquals(modal.getQuestion(), enteredQuestion);
        Assert.assertEquals(modal.getQuestion().length(), length);
        String expected = length + "/" + modal.getQuestionLimit();
        Assert.assertEquals(modal.getQuestionCounter().replaceAll(WHITESPACE_REGEX, ""), expected);
    }

    @Then("the FAQ answer should contain {int} characters and its counter should be correct")
    public void verifyAnswerCounter(int length) {
        Assert.assertEquals(modal.getAnswer(), enteredAnswer);
        Assert.assertEquals(modal.getAnswer().length(), length);
        String expected = length + "/" + modal.getAnswerLimit();
        Assert.assertEquals(modal.getAnswerCounter().replaceAll(WHITESPACE_REGEX, ""), expected);
    }

    @Then("the FAQ form should have no validation errors")
    public void verifyNoValidationErrors() {
        Assert.assertTrue(modal.getValidationErrors().isEmpty(),
                "Unexpected FAQ validation errors: " + modal.getValidationErrors());
    }

    @Then("the FAQ draft and publication buttons should be disabled")
    public void verifyInitialButtons() {
        Assert.assertFalse(modal.isDraftEnabled(), "Draft must initially be disabled");
        verifyPublicationDisabled();
    }

    @Then("the FAQ draft button should be enabled")
    public void verifyDraftEnabled() {
        Assert.assertTrue(modal.isDraftEnabled(), "Draft must be enabled");
    }

    @Then("the FAQ publication button should be enabled")
    public void verifyPublicationEnabled() {
        Assert.assertTrue(modal.isPublishEnabled(), "Publication must be enabled");
    }

    @Then("the FAQ publication button should be disabled")
    public void verifyPublicationDisabled() {
        Assert.assertFalse(modal.isPublishEnabled(), "Publication must be disabled");
    }

    @Then("the FAQ answer should be empty")
    public void verifyEmptyAnswer() {
        Assert.assertEquals(modal.getAnswer(), "");
    }

    @When("the administrator requests to close the FAQ form")
    public void requestClose() {
        discardConfirmation = modal.requestCloseWithConfirmation()
                .orElseThrow(() -> new AssertionError("Expected confirmation for unsaved FAQ changes"));
    }

    @Then("the FAQ discard confirmation should be displayed")
    public void verifyDiscardConfirmation() {
        Assert.assertEquals(discardConfirmation.getTitle().replaceAll(WHITESPACE_REGEX, " ").trim(),
                DISCARD_CONFIRMATION_TITLE);
        Assert.assertEquals(discardConfirmation.getConfirmText(), CONFIRM_BUTTON_TEXT);
        Assert.assertEquals(discardConfirmation.getCancelText(), CANCEL_BUTTON_TEXT);
    }

    @When("the administrator declines discarding FAQ changes")
    public void declineDiscard() {
        discardConfirmation.cancel();
        discardConfirmation = null;
    }

    @Then("the FAQ form should remain open with the entered values and pages")
    public void verifyPreservedForm() {
        Assert.assertEquals(modal.getTitle(), ADD_QUESTION_TITLE);
        Assert.assertEquals(modal.getQuestion(), enteredQuestion);
        Assert.assertEquals(modal.getAnswer(), enteredAnswer);
        Assert.assertEqualsNoOrder(modal.getSelectedPages().toArray(), selectedPages.toArray(), "Preserved FAQ pages");
    }

    @When("the administrator confirms discarding FAQ changes")
    public void confirmDiscard() {
        discardConfirmation.confirm();
        modal.waitUntilClosed();
        discardConfirmation = null;
    }

    @Then("the FAQ form should be closed and the FAQ page should be visible")
    public void verifyClosedForm() {
        modal.waitUntilClosed();
        getFaqPage().waitUntilVisible();
    }

    @Then("the FAQ form should be empty")
    public void verifyEmptyForm() {
        Assert.assertEquals(modal.getQuestion(), "");
        verifyEmptyAnswer();
        Assert.assertTrue(modal.getSelectedPages().isEmpty(), "New form must have no selected pages");
        verifyInitialButtons();
    }
}
