package org.academy.cucumber.steps.admin;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.academy.cucumber.hooks.CucumberHook;
import org.academy.cucumber.steps.AuthSteps;
import org.academy.ui.modals.admin.ConfirmationModal;
import org.academy.ui.modals.admin.team.BaseTeamCategoryModal;
import org.academy.ui.modals.admin.team.TeamEditCategoryModal;
import org.academy.ui.pages.Admin.TeamPageAdmin;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.net.URI;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class AdminTeamPageStep {

    private static final String TEAM_PATH = "admin-panel/team";

    private final CucumberHook cucumberHook;
    private final AuthSteps authSteps;
    private BaseTeamCategoryModal<?> categoryModal;
    private String currentField;

    private ConfirmationModal confirmation;

    private static final String DISCARD_TITLE = "Зміни будуть втрачені. Бажаєте продовжити?";
    private final Map<String, String> enteredValues = new HashMap<>();
    private TeamEditCategoryModal editModal;
    private List<String> categoryNames;
    private Map<String, String> originalValues;
    private String originalCategory;
    private String selectedCategory;

    public AdminTeamPageStep(CucumberHook cucumberHook, AuthSteps authSteps) {
        this.cucumberHook = cucumberHook;
        this.authSteps = authSteps;
    }

    @Given("the admin is logged in")
    public void adminIsLoggedIn() {
        authSteps.loginAsAdministrator();
    }

    @Given("the admin opens the Team page")
    public void openTeamPage() {
        cucumberHook.getDriver().get(URI.create(cucumberHook.getTestValueProvider().getBaseUrl())
                .resolve("/" + TEAM_PATH).toString());
        teamPage();
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

    @When("the admin closes the Add/Edit category modal")
    public void closeAddCategoryModal() {
        confirmation = categoryModal.requestCloseWithConfirmation();
    }

    @Then("the Add/Edit category {string} confirmation pop-up is displayed")
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

    @Then("the Add category confirmation pop-up is closed")
    public void verifyConfirmationClosed() {
        Assert.assertTrue(categoryModal.isConfirmationClosed(), "The confirmation pop-up should close.");
    }

    @Then("the Add/Edit category modal is closed")
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

    @When("the admin closes the unchanged Add/Edit category modal")
    public void closeUnchangedModal() {
        categoryModal.clickCloseIcon();
    }

    @Then("no confirmation pop-up is displayed")
    public void verifyNoConfirmation() {
        Assert.assertTrue(categoryModal.isConfirmationClosed(),
                "No confirmation pop-up should appear when closing an unchanged form.");
    }

    @When("the admin opens the Edit category modal")
    public void openEditForm() {
        TeamPageAdmin page = teamPage();
        categoryNames = page.getCategoryBar().getCategoryNames();
        editModal = page.getCategoryBar().openContextMenu().clickEditCategory();
        categoryModal = editModal;
        Assert.assertEquals(categoryModal.getTitle(), "Редагувати категорію");
        originalCategory = categoryNames.getFirst();
        editModal.selectCategory(originalCategory);
        uiWait().until(d -> originalCategory.equals(value("Назва")));
        originalValues = values();
        selectedCategory = originalCategory;
        rememberValues();
    }

    @When("the admin enters {int} characters into the {string} field")
    public void enterCharacters(int length, String field) {
        if (enteredValues.isEmpty()) rememberValues();
        currentField = field;
        categoryModal.clearField(field).appendToField(field, "a".repeat(length));
        enteredValues.put(field, "a".repeat(length));
    }

    @When("the admin appends a character to the Team category {string} field")
    public void appendCharacter(String field) {
        categoryModal.appendToField(field, "z").blurField(field);
    }

    @When("the admin appends {string} to the {string} field")
    public void appendText(String text, String field) {
        currentField = field;
        categoryModal.appendToField(field, text);
    }

    @When("the admin removes the last character from the {string} field")
    public void removeLastCharacter(String field) {
        currentField = field;
        categoryModal.removeLastCharacter(field);
    }

    @Then("the Team category {string} field should contain {int} characters without errors")
    public void verifyLength(String field, int length) {
        assertValue(field, "a".repeat(length));
        assertCounter(field, length + "/" + limit(field));
        assertErrors(field, List.of());
    }

    @Then("the Team category {string} value should be {string} with the correct counter")
    public void verifyValue(String field, String expected) {
        assertValue(field, expected);
        assertCounter(field, expected.length() + "/" + limit(field));
    }

    @Then("the Team category form should have no validation errors")
    public void verifyNoErrors() {
        assertErrors("Назва", List.of());
        assertErrors("Опис", List.of());
    }

    @Then("^saving the Team category should be (enabled|disabled)$")
    public void verifySaveState(String state) {
        Assert.assertEquals(categoryModal.isSaveEnabled(), state.equals("enabled"), "Save button state");
    }

    @When("the admin declines discarding Team category changes")
    public void declineDiscard() {
        assertDiscardConfirmation();
        confirmation.cancel();
    }

    @Then("the Team category form should retain the entered values")
    public void verifyPreservedValues() {
        Assert.assertTrue(categoryModal.isDisplayed(), "Category form must remain open");
        Assert.assertEquals(values(), enteredValues, "Preserved category values");
        if (selectedCategory != null) Assert.assertEquals(editModal.getSelectedCategory(), selectedCategory);
    }

    @Then("the editable Team categories should match the category bar without duplicates")
    public void verifyCategories() {
        List<String> options = editModal.getAvailableCategories();
        Assert.assertEquals(options.size(), new HashSet<>(options).size(), "Duplicate category options");
        Assert.assertEqualsNoOrder(options.toArray(), categoryNames.toArray(), "Available categories");
    }

    @When("the admin selects another existing Team category")
    public void selectAnotherCategory() {
        Assert.assertTrue(categoryNames.size() >= 2, "Two existing categories are required; none are created");
        selectedCategory = categoryNames.get(1);
        editModal.selectCategory(selectedCategory);
    }

    @Then("only that Team category should be selected")
    public void verifySelection() {
        Assert.assertEquals(editModal.getSelectedCategory(), selectedCategory);
        assertValue("Назва", selectedCategory);
        Assert.assertNotEquals(selectedCategory, originalCategory);
    }

    @When("the admin reloads the Team category page and reopens the original category")
    public void reopenOriginal() {
        cucumberHook.getDriver().navigate().refresh();
        editModal = teamPage().getCategoryBar().openContextMenu().clickEditCategory();
        categoryModal = editModal;
        editModal.selectCategory(originalCategory);
    }

    @Then("the original Team category values should be unchanged")
    public void verifyOriginalValues() {
        Assert.assertEquals(values(), originalValues, "Discarded edits must not be persisted");
        Assert.assertEqualsNoOrder(teamPage().getCategoryBar().getCategoryNames().toArray(), categoryNames.toArray());
    }

    private TeamPageAdmin teamPage() {
        TeamPageAdmin page = new TeamPageAdmin(cucumberHook.getDriver());
        uiWait().until(d -> !page.getCategoryBar().getCategoryNames().isEmpty());
        return page;
    }

    private WebDriverWait uiWait() {
        return new WebDriverWait(cucumberHook.getDriver(), Duration.ofSeconds(10));
    }

    private String value(String field) {
        return categoryModal.getFieldValue(field);
    }

    private Map<String, String> values() {
        return Map.of("Назва", value("Назва"), "Опис", value("Опис"));
    }

    private void rememberValues() {
        enteredValues.clear();
        enteredValues.putAll(values());
    }

    private int limit(String field) {
        return switch (field) {
            case "Назва" -> 20;
            case "Опис" -> 200;
            default -> throw new IllegalArgumentException("Unknown category field: " + field);
        };
    }

    private void assertValue(String field, String expected) {
        uiWait().until(d -> expected.equals(value(field)));
        Assert.assertEquals(value(field), expected, "Category field: " + field);
    }

    private void assertCounter(String field, String expected) {
        uiWait().until(d -> expected.equals(categoryModal.getFieldCounter(field).replaceAll("\\s+", "")));
        Assert.assertEquals(categoryModal.getFieldCounter(field).replaceAll("\\s+", ""), expected, "Counter: " + field);
    }

    private void assertErrors(String field, List<String> expected) {
        uiWait().until(d -> expected.equals(categoryModal.getFieldErrors(field)));
        Assert.assertEquals(categoryModal.getFieldErrors(field), expected, "Validation: " + field);
    }

    private void assertDiscardConfirmation() {
        Assert.assertEquals(confirmation.getTitle().replaceAll("\\s+", " ").trim(), DISCARD_TITLE);
        Assert.assertEquals(confirmation.getConfirmText(), "Так");
        Assert.assertEquals(confirmation.getCancelText(), "Ні");
    }
}
