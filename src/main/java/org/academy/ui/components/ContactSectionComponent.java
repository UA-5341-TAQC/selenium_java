package org.academy.ui.components;

import io.qameta.allure.Step;
import org.academy.ui.modals.ContactModal;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ContactSectionComponent extends BaseComponent {

    @FindBy(css = "h2[class*='ContactSection_contact-title']")
    private WebElement title;

    @FindBy(css = "div[class*='ContactSection_contact-button'] h4")
    private WebElement description;

    @FindBy(css = "div[class*='ContactSection_contact-button'] button")
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

    @Step("Get Contact button text")
    public String getContactButtonText() {
        return getText(contactButton).trim();
    }

    @Step("Click Contact button")
    public ContactModal clickContactButton() {
        click(contactButton);
        return new ContactModal(waitUntilVisible(ContactModal.ROOT_LOCATOR));
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
