package org.academy.ui.modals.admin.programs;

import io.qameta.allure.Step;
import org.academy.ui.components.admin.programs.sections.ProgramSectionContentFactory;
import org.academy.ui.components.admin.programs.sections.content.ProgramSectionContent;
import org.academy.ui.pages.Admin.AdminProgramsPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Objects;

public class TranslateProgramModal extends BaseProgramModal<TranslateProgramModal> {

    public static final String ROOT_SELECTOR = ".translate-program-modal";

    @FindBy(css = ".translate-program-modal__language-select-head")
    private WebElement languageButton;

    @FindBy(css = ".translate-program-modal__top-actions button")
    private WebElement saveButton;

    @FindBy(css = ".translate-program-modal__language-select .select-options button")
    private List<WebElement> languageOptions;

    @FindBy(css = ".translate-program-modal__language-select .select")
    private WebElement languageSelect;

    @FindBy(css = "[class*='TranslateProgramForm_section-wrapper__']"
            + " > [class*='TranslateProgramForm_section-editor__'] > *")
    private List<WebElement> sectionLayouts;

    public TranslateProgramModal(WebElement rootElement) {
        super(rootElement);
    }

    @Override
    protected TranslateProgramModal self() {
        return this;
    }

    @Step("Read program translation language")
    public String getLanguageText() {
        return getText(languageButton);
    }

    @Step("Check whether saving translation is enabled")
    public boolean isSaveEnabled() {
        return saveButton.isEnabled();
    }

    @Step("Read available program translation languages")
    public List<String> getAvailableLanguages() {
        openLanguages();
        List<String> languages = languageOptions.stream().map(WebElement::getText).toList();
        click(languageButton);
        wait.until(d -> !isLanguageSelectOpen());
        return languages;
    }

    @Step("Select program translation language: {language}")
    public TranslateProgramModal selectLanguage(String language) {
        Objects.requireNonNull(language, "Language must not be null");
        openLanguages();
        for (WebElement option : languageOptions) {
            if (language.equals(option.getText())) {
                click(option);
                wait.until(d -> !isLanguageSelectOpen() && language.equals(getLanguageText()));
                return this;
            }
        }
        click(languageButton);
        wait.until(d -> !isLanguageSelectOpen());
        throw new IllegalArgumentException("Unknown program translation language: " + language);
    }

    /**
     * All templates in display order. Re-read after switching languages or changing
     * cards.
     */
    @Step("Read program translation sections")
    public List<ProgramSectionContent> getSections() {
        return sectionLayouts.stream().map(ProgramSectionContentFactory::create).toList();
    }

    /**
     * Returns a section by its zero-based display position and checks the requested
     * content type.
     * Translation fields are already editable; no separate edit or section save is
     * needed.
     *
     * <pre>{@code
     * var faq = translation.getSection(2, ProgramFaqSectionContent.class);
     * faq.getFaqCard(0).setQuestion("Who can participate?").setAnswer("Veterans and their families.");
     * translation.save(); // Saves the whole translation.
     * }</pre>
     * <p>
     * Re-read sections after switching languages.
     *
     * @throws IllegalArgumentException  if the section has a different content type
     * @throws IndexOutOfBoundsException if no section exists at the given position
     */
    public <T extends ProgramSectionContent> T getSection(int index, Class<T> type) {
        return getSection(index).as(type);
    }

    public ProgramSectionContent getSection(int index) {
        return ProgramSectionContentFactory.create(sectionLayouts.get(index));
    }

    private boolean isLanguageSelectOpen() {
        return List.of(languageSelect.getDomAttribute("class").split("\\s+"))
                .contains("select-opened");
    }

    private void openLanguages() {
        if (!isLanguageSelectOpen()) {
            click(languageButton);
        }
        waitUntilAllVisible(languageOptions);
    }

    @Step("Save program translation")
    public AdminProgramsPage save() {
        click(saveButton);
        waitUntilClosed();
        return new AdminProgramsPage(driver).waitUntilVisible();
    }
}
