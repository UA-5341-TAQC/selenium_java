package org.academy.ui.modals.admin.events;

import io.qameta.allure.Step;
import lombok.Getter;
import org.academy.ui.modals.BaseModal;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Getter
public class EventImageCropModalAdmin extends BaseModal {

    @FindBy(css = ".modal-container:has(.cropper-container)")
    private WebElement modal;

    @FindBy(css = ".modal-container:has(.cropper-container) .modal-header-text")
    private WebElement modalTitle;

    @FindBy(css = ".modal-container:has(.cropper-container) img[alt='Crop target']")
    private WebElement cropImage;

    @FindBy(css = ".modal-container:has(.cropper-container) button[aria-label='Close modal']")
    private WebElement closeButton;

    @FindBy(xpath = "//div[contains(@class,'modal-container')][.//div[contains(@class,'cropper-container')]]//button[normalize-space()='Так']")
    private WebElement confirmButton;

    @FindBy(xpath = "//div[contains(@class,'modal-container')][.//div[contains(@class,'cropper-container')]]//button[normalize-space()='Ні']")
    private WebElement cancelButton;

    public EventImageCropModalAdmin(WebElement rootElement) {
        super(rootElement);
    }

    @Step("Check image crop modal is displayed")
    public boolean isDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(modal)
        ).isDisplayed();
    }

    @Step("Get image crop modal title")
    public String getModalTitleText() {
        return modalTitle.getText().trim();
    }

    @Step("Check crop image is displayed")
    public boolean isCropImageDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(cropImage)
        ).isDisplayed();
    }

    @Step("Confirm image cropping")
    public void confirmCrop() {
        wait.until(
                ExpectedConditions.elementToBeClickable(confirmButton)
        ).click();

        wait.until(
                ExpectedConditions.invisibilityOf(modal)
        );
    }

    @Step("Cancel image cropping")
    public void cancelCrop() {
        wait.until(
                ExpectedConditions.elementToBeClickable(cancelButton)
        ).click();

        wait.until(
                ExpectedConditions.invisibilityOf(modal)
        );
    }

    @Step("Close image crop modal")
    public void closeModal() {
        wait.until(
                ExpectedConditions.elementToBeClickable(closeButton)
        ).click();

        wait.until(
                ExpectedConditions.invisibilityOf(modal)
        );
    }
}
