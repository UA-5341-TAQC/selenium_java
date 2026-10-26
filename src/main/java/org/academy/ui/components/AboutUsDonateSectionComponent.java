package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
        return getText(titleElement);
    }

    @Step("Get AboutUs Donation section description")
    public String getDescriptionText() {
        return getText(describtionElement);
    }

    @Step("Check if Background Image is displayed")
    public boolean isBackgroundImageDisplayed() {
        return isVisible(backgroundImageElement);
    }

    @Step("Click Donate CTA button")
    public void clickDonateCtaButton() {
        click(donateButtonElement);
    }

    @Step("Click Become Partner CTA button")
    public void clickBecomePartnerCtaButton() {
        click(becomePartnerButtonElement);
    }
}
