package org.academy.cucumber.steps.admin;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.academy.cucumber.hooks.CucumberHook;
import org.academy.ui.pages.Admin.AdminPartnerPage;
import org.testng.Assert;

public class AdminPartnerPageStep {

    private final CucumberHook cucumberHook;
    private AdminPartnerPage adminPartnerPage;

    public AdminPartnerPageStep(CucumberHook cucumberHook) {
        this.cucumberHook = cucumberHook;
    }

    @Given("the admin navigates to the \"Партнери\" page")
    public void navigateToAdminPartnerPage() {
        adminPartnerPage = new AdminPartnerPage(cucumberHook.getDriver());
        adminPartnerPage.open(cucumberHook.getTestValueProvider().getBaseUrl());
    }

    @Given("the admin clears the Banner title field")
    public void clearBannerTitle() {
        adminPartnerPage.clearTitle();
    }

    @Given("the admin clears the Banner description field")
    public void clearBannerDescription() {
        adminPartnerPage.clearDescription();
    }

    @When("the admin enters {string} into the Banner title field")
    public void enterTextIntoBannerTitle(String text) {
        adminPartnerPage.enterTitle(text);
    }

    @When("the admin enters {string} into the Banner description field")
    public void enterTextIntoBannerDescription(String text) {
        adminPartnerPage.enterDescription(text);
    }

    @When("the admin appends {string} to the Banner title field")
    public void appendTextToBannerTitle(String text) {
        adminPartnerPage.appendToTitle(text);
    }

    @When("the admin appends {string} to the Banner description field")
    public void appendTextToBannerDescription(String text) {
        adminPartnerPage.appendToDescription(text);
    }

    @When("the admin triggers validation by blurring the Banner description field")
    public void blurBannerDescription() {
        adminPartnerPage.blurDescription();
    }

    @Then("the Banner title field contains {string}")
    public void verifyBannerTitle(String expectedText) {
        Assert.assertEquals(
                adminPartnerPage.getTitle(),
                expectedText,
                "Banner title does not contain the expected text"
        );
    }

    @Then("the Banner description field contains {string}")
    public void verifyBannerDescription(String expectedText) {
        Assert.assertEquals(
                adminPartnerPage.getDescriptionValue(),
                expectedText,
                "Banner description does not contain the expected text"
        );
    }

    @Then("the Banner title counter reads {string}")
    public void verifyBannerTitleCounter(String expectedCounter) {
        adminPartnerPage.waitForTitleCounter(expectedCounter);

        Assert.assertEquals(
                adminPartnerPage.getTitleCharacterCounterText(),
                expectedCounter,
                "Banner title counter does not match"
        );
    }

    @Then("the Banner description counter reads {string}")
    public void verifyBannerDescriptionCounter(String expectedCounter) {
        adminPartnerPage.waitForDescriptionCounter(expectedCounter);

        Assert.assertEquals(
                adminPartnerPage.getDescriptionCharacterCounterText(),
                expectedCounter,
                "Banner description counter does not match"
        );
    }

    @Then("no Banner validation errors are displayed")
    public void verifyNoBannerValidationErrors() {
        Assert.assertTrue(
                adminPartnerPage.bannerHasNoErrors(),
                "Unexpected validation errors are displayed in Banner form"
        );
    }

    @Then("the Banner {string} button becomes enabled")
    public void verifyBannerPublishButtonEnabled(String buttonName) {
        Assert.assertEquals(buttonName, "Опублікувати");

        Assert.assertTrue(
                adminPartnerPage.isPublishButtonEnabled(),
                "Banner 'Опублікувати' button should be enabled"
        );
    }

    @When("the admin triggers validation by blurring the Banner title field")
    public void blurBannerTitle() {
        adminPartnerPage.blurTitle();
    }

    @Then("the Banner title error message is {string}")
    public void verifyBannerTitleError(String expectedError) {
        Assert.assertEquals(
                adminPartnerPage.getTitleErrorText(),
                expectedError,
                "Unexpected Banner title validation message"
        );
    }

    @Then("the Banner description error message is {string}")
    public void verifyBannerDescriptionError(String expectedError) {
        Assert.assertEquals(
                adminPartnerPage.getDescriptionErrorText(),
                expectedError,
                "Unexpected Banner description validation message"
        );
    }

    @Then("the Banner {string} button remains disabled")
    public void verifyBannerPublishButtonDisabled(String buttonName) {
        Assert.assertEquals(buttonName, "Опублікувати");

        Assert.assertFalse(
                adminPartnerPage.isPublishButtonEnabled(),
                "Banner 'Опублікувати' button should remain disabled"
        );
    }

    @Then("the Banner title field is empty")
    public void verifyBannerTitleIsEmpty() {
        Assert.assertTrue(
                adminPartnerPage.getTitle().isEmpty(),
                "Banner title field should be empty"
        );
    }

    @Then("the Banner description field is empty")
    public void verifyBannerDescriptionIsEmpty() {
        Assert.assertTrue(
                adminPartnerPage.getDescriptionValue().isEmpty(),
                "Banner description field should be empty"
        );
    }
}