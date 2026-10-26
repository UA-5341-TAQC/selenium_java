package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
        return getText(descriptionElement);
    }

    @Step("Check if SupportCard image is displayed")
    public boolean isImageDisplayed() {
        return isVisible(imageElement);
    }


}