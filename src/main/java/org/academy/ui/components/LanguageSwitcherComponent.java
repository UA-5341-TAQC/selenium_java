package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

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
        wait.until(ExpectedConditions.visibilityOf(currentLanguageText));
        return currentLanguageText.getText().trim();
    }

    @Step("Check if language switcher dropdown is open")
    public boolean isDropdownOpen() {
        String rootClass = rootElement.getAttribute("class");
        return rootClass != null && rootClass.contains("select-opened");
    }

    @Step("Open language switcher dropdown")
    public LanguageSwitcherComponent openDropdown() {
        if (!isDropdownOpen()) {
            wait.until(ExpectedConditions.elementToBeClickable(switcherHeadButton));
            switcherHeadButton.click();
            wait.until(ExpectedConditions.visibilityOf(optionsContainer));
        }
        return this;
    }

    @Step("Close language switcher dropdown")
    public LanguageSwitcherComponent closeDropdown() {
        if (isDropdownOpen()) {
            wait.until(ExpectedConditions.elementToBeClickable(switcherHeadButton));
            switcherHeadButton.click();
        }
        return this;
    }

    @Step("Get list of available languages")
    public List<String> getAvailableLanguages() {
        openDropdown();
        return languageOptions.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    @Step("Select language: {targetLanguage}")
    public LanguageSwitcherComponent selectLanguage(String targetLanguage) {
        if (getCurrentLanguage().equalsIgnoreCase(targetLanguage)) {
            return this;
        }
        openDropdown();
        for (WebElement option : languageOptions) {
            if (option.getText().trim().equalsIgnoreCase(targetLanguage)) {
                wait.until(ExpectedConditions.elementToBeClickable(option));
                option.click();
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
