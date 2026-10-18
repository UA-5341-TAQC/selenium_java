package org.academy.ui.modals;

import io.qameta.allure.Step;
import lombok.Getter;
import org.academy.ui.pages.Admin.BaseAdminPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.nio.file.Path;

@Getter
public class AdminAddEditEventsNewsModal extends BaseAdminPage {

    @FindBy(css = ".modal-container:has(#event-title)")
    private WebElement modal;

    @FindBy(css = ".modal-container:has(#event-title) .modal-header-text")
    private WebElement modalTitle;

    @FindBy(css = ".modal-container:has(#event-title) button[aria-label='Close modal']")
    private WebElement closeButton;

    @FindBy(css = ".modal-container:has(#event-title) [class*='EventModal_category-chip']")
    private WebElement categoryChip;

    @FindBy(id = "event-title")
    private WebElement titleInput;

    @FindBy(id = "event-description")
    private WebElement descriptionInput;

    @FindBy(id = "event-additional-description")
    private WebElement additionalDescriptionInput;

    @FindBy(id = "event-date")
    private WebElement dateButton;

    @FindBy(css = ".modal-container:has(#event-title) [data-testid='image-input-hidden']")
    private WebElement imageInput;

    @FindBy(css = ".modal-container:has(#event-title) [data-testid='preview-image']")
    private WebElement imagePreview;

    @FindBy(css = ".modal-container:has(#event-title) [data-testid='remove-photo-button']")
    private WebElement removePhotoButton;

    @FindBy(id = "event-link-ukr")
    private WebElement ukrainianLinkInput;

    @FindBy(id = "event-link-eng")
    private WebElement englishLinkInput;

    @FindBy(css = ".modal-container:has(#event-title) .modal-footer .btn-secondary")
    private WebElement saveDraftButton;

    @FindBy(css = ".modal-container:has(#event-title) .modal-footer .btn-primary")
    private WebElement publishButton;

    // Calendar

    @FindBy(css = "[role='dialog'][aria-label='Вибір дати']")
    private WebElement datePicker;

    @FindBy(css = "[role='dialog'][aria-label='Вибір дати'] button[aria-label='Вибрати місяць і рік']")
    private WebElement monthYearButton;

    @FindBy(xpath = "//div[@role='dialog' and @aria-label='Вибір дати']//button[normalize-space()='ОК']")
    private WebElement confirmDateButton;

    @FindBy(xpath = "//div[@role='dialog' and @aria-label='Вибір дати']//button[normalize-space()='Відмінити']")
    private WebElement cancelDateButton;

    public AdminAddEditEventsNewsModal (WebDriver driver) {
        super(driver);
    }

    @Step("Check Add/Edit Event modal is displayed")
    public boolean isDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(modal)).isDisplayed();
    }

    @Step("Get modal title")
    public String getModalTitleText() {
        return modalTitle.getText().trim();
    }

    @Step("Get event category")
    public String getCategory() {
        return categoryChip.getText().trim();
    }

    @Step("Fill event title: {title}")
    public void fillTitle(String title) {
        titleInput.clear();
        titleInput.sendKeys(title);
    }

    @Step("Fill event description: {description}")
    public void fillDescription(String description) {
        descriptionInput.clear();
        descriptionInput.sendKeys(description);
    }

    @Step("Fill additional description: {description}")
    public void fillAdditionalDescription(String description) {
        additionalDescriptionInput.clear();
        additionalDescriptionInput.sendKeys(description);
    }

    @Step("Open date picker")
    public void openDatePicker() {
        wait.until(ExpectedConditions.elementToBeClickable(dateButton)).click();
    }

    @Step("Check date picker is displayed")
    public boolean isDatePickerDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(datePicker)).isDisplayed();
    }

    @Step("Select event date: {date}")
    public void selectDate(String date) {
        openDatePicker();

        By dateLocator = By.cssSelector(
                "[role='dialog'][aria-label='Вибір дати'] " +
                        "button[aria-label='" + date + "']"
        );

        wait.until(ExpectedConditions.elementToBeClickable(dateLocator)).click();
        wait.until(ExpectedConditions.elementToBeClickable(confirmDateButton)).click();
    }

    @Step("Open month and year selector")
    public void openMonthYearSelector() {
        wait.until(ExpectedConditions.elementToBeClickable(monthYearButton)).click();
    }

    @Step("Cancel date selection")
    public void cancelDateSelection() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelDateButton)).click();
    }

    @Step("Get selected event date")
    public String getSelectedDate() {
        return dateButton.getText().trim();
    }

    @Step("Upload event image: {path}")
    public void uploadImage(String path) {
        imageInput.sendKeys(Path.of(path).toAbsolutePath().toString());
    }

    @Step("Check image preview is displayed")
    public boolean isImagePreviewDisplayed() {
        return !modal.findElements(
                By.cssSelector("[data-testid='preview-image']")
        ).isEmpty();
    }

    @Step("Remove event image")
    public void removeImage() {
        wait.until(ExpectedConditions.elementToBeClickable(removePhotoButton)).click();
    }

    @Step("Fill Ukrainian link: {link}")
    public void fillUkrainianLink(String link) {
        ukrainianLinkInput.clear();
        ukrainianLinkInput.sendKeys(link);
    }

    @Step("Fill English link: {link}")
    public void fillEnglishLink(String link) {
        englishLinkInput.clear();
        englishLinkInput.sendKeys(link);
    }

    @Step("Save event as draft")
    public void saveAsDraft() {
        wait.until(ExpectedConditions.elementToBeClickable(saveDraftButton)).click();
    }

    @Step("Publish event")
    public void publishEvent() {
        wait.until(ExpectedConditions.elementToBeClickable(publishButton)).click();
    }

    @Step("Check publish button is enabled")
    public boolean isPublishEnabled() {
        return publishButton.isEnabled();
    }

    @Step("Check save draft button is enabled")
    public boolean isSaveDraftEnabled() {
        return saveDraftButton.isEnabled();
    }

    @Step("Close Add/Edit Event modal")
    public void closeModal() {
        wait.until(ExpectedConditions.elementToBeClickable(closeButton)).click();
    }
}
