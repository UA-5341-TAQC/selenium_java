package org.academy.ui.modals;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BaseModal extends BaseComponent {

    @FindBy(css = "button[aria-label='Close modal']")
    private WebElement closeButton;

    protected BaseModal(WebElement rootElement) {
        super(rootElement);
    }

    // Чекає видимої модалки за локатором та передає її кореневий елемент іншому конструктору.
    protected BaseModal(WebDriver driver, By modalLocator) {
        this(new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(modalLocator)));
    }

    /**
     * May open an unsaved-changes confirmation instead of closing.
     */
    @Step("Request modal close")
    public void requestClose() {
        click(closeButton);
    }

    /**
     * Call after handling any confirmation; does not click or confirm anything.
     */
    public void waitUntilClosed() {
        waitUntilInvisible(rootElement);
    }
}
