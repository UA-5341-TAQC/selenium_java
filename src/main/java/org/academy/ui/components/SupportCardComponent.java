package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SupportCardComponent extends BaseComponent {

    @FindBy(css = "p[class*='SupportCard_description']")
    private WebElement descriptionElement;

    @FindBy(css = "img[class*='SupportCard_image']")
    private WebElement imageElement;


    public SupportCardComponent(WebElement element) {
        super(element);
    }

    @Step("Get SupportCard description")
    public String getDescriptionText() {
        wait.until(ExpectedConditions.visibilityOf(descriptionElement));
        return descriptionElement.getText().trim();
    }

    @Step("Check if SupportCard image is displayed")
    public boolean isImageDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(imageElement));
        return imageElement.isDisplayed();
    }


}