package org.academy.ui.modals.admin;

import io.qameta.allure.Step;
import org.academy.ui.components.*;
import org.academy.ui.modals.BaseModal;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class TranslatePdfSectionModal extends BaseModal {

    public static final String ROOT_SELECTOR = ".modal-container";

    // Modal Locators
    @FindBy(css = "[data-testid='modal-overlay']")
    private WebElement modalOverlayElement;

    @FindBy(css = ".modal-header-text")
    private WebElement modalHeaderElement;

    @FindBy(css = "button[aria-label='Close modal']")
    private WebElement closeButtonElement;

    // Language Selection Locators
    @FindBy(css = "[class*='TranslationControls_container__']")
    private WebElement translationControlsElement;


    @FindBy(css = ".select.language-select > button.select-head")
    private WebElement languageSelectButtonElement;

    @FindBy(css = ".select.language-select .select-head .empty")
    private WebElement currentLanguageElement;

    @FindBy(css = ".select-options button.select-options")
    private WebElement selectedLanguageOptionElement;

    @FindBy(css = ".select.language-select .select-options button")
    private List<WebElement> languageOptionElements;

    // Title Locators
    @FindBy(css = "label[for='pdf-section-localization-title']")
    private WebElement titleLabelElement;

    @FindBy(css = "input#pdf-section-localization-title")
    private WebElement titleInputElement;

    @FindBy(css = "button[aria-label='Clear input']")
    private WebElement clearTitleButtonElement;

    @FindBy(css = "output#pdf-section-localization-title-character-count")
    private WebElement titleCharacterCountElement;

    // Description Locators
    @FindBy(css = "label[for='pdf-section-localization-description']")
    private WebElement descriptionLabelElement;

    @FindBy(css = "textarea#pdf-section-localization-description")
    private WebElement descriptionInputElement;

    @FindBy(css = "output#pdf-section-localization-description-character-count")
    private WebElement descriptionCharacterCountElement;

    // Error Locators
    @FindBy(css = ".input-group .input-error")
    private WebElement errorTitleInput;

    @FindBy(css = "[class*='InputErrorWithCharacterCounter_error-section__']")
    private WebElement errorDescriptionInput;

    // Save Button Locator
    @FindBy(css = "[class*='LocalizationModal_save-localization-btn__']")
    private WebElement saveTranslationButtonElement;

    public TranslatePdfSectionModal(WebElement rootElement) {
        super(rootElement);
    }


    public boolean isModalDisplayed() {
        try {
            waitUntilVisible(modalOverlayElement);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    // Modal Methods

    @Step("Get modal overlay")
    public WebElement getModalOverlay() {
        return waitUntilVisible(modalOverlayElement);
    }

    @Step("Get modal title")
    public String getModalTitle() {
        return getText(modalHeaderElement);
    }

    @Step("Close add translation modal")
    public void close() {
        click(closeButtonElement);
        waitUntilClosed();
    }
    // Language Methods

    @Step("Get translation controls")
    public WebElement getTranslationControls() {
        return waitUntilVisible(translationControlsElement);
    }

    @Step("Get current language")
    public String getCurrentLanguage() {
        return getText(currentLanguageElement);
    }

    @Step("Open language list")
    public TranslatePdfSectionModal openLanguageList() {
        click(languageSelectButtonElement);
        waitUntilAllVisible(languageOptionElements);
        return this;
    }

    @Step("Get selected language option")
    public String getSelectedLanguageOption() {
        return getText(selectedLanguageOptionElement);
    }

    @Step("Get available language options")
    public List<String> getLanguageOptionTexts() {
        return getTexts(languageOptionElements);
    }

    @Step("Select language: {language}")
    public TranslatePdfSectionModal selectLanguage(String language) {
        openLanguageList();

        click(findByText(languageOptionElements, language));

        return this;
    }

    // Title Methods

    @Step("Get title label")
    public String getTitleLabel() {
        return getText(titleLabelElement);
    }

    @Step("Get translation title")
    public String getTitle() {
        return getValue(titleInputElement);
    }

    @Step("Set translation title: {title}")
    public TranslatePdfSectionModal setTitle(String title) {
        fillInput(titleInputElement, title);
        return this;
    }

    @Step("Clear translation title")
    public TranslatePdfSectionModal clearTitle() {
        click(clearTitleButtonElement);
        return this;
    }

    @Step("Get title character count")
    public String getTitleCharacterCount() {
        return getText(titleCharacterCountElement);
    }

    // Description Methods

    @Step("Get description label")
    public String getDescriptionLabel() {
        return getText(descriptionLabelElement);
    }

    @Step("Get translation description")
    public String getDescription() {
        return getValue(descriptionInputElement);
    }

    @Step("Set translation description")
    public TranslatePdfSectionModal setDescription(String description) {
        fillInput(descriptionInputElement, description);
        return this;
    }

    @Step("Get description character count")
    public String getDescriptionCharacterCount() {
        return getText(descriptionCharacterCountElement);
    }

    // Error Methods

    @Step("Get error text for title")
    public String getTitleErrorText() {
        return getText(errorTitleInput);
    }

    @Step("Get error text for description")
    public String getDescriptionTitleErrorText() {
        return getText(errorDescriptionInput);
    }

    // Save Methods

    @Step("Check if save translation button is enabled")
    public boolean isSaveTranslationButtonEnabled() {
        return saveTranslationButtonElement.isEnabled();
    }

    @Step("Save translation")
    public void saveTranslation() {
        click(saveTranslationButtonElement);
        waitUntilClosed();
    }
}