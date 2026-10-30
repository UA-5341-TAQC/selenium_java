package org.academy.cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.academy.cucumber.hooks.CucumberHook;
import org.academy.ui.components.ContactFormCardComponent;
import org.academy.ui.components.CopyTextButtonComponent;
import org.academy.ui.components.contact.ContactDetailsSectionComponent;
import org.academy.ui.pages.ContactUsPage;
import org.testng.Assert;

import java.net.URI;
import java.util.List;
import java.util.Map;

public class ContactUsStep {

    private final CucumberHook cucumberHook;
    private ContactFormCardComponent.ContactFormData lastSubmittedData;

    public ContactUsStep(CucumberHook cucumberHook) {
        this.cucumberHook = cucumberHook;
    }

    private ContactUsPage getContactUsPage() {
        return new ContactUsPage(cucumberHook.getDriver());
    }

    @Given("the visitor opens the {string} page at {string}")
    public void openPageAt(String pageName, String path) {
        String fullUrl = URI.create(cucumberHook.getTestValueProvider().getBaseUrl()).resolve(path).toString();
        cucumberHook.getDriver().get(fullUrl);
    }

    @Then("the contact details section title should be {string}")
    public void verifyContactDetailsSectionTitle(String expectedTitle) {
        Assert.assertEquals(getContactUsPage().getContactDetailsSection().getTitleText(), expectedTitle,
                "Heading text should strictly match mockup");
    }

    @Then("the contact details section description should be {string}")
    public void verifyContactDetailsSectionDescription(String expectedDescription) {
        Assert.assertEquals(getContactUsPage().getContactDetailsSection().getDescriptionText(), expectedDescription,
                "Description text should match mockup");
    }

    @Then("the contact email should contain {string} and have a visible copy button")
    public void verifyContactEmail(String expectedChar) {
        ContactDetailsSectionComponent detailsSection = getContactUsPage().getContactDetailsSection();
        Assert.assertTrue(detailsSection.getEmailText().contains(expectedChar),
                "Contact email should contain " + expectedChar + " symbol");
        Assert.assertTrue(detailsSection.getEmailCopyButton().isDisplayed(),
                "Email copy button should be displayed");
    }

    @Then("the contact phone number should contain {string} and have a visible copy button")
    public void verifyContactPhone(String expectedChar) {
        ContactDetailsSectionComponent detailsSection = getContactUsPage().getContactDetailsSection();
        Assert.assertTrue(detailsSection.getPhoneText().contains(expectedChar),
                "Contact phone number should contain " + expectedChar + " symbol");
        Assert.assertTrue(detailsSection.getPhoneCopyButton().isDisplayed(),
                "Phone copy button should be displayed");
    }

    @Then("the contact address should not be blank")
    public void verifyContactAddressNotBlank() {
        Assert.assertFalse(getContactUsPage().getContactDetailsSection().getAddressText().isBlank(),
                "Contact address should not be blank");
    }

    @Then("the social media section should display {int} links:")
    public void verifySocialMediaLinks(int expectedCount, List<String> expectedNames) {
        ContactDetailsSectionComponent detailsSection = getContactUsPage().getContactDetailsSection();
        Assert.assertEquals(detailsSection.getSocialMediaLinksCount(), expectedCount,
                "Should display " + expectedCount + " social media links");
        List<String> actualNames = detailsSection.getSocialMediaNames();
        for (String name : expectedNames) {
            Assert.assertTrue(actualNames.contains(name), "Social links should contain " + name);
        }
    }

    @Then("all contact form fields and the submit button should be displayed")
    public void verifyAllContactFormFieldsDisplayed() {
        Assert.assertTrue(getContactUsPage().getContactFormCard().areAllFieldsDisplayed(),
                "All contact form fields and submit button should be displayed");
    }

    @Then("the submit button text should be {string}")
    public void verifySubmitButtonText(String expectedText) {
        Assert.assertEquals(getContactUsPage().getContactFormCard().getSubmitButtonText(), expectedText,
                "Submit button text should match expected");
    }

    @When("the visitor fills the contact form with the following data:")
    public void fillContactForm(Map<String, String> data) {
        lastSubmittedData = new ContactFormCardComponent.ContactFormData(
                data.get("name"),
                data.get("email"),
                data.get("subject"),
                data.get("message")
        );
        getContactUsPage().getContactFormCard().fillForm(lastSubmittedData);
    }

    @Then("the contact form fields should contain the entered values")
    public void verifyContactFormFieldsContainEnteredValues() {
        ContactFormCardComponent.ContactFormData actualData = getContactUsPage().getContactFormCard().getFormData();
        Assert.assertEquals(actualData, lastSubmittedData,
                "Entered form values should match expected ContactFormData");
    }

    @Then("the submit button with text {string} should be displayed")
    public void verifySubmitButtonWithTextDisplayed(String expectedText) {
        ContactFormCardComponent form = getContactUsPage().getContactFormCard();
        Assert.assertTrue(form.isSubmitButtonDisplayed(), "Submit button should be displayed");
        Assert.assertEquals(form.getSubmitButtonText(), expectedText,
                "Submit button text should match expected");
    }

    @When("the visitor enters {string} into the subject field and triggers blur")
    public void enterSubjectAndBlur(String subject) {
        getContactUsPage().getContactFormCard().enterSubject(subject).triggerSubjectBlur();
    }

    @When("the visitor enters {string} into the message field and triggers blur")
    public void enterMessageAndBlur(String message) {
        getContactUsPage().getContactFormCard().enterMessage(message).triggerMessageBlur();
    }

    @When("the visitor enters {string} into the email field and triggers blur")
    public void enterEmailAndBlur(String email) {
        getContactUsPage().getContactFormCard().enterEmail(email).triggerEmailBlur();
    }

