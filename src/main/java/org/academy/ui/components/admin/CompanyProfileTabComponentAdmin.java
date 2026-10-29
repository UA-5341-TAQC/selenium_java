package org.academy.ui.components.admin;

import io.qameta.allure.Step;
import lombok.Getter;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

@Getter
public class CompanyProfileTabComponentAdmin extends BaseComponent {

    @FindBy(css = "[class*='CompanyProfileTab_profile-tab-title__']")
    private WebElement sectionTitle;

    @FindBy(id = "phone")
    private WebElement phoneInput;

    @FindBy(id = "addressUa")
    private WebElement addressUaInput;

    @FindBy(id = "addressEng")
    private WebElement addressEngInput;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "correspondenceEmail")
    private WebElement correspondenceEmailInput;

    @FindBy(id = "mottoUa")
    private WebElement mottoUaInput;

    @FindBy(id = "mottoEng")
    private WebElement mottoEngInput;

    // All contact fields
    @FindBy(css = "input[id]")
    private List<WebElement> inputFields;

    @FindBy(css = "[class*='CompanyProfileFormGroup_custom-form-group__'] label")
    private List<WebElement> fieldLabels;

    // Required field markers
    @FindBy(css = "[class*='CompanyProfileFormGroup_custom-form-group-required__']")
    private List<WebElement> requiredMarkers;

    // Validation errors
    @FindBy(css = "[class*='InputErrorWithCharacterCounter_error-section__']")
    private List<WebElement> validationErrors;

    // Character counters
    @FindBy(css = "output[id$='-character-count']")
    private List<WebElement> characterCounters;

    // Information buttons
    @FindBy(css = "button[aria-label='Show additional information']")
    private List<WebElement> informationButtons;

    @FindBy(css = "[class*='CompanyProfileFormGroup_custom-form-group__']:has(#addressUa) button[aria-label='Show additional information']")
    private WebElement addressUaTooltipButton;

    @FindBy(css = "[class*='CompanyProfileFormGroup_custom-form-group__']:has(#addressEng) button[aria-label='Show additional information']")
    private WebElement addressEngTooltipButton;

    // Individual character counters
    @FindBy(css = "output[id='phone-character-count']")
    private WebElement phoneCounter;

    @FindBy(css = "output[id='addressUa-character-count']")
    private WebElement addressUaCounter;

    @FindBy(css = "output[id='addressEng-character-count']")
    private WebElement addressEngCounter;

    @FindBy(css = "output[id='email-character-count']")
    private WebElement emailCounter;

    @FindBy(css = "output[id='correspondenceEmail-character-count']")
    private WebElement correspondenceEmailCounter;

    @FindBy(css = "output[id='mottoUa-character-count']")
    private WebElement mottoUaCounter;

    @FindBy(css = "output[id='mottoEng-character-count']")
    private WebElement mottoEngCounter;

    public CompanyProfileTabComponentAdmin(WebElement rootElement) {
        super(rootElement);
    }

    @Step("Check company profile tab is displayed")
    public boolean isDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(rootElement)
        ).isDisplayed();
    }

    @Step("Get contacts section title")
    public String getSectionTitleText() {
        return sectionTitle.getText().trim();
    }

    @Step("Get number of contact fields")
    public int getFieldsCount() {
        return inputFields.size();
    }

    @Step("Get all contact field labels")
    public List<String> getFieldLabelTexts() {
        return fieldLabels.stream()
                .map(element -> element.getText().trim())
                .toList();
    }

    // Fill fields

    @Step("Fill phone: {value}")
    public void fillPhone(String value) {
        fillField(phoneInput, value);
    }

    @Step("Fill Ukrainian address: {value}")
    public void fillAddressUa(String value) {
        fillField(addressUaInput, value);
    }

    @Step("Fill English address: {value}")
    public void fillAddressEng(String value) {
        fillField(addressEngInput, value);
    }

    @Step("Fill email: {value}")
    public void fillEmail(String value) {
        fillField(emailInput, value);
    }

    @Step("Fill correspondence email: {value}")
    public void fillCorrespondenceEmail(String value) {
        fillField(correspondenceEmailInput, value);
    }

    @Step("Fill Ukrainian motto: {value}")
    public void fillMottoUa(String value) {
        fillField(mottoUaInput, value);
    }

    @Step("Fill English motto: {value}")
    public void fillMottoEng(String value) {
        fillField(mottoEngInput, value);
    }

    private void fillField(WebElement field, String value) {
        WebElement input = wait.until(
                ExpectedConditions.elementToBeClickable(field)
        );

        input.clear();
        input.sendKeys(value);
    }

    // Get field values

    @Step("Get phone")
    public String getPhone() {
        return getValue(phoneInput);
    }

    @Step("Get Ukrainian address")
    public String getAddressUa() {
        return getValue(addressUaInput);
    }

    @Step("Get English address")
    public String getAddressEng() {
        return getValue(addressEngInput);
    }

    @Step("Get email")
    public String getEmail() {
        return getValue(emailInput);
    }

    @Step("Get correspondence email")
    public String getCorrespondenceEmail() {
        return getValue(correspondenceEmailInput);
    }

    @Step("Get Ukrainian motto")
    public String getMottoUa() {
        return getValue(mottoUaInput);
    }

    @Step("Get English motto")
    public String getMottoEng() {
        return getValue(mottoEngInput);
    }

    // Editability

    @Step("Check all contact fields are editable")
    public boolean areFieldsEditable() {
        return !inputFields.isEmpty()
                && inputFields.stream().allMatch(WebElement::isEnabled);
    }

    @Step("Check all contact fields are disabled")
    public boolean areFieldsDisabled() {
        return !inputFields.isEmpty()
                && inputFields.stream().noneMatch(WebElement::isEnabled);
    }

    @Step("Check phone is editable")
    public boolean isPhoneEditable() {
        return phoneInput.isEnabled();
    }

    @Step("Check Ukrainian address is editable")
    public boolean isAddressUaEditable() {
        return addressUaInput.isEnabled();
    }

    @Step("Check English address is editable")
    public boolean isAddressEngEditable() {
        return addressEngInput.isEnabled();
    }

    @Step("Check email is editable")
    public boolean isEmailEditable() {
        return emailInput.isEnabled();
    }

    @Step("Check correspondence email is editable")
    public boolean isCorrespondenceEmailEditable() {
        return correspondenceEmailInput.isEnabled();
    }

    @Step("Check Ukrainian motto is editable")
    public boolean isMottoUaEditable() {
        return mottoUaInput.isEnabled();
    }

    @Step("Check English motto is editable")
    public boolean isMottoEngEditable() {
        return mottoEngInput.isEnabled();
    }

    // Required fields

    @Step("Get required field markers count")
    public int getRequiredMarkersCount() {
        return requiredMarkers.size();
    }

    @Step("Check required fields have values")
    public boolean areRequiredFieldsFilled() {
        return !getPhone().isBlank()
                && !getAddressUa().isBlank()
                && !getAddressEng().isBlank()
                && !getEmail().isBlank()
                && !getCorrespondenceEmail().isBlank();
    }

    // Maximum length

    @Step("Get phone maximum length")
    public int getPhoneMaxLength() {
        return getMaxLength(phoneInput);
    }

    @Step("Get Ukrainian address maximum length")
    public int getAddressUaMaxLength() {
        return getMaxLength(addressUaInput);
    }

    @Step("Get English address maximum length")
    public int getAddressEngMaxLength() {
        return getMaxLength(addressEngInput);
    }

    @Step("Get email maximum length")
    public int getEmailMaxLength() {
        return getMaxLength(emailInput);
    }

    @Step("Get correspondence email maximum length")
    public int getCorrespondenceEmailMaxLength() {
        return getMaxLength(correspondenceEmailInput);
    }

    @Step("Get Ukrainian motto maximum length")
    public int getMottoUaMaxLength() {
        return getMaxLength(mottoUaInput);
    }

    @Step("Get English motto maximum length")
    public int getMottoEngMaxLength() {
        return getMaxLength(mottoEngInput);
    }

    private int getMaxLength(WebElement field) {
        return Integer.parseInt(field.getDomAttribute("maxlength"));
    }

    // Character counters

    @Step("Get phone character counter")
    public String getPhoneCharacterCounter() {
        return phoneCounter.getText().trim();
    }

    @Step("Get Ukrainian address character counter")
    public String getAddressUaCharacterCounter() {
        return addressUaCounter.getText().trim();
    }

    @Step("Get English address character counter")
    public String getAddressEngCharacterCounter() {
        return addressEngCounter.getText().trim();
    }

    @Step("Get email character counter")
    public String getEmailCharacterCounter() {
        return emailCounter.getText().trim();
    }

    @Step("Get correspondence email character counter")
    public String getCorrespondenceEmailCharacterCounter() {
        return correspondenceEmailCounter.getText().trim();
    }

    @Step("Get Ukrainian motto character counter")
    public String getMottoUaCharacterCounter() {
        return mottoUaCounter.getText().trim();
    }

    @Step("Get English motto character counter")
    public String getMottoEngCharacterCounter() {
        return mottoEngCounter.getText().trim();
    }

    @Step("Get total character counters count")
    public int getCharacterCountersCount() {
        return characterCounters.size();
    }

    // Validation

    @Step("Check phone has validation error")
    public boolean hasPhoneValidationError() {
        return hasError(phoneInput);
    }

    @Step("Check Ukrainian address has validation error")
    public boolean hasAddressUaValidationError() {
        return hasError(addressUaInput);
    }

    @Step("Check English address has validation error")
    public boolean hasAddressEngValidationError() {
        return hasError(addressEngInput);
    }

    @Step("Check email has validation error")
    public boolean hasEmailValidationError() {
        return hasError(emailInput);
    }

    @Step("Check correspondence email has validation error")
    public boolean hasCorrespondenceEmailValidationError() {
        return hasError(correspondenceEmailInput);
    }

    @Step("Check Ukrainian motto has validation error")
    public boolean hasMottoUaValidationError() {
        return hasError(mottoUaInput);
    }

    @Step("Check English motto has validation error")
    public boolean hasMottoEngValidationError() {
        return hasError(mottoEngInput);
    }

    private boolean hasError(WebElement field) {
        return "true".equals(
                field.getDomAttribute("aria-invalid")
        );
    }

    @Step("Get all visible validation error messages")
    public List<String> getValidationErrorMessages() {
        return validationErrors.stream()
                .filter(WebElement::isDisplayed)
                .map(element -> element.getText().trim())
                .filter(text -> !text.isEmpty())
                .toList();
    }

    @Step("Check any validation errors are displayed")
    public boolean hasAnyValidationErrors() {
        return !getValidationErrorMessages().isEmpty();
    }

    // Information tooltips

    @Step("Get information buttons count")
    public int getInformationButtonsCount() {
        return informationButtons.size();
    }

    @Step("Open Ukrainian address information tooltip")
    public void openAddressUaTooltip() {
        wait.until(
                ExpectedConditions.elementToBeClickable(addressUaTooltipButton)
        ).click();
    }

    @Step("Open English address information tooltip")
    public void openAddressEngTooltip() {
        wait.until(
                ExpectedConditions.elementToBeClickable(addressEngTooltipButton)
        ).click();
    }
}
