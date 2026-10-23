package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class LanguageSwitcherComponent extends BaseComponent {

    @FindBy(css = "button.language-switcher-head, button.select-head")
    private WebElement switcherHeadButton;

    @FindBy(css = "button.language-switcher-head span, button.select-head span")
    private WebElement currentLanguageText;

    @FindBy(css = ".select-options")
    private WebElement optionsContainer;

    @FindBy(css = "button.language-switcher-option")
    private List<WebElement> languageOptions;

    public LanguageSwitcherComponent(WebElement element) {
        super(element);
    }

    @Step("Get current active language")
    public String getCurrentLanguage() {
        return getText(currentLanguageText);
    }

    @Step("Check if language switcher dropdown is open")
    public boolean isDropdownOpen() {
        String rootClass = getDomAttribute(rootElement, "class");
        return rootClass != null && rootClass.contains("select-opened");
    }

    @Step("Open language switcher dropdown")
    public LanguageSwitcherComponent openDropdown() {
        if (!isDropdownOpen()) {
            click(switcherHeadButton);
            waitUntilVisible(optionsContainer);
        }
        return this;
    }

    @Step("Close language switcher dropdown")
    public LanguageSwitcherComponent closeDropdown() {
        if (isDropdownOpen()) {
            click(switcherHeadButton);
        }
        return this;
    }

    @Step("Get list of available languages")
    public List<String> getAvailableLanguages() {
        openDropdown();
        return getTexts(languageOptions);
    }

    @Step("Select language: {targetLanguage}")
    public LanguageSwitcherComponent selectLanguage(String targetLanguage) {
        if (getCurrentLanguage().equalsIgnoreCase(targetLanguage)) {
            return this;
        }
        openDropdown();
        for (WebElement option : languageOptions) {
            if (option.getText().trim().equalsIgnoreCase(targetLanguage)) {
                click(option);
                wait.until(d -> getCurrentLanguage().equalsIgnoreCase(targetLanguage));
                return this;
            }
        }
        throw new IllegalArgumentException("Language option not found: " + targetLanguage);
    }

    @Step("Switch language to English (EN)")
    public LanguageSwitcherComponent switchToEn() {
        return selectLanguage("EN");
    }

    @Step("Switch language to Ukrainian (UA)")
    public LanguageSwitcherComponent switchToUa() {
        return selectLanguage("UA");
    }
}


