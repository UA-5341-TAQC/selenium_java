package org.academy.ui.modals;

import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ContactFormPopUpModal extends BaseModal {

    public static final String ROOT_SELECTOR = "[data-testid='popup-overlay']";

    @FindBy(css = "aside[class*='ContactFormPopUp_popup']")
    private WebElement dialog;

    @FindBy(css = "button[aria-label='Закрити форму']")
    private WebElement closeButton;

    @FindBy(css = "h2[class*='ContactFormPopUp_title']")
    private WebElement title;

    @FindBy(css = "form[aria-label=\"Форма зворотного зв'язку\"]")
    private WebElement contactForm;

    @FindBy(css = "input[name='name']")
    private WebElement nameInput;

    @FindBy(css = "input[name='email']")
    private WebElement emailInput;

    @FindBy(css = "input[name='subject']")
    private WebElement subjectInput;

    @FindBy(css = "textarea[name='message']")
    private WebElement messageInput;

    @FindBy(css = "input[name='cf-turnstile-response']")
    private WebElement captchaResponseTokenInput;

    @FindBy(css = "form[aria-label=\"Форма зворотного зв'язку\"] button[type='submit']")
    private WebElement submitButton;

    public ContactFormPopUpModal(WebElement rootElement) {
        super(rootElement);
    }

    @Step("Check whether Contact Form popup is displayed")
    public boolean isDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(dialog)).isDisplayed();
    }

    @Step("Get Contact Form popup title")
    public String getTitleText() {
        return getText(title).replaceAll("\\s+", " ").trim();
    }

    @Step("Get contact form element")
    public WebElement getContactFormElement() {
        return wait.until(ExpectedConditions.visibilityOf(contactForm));
    }

    @Step("Get contact name")
    public String getName() {
        return getValue(nameInput);
    }

    @Step("Enter contact name")
    public ContactFormPopUpModal enterName(String name) {
        fillInput(nameInput, name);
        return this;
    }

    @Step("Get contact email")
    public String getEmail() {
        return getValue(emailInput);
    }

    @Step("Enter contact email")
    public ContactFormPopUpModal enterEmail(String email) {
        fillInput(emailInput, email);
        return this;
    }

    @Step("Get contact subject")
    public String getSubject() {
        return getValue(subjectInput);
    }

    @Step("Enter contact subject")
    public ContactFormPopUpModal enterSubject(String subject) {
        fillInput(subjectInput, subject);
        return this;
    }

    @Step("Get contact message")
    public String getMessage() {
        return getValue(messageInput);
    }

    @Step("Enter contact message")
    public ContactFormPopUpModal enterMessage(String message) {
        fillInput(messageInput, message);
        return this;
    }

    @Step("Check whether captcha response token is available")
    public boolean isCaptchaResponseTokenAvailable() {
        String token = captchaResponseTokenInput.getDomProperty("value");
        return token != null && !token.isBlank();
    }

    @Step("Check whether the contact form can be submitted")
    public boolean isSubmitEnabled() {
        return submitButton.isEnabled();
    }

    @Step("Submit contact form")
    public ContactFormPopUpModal submit() {
        click(submitButton);
        return this;
    }

    @Override
    @Step("Request Contact Form popup close")
    public void requestClose() {
        click(closeButton);
    }

    @Step("Close Contact Form popup")
    public void close() {
        requestClose();
        waitUntilClosed();
    }

    @Step("Close Contact Form popup with Escape")
    public void closeWithEscape() {
        new Actions(driver).sendKeys(Keys.ESCAPE).perform();
        waitUntilClosed();
    }

    @Step("Close Contact Form popup by clicking the backdrop")
    public void closeByBackdrop() {
        int offsetX = 1 - rootElement.getRect().getWidth() / 2;
        int offsetY = 1 - rootElement.getRect().getHeight() / 2;
        new Actions(driver)
                .moveToElement(rootElement, offsetX, offsetY)
                .click()
                .perform();
        waitUntilClosed();
    }
}
