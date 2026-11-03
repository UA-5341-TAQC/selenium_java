package org.academy.cucumber.steps.admin;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.academy.cucumber.hooks.CucumberHook;
import org.academy.ui.modals.admin.ConfirmationModal;
import org.academy.ui.modals.admin.team.TeamAddCategoryModal;
import org.academy.ui.pages.Admin.TeamPageAdmin;
import org.academy.ui.pages.LoginPage;
import org.academy.utils.TestEnvProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class AdminTeamPageStep {

    private static final String TEAM_PATH = "admin-panel/team";

    private final CucumberHook cucumberHook;
    private TeamAddCategoryModal categoryModal;
    private String currentField;

    private ConfirmationModal confirmation;

    public AdminTeamPageStep(CucumberHook cucumberHook) {
        this.cucumberHook = cucumberHook;
    }

    @Given("the admin is logged in")
    public void adminIsLoggedIn() {
        WebDriver driver = cucumberHook.getDriver();
        TestEnvProvider env = cucumberHook.getTestValueProvider();

        driver.get(env.getAdminLoginUrl());
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(env.getAdminEmail());
        loginPage.enterPassword(env.getAdminPassword());
        loginPage.clickLoginButton();

        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains("/admin-panel"));
    }

    @Given("the admin opens the Team page")
    public void openTeamPage() {
        cucumberHook.getDriver().get(cucumberHook.getTestValueProvider().getBaseUrl() + TEAM_PATH);
    }

    @Given("the admin opens the Add category modal")
    public void openAddCategoryModal() {
        categoryModal = new TeamPageAdmin(cucumberHook.getDriver())
                .getCategoryBar()
                .openContextMenu()
                .clickAddCategory();
    }

    @When("the admin enters {string} into the {string} field")
    public void enterText(String text, String fieldLabel) {
        currentField = fieldLabel;
        if (fieldLabel.equals("Назва")) {
            categoryModal.setName(text);
        } else {
            categoryModal.setDescription(text);
        }
    }

    @When("the admin clicks outside the field")
    public void clickOutsideField() {
        categoryModal.blurField(currentField);
    }

    @When("the admin clicks inside the {string} field")
    public void clickInsideField(String fieldLabel) {
        categoryModal.focusField(fieldLabel);
    }

    @Then("the error {string} is displayed for the {string} field")
    public void verifyError(String expectedError, String fieldLabel) {
        Assert.assertEquals(categoryModal.getFieldError(fieldLabel), expectedError,
                "Wrong validation error for the '" + fieldLabel + "' field.");
    }

    @Then("the clean-up icon is not displayed in the {string} field")
    public void verifyClearIconHidden(String fieldLabel) {
        Assert.assertTrue(categoryModal.isClearIconHidden(fieldLabel),
                "Clean-up icon should be hidden when the '" + fieldLabel + "' field is not focused.");
    }

    @Then("the clean-up icon is displayed in red in the {string} field")
    public void verifyClearIconRed(String fieldLabel) {
        Assert.assertTrue(categoryModal.isClearIconDisplayed(fieldLabel),
                "Clean-up icon should be displayed in the focused '" + fieldLabel + "' field.");
        Assert.assertTrue(categoryModal.isClearIconInErrorState(fieldLabel),
                "Clean-up icon should be red (error state) in the '" + fieldLabel + "' field.");
    }

    @Then("the clean-up icon is displayed in the {string} field")
    public void verifyClearIconDisplayed(String fieldLabel) {
        Assert.assertTrue(categoryModal.isClearIconDisplayed(fieldLabel),
                "Clean-up icon should be displayed in the focused non-empty '" + fieldLabel + "' field.");
    }

    @When("the admin clicks the clean-up icon in the {string} field")
    public void clickClearIcon(String fieldLabel) {
        categoryModal.clickClearIcon(fieldLabel);
    }

    @Then("the {string} field is empty")
    public void verifyFieldIsEmpty(String fieldLabel) {
        Assert.assertEquals(categoryModal.getFieldValue(fieldLabel), "",
                "The '" + fieldLabel + "' field should be empty after clicking the clean-up icon.");
    }

    @Then("the {string} character counter shows {string}")
    public void verifyCounter(String fieldLabel, String expectedCounter) {
        Assert.assertEquals(categoryModal.getFieldCounter(fieldLabel), expectedCounter,
                "The '" + fieldLabel + "' character counter is wrong.");
    }

    @When("the admin closes the Add category modal")
    public void closeAddCategoryModal() {
        confirmation = categoryModal.requestCloseWithConfirmation();
    }

    @Then("the {string} confirmation pop-up is displayed")
    public void verifyConfirmationText(String expectedText) {
        Assert.assertEquals(confirmation.getTitle().replaceAll("\\s+", " "), expectedText,
                "Unsaved changes pop-up text is wrong.");
    }

    @When("the admin confirms closing in the pop-up")
    public void confirmClosing() {
        Assert.assertEquals(confirmation.getConfirmText(), "Так",
                "Confirm button text is unexpected — stopping to avoid a wrong action.");
        confirmation.confirm();
    }

    @Then("the confirmation pop-up is closed")
    public void verifyConfirmationClosed() {
        Assert.assertTrue(categoryModal.isConfirmationClosed(), "The confirmation pop-up should close.");
    }

    @Then("the Add category modal is closed")
    public void verifyAddCategoryModalClosed() {
        Assert.assertTrue(categoryModal.isClosed(), "The 'Додати категорію' modal should close.");
    }

    @Then("the category {string} is not displayed on the Team page")
    public void verifyCategoryNotSaved(String categoryName) {
        List<String> categories = new TeamPageAdmin(cucumberHook.getDriver())
                .getCategoryBar()
                .getCategoryNames();
        Assert.assertFalse(categories.contains(categoryName),
                "Discarded category should not be saved. Categories on the page: " + categories);
    }

    @Then("the pop-up has the {string} and {string} buttons")
    public void verifyPopUpButtons(String confirmText, String cancelText) {
        Assert.assertEquals(confirmation.getConfirmText(), confirmText, "Confirm button text is wrong.");
        Assert.assertEquals(confirmation.getCancelText(), cancelText, "Cancel button text is wrong.");
    }

    @Then("the Add category modal is still open")
    public void verifyAddCategoryModalStillOpen() {
        Assert.assertTrue(categoryModal.isDisplayed(),
                "The 'Додати категорію' modal should stay open behind the pop-up.");
    }

    @When("the admin closes the unchanged Add category modal")
    public void closeUnchangedModal() {
        categoryModal.clickCloseIcon();
    }

    @Then("no confirmation pop-up is displayed")
    public void verifyNoConfirmation() {
        Assert.assertTrue(categoryModal.isConfirmationClosed(),
                "No confirmation pop-up should appear when closing an unchanged form.");
    }
}
