package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.net.URI;

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

    @FindBy(css = ".main-block .menu a, .main-block .about-us a, .main-block .hippotherapy a")
    private List<WebElement> navigationLinks;

    @FindBy(xpath = ".//div[contains(@class, 'main-contacts')]//button[contains(., '@')]")
    private WebElement emailContactButton;

    @FindBy(xpath = ".//div[contains(@class, 'main-contacts')]//button[contains(., '+')]")
    private WebElement phoneContactButton;

    @FindBy(css = ".social-media button.contact-item, .social-media a")
    private List<WebElement> socialMediaButtons;

    @FindBy(css = ".scrolling-text")
    private WebElement scrollingTextElement;

    @FindBy(css = "span.title")
    private List<WebElement> subTitlesText;

    public FooterComponent(WebElement element) {
        super(element);
    }

    public Rectangle getBounds() {
        return waitUntilVisible(rootElement).getRect();
    }

    public FooterComponent scrollIntoView() {
        scrollIntoView(rootElement);
        return this;
    }

    @Step("Get footer navigation paths")
    public List<String> getNavigationPaths() {
        return waitUntilAllVisible(navigationLinks).stream()
                .map(link -> URI.create(link.getDomProperty("href")).getPath())
                .toList();
    }

    @Step("Click footer navigation link: {path}")
    public void clickNavigationLink(String path) {
        WebElement link = waitUntilAllVisible(navigationLinks).stream()
                .filter(item -> path.equals(URI.create(item.getDomProperty("href")).getPath()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Footer link not found: " + path));
        scrollIntoView(link);
        click(link);
    }

    @Step("Click footer social media button: {name}")
    public void clickSocialMedia(String name) {
        WebElement button = findByText(socialMediaButtons, name);
        scrollIntoView(button);
        click(button);
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

    @Step("Get SubTitles text")
    public List<String> getSubtitlesTexts() {
        return getTexts(subTitlesText);
    }
}


