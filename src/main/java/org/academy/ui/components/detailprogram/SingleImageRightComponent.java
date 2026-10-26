package org.academy.ui.components.detailprogram;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class SingleImageRightComponent extends BaseComponent {

    @FindBy(css = "h2[class*='ViewSingleImageRight_title']")
    private WebElement titleElement;

    @FindBy(css = "p[class*='ViewSingleImageRight_description']")
    private WebElement descriptionElement;

    @FindBy(css = "img[class*='ViewSingleImageRight_image']")
    private WebElement imageElement;


    public SingleImageRightComponent(WebElement element) {
        super(element);
    }

    @Step("Get title text")
    public String getTitleText() {
        wait.until(ExpectedConditions.visibilityOf(titleElement));
        return titleElement.getText().trim();
    }

    @Step("Get description text")
    public String getDescriptionText() {
        wait.until(ExpectedConditions.visibilityOf(descriptionElement));
        return descriptionElement.getText().trim();
    }

    @Step("Check if image is displayed")
    public boolean isImageDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(imageElement));
        return imageElement.isDisplayed();
    }

}
