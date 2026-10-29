package org.academy.ui.components.contact;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.academy.ui.components.CopyTextButtonComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ContactDetailsSectionComponent extends BaseComponent {

    @FindBy(css = "h1[class*='contact-details-title']")
    private WebElement pageTitle;

    @FindBy(css = "p[class*='contact-details-description']")
    private WebElement pageDescription;

    @FindBy(xpath = ".//div[contains(@class, 'contact-details-item')][.//p[contains(text(), '@')]]//p")
    private WebElement emailText;

    @FindBy(css = "button[aria-label*='email'], button[aria-label*='Email'], button[aria-label*='пошт']")
    private WebElement emailCopyButton;

    @FindBy(xpath = ".//div[contains(@class, 'contact-details-item')][.//p[contains(text(), '+')]]//p")
    private WebElement phoneText;

    @FindBy(css = "button[aria-label*='телефон'], button[aria-label*='phone'], button[aria-label*='Phone']")
    private WebElement phoneCopyButton;

    @FindBy(css = "p[class*='contact-details-address']")
    private WebElement addressText;

    @FindBy(css = "a[class*='social-links-item']")
    private List<WebElement> socialMediaLinks;

    @FindBy(css = "h2[class*='ContactDetailsSection_contact-details-block-title']")
    private List<WebElement> secondTitlesText;

    public ContactDetailsSectionComponent(WebElement element) {
        super(element);
    }

    @Step("Get contact details section title")
    public String getTitleText() {
        return getText(pageTitle);
    }

    @Step("Get contact details description")
    public String getDescriptionText() {
        return getText(pageDescription);
    }

    @Step("Get email contact text")
    public String getEmailText() {
        return getText(emailText);
    }

    @Step("Get email copy button component")
    public CopyTextButtonComponent getEmailCopyButton() {
        return new CopyTextButtonComponent(emailCopyButton);
    }

    @Step("Get phone contact text")
    public String getPhoneText() {
        return getText(phoneText);
    }

    @Step("Get phone copy button component")
    public CopyTextButtonComponent getPhoneCopyButton() {
        return new CopyTextButtonComponent(phoneCopyButton);
    }

    @Step("Get address text")
    public String getAddressText() {
        return getText(addressText);
    }

    @Step("Get social media links count")
    public int getSocialMediaLinksCount() {
        return socialMediaLinks.size();
    }

    @Step("Get social media link names")
    public List<String> getSocialMediaNames() {
        return getTexts(socialMediaLinks);
    }

    @Step("Get social media link URLs")
    public List<String> getSocialMediaUrls() {
        return socialMediaLinks.stream()
                .map(link -> getDomAttribute(link, "href"))
                .toList();
    }

    @Step("Get social media link target attributes")
    public List<String> getSocialMediaTargets() {
        return socialMediaLinks.stream()
                .map(link -> getDomAttribute(link, "target"))
                .toList();
    }

    @Step("Get second titles text")
    public List<String> getSecondTitles() {
        return getTexts(secondTitlesText);
    }
}
