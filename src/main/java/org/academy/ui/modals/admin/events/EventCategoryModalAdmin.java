package org.academy.ui.modals.admin.events;

import io.qameta.allure.Step;
import lombok.Getter;
import org.academy.ui.modals.BaseModal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Getter
public class EventCategoryModalAdmin extends BaseModal {

    // Add category

    @FindBy(xpath = "//div[contains(@class,'modal-container')][.//span[normalize-space()='Додати категорію']]")
    private WebElement addModal;

    @FindBy(css = ".modal-container:has(.modal-header-text) .event-category-modal-form")
    private WebElement categoryForm;

    @FindBy(xpath = "//div[contains(@class,'modal-container')][.//span[normalize-space()='Додати категорію']]//input[@id='event-category-name']")
    private WebElement addCategoryNameInput;

    @FindBy(xpath = "//div[contains(@class,'modal-container')][.//span[normalize-space()='Додати категорію']]//button[contains(@class,'event-category-modal-save-button')]")
    private WebElement addCategorySaveButton;

    // Edit category

    @FindBy(xpath = "//div[contains(@class,'modal-container')][.//span[normalize-space()='Редагувати категорію']]")
    private WebElement editModal;

    @FindBy(id = "edit-event-category-select")
    private WebElement editCategorySelect;

    @FindBy(xpath = "//div[contains(@class,'modal-container')][.//span[normalize-space()='Редагувати категорію']]//input[@id='event-category-name']")
    private WebElement editCategoryNameInput;

    @FindBy(xpath = "//div[contains(@class,'modal-container')][.//span[normalize-space()='Редагувати категорію']]//button[contains(@class,'event-category-modal-save-button')]")
    private WebElement editCategorySaveButton;

    // Delete category

    @FindBy(css = ".modal-container:has(.delete-category-modal-content)")
    private WebElement deleteModal;

    @FindBy(id = "delete-category-select")
    private WebElement deleteCategorySelect;

    @FindBy(css = ".modal-container:has(.delete-category-modal-content) .modal-footer .btn-secondary")
    private WebElement cancelDeleteButton;

    @FindBy(css = ".modal-container:has(.delete-category-modal-content) .modal-footer .btn-primary")
    private WebElement confirmDeleteButton;

    // Translate category

    @FindBy(css = ".modal-container:has(#translate-event-category-form)")
    private WebElement translationModal;

    @FindBy(css = ".modal-container:has(#translate-event-category-form) .language-select .select-head")
    private WebElement translationLanguageButton;

    @FindBy(css = ".modal-container:has(#translate-event-category-form) [class*='TranslationControls_generate-button']")
    private WebElement generateTranslationButton;

    @FindBy(css = "#translate-event-category-form #category-select")
    private WebElement translationCategorySelect;

    @FindBy(css = "#translate-event-category-form #name")
    private WebElement translationNameInput;

    @FindBy(css = "#translate-event-category-form #name-character-count")
    private WebElement translationNameCounter;

    @FindBy(css = ".modal-container:has(#translate-event-category-form) .localization-modal .btn-primary")
    private WebElement saveTranslationButton;

    public EventCategoryModalAdmin(WebElement rootElement) {
        super(rootElement);
    }

    // Add category

