package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class CtaSectionComponent extends BaseComponent {

    @FindBy(css = "h2[class*='CtaSection_title']")
    private WebElement titleElement;

    @FindBy(css = "p[class*='CtaSection_description']")
    private WebElement descriptionElement;

    @FindBy(css = "video[class*='BackgroundMedia_media']")
    private WebElement backgroundMediaElement;

    @FindBy(css = "div[class*='CtaSection_actions'] a[class*='Button_button'][class*='Button_primary'][href*='donate']")
    private WebElement donateButtonElement;

    @FindBy(css = "div[class*='CtaSection_actions'] a[class*='Button_button'][class*='Button_secondary'][href*='donate']")
    private WebElement becomePartnerButtonElement;


    public CtaSectionComponent(WebElement element) {
        super(element);
    }

    @Step("Get CTA title")
    public String getTitleText() {
        wait.until(ExpectedConditions.visibilityOf(titleElement));
        return titleElement.getText().trim();
    }

    @Step("Get CTA description")
    public String getDescriptionText() {
        wait.until(ExpectedConditions.visibilityOf(descriptionElement));
        return descriptionElement.getText().trim();
    }

    @Step("Check if CTA Section video is displayed")
    public boolean isCTAVideoDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(backgroundMediaElement));
        return backgroundMediaElement.isDisplayed();
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
