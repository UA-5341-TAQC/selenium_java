package org.academy.ui.components.detailprogram;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class SingleTitleDescriptionAuthorPairsComponent extends BaseComponent {

    @FindBy(css = "h2[class*='SingleTitleDescriptionAuthorPairs_title']")
    private WebElement titleElement;

    @FindBy(css = "div[class*='DescriptionAuthorPairCard_card']")
    private List <WebElement> descriptionAuthorPairCardElement;

    public SingleTitleDescriptionAuthorPairsComponent(WebElement element) {
        super(element);
    }

    @Step("Get title")
    public String getTitleText() {
        wait.until(ExpectedConditions.visibilityOf(titleElement));
        return titleElement.getText().trim();
    }

    @Step("Get cards count")
    public int getCardsCount(){
        wait.until(ExpectedConditions.visibilityOfAllElements(descriptionAuthorPairCardElement));
        return descriptionAuthorPairCardElement.size();
    }

    @Step("Get cards list text")
    public List<String> getCardsListText() {
        wait.until(ExpectedConditions.visibilityOfAllElements(descriptionAuthorPairCardElement));
        return descriptionAuthorPairCardElement.stream()
                .map(element -> element.getText().trim())
                .toList();
    }
}
