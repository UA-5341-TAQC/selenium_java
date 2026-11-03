package org.academy.cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.academy.cucumber.hooks.CucumberHook;
import org.academy.ui.components.ContactFormPopUpComponent;
import org.academy.ui.components.ContactSectionComponent;
import org.academy.ui.pages.ProgramsPage;
import org.testng.Assert;

import java.net.URI;

public class ProgramsPageStep {

    private final CucumberHook cucumberHook;
    private static final String PROGRAMS_PATH = "/programs";

    public ProgramsPageStep(CucumberHook cucumberHook) {
        this.cucumberHook = cucumberHook;
    }

    private ProgramsPage getProgramsPage() {
        return new ProgramsPage(cucumberHook.getDriver());
    }

    @Given("Programs page is open")
    public void openReportsPage() {
        cucumberHook.getDriver().get(URI.create(cucumberHook.getTestValueProvider().getBaseUrl())
                .resolve(PROGRAMS_PATH).toString());
        getProgramsPage().waitForContentText();
    }

    @When("The CTA Зв'язатися button clicked")
    public void clickCTAButtonContactUs() {
        ContactSectionComponent contactUsComponent = getProgramsPage()
                .getContactSection();
        contactUsComponent.clickContactButton();

    }

    @Then("The contact form is displayed on the right side of the page")
    public void isContactUsPopUpDisplayed() {
        ContactFormPopUpComponent contactFormPopUpComponent = getProgramsPage()
                .getContactSection().getContactPopUpComponent();

        Assert.assertTrue(contactFormPopUpComponent.isDisplayed(),
                "The contact form is not displayed");
    }

    @Then("Click the X button in the contact form")
    public void closePopUpForm() {
        ContactFormPopUpComponent contactFormPopUpComponent = getProgramsPage()
                .getContactSection().getContactPopUpComponent();

        contactFormPopUpComponent.closeByButton();

    }

    @Then("The contact form is no longer displayed on the page")
    public void noPopUpForm() {
        ContactSectionComponent contactSectionComponent = getProgramsPage()
                .getContactSection();

        Assert.assertFalse(contactSectionComponent.isContactPopUpComponentVisible(),"The contact form is still displayed");
    }

    @Then("The Зв'язатися button remains available on the Програми page")
    public void isContactUsButtonClickable() {
        ContactSectionComponent contactUsComponent = getProgramsPage()
                .getContactSection();

        Assert.assertFalse(contactUsComponent.isContactPopUpComponentVisible(),
                "The contact form is still displayed");
    }

}

