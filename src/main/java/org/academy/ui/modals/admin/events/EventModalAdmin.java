package org.academy.ui.modals.admin.events;

import io.qameta.allure.Step;
import lombok.Getter;
import org.academy.ui.modals.BaseModal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class EventModalAdmin extends BaseModal {

    // Modal

    @FindBy(css = ".modal-container:has(#event-title)")
    private WebElement modal;

    @FindBy(css = ".modal-container:has(#event-title) .modal-header-text")
    private WebElement modalTitle;

    @FindBy(css = ".modal-container:has(#event-title) button[aria-label='Close modal']")
    private WebElement closeButton;

    @FindBy(css = ".modal-container:has(#event-title) [class*='EventModal_category-chip']")
    private WebElement categoryChip;

    // Event fields

    @FindBy(id = "event-title")
    private WebElement titleInput;

    @FindBy(id = "event-description")
    private WebElement descriptionInput;

    @FindBy(id = "event-additional-description")
    private WebElement additionalDescriptionInput;

    // Image

    @FindBy(id = "event-image")
    private WebElement imageInput;

    @FindBy(css = "[class*='EventImageInput_container']")
    private WebElement imageContainer;

    @FindBy(css = ".modal-container:has(#event-title) img[data-testid='preview-image']")
    private WebElement imagePreview;

    // Links

    @FindBy(id = "event-link-ukr")
    private WebElement ukrainianLinkInput;

    @FindBy(id = "event-link-eng")
    private WebElement englishLinkInput;

    // Action buttons

    @FindBy(css = ".modal-container:has(#event-title) .modal-footer .btn-secondary")
    private WebElement saveDraftButton;

    @FindBy(css = ".modal-container:has(#event-title) .modal-footer .btn-primary")
    private WebElement publishButton;

    // Calendar

    @FindBy(id = "event-date")
    private WebElement dateButton;

    @FindBy(css = "[role='dialog'][aria-label='Вибір дати']")
    private WebElement datePicker;

    @FindBy(css = "[role='dialog'][aria-label='Вибір дати'] button[aria-label='Вибрати місяць і рік']")
    private WebElement monthYearButton;

    @FindBy(xpath = "//div[@role='dialog' and @aria-label='Вибір дати']//button[normalize-space()='ОК']")
    private WebElement confirmDateButton;

    @FindBy(xpath = "//div[@role='dialog' and @aria-label='Вибір дати']//button[normalize-space()='Відмінити']")
    private WebElement cancelDateButton;

    public EventModalAdmin(WebElement rootElement) {
        super(rootElement);
    }

    // Modal methods

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

    @Step("Close Add/Edit Event modal")
    public void closeModal() {
        wait.until(ExpectedConditions.elementToBeClickable(closeButton)).click();
    }

    // Event fields methods

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

    // Calendar methods

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

        LocalDate selectedDate = LocalDate.parse(
                date,
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
        );

        String formattedDate = selectedDate.format(
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
        );

        By dateLocator = By.cssSelector(
                "[role='dialog'][aria-label='Вибір дати'] " +
                        "button[aria-label='" + date + "']"
        );

        wait.until(ExpectedConditions.elementToBeClickable(dateLocator)).click();
        wait.until(ExpectedConditions.elementToBeClickable(confirmDateButton)).click();
        wait.until(
                ExpectedConditions.invisibilityOf(datePicker)
        );
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
        return wait.until(
                ExpectedConditions.visibilityOf(imagePreview)
        ).isDisplayed();
    }

    @Step("Get uploaded image source")
    public String getImageSource() {
        return imagePreview.getDomAttribute("src");
    }

    @Step("Check image upload container is displayed")
    public boolean isImageContainerDisplayed() {
        return imageContainer.isDisplayed();
    }

    // Link methods

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

    // Action methods

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
}
