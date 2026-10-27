package org.academy.ui.modals.admin;

import io.qameta.allure.Step;
import org.academy.ui.modals.BaseModal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Shared fixed-size image cropper. The requested output size belongs to the
 * image input.
 */
public class CropperModal extends BaseModal {

    public static final String ROOT_SELECTOR = ".modal-container:has(> .modal-body > .cropper-container)";

    @FindBy(css = ":scope > .modal-header .modal-header-text")
    private WebElement title;

    @FindBy(css = "img[alt='Crop target']")
    private WebElement image;

    @FindBy(css = ".ReactCrop__crop-selection")
    private WebElement selection;

    @FindBy(css = ".cropper-size-text")
    private WebElement outputSize;

    @FindBy(css = ":scope > .modal-footer > .btn-primary")
    private WebElement confirmButton;

    @FindBy(css = ":scope > .modal-footer > .btn-secondary")
    private WebElement cancelButton;

    public CropperModal(WebElement rootElement) {
        super(rootElement);
    }

    public static CropperModal waitFor(WebDriverWait wait) {
        WebElement root = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(ROOT_SELECTOR)));
        CropperModal modal = new CropperModal(root);
        modal.waitUntilReady();
        return modal;
    }

    @Step("Check image crop modal is displayed")
    public boolean isDisplayed() {
        return waitUntilVisible(rootElement).isDisplayed();
    }

    @Step("Check crop image is displayed")
    public boolean isCropImageDisplayed() {
        return waitUntilVisible(image).isDisplayed();
    }

    public void waitUntilReady() {
        wait.until(d -> image.isDisplayed()
                && "true".equals(image.getDomProperty("complete"))
                && Integer.parseInt(image.getDomProperty("naturalWidth")) > 0);
        waitUntilVisible(selection);
    }

    @Step("Get image crop modal title")
    public String getModalTitleText() {
        return getText(title).trim();
    }

    public String getOutputSizeText() {
        return getText(outputSize);
    }

    /** Moves the fixed frame; resizing and zoom are not offered by this cropper. */
    @Step("Move crop selection by {xOffset}, {yOffset} rendered pixels")
    public CropperModal moveSelectionBy(int xOffset, int yOffset) {
        waitUntilReady();
        actions.dragAndDropBy(selection, xOffset, yOffset).perform();
        return this;
    }

    @Step("Apply image cropping")
    public void confirmCrop() {
        waitUntilReady();
        click(confirmButton);
        waitUntilClosed();
    }

    /** The image input may keep the uncropped upload after cancellation. */
    @Step("Cancel image cropping")
    public void cancelCrop() {
        click(cancelButton);
        waitUntilClosed();
    }

    @Step("Dismiss image cropping")
    public void closeModal() {
        requestClose();
        waitUntilClosed();
    }
}
