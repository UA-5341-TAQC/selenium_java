package org.academy.ui.components.admin.whoweare;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class WhoWeAreCardComponent extends WhoWeAreDescriptionEditorComponent {

    private static final String IMAGE_LOADED_SCRIPT =
            "return arguments[0].complete && typeof arguments[0].naturalWidth !== 'undefined' && arguments[0].naturalWidth > 0;";

    @FindBy(css = "[class*='WhoWeAreImageInput_container']")
    private WebElement imageContainer;

    @FindBy(css="input[data-testid='image-input-hidden']")
    private WebElement imageInput;

    @FindBy(css = "img[data-testid='preview-image'], img")
    private List<WebElement> previewImages;

    public WhoWeAreCardComponent(WebElement rootElement) {
        super(rootElement);
    }

    @Step("Check if card image is displayed and loaded")
    public boolean isImageDisplayed() {
        waitUntilVisible(imageContainer);
        if (!previewImages.isEmpty() && isVisible(previewImages.get(0))) {
            WebElement img = previewImages.get(0);
            return wait.until(d -> Boolean.TRUE.equals(js.executeScript(IMAGE_LOADED_SCRIPT, img)));
        }
        String bgImage = imageContainer.getCssValue("background-image");
        String styleAttr = getDomAttribute(imageContainer, "style");
        return (bgImage != null && bgImage.contains("url("))
                || (styleAttr != null && styleAttr.contains("url("));
    }

    @Step("Get current preview image source")
    public String getPreviewImageSrc() {
        if (previewImages.isEmpty()) {
            return null;
        }

        return previewImages.get(0).getAttribute("src");
    }

    @Step("Wait until preview image changes")
    public void waitForPreviewImageChanged(String previousSrc) {
        wait.until(driver -> {
            String currentSrc = getPreviewImageSrc();
            return currentSrc != null && !currentSrc.equals(previousSrc);
        });
    }

    @Step("Upload image to People card")
    public void uploadImage(String filePath) {
        imageInput.sendKeys(filePath);
    }
}
