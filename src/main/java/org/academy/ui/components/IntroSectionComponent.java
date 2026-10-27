package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class IntroSectionComponent extends BaseComponent {

    @FindBy(css = "h1[class*='IntroSection_title'], [class*='IntroSection_title']")
    private WebElement title;

    @FindBy(css = "p[class*='IntroSection_subtitle'], p[class*='IntroSection_description'], [class*='IntroSection_description']")
    private WebElement description;

    @FindBy(css = "button[class*='IntroSection_button'], a[class*='IntroSection_button'], button, a")
    private WebElement ctaButton;

    @FindBy(css = "img[class*='IntroSection_bg-img'], img[class*='IntroSection_image'], img")
    private WebElement image;

    public IntroSectionComponent(WebElement element) {
        super(element);
    }

    @Step("Get Hero title")
    public String getTitleText() {
        return getText(title);
    }

    @Step("Get Hero description")
    public String getDescriptionText() {
        return getText(description);
    }

    @Step("Get Hero CTA button text")
    public String getCtaButtonText() {
        return getText(ctaButton);
    }

    @Step("Click Hero CTA button")
    public void clickCtaButton() {
        click(ctaButton);
    }

    @Step("Check if Hero title is displayed")
    public boolean isTitleDisplayed() {
        return isVisible(title);
    }

    @Step("Check if Hero description is displayed")
    public boolean isDescriptionDisplayed() {
        return isVisible(description);
    }

    @Step("Check if Hero image is displayed")
    public boolean isImageDisplayed() {
        return isVisible(image);
    }

    @Step("Check if Hero CTA button is displayed")
    public boolean isCtaButtonDisplayed() {
        return isVisible(ctaButton);
    }
}
