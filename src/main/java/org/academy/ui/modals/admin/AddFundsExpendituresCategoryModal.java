package org.academy.ui.modals.admin;

import io.qameta.allure.Step;
import org.academy.ui.modals.BaseModal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class AddFundsExpendituresCategoryModal extends BaseModal {

    public static final String ROOT_SELECTOR =
            "[class*='AddFundsExpendituresCategoryModal_modal']";

    public static final By ROOT_LOCATOR =
            By.cssSelector(ROOT_SELECTOR);

    @FindBy(css = "[class*='AddFundsExpendituresCategoryModal_title']")
    private WebElement titleElement;

    @FindBy(css = "[class*='AddFundsExpendituresCategoryModal_subtitle']")
    private WebElement subtitleElement;

    @FindBy(css = "[class*='AddFundsExpendituresCategoryModal_selectHead']")
    private WebElement categoryTypeDropdown;

    @FindBy(css = ".select-options button")
    private List<WebElement> categoryTypeOptions;

    @FindBy(css = "#category-name")
    private WebElement nameInput;

    @FindBy(css = "#category-name-character-count")
    private WebElement characterCountElement;

    @FindBy(css = "[class*='AddFundsExpendituresCategoryModal_error']")
    private List<WebElement> errorElements;

    @FindBy(css = "[class*='AddFundsExpendituresCategoryModal_submit']")
    private WebElement submitButton;

    public AddFundsExpendituresCategoryModal(WebElement rootElement) {
        super(rootElement);
    }

    public AddFundsExpendituresCategoryModal(WebDriver driver) {
        super(driver, ROOT_LOCATOR);
    }

    @Step("Read category modal title")
    public String getTitle() {
        return getText(titleElement);
    }

    @Step("Read category modal subtitle")
    public String getSubtitle() {
        return getText(subtitleElement);
    }

    @Step("Read selected category type")
    public String getSelectedCategoryType() {
        return getText(categoryTypeDropdown);
    }

    @Step("Open category type dropdown")
    public AddFundsExpendituresCategoryModal openCategoryTypeDropdown() {
        if (!isCategoryTypeDropdownOpen()) {
            click(categoryTypeDropdown);
        }
        return this;
    }

    @Step("Read available category types")
    public List<String> getCategoryTypeOptions() {
        openCategoryTypeDropdown();
        return getTexts(waitUntilAllVisible(categoryTypeOptions));
    }

    @Step("Select category type {type}")
    public AddFundsExpendituresCategoryModal selectCategoryType(String type) {
        if (!isCategoryTypeDropdownOpen()) {
            click(categoryTypeDropdown);
        }

        for (WebElement option : waitUntilAllVisible(categoryTypeOptions)) {
            if (option.getText().trim().equals(type)) {
                click(option);
                return this;
            }
        }

        throw new IllegalStateException(
                "Category type option not found: " + type
        );
    }

    private boolean isCategoryTypeDropdownOpen() {
        return !categoryTypeOptions.isEmpty() && isDisplayed(categoryTypeOptions.getFirst());
    }

    @Step("Read category name")
    public String getName() {
        return getValue(nameInput);
    }

    @Step("Enter category name {name}")
    public AddFundsExpendituresCategoryModal enterName(String name) {
        fillInput(nameInput, name);
        return this;
    }

    @Step("Clear category name")
    public AddFundsExpendituresCategoryModal clearName() {
        clearInput(nameInput);
        return this;
    }

    @Step("Read category name character count")
    public String getCharacterCount() {
        return getText(characterCountElement);
    }

    @Step("Check whether submit button is enabled")
    public boolean isSubmitEnabled() {
        return waitUntilVisible(submitButton).isEnabled();
    }

    @Step("Read submit button text")
    public String getSubmitButtonText() {
        return getText(submitButton);
    }

    @Step("Read visible validation errors")
    public List<String> getValidationErrors() {
        return errorElements.stream()
                .filter(this::isDisplayed)
                .map(element -> element.getText().trim())
                .filter(text -> !text.isEmpty())
                .toList();
    }

    @Step("Click submit button")
    public void clickSubmit() {
        click(submitButton);
    }

    @Step("Submit category and wait for modal to close")
    public void submit() {
        clickSubmit();
        waitUntilClosed();
    }

    @Step("Close modal with the close icon")
    public void close() {
        requestClose();
        waitUntilClosed();
    }
}