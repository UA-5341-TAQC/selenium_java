package org.academy.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NotFoundPage extends BasePublicPage {

    @FindBy(css = "[class*='NotFoundIntro_text']")
    private WebElement introElement;

    @FindBy(css = "[class*='NotFoundMessage_title']")
    private WebElement messageTitleElement;

    @FindBy(css = "[class*='NotFoundMessage_description']")
    private WebElement messageDescriptionElement;

    // '/' on the Ukrainian version and '/en' on the English one.
    @FindBy(css = "a[class*='Button_tertiary'][href='/'], a[class*='Button_tertiary'][href='/en']")
    private WebElement returnHomeLink;

    public NotFoundPage(WebDriver driver) {
        super(driver);
    }

    @Step("Read 404 intro text")
    public String getIntroText() {
        return getText(introElement);
    }

    @Step("Read 404 message title")
    public String getMessageTitle() {
        return getText(messageTitleElement);
    }

    @Step("Read 404 message description")
    public String getMessageDescription() {
        return getText(messageDescriptionElement);
    }

    @Step("Check if Return to Homepage link is displayed")
    public boolean isReturnHomeLinkDisplayed() {
        return isVisible(returnHomeLink);
    }

    @Step("Read Return to Homepage link text")
    public String getReturnHomeLinkText() {
        return getText(returnHomeLink);
    }

    @Step("Read Return to Homepage link href")
    public String getReturnHomeLinkHref() {
        return getDomAttribute(returnHomeLink, "href");
    }

    @Step("Click Return to Homepage link")
    public void clickReturnHomeLink() {
        click(returnHomeLink);
    }
}