package org.academy.ui.modals.admin.programs;

import io.qameta.allure.Step;
import org.academy.ui.pages.Admin.AdminProgramsPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TranslateProgramModal extends BaseProgramModal<TranslateProgramModal> {

    public static final String ROOT_SELECTOR = ".translate-program-modal";

    @FindBy(css = ".translate-program-modal__language-select-head")
    private WebElement languageButton;

    @FindBy(css = ".translate-program-modal__top-actions button")
    private WebElement saveButton;

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

    // TODO: Model translation sections with template-specific components.

    @Step("Save program translation")
    public AdminProgramsPage save() {
        click(saveButton);
        waitUntilClosed();
        return new AdminProgramsPage(driver).waitUntilVisible();
    }
}
