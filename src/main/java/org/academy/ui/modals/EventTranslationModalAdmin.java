package org.academy.ui.modals;

import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Getter
public class EventTranslationModalAdmin extends BaseModal {

    @FindBy(css = ".modal-container:has(#translate-event-form)")
    private WebElement modal;

    @FindBy(css = ".modal-container:has(#translate-event-form) .modal-header-text")
    private WebElement modalTitle;

    @FindBy(css = ".modal-container:has(#translate-event-form) button[aria-label='Close modal']")
    private WebElement closeButton;

    @FindBy(css = ".modal-container:has(#translate-event-form) .language-select .select-head")
    private WebElement languageButton;

    @FindBy(css = ".modal-container:has(#translate-event-form) [class*='TranslationControls_generate-button']")
    private WebElement generateTranslationButton;

    @FindBy(css = "#translate-event-form #title")
    private WebElement titleInput;

    @FindBy(css = "#translate-event-form #description")
    private WebElement descriptionInput;

    @FindBy(css = "#translate-event-form #additionalDescription")
    private WebElement additionalDescriptionInput;

    @FindBy(css = ".modal-container:has(#translate-event-form) .localization-modal .btn-primary")
    private WebElement saveTranslationButton;

    public EventTranslationModalAdmin(WebElement rootElement) {
        super(rootElement);
    }

    @Step("Check Event Translation modal is displayed")
    public boolean isDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(modal)).isDisplayed();
    }

    @Step("Get translation modal title")
    public String getModalTitleText() {
        return modalTitle.getText().trim();
    }

    @Step("Get selected translation language")
    public String getLanguage() {
        return languageButton.getText().trim();
    }

    @Step("Select translation language: {language}")
    public void selectLanguage(String language) {
        wait.until(ExpectedConditions.elementToBeClickable(languageButton)).click();

        By option = By.xpath(
                "//div[contains(@class,'modal-container')]"
                        + "[.//form[@id='translate-event-form']]"
                        + "//div[contains(@class,'select-options')]"
                        + "//button[.//span[normalize-space()='" + language + "']]"
        );

        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    @Step("Fill translated event title: {title}")
    public void fillTitle(String title) {
        titleInput.clear();
        titleInput.sendKeys(title);
    }

    @Step("Fill translated event description: {description}")
    public void fillDescription(String description) {
        descriptionInput.clear();
        descriptionInput.sendKeys(description);
    }

    @Step("Fill translated additional description: {description}")
    public void fillAdditionalDescription(String description) {
        additionalDescriptionInput.clear();
        additionalDescriptionInput.sendKeys(description);
    }

    @Step("Generate event translation")
    public void generateTranslation() {
        wait.until(ExpectedConditions.elementToBeClickable(generateTranslationButton)).click();
    }

    @Step("Save event translation")
    public void saveTranslation() {
        wait.until(ExpectedConditions.elementToBeClickable(saveTranslationButton)).click();
    }

    @Step("Check Generate Translation button is enabled")
    public boolean isGenerateTranslationEnabled() {
        return generateTranslationButton.isEnabled();
    }

    @Step("Check Save Translation button is enabled")
    public boolean isSaveTranslationEnabled() {
        return saveTranslationButton.isEnabled();
    }

    @Step("Close Event Translation modal")
    public void closeModal() {
        wait.until(ExpectedConditions.elementToBeClickable(closeButton)).click();
    }
}
