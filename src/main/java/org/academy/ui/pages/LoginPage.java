package org.academy.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;

    @FindBy(css = "button.visibility-toggle, [data-testid='visibility-toggle-button']")
    private WebElement passwordVisibilityToggle;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Enter email: {email}")
    public LoginPage enterEmail(String email) {
        fillInput(emailField, email);
        return this;
    }

    @Step("Enter password: {password}")
    public LoginPage enterPassword(String password) {
        fillInput(passwordField, password);
        return this;
    }

    @Step("Click login button")
    public void clickLoginButton() {
        click(loginButton);
    }

    @Step("Toggle password visibility")
    public LoginPage togglePasswordVisibility() {
        click(passwordVisibilityToggle);
        return this;
    }

    @Step("Get password field input type")
    public String getPasswordFieldType() {
        return getDomAttribute(passwordField, "type");
    }

    @Step("Check if password is visible as plain text")
    public boolean isPasswordVisible() {
        return "text".equalsIgnoreCase(getPasswordFieldType());
    }
}

