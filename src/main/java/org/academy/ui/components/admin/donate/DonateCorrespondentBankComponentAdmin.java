package org.academy.ui.components.admin.donate;

import io.qameta.allure.Step;
import lombok.Getter;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Arrays;
import java.util.List;

@Getter
public class DonateCorrespondentBankComponentAdmin extends BaseComponent {

    // Header

    @FindBy(css = ".form-head-container > .form-name")
    private List<WebElement> bankNames;

    @FindBy(css = ".form-name-actions button[aria-label='edit-btn']")
    private WebElement editButton;

    @FindBy(css = ".form-name-actions button[aria-label='delete-btn']")
    private WebElement deleteButton;

    // Fields

    @FindBy(css = ".form-body textarea[name='name']")
    private List<WebElement> nameInputs;

    @FindBy(css = ".form-body textarea[name='swift']")
    private List<WebElement> swiftInputs;

    @FindBy(css = ".form-body textarea[name='account']")
    private List<WebElement> accountInputs;

    @FindBy(css = ".form-body textarea[name='foreignIban']")
    private List<WebElement> ibanInputs;

    // Form actions

    @FindBy(css = ".form-body .form-footer .btn-secondary")
    private List<WebElement> cancelButtons;

    @FindBy(css = ".form-body .form-footer .btn-primary")
    private List<WebElement> publishButtons;

    public DonateCorrespondentBankComponentAdmin(WebElement rootElement) {
        super(rootElement);
    }

    // State

    @Step("Check correspondent bank is in edit mode")
    public boolean isEditMode() {
        return hasClass("edit");
    }

    @Step("Check correspondent bank is in view mode")
    public boolean isViewMode() {
        return hasClass("view");
    }

    @Step("Check correspondent bank is in create mode")
    public boolean isCreateMode() {
        return hasClass("create");
    }

    private boolean hasClass(String className) {
        String classes = rootElement.getDomAttribute("class");

        return classes != null
                && Arrays.asList(classes.trim().split("\\s+"))
                .contains(className);
    }

    // Information

    @Step("Get correspondent bank name")
    public String getBankNameText() {
        if (isEditMode() || isCreateMode()) {
            return nameInputs.get(0).getDomProperty("value");
        }

        return bankNames.get(0).getText().trim();
    }

    @Step("Get correspondent bank SWIFT")
    public String getSwiftText() {
        return swiftInputs.get(0).getDomProperty("value");
    }

    @Step("Get correspondent bank account")
    public String getAccountText() {
        return accountInputs.get(0).getDomProperty("value");
    }

    @Step("Get correspondent bank IBAN")
    public String getIbanText() {
        return ibanInputs.get(0).getDomProperty("value");
    }

    @Step("Check correspondent bank has SWIFT field")
    public boolean hasSwift() {
        return !swiftInputs.isEmpty();
    }

    @Step("Check correspondent bank has account field")
    public boolean hasAccount() {
        return !accountInputs.isEmpty();
    }

    @Step("Check correspondent bank has IBAN field")
    public boolean hasIban() {
        return !ibanInputs.isEmpty();
    }

    // Actions

    @Step("Expand correspondent bank")
    public void expandBank() {
        if (!isViewMode() || bankNames.isEmpty()) {
            return;
        }

        WebElement bankName = bankNames.get(0);
        WebElement arrow = bankName.findElement(By.cssSelector(".arrow"));

        String classes = arrow.getDomAttribute("class");

        if (classes == null || !classes.contains("expanded")) {
            wait.until(ExpectedConditions.elementToBeClickable(bankName))
                    .click();
        }
    }

    @Step("Click Edit Correspondent Bank")
    public void clickEditButton() {
        wait.until(ExpectedConditions.elementToBeClickable(editButton))
                .click();

        wait.until(driver -> isEditMode());
    }

    @Step("Click Delete Correspondent Bank")
    public void clickDeleteButton() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton))
                .click();
    }

    // Edit fields

    @Step("Enter correspondent bank name: {name}")
    public void enterBankName(String name) {
        fillField(nameInputs.get(0), name);
    }

    @Step("Enter correspondent bank SWIFT: {swift}")
    public void enterSwift(String swift) {
        fillField(swiftInputs.get(0), swift);
    }

    @Step("Enter correspondent bank account: {account}")
    public void enterAccount(String account) {
        fillField(accountInputs.get(0), account);
    }

    @Step("Enter correspondent bank IBAN: {iban}")
    public void enterIban(String iban) {
        fillField(ibanInputs.get(0), iban);
    }

    private void fillField(WebElement element, String value) {
        wait.until(ExpectedConditions.elementToBeClickable(element));

        element.clear();
        element.sendKeys(value);
    }

    // Form actions

    @Step("Cancel correspondent bank editing")
    public void cancelEditing() {
        wait.until(ExpectedConditions.elementToBeClickable(
                cancelButtons.get(0)
        )).click();
    }

    @Step("Publish correspondent bank changes")
    public void publishChanges() {
        wait.until(ExpectedConditions.elementToBeClickable(
                publishButtons.get(0)
        )).click();
    }

    @Step("Check correspondent bank Publish button is enabled")
    public boolean isPublishEnabled() {
        return !publishButtons.isEmpty()
                && publishButtons.get(0).isEnabled();
    }
}
