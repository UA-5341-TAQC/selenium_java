package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FaqCardComponent extends BaseComponent{
    @FindBy(css = "summary[class*='FaqCard_question-block']")
    private WebElement questionBlock;

    @FindBy(css = "summary[class*='FaqCard_question-block'] p")
    private WebElement questionSummary;

    @FindBy(css = "summary p, [class*='question-block'] p, summary")
    private WebElement questionText;

    @FindBy(css = "div[class*='FaqCard_answer-block']")
    private WebElement answerBlock;

    public FaqCardComponent(WebElement element) {
        super(element);
    }

    private String normalize(String text) {
        return text.replaceAll("\\s+", " ").trim();
    }

    @Step("Get FAQ question text")
    public String getQuestionText() {
        return normalize(getText(questionText));
    }

    @Step("Get FAQ answer text")
    public String getAnswerText() {
        if (!isExpanded()) {
            expand();
        }
        return getText(answerBlock);
    }

    @Step("Check if FAQ card is expanded")
    public boolean isExpanded() {
        return rootElement.getDomAttribute("open") != null;
    }

    @Step("Check if FAQ answer is displayed")
    public boolean isAnswerDisplayed() {
        return answerBlock.isDisplayed();
    }

    @Step("Toggle FAQ card")
    public FaqCardComponent toggle() {
        scrollIntoView(questionSummary);
        click(questionSummary);
        return this;
    }

    @Step("Expand FAQ card")
    public FaqCardComponent expand() {
        if (!isExpanded()) {
            toggle();
            wait.until(d -> isExpanded());
        }
        return this;
    }

    @Step("Collapse FAQ card")
    public FaqCardComponent collapse() {
        if (isExpanded()) {
            toggle();
            wait.until(d -> !isExpanded());
        }
        return this;
    }
}
