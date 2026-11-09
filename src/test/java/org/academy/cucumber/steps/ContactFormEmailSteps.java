package org.academy.cucumber.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.academy.cucumber.hooks.CucumberHook;
import org.academy.ui.components.ContactFormCardComponent;
import org.academy.ui.pages.ContactUsPage;
import org.testng.Assert;

/**
 * Steps of contact_form_email_validation.feature. The page is opened and the form is filled by the shared
 * "the visitor opens the {string} page at {string}" and
 * "the visitor fills the contact form with the following data:" steps.
 */
public class ContactFormEmailSteps {

    private final CucumberHook cucumberHook;

    public ContactFormEmailSteps(CucumberHook cucumberHook) {
        this.cucumberHook = cucumberHook;
    }

    private ContactUsPage getContactUsPage() {
        return new ContactUsPage(cucumberHook.getDriver());
    }

    private ContactFormCardComponent getForm() {
        return getContactUsPage().getContactFormCard();
    }

    @When("the visitor types {string} into the e-mail field")
    public void typeEmail(String email) {
        getForm().enterEmail(email);
    }

    @When("the visitor submits the contact form")
    public void submitForm() {
        getForm().submitForm();
    }

    @Then("the e-mail field should contain {string}")
    public void verifyEmailValue(String expectedValue) {
        Assert.assertEquals(getForm().getEmailValue(), expectedValue,
                "The e-mail field should contain the expected value");
    }

    @Then("the e-mail error {string} should be displayed")
    public void verifyEmailError(String expectedError) {
        Assert.assertTrue(getForm().isValidationErrorDisplayed(expectedError),
                "Error should be displayed: " + expectedError);
    }

    @Then("the contact form should not be sent")
    public void verifyFormNotSent() {
        Assert.assertFalse(getContactUsPage().isToastPresent(), "The form must not be submitted");
    }
}