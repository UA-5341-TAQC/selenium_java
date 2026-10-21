package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class FaqSectionComponent extends BaseComponent {

    @FindBy(css = "h2[class*='FaqSection_title']")
    private WebElement sectionTitle;

    @FindBy(css = "details[class*='FaqCard_faq-item'], details")
    private List<WebElement> faqCardElements;

    public FaqSectionComponent(WebElement element) {
        super(element);
    }

    @Step("Get FAQ section title")
    public String getTitleText() {
        return getText(sectionTitle);
    }

    @Step("Get total number of FAQ cards")
    public int getFaqCardsCount() {
        return faqCardElements.size();
    }

    @Step("Get list of FAQ card components")
    public List<FaqCardComponent> getFaqCards() {
        return faqCardElements.stream()
                .map(FaqCardComponent::new)
                .collect(Collectors.toList());
    }

    @Step("Get FAQ card by index: {index}")
    public FaqCardComponent getFaqCard(int index) {
        if (index < 0 || index >= faqCardElements.size()) {
            throw new IndexOutOfBoundsException("Invalid FAQ card index: " + index + ", total cards: " + faqCardElements.size());
        }
        return new FaqCardComponent(faqCardElements.get(index));
    }

    @Step("Get FAQ card containing question: {questionSubstr}")
    public FaqCardComponent getFaqCardByQuestion(String questionSubstr) {
        for (WebElement el : faqCardElements) {
            FaqCardComponent card = new FaqCardComponent(el);
            if (card.getQuestionText().toLowerCase().contains(questionSubstr.toLowerCase())) {
                return card;
            }
        }
        throw new IllegalArgumentException("FAQ card not found with question substring: " + questionSubstr);
    }

    @Step("Expand card at index {index} and get answer text")
    public String expandAndGetAnswer(int index) {
        FaqCardComponent card = getFaqCard(index);
        return card.getAnswerText();
    }
}