    @Then("the validation error {string} should be displayed")
    public void verifyValidationErrorDisplayed(String expectedError) {
        List<String> errors = getContactUsPage().getContactFormCard()
                .waitForValidationError(expectedError)
                .getValidationErrors();
        Assert.assertTrue(errors.contains(expectedError),
                "Error should be displayed: " + expectedError);
    }

    @Then("the validation error {string} should disappear")
    @Then("the validation error {string} should not be displayed")
    public void verifyValidationErrorNotDisplayed(String expectedError) {
        List<String> errors = getContactUsPage().getContactFormCard()
                .waitForValidationErrorToDisappear(expectedError)
                .getValidationErrors();
        Assert.assertFalse(errors.contains(expectedError),
                "Validation error should not be displayed: " + expectedError);
    }

    @When("the visitor enters a string of {int} {string} characters into the subject field")
    public void enterRepeatedCharactersIntoSubject(int count, String character) {
        getContactUsPage().getContactFormCard().enterSubject(character.repeat(count));
    }

    @When("the visitor enters a string of {int} {string} characters followed by {string} into the subject field")
    public void enterRepeatedCharactersFollowedByIntoSubject(int count, String character, String suffix) {
        getContactUsPage().getContactFormCard().enterSubject(character.repeat(count) + suffix);
    }

    @When("the visitor enters a string of {int} {string} characters into the message field")
    public void enterRepeatedCharactersIntoMessage(int count, String character) {
        getContactUsPage().getContactFormCard().enterMessage(character.repeat(count));
    }

    @When("the visitor enters a string of {int} {string} characters followed by {string} into the message field")
    public void enterRepeatedCharactersFollowedByIntoMessage(int count, String character, String suffix) {
        getContactUsPage().getContactFormCard().enterMessage(character.repeat(count) + suffix);
    }

    @Then("the subject field value length should be {int}")
    @Then("the subject field value length should remain {int}")
    public void verifySubjectLength(int expectedLength) {
        Assert.assertEquals(getContactUsPage().getContactFormCard().getSubjectValue().length(), expectedLength,
                "Subject field value length mismatch");
    }

    @Then("the message field value length should be {int}")
    @Then("the message field value length should remain {int}")
    public void verifyMessageLength(int expectedLength) {
        Assert.assertEquals(getContactUsPage().getContactFormCard().getMessageValue().length(), expectedLength,
                "Message textarea value length mismatch");
    }

    @Then("the following validation errors should be displayed:")
    public void verifyFollowingValidationErrorsDisplayed(List<String> expectedErrors) {
        ContactFormCardComponent form = getContactUsPage().getContactFormCard();
        for (String error : expectedErrors) {
            form.waitForValidationError(error);
        }
        List<String> actualErrors = form.getValidationErrors();
        for (String expectedError : expectedErrors) {
            Assert.assertTrue(actualErrors.contains(expectedError),
                    "Should display minimum length error: " + expectedError);
        }
    }

    @Then("the contact details section should contain {int} social media links")
    public void verifyContactDetailsSocialMediaLinksCount(int expectedCount) {
        Assert.assertEquals(getContactUsPage().getContactDetailsSection().getSocialMediaLinksCount(), expectedCount,
                "Should have " + expectedCount + " social media links");
    }

    @Then("each social media link URL should start with {string}")
    public void verifyEachSocialMediaLinkUrlStartsWith(String prefix) {
        List<String> urls = getContactUsPage().getContactDetailsSection().getSocialMediaUrls();
        Assert.assertFalse(urls.isEmpty(), "Social media URLs list should not be empty");
        for (String url : urls) {
            Assert.assertNotNull(url, "Social link URL should not be null");
            Assert.assertTrue(url.startsWith(prefix),
                    "Social link URL should start with " + prefix + ", found: " + url);
        }
    }

    @Then("each social media link should have target attribute {string}")
    public void verifyEachSocialMediaLinkTargetAttribute(String expectedTarget) {
        List<String> targets = getContactUsPage().getContactDetailsSection().getSocialMediaTargets();
        Assert.assertFalse(targets.isEmpty(), "Social media targets list should not be empty");
        for (String target : targets) {
            Assert.assertEquals(target, expectedTarget,
                    "Social media link target attribute should be " + expectedTarget + ", found: " + target);
        }
    }

    @When("the visitor clicks the copy email button")
    public void clickCopyEmailButton() {
        getContactUsPage().getContactDetailsSection().getEmailCopyButton().clickCopy();
    }

    @Then("the copy email button should remain displayed and enabled")
    public void verifyCopyEmailButtonDisplayedAndEnabled() {
        CopyTextButtonComponent emailCopyButton = getContactUsPage().getContactDetailsSection().getEmailCopyButton();
        Assert.assertTrue(emailCopyButton.isDisplayed(), "Email copy button should be displayed");
        Assert.assertTrue(emailCopyButton.isEnabled(), "Email copy button should be enabled");
    }

    @When("the visitor clicks the copy phone number button")
    public void clickCopyPhoneNumberButton() {
        getContactUsPage().getContactDetailsSection().getPhoneCopyButton().clickCopy();
    }

    @Then("the copy phone number button should remain displayed and enabled")
    public void verifyCopyPhoneNumberButtonDisplayedAndEnabled() {
        CopyTextButtonComponent phoneCopyButton = getContactUsPage().getContactDetailsSection().getPhoneCopyButton();
        Assert.assertTrue(phoneCopyButton.isDisplayed(), "Phone copy button should be displayed");
        Assert.assertTrue(phoneCopyButton.isEnabled(), "Phone copy button should be enabled");
    }
}
