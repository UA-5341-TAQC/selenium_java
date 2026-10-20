package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ValueCardComponent extends BaseComponent {

    @FindBy(css = "h3[class*='ValueCard_name']")
    private WebElement nameElement;

    @FindBy(css = "div[class*='ValueCard_description']")
    private WebElement descriptionElement;


    public ValueCardComponent(WebElement element) {
        super(element);
    }

    @Step("Get Value Card description")
    public String getDescriptionText() {
        wait.until(ExpectedConditions.visibilityOf(descriptionElement));
        return descriptionElement.getText().trim();
    }

    @Step("Get Value Card name")
    public String getNameText() {
        wait.until(ExpectedConditions.visibilityOf(nameElement));
        return nameElement.getText().trim();
    }

}