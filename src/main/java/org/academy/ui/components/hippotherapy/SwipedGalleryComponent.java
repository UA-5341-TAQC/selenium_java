package org.academy.ui.components.hippotherapy;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SwipedGalleryComponent extends BaseComponent {

    @FindBy(css = "h2[class*='SwipedGallery_title'], h2")
    private WebElement title;

    @FindBy(css = "[class*='SwipedCard_swiped-card']")
    private List<WebElement> cardElements;

    @FindBy(css = ".swiper-button-prev, [class*='swiper-button-prev']")
    private WebElement prevButton;

    @FindBy(css = ".swiper-button-next, [class*='swiper-button-next']")
    private WebElement nextButton;

    public SwipedGalleryComponent(WebElement element) {
        super(element);
    }

    @Step("Get Swiped Gallery title")
    public String getTitleText() {
        return getText(title);
    }

    @Step("Get total cards count")
    public int getCardsCount() {
        return cardElements.size();
    }

    @Step("Get list of SwipedCard components")
    public List<SwipedCardComponent> getCards() {
        return cardElements.stream()
                .map(SwipedCardComponent::new)
                .toList();
    }

    @Step("Get SwipedCard component by index: {index}")
    public SwipedCardComponent getCard(int index) {
        if (index < 0 || index >= cardElements.size()) {
            throw new IndexOutOfBoundsException("Invalid SwipedCard index: " + index + ", total cards: " + cardElements.size());
        }
        return new SwipedCardComponent(cardElements.get(index));
    }

    @Step("Get card description texts")
    public List<String> getCardDescriptions() {
        return getCards().stream()
                .map(SwipedCardComponent::getDescriptionText)
                .toList();
    }

    @Step("Click previous slide button")
    public void clickPrev() {
        click(prevButton);
    }

    @Step("Click next slide button")
    public void clickNext() {
        click(nextButton);
    }

    @Step("Check if Swiped Gallery is displayed")
    public boolean isDisplayed() {
        return isVisible(title);
    }
}
