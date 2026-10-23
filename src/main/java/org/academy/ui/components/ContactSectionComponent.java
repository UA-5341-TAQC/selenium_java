package org.academy.ui.components;

import io.qameta.allure.Step;
import org.academy.ui.modals.ContactModal;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ContactSectionComponent extends BaseComponent {

    @FindBy(css = "h2[class*='ContactSection_contact-title'], h2")
    private WebElement title;

    @FindBy(css = "div[class*='ContactSection_contact-button'] h4, h4")
    private WebElement description;

    @FindBy(css = "div[class*='ContactSection_contact-button'] button, button")
    private WebElement contactButton;

    @FindBy(css = "video")
    private WebElement video;

    public ContactSectionComponent(WebElement element) {
        super(element);
    }

    private String normalize(String text) {
        return text.replaceAll("\\s+", " ").trim();
    }

    @Step("Get Contact section title")
    public String getTitleText() {
        return normalize(getText(title));
    }

    @Step("Get Contact section description")
    public String getDescriptionText() {
        return normalize(getText(description));
    }

    @Step("Get Contact section subtitle")
    public String getSubtitleText() {
        return getDescriptionText();
    }

    @Step("Get Contact button text")
    public String getContactButtonText() {
        return getText(contactButton).trim();
    }

    @Step("Click Contact button to open ContactFormPopUpComponent")
    public ContactFormPopUpComponent clickContactButton() {
        click(contactButton);
        WebElement popupRoot = waitUntilVisible(By.cssSelector(ContactFormPopUpComponent.ROOT_SELECTOR));
        return new ContactFormPopUpComponent(popupRoot);
    }

    @Step("Click Contact button to open ContactModal")
    public ContactModal clickContactButtonAsModal() {
        click(contactButton);
        return new ContactModal(waitUntilVisible(ContactModal.ROOT_LOCATOR));
    }

    @Step("Click Contact button to open ContactModal")
    public ContactModal openContactModal() {
        return clickContactButtonAsModal();
    }

    @Step("Click enroll/contact button to open ContactFormPopUpComponent")
    public ContactFormPopUpComponent clickEnrollButton() {
        return clickContactButton();
    }

    @Step("Check if Contact Section video is present/displayed")
    public boolean isVideoDisplayed() {
        return isDisplayed(video);
    }

    @Step("Check if background video is playing")
    public boolean isVideoPlaying() {
        double startTime = getVideoCurrentTime();
        try {
            wait.until(driver -> getVideoCurrentTime() > startTime);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    private double getVideoCurrentTime() {
        String value = video.getDomProperty("currentTime");
        return value == null ? 0 : Double.parseDouble(value);
    }

    @Step("Check if background video is loaded")
    public boolean isVideoLoaded() {
        String readyState = video.getDomProperty("readyState");
        return readyState != null && Integer.parseInt(readyState) >= 2;
    }
}
