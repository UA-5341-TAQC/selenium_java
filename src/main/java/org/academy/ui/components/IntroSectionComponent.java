package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
        wait.until(ExpectedConditions.visibilityOf(title));
        return title.getText().trim();
    }

    @Step("Get Hero description")
    public String getDescriptionText() {
        wait.until(ExpectedConditions.visibilityOf(description));
        return description.getText().trim();
    }

    @Step("Click Hero CTA button")
    public void clickCtaButton() {
        wait.until(ExpectedConditions.elementToBeClickable(ctaButton)).click();
    }

    @Step("Check if Hero image is displayed")
    public boolean isImageDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(image));
        return image.isDisplayed();
    }
}
