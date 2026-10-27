package org.academy.ui.modals.admin.faq;

import io.qameta.allure.Step;
import org.academy.ui.constants.HtmlAttribute;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Objects;

public class TranslateFaqModal extends BaseFaqModal<TranslateFaqModal> {

    public static final String ROOT_SELECTOR = ".modal-container:has(> .modal-body form.translate-faq-form)";

    @FindBy(css = "[class*='TranslationControls_language-select-head__']")
    private WebElement languageButton;
    @FindBy(css = ".language-select .select-options button")
    private List<WebElement> languages;

    @FindBy(css = ".language-select")
    private WebElement languageSelect;
    @FindBy(css = ":scope > .modal-footer .localization-modal > button.btn-primary")
    private WebElement saveButton;

    public TranslateFaqModal(WebElement rootElement) {
        super(rootElement);
    }

    public static TranslateFaqModal waitFor(WebDriverWait wait) {
        WebElement root = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(ROOT_SELECTOR)));
        return new TranslateFaqModal(root);
    }

    @Override
    protected TranslateFaqModal self() {
        return this;
    }

    public String getLanguageText() { return getText(languageButton); }
    public boolean isSaveEnabled() { return saveButton.isEnabled(); }

    public TranslateFaqModal selectLanguage(String name) {
        Objects.requireNonNull(name, "Language must not be null");
        if (!isLanguageSelectOpen()) {
            click(languageButton);
        }
        waitUntilAllVisible(languages);
        for (WebElement language : languages) {
            if (name.equals(language.getText().trim())) {
                click(language);
                wait.until(d -> name.equals(getLanguageText())
                        && !isLanguageSelectOpen());
                return this;
            }
        }
        click(languageButton);
        throw new IllegalArgumentException("Unknown FAQ translation language: " + name);
    }

    private boolean isLanguageSelectOpen() {
        return List.of(languageSelect.getDomAttribute(HtmlAttribute.CLASS.getValue()).split("\\s+"))
                .contains("select-opened");
    }

    /** Saving does not open a confirmation. An API error may leave the form open. */
    @Step("Save FAQ translation")
    public void save() {
        click(saveButton);
    }

}
