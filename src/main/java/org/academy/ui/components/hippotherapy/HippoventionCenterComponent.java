package org.academy.ui.components.hippotherapy;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HippoventionCenterComponent extends BaseComponent {

    @FindBy(css = "div[class*='HippoventionCenter_text'] h3, h3")
    private WebElement heading;

    @FindBy(css = "div[class*='HippoventionCenter_text'] ul li, ul li")
    private List<WebElement> prosItems;

    @FindBy(css = "div[class*='HippoventionCenter_text'] p, p")
    private WebElement description;

    @FindBy(css = "img[class*='HippoventionCenter_image'], img")
    private WebElement image;

    public HippoventionCenterComponent(WebElement element) {
        super(element);
    }

    @Step("Get Hippovention Center heading")
    public String getHeadingText() {
        return getText(heading);
    }

    @Step("Get Hippovention Center pros items")
    public List<String> getProsList() {
        return getTexts(prosItems);
    }

    @Step("Get Hippovention Center description")
    public String getDescriptionText() {
        return getText(description);
    }

    @Step("Check if Hippovention Center image is displayed")
    public boolean isImageDisplayed() {
        return isVisible(image);
    }

    @Step("Get Hippovention Center image src")
    public String getImageSrc() {
        return getDomAttribute(image, "src");
    }
}
