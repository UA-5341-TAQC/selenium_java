package org.academy.ui.components.admin.whoweare;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WhoWeAreCardComponent extends WhoWeAreDescriptionEditorComponent {

    private static final String IMAGE_LOADED_SCRIPT =
            "return arguments[0].complete && typeof arguments[0].naturalWidth !== 'undefined' && arguments[0].naturalWidth > 0;";

    @FindBy(css = "img")
    private WebElement cardImage;

    public WhoWeAreCardComponent(WebElement rootElement) {
        super(rootElement);
    }

    @Step("Check if card image is displayed and loaded")
    public boolean isImageDisplayed() {
        return isVisible(cardImage) && Boolean.TRUE.equals(js.executeScript(IMAGE_LOADED_SCRIPT, cardImage));
    }
}
