package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class FooterComponent extends BaseComponent {

    @FindBy(css = ".logo-container a")
    private List<WebElement> logoLinks;

    @FindBy(css = ".newsletter-title")
    private WebElement newsletterTitle;

    @FindBy(css = ".newsletter-form input[type='email']")
    private WebElement newsletterEmailInput;

    @FindBy(css = ".newsletter-form button.submit-btn")
    private WebElement newsletterSubmitButton;

    @FindBy(css = ".main-block .menu a")
    private List<WebElement> menuColumnLinks;

    @FindBy(css = ".main-block .about-us a")
    private List<WebElement> aboutUsColumnLinks;

    @FindBy(css = ".main-block .hippotherapy a")
    private List<WebElement> hippotherapyColumnLinks;

    @FindBy(xpath = ".//div[contains(@class, 'main-contacts')]//button[contains(., '@')]")
    private WebElement emailContactButton;

    @FindBy(xpath = ".//div[contains(@class, 'main-contacts')]//button[contains(., '+')]")
    private WebElement phoneContactButton;

    @FindBy(css = ".social-media button.contact-item, .social-media a")
    private List<WebElement> socialMediaButtons;

    @FindBy(css = ".scrolling-text")
    private WebElement scrollingTextElement;

    public FooterComponent(WebElement element) {
        super(element);
    }

    @Step("Click footer logo")
    public void clickLogo() {
        if (logoLinks.isEmpty()) {
            throw new NoSuchElementException("Footer logo link is missing from DOM");
        }
        wait.until(ExpectedConditions.elementToBeClickable(logoLinks.get(0))).click();
    }

    @Step("Check if newsletter subscription block is displayed")
    public boolean isNewsletterDisplayed() {
        try {
            return newsletterTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Get newsletter subscription title")
    public String getNewsletterTitle() {
        wait.until(ExpectedConditions.visibilityOf(newsletterTitle));
        return newsletterTitle.getText().trim();
    }

    @Step("Enter newsletter email: {email}")
    public FooterComponent enterNewsletterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(newsletterEmailInput));
        newsletterEmailInput.clear();
        newsletterEmailInput.sendKeys(email);
        return this;
    }

    @Step("Submit newsletter subscription")
    public FooterComponent submitNewsletter() {
        wait.until(ExpectedConditions.elementToBeClickable(newsletterSubmitButton)).click();
        return this;
    }

    @Step("Get Menu column links")
    public List<String> getMenuColumnLinkTexts() {
        return menuColumnLinks.stream().map(WebElement::getText).map(String::trim).collect(Collectors.toList());
    }

    @Step("Get About Us column links")
    public List<String> getAboutUsColumnLinkTexts() {
        return aboutUsColumnLinks.stream().map(WebElement::getText).map(String::trim).collect(Collectors.toList());
    }

    @Step("Get Hippotherapy column links")
    public List<String> getHippotherapyColumnLinkTexts() {
        return hippotherapyColumnLinks.stream().map(WebElement::getText).map(String::trim).collect(Collectors.toList());
    }

    @Step("Get email contact text")
    public String getEmailContactText() {
        wait.until(ExpectedConditions.visibilityOf(emailContactButton));
        return emailContactButton.getText().trim();
    }

    @Step("Click email contact button to copy")
    public CopyTextButtonComponent getEmailCopyButton() {
        return new CopyTextButtonComponent(emailContactButton);
    }

    @Step("Get phone contact text")
    public String getPhoneContactText() {
        wait.until(ExpectedConditions.visibilityOf(phoneContactButton));
        return phoneContactButton.getText().trim();
    }

    @Step("Click phone contact button to copy")
    public CopyTextButtonComponent getPhoneCopyButton() {
        return new CopyTextButtonComponent(phoneContactButton);
    }

    @Step("Get social media items count")
    public int getSocialMediaCount() {
        return socialMediaButtons.size();
    }

    @Step("Get social media button texts")
    public List<String> getSocialMediaTexts() {
        return socialMediaButtons.stream().map(WebElement::getText).map(String::trim).collect(Collectors.toList());
    }

    @Step("Get scrolling motto ticker text")
    public String getScrollingMottoText() {
        wait.until(ExpectedConditions.visibilityOf(scrollingTextElement));
        return scrollingTextElement.getText().trim();
    }
}
