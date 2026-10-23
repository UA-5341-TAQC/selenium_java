package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class FaqSectionComponent extends BaseComponent {

    @FindBy(css = "h2[class*='FaqSection_title']")
    private WebElement sectionTitle;

    @FindBy(css = "details[class*='FaqCard_faq-item']")
    private List<WebElement> cardElements;

    public FaqSectionComponent(WebElement element) {
        super(element);
    }

    @Step("Get FAQ section title")
    public String getTitleText() {
        return getText(sectionTitle).trim();
    }

    @Step("Get total number of FAQ cards")
    public int getFaqCardsCount() {
        return cardElements.size();
    }

    @Step("Get list of FAQ card components")
    public List<FaqCardComponent> getCards() {
        return waitUntilAllVisible(cardElements).stream()
                .map(FaqCardComponent::new)
                .toList();
    }

    @Step("Get FAQ card by index: {index}")
    public FaqCardComponent getFaqCard(int index) {
        if (index < 0 || index >= cardElements.size()) {
            throw new IndexOutOfBoundsException("Invalid FAQ card index: " + index + ", total cards: " + cardElements.size());
        }
        return new FaqCardComponent(cardElements.get(index));
    }

    @Step("Get FAQ card containing question: {questionSubstr}")
    public FaqCardComponent getFaqCardByQuestion(String questionSubstr) {
        for (WebElement el : cardElements) {
            FaqCardComponent card = new FaqCardComponent(el);
            if (card.getQuestionText().toLowerCase().contains(questionSubstr.toLowerCase())) {
                return card;
            }
        }
        throw new IllegalArgumentException("FAQ card not found with question substring: " + questionSubstr);
    }

    @Step("Get FAQ card by question")
    public FaqCardComponent getCardByQuestion(String question) {
        return getCards().stream()
                .filter(card -> card.getQuestionText().equals(question))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("FAQ card not found: " + question));
    }

    @Step("Expand card at index {index} and get answer text")
    public String expandAndGetAnswer(int index) {
        FaqCardComponent card = getFaqCard(index);
        return card.getAnswerText();
    }
}
