package org.academy.cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.academy.cucumber.hooks.CucumberHook;
import org.academy.ui.components.ContactFormCardComponent;
import org.academy.ui.pages.ContactUsPage;
import org.academy.ui.pages.ProgramsPage;
import org.academy.ui.pages.SupportUsPage;

import org.testng.Assert;

import java.net.URI;
import java.util.List;

public class ContactUsFormSteps {

    private final CucumberHook cucumberHook;
    private ContactFormCardComponent.ContactFormData lastSubmittedData;

    private ContactFormCardComponent activeContactForm;

    private final static String contactPath ="/contact-us";
    private final static String programsPath ="/programs";
    private final static String supportPath ="/support-us";


    public ContactUsFormSteps(CucumberHook cucumberHook) {
        this.cucumberHook = cucumberHook;
    }

    private ContactUsPage getContactUsPage() {
        return new ContactUsPage(cucumberHook.getDriver());
    }

    private SupportUsPage getSupportPage() {
        return new SupportUsPage(cucumberHook.getDriver());
    }

    private ProgramsPage getProgramsPage() {
        return new ProgramsPage(cucumberHook.getDriver());
    }


    @Given("the user opens the contact form via {string}")
    public void theUserOpensTheContactFormVia(String entryPoint) {
        String baseUrl = cucumberHook.getTestValueProvider().getBaseUrl();

        switch (entryPoint) {
            case "EntryPointA" -> {
                String fullUrl = URI.create(baseUrl).resolve(contactPath).toString();
                cucumberHook.getDriver().get(fullUrl);
                activeContactForm = getContactUsPage().getContactFormCard();
            }
            case "EntryPointB" -> {
                String fullUrl = URI.create(baseUrl).resolve(programsPath).toString();
                cucumberHook.getDriver().get(fullUrl);
                activeContactForm = getProgramsPage()
                        .getContactSection()
                        .clickContactButton()
                        .getContactFormCard();
            }
            case "EntryPointC" -> {
                String fullUrl = URI.create(baseUrl).resolve(supportPath).toString();
                cucumberHook.getDriver().get(fullUrl);
                getSupportPage().scrollIntoViewContactForm();
                activeContactForm = getSupportPage().getContactFormCard();
            }
            default -> throw new IllegalArgumentException("Unsupported entry point: " + entryPoint);
        }

        Assert.assertNotNull(activeContactForm, "Contact form was not initialized for " + entryPoint);
        Assert.assertTrue(activeContactForm.areAllFieldsDisplayed(),
                "There is an issue with form display on entry point: " + entryPoint);
    }


    @Then("the system displays the counter message {string}")
    public void theSystemDisplaysTheCounterMessage(String validationMessage) {
        List<String> validError = activeContactForm.getValidationMessageText();
        Assert.assertTrue(validError.contains(validationMessage),
                "There is an issue with counter validation message");
    }

    @Then("the system displays the counter message empty")
    public void theSystemDisplaysTheCounterMessageEmpty() {
        List<String> validError = activeContactForm.getValidationMessageText();
        Assert.assertTrue(validError.isEmpty(),
                "There is a validation message");
    }

    // duplicate but specific for only Contact Form component tests
    @When("the visitor enters a string of {int} {string} characters into the subject field into form")
    public void theVisitorEnterTextIntoTheSubjectFieldForm(int count, String text) {
        activeContactForm.enterSubject(text.repeat(count));
    }

    @When("the visitor enters a string of {int} {string} characters into the message field into form")
    public void theVisitorEnterStringIntoTheMessageFieldForm(int count, String text) {
        activeContactForm.enterMessage(text.repeat(count));
    }

}
