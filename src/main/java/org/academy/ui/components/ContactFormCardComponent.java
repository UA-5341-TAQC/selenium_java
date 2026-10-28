package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ContactFormCardComponent extends BaseComponent {

    @FindBy(css = "input[name='name']")
    private WebElement nameInput;

    @FindBy(css = "input[name='email']")
    private WebElement emailInput;

    @FindBy(css = "input[name='subject']")
    private WebElement subjectInput;

    @FindBy(css = "textarea[name='message']")
    private WebElement messageTextarea;

    @FindBy(css = "button[type='submit']")
    private WebElement submitButton;

    @FindBy(css = "span[role='alert'], [class*='contact-form-message--error']")
    private List<WebElement> validationErrors;

    @FindBy(css = "input[placeholder], textarea[placeholder]")
    private List<WebElement> placeholdersText;

    public ContactFormCardComponent(WebElement element) {
        super(element);
    }

    @Step("Enter name: {name}")
    public ContactFormCardComponent enterName(String name) {
        fillInput(nameInput, name);
        return this;
    }

    @Step("Enter email: {email}")
    public ContactFormCardComponent enterEmail(String email) {
        fillInput(emailInput, email);
        return this;
    }

    @Step("Enter subject: {subject}")
    public ContactFormCardComponent enterSubject(String subject) {
        fillInput(subjectInput, subject);
        return this;
    }

    @Step("Enter message: {message}")
    public ContactFormCardComponent enterMessage(String message) {
        fillInput(messageTextarea, message);
        return this;
    }

    @Step("Fill complete contact form using data object")
    public ContactFormCardComponent fillForm(ContactFormData data) {
        return fillForm(data.name(), data.email(), data.subject(), data.message());
    }

    @Step("Fill complete contact form")
    public ContactFormCardComponent fillForm(String name, String email, String subject, String message) {
        enterName(name);
        enterEmail(email);
        enterSubject(subject);
        enterMessage(message);
        return this;
    }

    @Step("Clear all fields in contact form")
    public ContactFormCardComponent clearFields() {
        clearInput(nameInput);
        clearInput(emailInput);
        clearInput(subjectInput);
        clearInput(messageTextarea);
        return this;
    }

    @Step("Trigger blur on name input")
    public ContactFormCardComponent triggerNameBlur() {
        blur(nameInput);
        return this;
    }

    @Step("Trigger blur on email input")
    public ContactFormCardComponent triggerEmailBlur() {
        blur(emailInput);
        return this;
    }

    @Step("Trigger blur on subject input")
    public ContactFormCardComponent triggerSubjectBlur() {
        blur(subjectInput);
        return this;
    }

    @Step("Trigger blur on message textarea")
    public ContactFormCardComponent triggerMessageBlur() {
        blur(messageTextarea);
        return this;
    }

    @Step("Check if submit button is enabled")
    public boolean isSubmitButtonEnabled() {
        return submitButton.isEnabled() && getDomAttribute(submitButton, "disabled") == null;
    }

    @Step("Click submit button")
    public ContactFormCardComponent clickSubmit() {
        click(submitButton);
        return this;
    }

    @Step("Get list of validation error messages")
    public List<String> getValidationErrors() {
        return validationErrors.stream()
                .filter(this::isDisplayed)
                .map(element -> {
                    try {
                        return element.getText().trim();
                    } catch (StaleElementReferenceException e) {
                        return "";
                    }
                })
                .filter(text -> !text.isEmpty())
                .toList();
    }

    @Step("Wait for validation error: {expectedError}")
    public ContactFormCardComponent waitForValidationError(String expectedError) {
        wait.until(driver -> getValidationErrors().contains(expectedError));
        return this;
    }

    @Step("Wait for validation error to disappear: {expectedError}")
    public ContactFormCardComponent waitForValidationErrorToDisappear(String expectedError) {
        wait.until(driver -> !getValidationErrors().contains(expectedError));
        return this;
    }

    @Step("Get submit button text")
    public String getSubmitButtonText() {
        return getText(submitButton);
    }

    @Step("Get value of name input")
    public String getNameValue() {
        return getValue(nameInput);
    }

    @Step("Get value of email input")
    public String getEmailValue() {
        return getValue(emailInput);
    }

    @Step("Get value of subject input")
    public String getSubjectValue() {
        return getValue(subjectInput);
    }

    @Step("Get value of message textarea")
    public String getMessageValue() {
        return getValue(messageTextarea);
    }

    @Step("Get current contact form data")
    public ContactFormData getFormData() {
        return new ContactFormData(
                getNameValue(),
                getEmailValue(),
                getSubjectValue(),
                getMessageValue()
        );
    }

    @Step("Check if name input is displayed")
    public boolean isNameInputDisplayed() {
        return isDisplayed(nameInput);
    }

    @Step("Check if email input is displayed")
    public boolean isEmailInputDisplayed() {
        return isDisplayed(emailInput);
    }

    @Step("Check if subject input is displayed")
    public boolean isSubjectInputDisplayed() {
        return isDisplayed(subjectInput);
    }

    @Step("Check if message textarea is displayed")
    public boolean isMessageTextareaDisplayed() {
        return isDisplayed(messageTextarea);
    }

    @Step("Check if submit button is displayed")
    public boolean isSubmitButtonDisplayed() {
        return isDisplayed(submitButton);
    }

    @Step("Check if all contact form fields and submit button are displayed")
    public boolean areAllFieldsDisplayed() {
        return isNameInputDisplayed()
                && isEmailInputDisplayed()
                && isSubjectInputDisplayed()
                && isMessageTextareaDisplayed()
                && isSubmitButtonDisplayed();
    }

    public record ContactFormData(String name, String email, String subject, String message) {
    }

    @Step("Get placeholders text")
    public List<String> getFormPlaceholdersText() {
        return placeholdersText.stream()
                .map(element -> element.getDomAttribute("placeholder"))
                .filter(placeholder -> placeholder != null && !placeholder.isBlank())
                .toList();
    }

}
