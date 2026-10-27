package org.academy.ui.components.detailprogram;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ViewTitleDescriptionSectionComponent extends BaseComponent {

    @FindBy(css = "h2[class*='ViewTitleDescriptionSection_title']")
    private WebElement titleElement;

    @FindBy(css = "p[class*='ViewTitleDescriptionSection_description']")
    private WebElement descriptionElement;

    public ViewTitleDescriptionSectionComponent(WebElement element) {
        super(element);
    }

    @Step("Get title text")
    public String getTitleText() {
        wait.until(ExpectedConditions.visibilityOf(titleElement));
        return titleElement.getText().trim();
    }

    @Step("Get description text")
    public String getDescriptionText() {
        wait.until(ExpectedConditions.visibilityOf(descriptionElement));
        return descriptionElement.getText().trim();
    }
}
