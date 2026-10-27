package org.academy.ui.components.detailprogram;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class TitleDescriptionCardSectionComponent extends BaseComponent {

    @FindBy(css = "h3[class*='TitleDescriptionCardsSection_title']")
    private WebElement titleElement;

    @FindBy(css = "div[class*='TitleDescriptionCardsSection_description'] p")
    private WebElement descriptionElement;

    @FindBy(css = "ul[class*='TitleDescriptionCardsSection_description-list']")
    private List<WebElement> descriptionsListElement;

    public TitleDescriptionCardSectionComponent(WebElement element) {
        super(element);
    }

    @Step("Get title")
    public String getTitleText() {
        wait.until(ExpectedConditions.visibilityOf(titleElement));
        return titleElement.getText().trim();
    }

    @Step("Get description text")
    public String getDescriptionText() {
        wait.until(ExpectedConditions.visibilityOf(descriptionElement));
        return descriptionElement.getText().trim();
    }

    @Step("Get list text")
    public List<String> getListText() {
        wait.until(ExpectedConditions.visibilityOfAllElements(descriptionsListElement));
        return descriptionsListElement.stream()
                .map(element -> element.getText().trim())
                .toList();
    }
}
