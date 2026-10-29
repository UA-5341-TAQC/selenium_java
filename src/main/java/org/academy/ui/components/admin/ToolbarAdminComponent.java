package org.academy.ui.components.admin;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ToolbarAdminComponent extends BaseComponent {

    @FindBy(css = "[data-testid='language-toolkit']")
    private WebElement languageToolkit;

    @FindBy(css = "[data-testid='language-toolkit'] .select-head")
    private WebElement languageDropdown;

    @FindBy(css = "[data-testid='language-toolkit'] .select-options")
    private WebElement languageOptions;

    @FindBy(css = "[data-testid='language-toolkit'] .select-options button")
    private List<WebElement> languages;

    @FindBy(css = "[data-testid='language-toolkit'] .select-options .select-options-selected")
    private WebElement selectedLanguage;

    @FindBy(css = "[data-testid='localization-toolkit']")
    private WebElement localizationToolkit;

    @FindBy(css = "[data-testid='localization-statuses']")
    private WebElement localizationStatuses;

    @FindBy(css = "input[type='search']")
    private WebElement searchInput;

    @FindBy(css = ".select-head")
    private WebElement dropdownHead;

    @FindBy(css = ".select-options")
    private WebElement dropdownOptions;

    @FindBy(css = ".select-options button")
    private List<WebElement> dropdownItems;

    @FindBy(css = ".select-options .select-options-selected")
    private WebElement selectedOption;

    @FindBy(css = "button.btn-primary")
    private WebElement primaryButton;

    @FindBy(css = "button[aria-label='Перекласти']")
    private WebElement translateButton;

    public ToolbarAdminComponent(WebElement element) {
        super(element);
    }

    // Language Toolkit Methods

    @Step("Get language toolkit")
    public WebElement getLanguageToolkit() {
        return waitUntilVisible(languageToolkit);
    }

    @Step("Click language dropdown")
    public void clickLanguageDropdown() {
        click(languageDropdown);
    }

    @Step("Get language options")
    public WebElement getLanguageOptions() {
        return waitUntilVisible(languageOptions);
    }

    @Step("Get all languages count")
    public int getLanguagesCount() {
        return languages.size();
    }

    @Step("Get all languages texts")
    public List<String> getLanguagesTexts() {
        return getTexts(languages);
    }

    @Step("Get selected language")
    public WebElement getSelectedLanguage() {
        return waitUntilVisible(selectedLanguage);
    }

    @Step("Get selected language text")
    public String getSelectedLanguageText() {
        return getText(selectedLanguage);
    }

    @Step("Select language by text: {languageText}")
    public void selectLanguage(String languageText) {
        click(findByText(languages, languageText));
    }

    // Localization Toolkit Methods

    @Step("Get localization toolkit")
    public WebElement getLocalizationToolkit() {
        return waitUntilVisible(localizationToolkit);
    }

    @Step("Get localization statuses")
    public WebElement getLocalizationStatuses() {
        return waitUntilVisible(localizationStatuses);
    }

    // Search Methods

    @Step("Get search input")
    public WebElement getSearchInput() {
        return waitUntilVisible(searchInput);
    }

    @Step("Fill search input: {searchText}")
    public void fillSearchInput(String searchText) {
        fillInput(searchInput, searchText);
    }

    @Step("Get search input value")
    public String getSearchInputValue() {
        return getValue(searchInput);
    }

    @Step("Clear search input")
    public void clearSearchInput() {
        clearInput(searchInput);
    }

    // Generic Dropdown Methods

    @Step("Click dropdown head")
    public void clickDropdownHead() {
        click(dropdownHead);
    }

    @Step("Get dropdown options")
    public WebElement getDropdownOptions() {
        return waitUntilVisible(dropdownOptions);
    }

    @Step("Get all dropdown items count")
    public int getDropdownItemsCount() {
        return dropdownItems.size();
    }

    @Step("Get all dropdown items texts")
    public List<String> getDropdownItemsTexts() {
        return getTexts(dropdownItems);
    }

    @Step("Get selected option")
    public WebElement getSelectedOption() {
        return waitUntilVisible(selectedOption);
    }

    @Step("Get selected option text")
    public String getSelectedOptionText() {
        return getText(selectedOption);
    }

    @Step("Select dropdown item by text: {itemText}")
    public void selectDropdownItem(String itemText) {
        click(findByText(dropdownItems, itemText));
    }

    // Button Methods

    @Step("Click primary button")
    public void clickPrimaryButton() {
        click(primaryButton);
    }

    @Step("Click translate button")
    public void clickTranslateButton() {
        click(translateButton);
    }

    @Step("Get primary button text")
    public String getPrimaryButtonText() {
        return getText(primaryButton);
    }
}