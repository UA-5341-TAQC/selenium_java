package org.academy.ui.components.admin.whoweare;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WhoWeAreCardComponent extends WhoWeAreDescriptionEditorComponent {

    private static final String IMAGE_LOADED_SCRIPT =
            "return arguments[0].complete && typeof arguments[0].naturalWidth !== 'undefined' && arguments[0].naturalWidth > 0;";
    private static final Pattern URL_PATTERN =
            Pattern.compile("url\\([\"']?([^\"')]+)[\"']?\\)");

    @FindBy(css = "[class*='WhoWeAreImageInput_container']")
    private WebElement imageContainer;

    @FindBy(css="input[data-testid='image-input-hidden']")
    private WebElement imageInput;

    @FindBy(css = "img[data-testid='preview-image'], img")
    private List<WebElement> previewImages;

    public WhoWeAreCardComponent(WebElement rootElement) {
        super(rootElement);
    }

    private static String extractUrl(String text) {
        if (text == null || text.isBlank()) {
            return null;
        }
        Matcher matcher = URL_PATTERN.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
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
        return extractUrl(bgImage) != null || extractUrl(styleAttr) != null;
    }

    @Step("Get current preview image source")
    public String getPreviewImageSrc() {
        if (!previewImages.isEmpty() && isVisible(previewImages.get(0))) {
            return previewImages.get(0).getAttribute("src");
        }
        if (imageContainer != null) {
            String bgUrl = extractUrl(imageContainer.getCssValue("background-image"));
            if (bgUrl != null) {
                return bgUrl;
            }
            String styleUrl = extractUrl(getDomAttribute(imageContainer, "style"));
            if (styleUrl != null) {
                return styleUrl;
            }
        }
        return null;
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
