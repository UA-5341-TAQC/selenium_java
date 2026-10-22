package org.academy.ui.components.admin.programs.sections.components;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/** Editable question/answer pair inside a program's FAQ template. */
public class ProgramFaqCardComponent extends BaseComponent {

    public static final String ROOT_SELECTOR = "[class*='EditableFaqCard_card__']";

    @FindBy(css = "textarea[id*='-faq-question-']")
    private WebElement questionInput;

    @FindBy(css = "textarea[id*='-faq-answer-']")
    private WebElement answerInput;

    @FindBy(css = "button[aria-label='Collapse answer']")
    private List<WebElement> collapseButtons;

    @FindBy(css = "button[aria-label='Expand answer']")
    private WebElement expandButton;

    @FindBy(css = "button[aria-label='Delete question']")
    private List<WebElement> deleteButtons;

    public ProgramFaqCardComponent(WebElement root) {
        super(root);
    }

    public SectionFieldComponent getQuestion() {
        return field(questionInput);
    }

    /** Expands the card to render its answer. Re-read the answer after collapsing it. */
    public SectionFieldComponent getAnswer() {
        setExpanded(true);
        return field(answerInput);
    }

    public boolean isExpanded() {
        return !collapseButtons.isEmpty();
    }

    public boolean canDelete() {
        return !deleteButtons.isEmpty();
    }

    @Step("Request program FAQ card deletion")
    public void requestDelete() {
        click(deleteButtons.get(0));
        // TODO: Return the shared confirmation component when available.
    }

    @Step("Set FAQ answer expanded: {expanded}")
    public ProgramFaqCardComponent setExpanded(boolean expanded) {
        if (isExpanded() != expanded) {
            click(expanded ? expandButton : collapseButtons.get(0));
            wait.until(d -> isExpanded() == expanded);
        }
        return this;
    }

    @Step("Set FAQ question")
    public ProgramFaqCardComponent setQuestion(String value) {
        getQuestion().setValue(value).blur();
        return this;
    }

    @Step("Set FAQ answer")
    public ProgramFaqCardComponent setAnswer(String value) {
        getAnswer().setValue(value).blur();
        return this;
    }

    private SectionFieldComponent field(WebElement input) {
        return SectionFieldComponent.fromInput(input);
    }
}
