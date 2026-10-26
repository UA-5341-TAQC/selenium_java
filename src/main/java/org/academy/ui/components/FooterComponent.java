package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

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
        click(logoLinks.get(0));
    }

    @Step("Check if newsletter subscription block is displayed")
    public boolean isNewsletterDisplayed() {
        return isDisplayed(newsletterTitle);
    }

    @Step("Get newsletter subscription title")
    public String getNewsletterTitle() {
        return getText(newsletterTitle);
    }

    @Step("Enter newsletter email: {email}")
    public FooterComponent enterNewsletterEmail(String email) {
        fillInput(newsletterEmailInput, email);
        return this;
    }

    @Step("Submit newsletter subscription")
    public FooterComponent submitNewsletter() {
        click(newsletterSubmitButton);
        return this;
    }

    @Step("Get Menu column links")
    public List<String> getMenuColumnLinkTexts() {
        return getTexts(menuColumnLinks);
    }

    @Step("Get About Us column links")
    public List<String> getAboutUsColumnLinkTexts() {
        return getTexts(aboutUsColumnLinks);
    }

    @Step("Get Hippotherapy column links")
    public List<String> getHippotherapyColumnLinkTexts() {
        return getTexts(hippotherapyColumnLinks);
    }

    @Step("Get email contact text")
    public String getEmailContactText() {
        return getText(emailContactButton);
    }

    @Step("Click email contact button to copy")
    public CopyTextButtonComponent getEmailCopyButton() {
        return new CopyTextButtonComponent(emailContactButton);
    }

    @Step("Get phone contact text")
    public String getPhoneContactText() {
        return getText(phoneContactButton);
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
        return getTexts(socialMediaButtons);
    }

    @Step("Get scrolling motto ticker text")
    public String getScrollingMottoText() {
        return getText(scrollingTextElement);
    }
}


