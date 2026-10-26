package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class SupportSectionComponent extends BaseComponent {

    @FindBy(css = "div[class*='SupportCard_people-card']")
    private List<WebElement> supportCardElements;

    @FindBy(css = "h2[class*='SupportSectionTablet_title'], [class*='SupportSectionTablet_title']")
    private WebElement titleElement;

    public SupportSectionComponent(WebElement element) {
        super(element);
    }

    @Step("Get Support section title")
    public String getTitleText() {
        return getText(titleElement);
    }

    @Step("Get Support Cards count")
    public int getSupportCardsCount() {
        return supportCardElements.size();
    }

    @Step("Get list of SupportCard components")
    public List<SupportCardComponent> getSupportCards() {
        return supportCardElements.stream()
                .map(SupportCardComponent::new)
                .collect(Collectors.toList());
    }

    @Step("Get Support Card component by index: {index}")
    public SupportCardComponent getSupportCardComponent(int index) {
        if (index < 0 || index >= supportCardElements.size()) {
            throw new IndexOutOfBoundsException("Invalid SupportCard index: " + index + ", total cards: " + supportCardElements.size());
        }
        return new SupportCardComponent(supportCardElements.get(index));
    }

    @Step("Get SupportCard component by name: {cardName}")
    public SupportCardComponent getSupportCardByName(String cardName) {
        for (WebElement cardEl : supportCardElements) {
            SupportCardComponent card = new SupportCardComponent(cardEl);
            if (card.getDescriptionText().toLowerCase().contains(cardName.toLowerCase())) {
                return card;
            }
        }
        throw new IllegalArgumentException(" SupportCard not found with name containing: " + cardName);
    }

}