package org.academy.ui.modals.admin;

import io.qameta.allure.Step;
import lombok.Getter;
import org.academy.ui.modals.BaseModal;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Getter
public class DonateConfirmationModalAdmin extends BaseModal {

    // Confirmation modal

    @FindBy(css = ".modal-header-text")
    private WebElement modalTitle;

    @FindBy(css = ".modal-footer .btn-secondary.confirmation-btn")
    private WebElement noButton;

    @FindBy(css = ".modal-footer .btn-primary.confirmation-btn")
    private WebElement yesButton;

    public DonateConfirmationModalAdmin(WebElement rootElement) {
        super(rootElement);
    }

    // Modal information

    @Step("Check Donate confirmation modal is displayed")
    public boolean isDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(rootElement)
        ).isDisplayed();
    }

    @Step("Get Donate confirmation modal title")
    public String getModalTitleText() {
        return modalTitle.getText().trim();
    }

    // Confirmation types

    @Step("Check publish changes confirmation")
    public boolean isPublishConfirmation() {
        return getModalTitleText()
                .equals("Опублікувати зміни?");
    }

    @Step("Check unsaved changes confirmation")
    public boolean isUnsavedChangesConfirmation() {
        return getModalTitleText()
                .replaceAll("\\s+", " ")
                .equals("Зміни будуть втрачені. Бажаєте продовжити?");
    }

    @Step("Check delete bank details confirmation")
    public boolean isDeleteBankDetailsConfirmation() {
        return getModalTitleText()
                .equals("Видалити реквізити?");
    }

    @Step("Check delete bank with correspondents confirmation")
    public boolean isDeleteBankWithCorrespondentsConfirmation() {
        return getModalTitleText()
                .equals(
                        "Основний та кореспондентські банки буде видалено. "
                                + "Видалити реквізити?"
                );
    }

    @Step("Check delete support option confirmation")
    public boolean isDeleteSupportOptionConfirmation() {
        return getModalTitleText()
                .equals("Видалити варіант підтримки?");
    }

    // Modal actions

    @Step("Click Yes in Donate confirmation modal")
    public void clickYes() {
        click(yesButton);
        waitUntilClosed();
    }

    @Step("Click No in Donate confirmation modal")
    public void clickNo() {
        click(noButton);
        waitUntilClosed();
    }

    @Step("Check Yes button is enabled")
    public boolean isYesButtonEnabled() {
        return yesButton.isEnabled();
    }

    @Step("Check No button is enabled")
    public boolean isNoButtonEnabled() {
        return noButton.isEnabled();
    }

    @Step("Close Donate confirmation modal")
    public void closeModal() {
        requestClose();
        waitUntilClosed();
    }
}
