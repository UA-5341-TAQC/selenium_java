package org.academy.ui.modals;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class BaseModal extends BaseComponent {

    @FindBy(css = "button[aria-label='Close modal']")
    private WebElement closeButton;

    protected BaseModal(WebElement rootElement) {
        super(rootElement);
    }

    /** May open an unsaved-changes confirmation instead of closing. */
    @Step("Request modal close")
    public void requestClose() {
        click(closeButton);
    }

    /** Call after handling any confirmation; does not click or confirm anything. */
    public void waitUntilClosed() {
        waitUntilInvisible(rootElement);
    }
}
