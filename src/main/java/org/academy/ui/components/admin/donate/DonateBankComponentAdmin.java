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
public class DonateBankComponentAdmin extends BaseComponent {

    // Bank header

    @FindBy(css = ".form-header + .form-name")
    private List<WebElement> bankNames;

    @FindBy(css = ".form-header button[aria-label='edit-btn']")
    private WebElement editButton;

    @FindBy(css = ".form-header button[aria-label='delete-btn']")
    private WebElement deleteButton;

    // Bank fields

    @FindBy(css = ".form-body textarea[name='name']")
    private List<WebElement> nameInputs;

    @FindBy(css = ".form-body textarea[name='receiver']")
    private WebElement receiverInput;

    @FindBy(css = ".form-body textarea[name='edrpou']")
    private List<WebElement> edrpouInputs;

    @FindBy(css = ".form-body textarea[name='ukrainianIban']")
    private WebElement ibanInput;

    @FindBy(css = ".form-body textarea[name='paymentPurpose']")
    private List<WebElement> paymentPurposeInputs;

    @FindBy(css = ".form-body textarea[name='swift']")
    private List<WebElement> swiftInputs;

    @FindBy(css = ".form-body textarea[name='address']")
    private List<WebElement> addressInputs;

    // Form actions

    @FindBy(css = ".form-body .form-footer .btn-secondary")
    private List<WebElement> cancelButtons;

    @FindBy(css = ".form-body .form-footer .btn-primary")
    private List<WebElement> publishButtons;

    // Correspondent banks

    @FindBy(css = ".generic-details.child .generic-form.child")
    private List<WebElement> correspondentBankItems;

    @FindBy(css = ".generic-details.child .generic-details-buttons .btn-add-new")
    private List<WebElement> addCorrespondentBankButtons;

    public DonateBankComponentAdmin(WebElement rootElement) {
        super(rootElement);
    }

    // Bank state

    @Step("Check bank is in edit mode")
    public boolean isEditMode() {
        return hasClass("edit");
    }

    @Step("Check bank is in view mode")
    public boolean isViewMode() {
        return hasClass("view");
    }

    @Step("Check bank is in create mode")
    public boolean isCreateMode() {
        return hasClass("create");
    }

    private boolean hasClass(String className) {
        String classes = rootElement.getDomAttribute("class");

        return classes != null
                && Arrays.asList(classes.trim().split("\\s+"))
                .contains(className);
    }

    // Bank information

    @Step("Get bank name")
    public String getBankNameText() {
        if (isEditMode() || isCreateMode()) {
            return nameInputs.get(0).getDomProperty("value");
        }

        return bankNames.get(0).getText().trim();
    }

    @Step("Get receiver")
    public String getReceiverText() {
        return receiverInput.getDomProperty("value");
    }

    @Step("Get IBAN")
    public String getIbanText() {
        return ibanInput.getDomProperty("value");
    }

    @Step("Get EDRPOU")
    public String getEdrpouText() {
        return edrpouInputs.get(0).getDomProperty("value");
    }

    @Step("Get payment purpose")
    public String getPaymentPurposeText() {
        return paymentPurposeInputs.get(0).getDomProperty("value");
    }

    @Step("Get SWIFT")
    public String getSwiftText() {
        return swiftInputs.get(0).getDomProperty("value");
    }

    @Step("Get bank address")
    public String getAddressText() {
        return addressInputs.get(0).getDomProperty("value");
    }

    @Step("Check EDRPOU field exists")
    public boolean hasEdrpou() {
        return !edrpouInputs.isEmpty();
    }

    @Step("Check SWIFT field exists")
    public boolean hasSwift() {
        return !swiftInputs.isEmpty();
    }

    @Step("Check payment purpose field exists")
    public boolean hasPaymentPurpose() {
        return !paymentPurposeInputs.isEmpty();
    }

    // Bank actions

    @Step("Expand bank details")
    public void expandBank() {
        if (!isViewMode() || bankNames.isEmpty()) {
            return;
        }

        WebElement bankName = bankNames.get(0);
        WebElement arrow = bankName.findElement(By.cssSelector(".arrow"));

        String arrowClasses = arrow.getDomAttribute("class");

        if (arrowClasses == null || !arrowClasses.contains("expanded")) {
            wait.until(ExpectedConditions.elementToBeClickable(bankName))
                    .click();
        }
    }

    @Step("Click Edit Bank")
    public void clickEditButton() {
        wait.until(ExpectedConditions.elementToBeClickable(editButton))
                .click();
    }

    @Step("Click Delete Bank")
    public void clickDeleteButton() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton))
                .click();
    }

    // Edit fields

    @Step("Enter bank name: {name}")
    public void enterBankName(String name) {
        fillField(nameInputs.get(0), name);
    }

    @Step("Enter receiver: {receiver}")
    public void enterReceiver(String receiver) {
        fillField(receiverInput, receiver);
    }

    @Step("Enter EDRPOU: {edrpou}")
    public void enterEdrpou(String edrpou) {
        fillField(edrpouInputs.get(0), edrpou);
    }

    @Step("Enter IBAN: {iban}")
    public void enterIban(String iban) {
        fillField(ibanInput, iban);
    }

    @Step("Enter payment purpose: {purpose}")
    public void enterPaymentPurpose(String purpose) {
        fillField(paymentPurposeInputs.get(0), purpose);
    }

    @Step("Enter SWIFT: {swift}")
    public void enterSwift(String swift) {
        fillField(swiftInputs.get(0), swift);
    }

    @Step("Enter bank address: {address}")
    public void enterAddress(String address) {
        fillField(addressInputs.get(0), address);
    }

    private void fillField(WebElement element, String value) {
        wait.until(ExpectedConditions.elementToBeClickable(element));

        element.clear();
        element.sendKeys(value);
    }

    // Form actions

    @Step("Cancel bank editing")
    public void cancelEditing() {
        wait.until(ExpectedConditions.elementToBeClickable(
                cancelButtons.get(0)
        )).click();
    }

    @Step("Publish bank changes")
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

    // Correspondent banks

    @Step("Get correspondent banks count")
    public int getCorrespondentBanksCount() {
        return correspondentBankItems.size();
    }

    @Step("Get correspondent bank by index: {index}")
    public DonateCorrespondentBankComponentAdmin getCorrespondentBank(
            int index) {

        return new DonateCorrespondentBankComponentAdmin(
                correspondentBankItems.get(index)
        );
    }

    @Step("Get all correspondent banks")
    public List<DonateCorrespondentBankComponentAdmin>
    getCorrespondentBankComponents() {

        return correspondentBankItems.stream()
                .map(DonateCorrespondentBankComponentAdmin::new)
                .toList();
    }

    @Step("Click Add Correspondent Bank")
    public void clickAddCorrespondentBank() {
        wait.until(ExpectedConditions.elementToBeClickable(
                addCorrespondentBankButtons.get(0)
        )).click();
    }
}
