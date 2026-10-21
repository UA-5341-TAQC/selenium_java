package org.academy.ui.components.hippotherapy;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.academy.ui.components.ContactFormPopUpComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ContactSectionComponent extends BaseComponent {

    @FindBy(css = "h2[class*='ContactSection_contact-title'], h2")
    private WebElement title;

    @FindBy(css = "div[class*='ContactSection_contact-button'] h4, h4")
    private WebElement subtitle;

    @FindBy(css = "div[class*='ContactSection_contact-button'] button, button")
    private WebElement contactButton;

    @FindBy(css = "video")
    private WebElement videoElement;

    public ContactSectionComponent(WebElement element) {
        super(element);
    }

    @Step("Get Contact Section title")
    public String getTitleText() {
        return getText(title);
    }

    @Step("Get Contact Section subtitle")
    public String getSubtitleText() {
        return getText(subtitle);
    }

    @Step("Check if Contact Section video is present")
    public boolean isVideoDisplayed() {
        return isDisplayed(videoElement);
    }

    @Step("Click Contact button to open ContactFormPopUpComponent")
    public ContactFormPopUpComponent clickContactButton() {
        click(contactButton);
        WebElement popupRoot = waitUntilVisible(By.cssSelector(ContactFormPopUpComponent.ROOT_SELECTOR));
        return new ContactFormPopUpComponent(popupRoot);
    }

    @Step("Click enroll/contact button to open ContactFormPopUpComponent")
    public ContactFormPopUpComponent clickEnrollButton() {
        return clickContactButton();
    }
}
