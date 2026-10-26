package org.academy.ui.components.admin.donate;

import io.qameta.allure.Step;
import lombok.Getter;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

@Getter
public class DonateSupportOptionComponentAdmin extends BaseComponent {

    // Header

    @FindBy(css = ".support-option-header-title")
    private List<WebElement> optionTitles;

    @FindBy(css = ".support-option-header-actions button[aria-label='edit-btn']")
    private List<WebElement> editButtons;

    @FindBy(css = ".support-option-header-actions button[aria-label='delete-btn']")
    private WebElement deleteButton;

    @FindBy(css = ".support-option-header.editable")
    private List<WebElement> editableHeaders;

    // Fields

    @FindBy(css = ".support-option-fields textarea[name='name']")
    private List<WebElement> nameInputs;

    @FindBy(css = ".support-option-fields textarea[name='value']")
    private WebElement valueInput;

    // Form actions

    @FindBy(css = ".support-option-actions .btn-secondary")
    private List<WebElement> cancelButtons;

    @FindBy(css = ".support-option-actions .btn-primary")
    private List<WebElement> publishButtons;

    public DonateSupportOptionComponentAdmin(WebElement rootElement) {
        super(rootElement);
    }

    // State

    @Step("Check support option is in view mode")
    public boolean isViewMode() {
        return editableHeaders.isEmpty();
    }

    @Step("Check support option is in edit mode")
    public boolean isEditMode() {
        return !editableHeaders.isEmpty() && !editButtons.isEmpty();
    }

    @Step("Check support option is in create mode")
    public boolean isCreateMode() {
        return !editableHeaders.isEmpty() && editButtons.isEmpty();
    }

    // Information

    @Step("Get support option title")
    public String getTitle() {
        if (!nameInputs.isEmpty()) {
            return nameInputs.get(0).getDomProperty("value");
        }

        return optionTitles.get(0).getText().trim();
    }

    @Step("Get support option value")
    public String getValueText() {
        return valueInput.getDomProperty("value");
    }

    // Actions

    @Step("Click Edit Support Option")
    public void clickEditButton() {
        wait.until(ExpectedConditions.elementToBeClickable(
                editButtons.get(0)
        )).click();
    }

    @Step("Click Delete Support Option")
    public void clickDeleteButton() {
        wait.until(ExpectedConditions.elementToBeClickable(
                deleteButton
        )).click();
    }

    // Edit fields

    @Step("Enter support option title: {title}")
    public void enterTitle(String title) {
        fillField(nameInputs.get(0), title);
    }

    @Step("Enter support option value: {value}")
    public void enterValue(String value) {
        fillField(valueInput, value);
    }

    private void fillField(WebElement element, String value) {
        wait.until(ExpectedConditions.elementToBeClickable(element));

        element.clear();
        element.sendKeys(value);
    }

    // Form actions

    @Step("Cancel support option editing")
    public void cancelEditing() {
        wait.until(ExpectedConditions.elementToBeClickable(
                cancelButtons.get(0)
        )).click();
    }

    @Step("Publish support option changes")
    public void publishChanges() {
        wait.until(ExpectedConditions.elementToBeClickable(
                publishButtons.get(0)
        )).click();
    }

    @Step("Check Publish button is enabled")
    public boolean isPublishEnabled() {
        return !publishButtons.isEmpty()
                && publishButtons.get(0).isEnabled();
    }
}
