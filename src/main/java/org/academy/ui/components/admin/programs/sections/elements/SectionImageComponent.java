package org.academy.ui.components.admin.programs.sections.elements;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.academy.ui.constants.HtmlAttribute;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

/**
 * One image slot with its validation errors, scoped to the PhotoInputGroup container.
 * <pre>{@code
 * image.replaceFile(imagePath);
 * // Handle cropping if it opens.
 * image.waitUntilImageSourceChanged(previousSource);
 * }</pre>
 */
public class SectionImageComponent extends BaseComponent {

    public static final String ROOT_SELECTOR =
            ".input-group:has(> [class*='ProgramSectionImageInput_container__'])";

    @FindBy(css = "[class*='ProgramSectionImageInput_container__']")
    private WebElement imageInput;

    @FindBy(css = "input[type='file']")
    private WebElement fileInput;

    @FindBy(css = "img[data-testid='preview-image']")
    private List<WebElement> previewImages;

    @FindBy(css = "button[aria-label='Crop image']")
    private List<WebElement> cropButtons;

    @FindBy(css = "button[aria-label='Remove image']")
    private WebElement removeButton;

    @FindBy(css = ".input-error")
    private List<WebElement> validationErrors;

    public SectionImageComponent(WebElement root) {
        super(root);
    }

    @Step("Choose section image")
    public SectionImageComponent chooseFile(Path file) {
        uploadFile(fileInput, file);
        // TODO: Return the shared cropper component when available.
        return this;
    }

    public boolean hasImage() {
        return !previewImages.isEmpty();
    }

    public List<String> getValidationErrors() {
        return validationErrors.stream()
                .filter(element -> Objects.requireNonNull(element).isDisplayed())
                .map(element -> Objects.requireNonNull(element).getText())
                .toList();
    }

    public String getImageSource() {
        return previewImages.get(0).getDomAttribute(HtmlAttribute.SRC.getValue());
    }

    public boolean isEnabled() {
        return fileInput.isEnabled();
    }

    public String getAcceptedFileTypes() {
        return fileInput.getDomAttribute(HtmlAttribute.ACCEPT.getValue());
    }

    /**
     * Replaces an existing image; cropping may open.
     */
    @Step("Choose replacement section image")
    public SectionImageComponent replaceFile(Path file) {
        if (!hasImage()) {
            throw new IllegalStateException("No section image to replace");
        }
        return chooseFile(file);
    }

    /**
     * Cropping is available only while the frontend retains the raw uploaded image.
     */
    public boolean canCrop() {
        return !cropButtons.isEmpty();
    }

    @Step("Open section image cropper")
    public void openCropper() {
        actions.moveToElement(imageInput).perform();
        click(cropButtons.get(0));
        // TODO: Return the shared cropper component when available.
    }

    /**
     * Call after handling cropping, if required.
     */
    public void waitUntilImagePresent() {
        wait.until(d -> hasImage());
    }

    /**
     * Call after handling a replacement cropper, if required.
     */
    public void waitUntilImageSourceChanged(String previousSource) {
        wait.until(d -> hasImage() && !java.util.Objects.equals(previousSource, getImageSource()));
    }

    /**
     * Call only after confirming removal in the shared modal.
     */
    public void waitUntilImageRemoved() {
        wait.until(d -> !hasImage());
    }

    @Step("Open section image removal confirmation")
    public void openRemovalConfirmation() {
        actions.moveToElement(imageInput).perform();
        click(removeButton);
        // TODO: Connect the shared image-removal confirmation component.
    }
}
