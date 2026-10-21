package org.academy.ui.components;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class FaqSectionComponent extends BaseComponent{
    @FindBy(css = "h2[class*='FaqSection_title']")
    private WebElement title;

    @FindBy(css = "details[class*='FaqCard_faq-item']")
    private List<WebElement> cardElements;

    public FaqSectionComponent(WebElement element) {
        super(element);
    }

    @Step("Get FAQ section title")
    public String getTitleText() {
        return getText(title).trim();
    }

    @Step("Get FAQ cards")
    public List<FaqCardComponent> getCards() {
        return waitUntilAllVisible(cardElements).stream()
                .map(FaqCardComponent::new)
                .toList();
    }

    @Step("Get FAQ card by question")
    public FaqCardComponent getCardByQuestion(String question) {
        return getCards().stream()
                .filter(card -> card.getQuestionText().equals(question))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("FAQ card not found: " + question));
    }
}