    @Step("Check Add Category modal is displayed")
    public boolean isAddModalDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(addModal)).isDisplayed();
    }

    @Step("Fill new category name: {name}")
    public void fillNewCategoryName(String name) {
        addCategoryNameInput.clear();
        addCategoryNameInput.sendKeys(name);
    }

    @Step("Save new category")
    public void saveNewCategory() {
        wait.until(ExpectedConditions.elementToBeClickable(addCategorySaveButton)).click();
    }

    // Edit category

    @Step("Check Edit Category modal is displayed")
    public boolean isEditModalDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(editModal)).isDisplayed();
    }

    @Step("Select category for editing: {category}")
    public void selectCategoryForEditing(String category) {
        selectCategory(editModal, editCategorySelect, category);
    }

    @Step("Fill edited category name: {name}")
    public void fillEditedCategoryName(String name) {
        editCategoryNameInput.clear();
        editCategoryNameInput.sendKeys(name);
    }

    @Step("Save edited category")
    public void saveEditedCategory() {
        wait.until(ExpectedConditions.elementToBeClickable(editCategorySaveButton)).click();
    }

    // Delete category

    @Step("Check Delete Category modal is displayed")
    public boolean isDeleteModalDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(deleteModal)).isDisplayed();
    }

    @Step("Select category for deletion: {category}")
    public void selectCategoryForDeletion(String category) {
        selectCategory(deleteModal, deleteCategorySelect, category);
    }

    @Step("Confirm category deletion")
    public void confirmDelete() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmDeleteButton)).click();
    }

    @Step("Cancel category deletion")
    public void cancelDelete() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelDeleteButton)).click();
    }

    // Translation

    @Step("Check Category Translation modal is displayed")
    public boolean isTranslationModalDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(translationModal)).isDisplayed();
    }

    @Step("Select category for translation: {category}")
    public void selectCategoryForTranslation(String category) {
        selectCategory(translationModal, translationCategorySelect, category);
    }

    @Step("Select category translation language: {language}")
    public void selectTranslationLanguage(String language) {
        wait.until(ExpectedConditions.elementToBeClickable(translationLanguageButton)).click();

        By option = By.xpath(
                "//div[contains(@class,'modal-container')]"
                        + "[.//form[@id='translate-event-category-form']]"
                        + "//div[contains(@class,'select-options')]"
                        + "//button[.//span[normalize-space()='" + language + "']]"
        );

        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    @Step("Fill category translation name: {name}")
    public void fillTranslationName(String name) {
        translationNameInput.clear();
        translationNameInput.sendKeys(name);
    }

    @Step("Get translation character count")
    public String getTranslationCharacterCount() {
        return translationNameCounter.getText().trim();
    }

    @Step("Generate category translation")
    public void generateTranslation() {
        wait.until(ExpectedConditions.elementToBeClickable(generateTranslationButton)).click();
    }

    @Step("Save category translation")
    public void saveTranslation() {
        wait.until(ExpectedConditions.elementToBeClickable(saveTranslationButton)).click();
    }

    // Common methods

    private void selectCategory(WebElement modal, WebElement select, String category) {
        wait.until(ExpectedConditions.elementToBeClickable(select)).click();

        By option = By.xpath(
                ".//div[@role='listbox']"
                        + "//*[@role='option' and normalize-space()='" + category + "']"
        );

        wait.until(driver -> {
            WebElement element = modal.findElement(option);
            return element.isDisplayed() && element.isEnabled() ? element : null;
        }).click();
    }

    @Step("Close Add Category modal")
    public void closeAddModal() {
        addModal.findElement(By.cssSelector("button[aria-label='Close modal']")).click();
    }

    @Step("Close Edit Category modal")
    public void closeEditModal() {
        editModal.findElement(By.cssSelector("button[aria-label='Close modal']")).click();
    }

    @Step("Close Delete Category modal")
    public void closeDeleteModal() {
        deleteModal.findElement(By.cssSelector("button[aria-label='Close modal']")).click();
    }

    @Step("Close Category Translation modal")
    public void closeTranslationModal() {
        translationModal.findElement(By.cssSelector("button[aria-label='Close modal']")).click();
    }

    @Step("Check Add Category save button is enabled")
    public boolean isAddCategorySaveEnabled() {
        return addCategorySaveButton.isEnabled();
    }

    @Step("Check Edit Category save button is enabled")
    public boolean isEditCategorySaveEnabled() {
        return editCategorySaveButton.isEnabled();
    }

    @Step("Check Delete Category button is enabled")
    public boolean isDeleteEnabled() {
        return confirmDeleteButton.isEnabled();
    }

    @Step("Check Save Translation button is enabled")
    public boolean isSaveTranslationEnabled() {
        return saveTranslationButton.isEnabled();
    }
}
