package org.academy.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(id = "email")
    WebElement emailField;
    @FindBy(id = "password")
    WebElement passwordField;
    @FindBy(xpath = "//button[@type='submit']")
    WebElement loginButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Enter email: {email}")
    public LoginPage enterEmail(String email) {
        emailField.sendKeys(email);
        return this;
    }

    @Step("Enter password: {password}")
    public LoginPage enterPassword(String password) {
        passwordField.sendKeys(password);
        return this;
    }

    @Step("Click login button")
    public void clickLoginButton() {
        loginButton.click();
    }
}
