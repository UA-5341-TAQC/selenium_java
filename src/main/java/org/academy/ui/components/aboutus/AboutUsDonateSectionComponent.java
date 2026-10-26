package org.academy.ui.components.aboutus;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AboutUsDonateSectionComponent extends BaseComponent {

    @FindBy(css = "div[class*='CtaSection_title']")
    private WebElement titleElement;

    @FindBy(css = "div[class*='CtaSection_description']")
    private WebElement descriptionElement;

    @FindBy(css = "img[class*='BackgroundMedia_media']")
    private WebElement backgroundImageElement;

    @FindBy(css = "div[class*='CtaSection_actions'] a[class*='Button_button'][class*='Button_primary'][href*='donate']")
    private WebElement donateButtonElement;

    @FindBy(css = "div[class*='CtaSection_actions'] a[class*='Button_button'][class*='Button_secondary'][href*='donate']")
    private WebElement becomePartnerButtonElement;

    public AboutUsDonateSectionComponent(WebElement element) {
        super(element);
    }

    @Step("Get AboutUs Donation section title")
    public String getTitleText() {
        wait.until(ExpectedConditions.visibilityOf(titleElement));
        return titleElement.getText().trim();
    }

    @Step("Get AboutUs Donation section description")
    public String getDescriptionText() {
        wait.until(ExpectedConditions.visibilityOf(descriptionElement));
        return descriptionElement.getText().trim();
    }

    @Step("Check if Background Image is displayed")
    public boolean isBackgroundImageDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(backgroundImageElement));
        return backgroundImageElement.isDisplayed();
    }

    @Step("Click Donate CTA button")
    public void clickDonateCtaButton() {
        wait.until(ExpectedConditions.elementToBeClickable(donateButtonElement)).click();
    }

    @Step("Click Become Partner CTA button")
    public void clickBecomePartnerCtaButton() {
        wait.until(ExpectedConditions.elementToBeClickable(becomePartnerButtonElement)).click();
    }
}