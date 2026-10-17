package org.academy.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
        wait.until(ExpectedConditions.visibilityOf(emailField));
        emailField.clear();
        emailField.sendKeys(email);
        return this;
    }

    @Step("Enter password: {password}")
    public LoginPage enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordField));
        passwordField.clear();
        passwordField.sendKeys(password);
        return this;
    }

    @Step("Click login button")
    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    @Step("Toggle password visibility")
    public LoginPage togglePasswordVisibility() {
        wait.until(ExpectedConditions.elementToBeClickable(passwordVisibilityToggle)).click();
        return this;
    }

    @Step("Get password field input type")
    public String getPasswordFieldType() {
        return passwordField.getAttribute("type");
    }

    @Step("Check if password is visible as plain text")
    public boolean isPasswordVisible() {
        return "text".equalsIgnoreCase(getPasswordFieldType());
    }
}
