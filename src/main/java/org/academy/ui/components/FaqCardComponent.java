package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FaqCardComponent extends BaseComponent {

    @FindBy(css = "summary, [class*='question-block']")
    private WebElement questionSummary;

    @FindBy(css = "summary p, [class*='question-block'] p, summary")
    private WebElement questionText;

    @FindBy(css = "[class*='FaqCard_answer-block']")
    private WebElement answerBlock;

    public FaqCardComponent(WebElement element) {
        super(element);
    }

    @Step("Get FAQ question text")
    public String getQuestionText() {
        return getText(questionText);
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
        return getDomAttribute(rootElement, "open") != null;
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

