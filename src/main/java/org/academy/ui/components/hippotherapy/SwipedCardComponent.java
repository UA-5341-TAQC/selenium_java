package org.academy.ui.components.hippotherapy;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SwipedCardComponent extends BaseComponent {

    @FindBy(css = "img[class*='SwipedCard_swiped-img'], img")
    private WebElement imageElement;

    @FindBy(css = "[class*='SwipedCard_swiped-info']")
    private WebElement infoElement;

    public SwipedCardComponent(WebElement element) {
        super(element);
    }

    @Step("Check if SwipedCard image is displayed")
    public boolean isImageDisplayed() {
        return isVisible(imageElement);
    }

    @Step("Get SwipedCard description text")
    public String getDescriptionText() {
        return getText(infoElement);
    }
}
