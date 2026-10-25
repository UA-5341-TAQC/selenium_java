package org.academy.ui.modals.admin;

import io.qameta.allure.Step;
import org.academy.ui.modals.BaseModal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Shared admin confirmation. Confirm, cancel and dismiss are separate actions:
 * the frontend may give the No button and the close icon different callbacks.
 */
public class ConfirmationModal extends BaseModal {

    public static final String ROOT_SELECTOR = ".modal-container.confirmation-modal";

    @FindBy(css = ":scope > .modal-header .modal-header-text")
    private WebElement title;

    @FindBy(css = ":scope > .modal-body")
    private WebElement content;

    @FindBy(css = ":scope > .modal-footer > .btn-primary")
    private WebElement confirmButton;

    @FindBy(css = ":scope > .modal-footer > .btn-secondary")
    private WebElement cancelButton;

    public ConfirmationModal(WebElement rootElement) {
        super(rootElement);
    }

    public static ConfirmationModal waitFor(WebDriverWait wait) {
        WebElement root = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(ROOT_SELECTOR)));
        return new ConfirmationModal(root);
    }

    public String getTitle() {
        return getText(title);
    }

    public String getContent() {
        // Empty confirmation bodies have no visible dimensions.
        waitUntilVisible(rootElement);
        return content.getText();
    }

    public String getConfirmText() {
        return getText(confirmButton);
    }

    public String getCancelText() {
        return getText(cancelButton);
    }

    public boolean isConfirmEnabled() {
        return confirmButton.isEnabled();
    }

    public boolean isCancelEnabled() {
        return cancelButton.isEnabled();
    }

    /** Does not assume success: an API error can leave the dialog open. */
    @Step("Confirm the pending action")
    public void confirm() {
        click(confirmButton);
    }

    @Step("Decline the pending action")
    public void cancel() {
        click(cancelButton);
        waitUntilClosed();
    }

    /** Runs onClose, which is not necessarily the same as onCancel. */
    @Step("Dismiss confirmation using the close icon")
    public void dismiss() {
        requestClose();
        waitUntilClosed();
    }
}
