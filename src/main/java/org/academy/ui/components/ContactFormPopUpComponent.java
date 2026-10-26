package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ContactFormPopUpComponent extends BaseComponent {

    public static final String ROOT_SELECTOR = "aside[class*='ContactFormPopUp_popup'], aside[role='presentation']";
    private static final By OVERLAY_LOCATOR = By.cssSelector("[data-testid='popup-overlay'], [data-testid='modal-overlay']");

    @FindBy(css = "button[class*='ContactFormPopUp_closeBtn'], button[aria-label='Закрити форму'], button[aria-label='Close form']")
    private WebElement closeButton;

    @FindBy(css = "h2[class*='ContactFormPopUp_title']")
    private WebElement titleElement;

    @FindBy(css = "form[class*='ContactFormCard_contact-form-card']")
    private WebElement contactFormElement;

    public ContactFormPopUpComponent(WebElement rootElement) {
        super(rootElement);
    }

    public ContactFormPopUpComponent(WebDriver driver) {
        this(driver.findElement(By.cssSelector(ROOT_SELECTOR)));
    }

    @Step("Check if ContactFormPopUpComponent is displayed")
    public boolean isDisplayed() {
        return isDisplayed(rootElement);
    }

    @Step("Wait for ContactFormPopUpComponent to be visible")
    public ContactFormPopUpComponent waitForVisible() {
        waitUntilVisible(rootElement);
        return this;
    }

    @Step("Wait for ContactFormPopUpComponent to disappear")
    public ContactFormPopUpComponent waitForDisappeared() {
        waitUntilInvisible(rootElement);
        return this;
    }

    @Step("Get ContactFormPopUpComponent title")
    public String getTitle() {
        return getText(titleElement);
    }

    @Step("Get embedded ContactFormCard component")
    public ContactFormCardComponent getContactFormCard() {
        return new ContactFormCardComponent(waitUntilVisible(contactFormElement));
    }

    @Step("Close ContactFormPopUpComponent by clicking close button")
    public void closeByButton() {
        click(closeButton);
        waitForDisappeared();
    }

    @Step("Close ContactFormPopUpComponent by clicking on backdrop/overlay")
    public void closeByOverlay() {
        click(waitUntilVisible(OVERLAY_LOCATOR));
        waitForDisappeared();
    }

    @Step("Close ContactFormPopUpComponent by pressing ESC key")
    public void closeByEscape() {
        actions.sendKeys(Keys.ESCAPE).perform();
        waitForDisappeared();
    }
}
