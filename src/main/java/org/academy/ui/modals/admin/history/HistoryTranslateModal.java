package org.academy.ui.modals.admin.history;

import io.qameta.allure.Step;
import org.academy.ui.modals.BaseModal;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Objects;

public class HistoryTranslateModal extends BaseModal {

    public static final String ROOT_SELECTOR = ".modal-container";

    // Modal Locators
    @FindBy(css = "[data-testid='modal-overlay']")
    private WebElement modalOverlayElement;

    @FindBy(css = "button[aria-label='Close modal']")
    private WebElement closeButtonElement;

    // Language Selection Locators
    @FindBy(css = "[class*='TranslationControls_container__']")
    private WebElement translationControlsElement;

    @FindBy(css = ".select.language-select > button.select-head")
    private WebElement languageSelectButtonElement;

    @FindBy(css = ".select.language-select .select-head .empty")
    private WebElement currentLanguageElement;

    @FindBy(css = ".select.language-select .select-options")
    private WebElement languageOptionsListElement;

    @FindBy(css = ".select.language-select .select-options-selected")
    private WebElement selectedLanguageOptionElement;

    @FindBy(css = ".select.language-select .select-options > *")
    private List<WebElement> languageOptionElements;

    // Sections Locators
    @FindBy(css = "[class*='TranslateHistoryModal_sections-list__']")
    private WebElement sectionsListElement;

    @FindBy(css = "[data-testid='translate-section-row']")
    private List<WebElement> sectionRowElements;

    // Title Locators
    @FindBy(css = "label[for='history-translation-title']")
    private List<WebElement> titleLabelElements;

    @FindBy(css = "textarea#history-translation-title")
    private List<WebElement> titleInputElements;

    @FindBy(css = "textarea#history-translation-title + button[aria-label='Clear input']")
    private List<WebElement> clearTitleButtonElements;

    // Description Locators
    @FindBy(css = "label[for='history-translation-description']")
    private List<WebElement> descriptionLabelElements;

    @FindBy(css = "textarea#history-translation-description")
    private List<WebElement> descriptionInputElements;

    @FindBy(css = "textarea#history-translation-description + button[aria-label='Clear input']")
    private List<WebElement> clearDescriptionButtonElements;

    // Error And Counter Locators
    @FindBy(css = "[class*='InputErrorWithCharacterCounter_error-section__']")
    private List<WebElement> errorSectionElements;

    @FindBy(css = "[class*='InputErrorWithCharacterCounter_counter-section__']")
    private List<WebElement> counterSectionElements;

    // Image Locators
    @FindBy(css = "[class*='ViewImagesBottomSection_image__']")
    private List<WebElement> imageElements;

    // Save Button Locator
    @FindBy(css = "[class*='LocalizationModal_save-localization-btn__']")
    private WebElement saveTranslationButtonElement;

    public HistoryTranslateModal(WebElement rootElement) {
        super(rootElement);
    }

    public static HistoryTranslateModal waitFor(WebDriverWait wait) {
        WebElement root = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(ROOT_SELECTOR)));
        return new HistoryTranslateModal(root);
    }

    // Modal Methods

    @Step("Get modal overlay")
    public WebElement getModalOverlay() {
        return waitUntilVisible(modalOverlayElement);
    }

    @Step("Close translate modal")
    public void close() {
        click(closeButtonElement);
        waitUntilClosed();
    }

    // Language Methods

    @Step("Get translation controls")
    public WebElement getTranslationControls() {
        return waitUntilVisible(translationControlsElement);
    }

    @Step("Get current language text")
    public String getCurrentLanguage() {
        return getText(currentLanguageElement);
    }

    @Step("Open language list")
    public HistoryTranslateModal openLanguageList() {
        click(languageSelectButtonElement);
        waitUntilVisible(languageOptionsListElement);
        return this;
    }

    @Step("Get selected language option text")
    public String getSelectedLanguageOption() {
        return getText(selectedLanguageOptionElement);
    }

    @Step("Get language options texts")
    public List<String> getLanguageOptionTexts() {
        return getTexts(languageOptionElements);
    }

    @Step("Select language: {language}")
    public HistoryTranslateModal selectLanguage(String language) {
        openLanguageList();
        click(findByText(languageOptionElements, language));
        return this;
    }

    // Sections Methods

    @Step("Get sections list")
    public WebElement getSectionsList() {
        return waitUntilVisible(sectionsListElement);
    }

    @Step("Get section rows count")
    public int getSectionRowsCount() {
        return sectionRowElements.size();
    }

    @Step("Get all section titles")
    public List<String> getSectionTitles() {
        return titleInputElements.stream()
                .map(input -> getValue(input))
                .toList();
    }

    // Title Methods

    @Step("Get title label text of section: {title}")
    public String getTitleLabelText(String title) {
        return getText(findInRow(title, titleLabelElements));
    }

    @Step("Change title of section {title} to: {newTitle}")
    public HistoryTranslateModal setSectionTitle(String title, String newTitle) {
        fillInput(findInRow(title, titleInputElements), newTitle);
        return this;
    }

    @Step("Clear title of section: {title}")
    public HistoryTranslateModal clearSectionTitle(String title) {
        click(findInRow(title, clearTitleButtonElements));
        return this;
    }

    // Description Methods

    @Step("Get description label text of section: {title}")
    public String getDescriptionLabelText(String title) {
        return getText(findInRow(title, descriptionLabelElements));
    }

    @Step("Get description of section: {title}")
    public String getSectionDescription(String title) {
        return getValue(findInRow(title, descriptionInputElements));
    }

    @Step("Set description of section {title}")
    public HistoryTranslateModal setSectionDescription(String title, String description) {
        fillInput(findInRow(title, descriptionInputElements), description);
        return this;
    }

    @Step("Clear description of section: {title}")
    public HistoryTranslateModal clearSectionDescription(String title) {
        click(findInRow(title, clearDescriptionButtonElements));
        return this;
    }

    // Error And Counter Methods

    @Step("Get error messages of section: {title}")
    public List<String> getErrorMessages(String title) {
        return getTexts(filterInside(findRowByTitle(title), errorSectionElements));
    }

    @Step("Get character counters of section: {title}")
    public List<String> getCharacterCounters(String title) {
        return getTexts(filterInside(findRowByTitle(title), counterSectionElements));
    }

    // Image Methods

    @Step("Get images count of section: {title}")
    public int getSectionImagesCount(String title) {
        return filterInside(findRowByTitle(title), imageElements).size();
    }

    // Save Methods

    @Step("Save translation")
    public void saveTranslation() {
        click(saveTranslationButtonElement);
    }

    // Private Helpers
    private WebElement findRowByTitle(String title) {
        String expected = Objects.requireNonNull(title, "Section title must not be null").trim();
        return wait.until(currentDriver -> sectionRowElements.stream()
                .filter(row -> filterInside(row, titleInputElements).stream()
                        .anyMatch(input -> expected.equalsIgnoreCase(input.getDomProperty("value").trim())))
                .findFirst()
                .orElse(null));
    }

    private WebElement findInRow(String title, List<WebElement> elements) {
        return filterInside(findRowByTitle(title), elements).stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Element not found in section row: " + title));
    }
}