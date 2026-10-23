package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

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
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    private String getFieldError(String tagName, String fieldName) {
        try {
            String xpath = String.format(".//%s[@name='%s']/ancestor::div[contains(@class, 'contact-form-field-wrapper')]//span[@role='alert']", tagName, fieldName);
            WebElement err = rootElement.findElement(By.xpath(xpath));
            return err.getText().trim();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    @Step("Get error for name field if displayed")
    public String getNameError() {
        return getFieldError("input", "name");
    }

    @Step("Get error for email field if displayed")
    public String getEmailError() {
        return getFieldError("input", "email");
    }

    @Step("Get error for subject field if displayed")
    public String getSubjectError() {
        return getFieldError("input", "subject");
    }

    @Step("Get error for message field if displayed")
    public String getMessageError() {
        return getFieldError("textarea", "message");
    }

    @Step("Get submit button text")
    public String getSubmitButtonText() {
        return getText(submitButton);
    }

    public record ContactFormData(String name, String email, String subject, String message) {
    }
}

