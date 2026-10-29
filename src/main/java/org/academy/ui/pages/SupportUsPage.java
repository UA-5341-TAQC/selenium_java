package org.academy.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SupportUsPage extends BasePublicPage {

    @FindBy(css = "[class*='IntroSection_title']")
    private WebElement introTitleElement;

    @FindBy(css = "[class*='IntroSection_additional-info']")
    private WebElement introDescriptionElement;

    @FindBy(css = "[class*='PartnersSection_title']")
    private WebElement partnersTitleElement;

    @FindBy(css = "[class*='PartnersSection_image']")
    private List<WebElement> partnerImageElements;

    @FindBy(css = "[class*='PartnersSection_caption']")
    private List<WebElement> partnerCardTitleElements;

    @FindBy(css = "[class*='ContactSection_title']")
    private WebElement contactSectionTitleElement;

    @FindBy(css = "[class*='ContactSection_description']")
    private List<WebElement> contactDescriptionElements;

    @FindBy(css = "[data-testid='contact-field-name']")
    private WebElement nameInput;

    @FindBy(css = "[data-testid='contact-field-email']")
    private WebElement emailInput;

    @FindBy(css = "[data-testid='contact-field-subject']")
    private WebElement subjectInput;

    @FindBy(css = "[data-testid='contact-field-message']")
    private WebElement messageInput;

    @FindBy(css = "[class*='ContactSection_submit']")
    private WebElement contactSubmitButtonElement;

    @FindBy(css = "[class*='CtaSection_root'] a[href='/donate']")
    private WebElement donateLinkElement;

    public SupportUsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Read Support Us intro title")
    public String getIntroTitle() {
        return getText(introTitleElement);
    }

    @Step("Read Support Us intro description")
    public String getIntroDescription() {
        return getText(introDescriptionElement);
    }

    @Step("Read partners section title")
    public String getPartnersTitle() {
        return getText(partnersTitleElement);
    }

    @Step("Read partner images count")
    public int getPartnerImagesCount() {
        return waitUntilAllVisible(partnerImageElements).size();
    }

    @Step("Read partner card titles")
    public List<String> getPartnerCardTitles() {
        return getTexts(waitUntilAllVisible(partnerCardTitleElements));
    }

    @Step("Read contact section title")
    public String getContactSectionTitle() {
        scrollIntoView(contactSectionTitleElement);
        return getText(contactSectionTitleElement);
    }

    @Step("Read contact section descriptions")
    public List<String> getContactDescriptions() {
        scrollIntoView(contactSectionTitleElement);
        return getTexts(waitUntilAllVisible(contactDescriptionElements));
    }

    @Step("Enter contact name")
    public void enterName(String name) {
        scrollIntoView(nameInput);
        nameInput.clear();
        nameInput.sendKeys(name);
    }

    @Step("Enter contact email")
    public void enterEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    @Step("Enter contact subject")
    public void enterSubject(String subject) {
        subjectInput.clear();
        subjectInput.sendKeys(subject);
    }

    @Step("Enter contact message")
    public void enterMessage(String message) {
        messageInput.clear();
        messageInput.sendKeys(message);
    }

    @Step("Check if contact submit button is displayed")
    public boolean isContactSubmitButtonDisplayed() {
        return isVisible(contactSubmitButtonElement);
    }

    @Step("Check if contact submit button is enabled")
    public boolean isContactSubmitButtonEnabled() {
        return contactSubmitButtonElement.isEnabled();
    }

    @Step("Click contact submit button")
    public void clickContactSubmitButton() {
        scrollIntoView(contactSubmitButtonElement);
        click(contactSubmitButtonElement);
    }

    @Step("Check if donate link is displayed")
    public boolean isDonateLinkDisplayed() {
        return isVisible(donateLinkElement);
    }

    @Step("Read donate link href")
    public String getDonateLinkHref() {
        return getDomAttribute(donateLinkElement, "href");
    }

    @Step("Click donate link")
    public void clickDonateLink() {
        scrollIntoView(donateLinkElement);
        click(donateLinkElement);
    }
}