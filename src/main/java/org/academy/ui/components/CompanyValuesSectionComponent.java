package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyValuesSectionComponent extends BaseComponent {

    @FindBy(css = "h2[class*='ValueCard_title']")
    private WebElement titleElement;

    @FindBy(css = "div[class*='ValueCard_item']")
    private List<WebElement> valueCardElements;

    @FindBy(css = "div[class*='CompanyValues_root'] div[class*='ValueCard_item']")
    private List<WebElement> programCardElements;

    public CompanyValuesSectionComponent(WebElement element) {
        super(element);
    }

    @Step("Get Company Values section title")
    public String getValueSectionTitleText() {
        wait.until(ExpectedConditions.visibilityOf(titleElement));
        return titleElement.getText().trim();
    }

    @Step("Get values cards count")
    public int getValuesCardsCount() {
        return valueCardElements.size();
    }

    @Step("Get list of Value Card components")
    public List<ValueCardComponent> getValueCards() {
        return valueCardElements.stream()
                .map(ValueCardComponent::new)
                .collect(Collectors.toList());
    }

    @Step("Get ValueCard component by index: {index}")
    public ValueCardComponent getProgramCard(int index) {
        if (index < 0 || index >= valueCardElements.size()) {
            throw new IndexOutOfBoundsException("Invalid ValueCard index: " + index + ", total cards: " + valueCardElements.size());
        }
        return new ValueCardComponent(valueCardElements.get(index));
    }

    @Step("Get ValueCard component by name: {cardName}")
    public ValueCardComponent getProgramCardByName(String cardName) {
        for (WebElement cardEl : valueCardElements) {
            ValueCardComponent card = new ValueCardComponent(cardEl);
            if (card.getNameText().toLowerCase().contains(cardName.toLowerCase())) {
                return card;
            }
        }
        throw new IllegalArgumentException("ValueCard not found with name containing: " + cardName);
    }

}