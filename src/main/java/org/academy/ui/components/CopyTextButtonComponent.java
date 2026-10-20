package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CopyTextButtonComponent extends BaseComponent {

    public CopyTextButtonComponent(WebElement element) {
        super(element);
    }

    @Step("Click copy button")
    public CopyTextButtonComponent clickCopy() {
        wait.until(ExpectedConditions.elementToBeClickable(rootElement));
        rootElement.click();
        return this;
    }

    @Step("Check if copy button is displayed")
    public boolean isDisplayed() {
        return rootElement.isDisplayed();
    }

    @Step("Check if copy button is enabled")
    public boolean isEnabled() {
        return rootElement.isEnabled();
    }

    @Step("Get associated text label")
    public String getAssociatedText() {
        try {
            WebElement label = rootElement.findElement(By.xpath("./preceding-sibling::span[contains(@class, 'label')]"));
            return label.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    @Step("Get aria-label")
    public String getAriaLabel() {
        return rootElement.getAttribute("aria-label");
    }

    @Step("Get class name")
    public String getClassName() {
        return rootElement.getAttribute("class");
    }
}
