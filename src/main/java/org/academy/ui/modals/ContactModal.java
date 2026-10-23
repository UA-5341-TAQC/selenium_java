package org.academy.ui.modals;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ContactModal extends BaseModal {

    public static final By ROOT_LOCATOR = By.cssSelector("aside[class*='ContactFormPopUp_popup']");
    @FindBy(css = "button[class*='ContactFormPopUp_closeBtn']")
    private WebElement contactCloseButton;

    @FindBy(css = "h2[class*='ContactFormPopUp_title']")
    private WebElement title;

    @FindBy(css = "input[name='name']")
    private WebElement nameInput;

    @FindBy(css = "input[name='email']")
    private WebElement emailInput;

    @FindBy(css = "input[name='subject']")
    private WebElement subjectInput;

    @FindBy(css = "textarea")
    private WebElement messageInput;

    @FindBy(css = "button[type='submit']")
    private WebElement submitButton;

    public ContactModal(WebElement rootElement) {
        super(rootElement);
    }

    @Override
    @Step("Request Contact modal close")
    public void requestClose() {
        click(contactCloseButton);
    }

    private String normalize(String text) {
        return text.replaceAll("\\s+", " ").trim();
    }

    @Step("Get Contact modal title")
    public String getTitleText() {
        return normalize(getText(title));
    }

    @Step("Fill name")
    public ContactModal fillName(String name) {
        fillInput(nameInput, name);
        return this;
    }

    @Step("Fill email")
    public ContactModal fillEmail(String email) {
        fillInput(emailInput, email);
        return this;
    }

    @Step("Fill subject")
    public ContactModal fillSubject(String subject) {
        fillInput(subjectInput, subject);
        return this;
    }

    @Step("Fill message")
    public ContactModal fillMessage(String message) {
        fillInput(messageInput, message);
        return this;
    }

    @Step("Get name value")
    public String getNameValue() {
        return getValue(nameInput);
    }

    @Step("Get email value")
    public String getEmailValue() {
        return getValue(emailInput);
    }

    @Step("Get subject value")
    public String getSubjectValue() {
        return getValue(subjectInput);
    }

    @Step("Get message value")
    public String getMessageValue() {
        return getValue(messageInput);
    }

    @Step("Click Submit button")
    public void clickSubmit() {
        click(submitButton);
    }

}
