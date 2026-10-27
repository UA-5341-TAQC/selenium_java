package org.academy.ui.modals.admin.faq;

import io.qameta.allure.Step;
import org.academy.ui.constants.HtmlAttribute;
import org.academy.ui.modals.BaseModal;
import org.academy.ui.modals.admin.ConfirmationModal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Optional;

/** Fields and close behavior shared by the FAQ editor and translation form. */
public abstract class BaseFaqModal<T extends BaseFaqModal<T>> extends BaseModal {

    private static final By CLOSE_CONFIRMATION = By.cssSelector(ConfirmationModal.ROOT_SELECTOR);

    @FindBy(css = ":scope > .modal-header .modal-header-text")
    private WebElement title;

    @FindBy(css = "#question")
    private WebElement question;

    @FindBy(css = "#answer")
    private WebElement answer;

    @FindBy(id = "question-character-count")
    private WebElement questionCounter;

    @FindBy(id = "answer-character-count")
    private WebElement answerCounter;

    @FindBy(css = ".input-error, [class*='InputErrorWithCharacterCounter_error-section__']")
    private List<WebElement> validationErrors;

    protected BaseFaqModal(WebElement rootElement) {
        super(rootElement);
    }

    protected abstract T self();

    public String getTitle() {
        return getText(title);
    }

    public String getQuestion() {
        return getValue(question);
    }

    public String getAnswer() {
        return getValue(answer);
    }

    @Step("Set FAQ question")
    public T setQuestion(String value) {
        fillInput(question, value);
        return self();
    }

    @Step("Set FAQ answer")
    public T setAnswer(String value) {
        fillInput(answer, value);
        return self();
    }

    public T blurQuestion() {
        blur(question);
        return self();
    }

    public T blurAnswer() {
        blur(answer);
        return self();
    }

    public int getQuestionLimit() {
        return Integer.parseInt(question.getDomAttribute(HtmlAttribute.MAX_LENGTH.getValue()));
    }

    public int getAnswerLimit() {
        return Integer.parseInt(answer.getDomAttribute(HtmlAttribute.MAX_LENGTH.getValue()));
    }

    public String getQuestionCounter() {
        return getText(questionCounter);
    }

    public String getAnswerCounter() {
        return getText(answerCounter);
    }

    public List<String> getValidationErrors() {
        return validationErrors.stream()
                .filter(WebElement::isDisplayed).map(WebElement::getText)
                .map(String::trim).filter(text -> !text.isEmpty()).toList();
    }

    /** Empty means the clean form closed; dirty forms require an explicit choice. */
    @Step("Request FAQ modal close")
    public Optional<ConfirmationModal> requestCloseWithConfirmation() {
        requestClose();
        return wait.until(d -> {
            WebElement confirmation = ExpectedConditions.visibilityOfElementLocated(
                    CLOSE_CONFIRMATION).apply(d);
            if (confirmation != null) {
                return Optional.of(new ConfirmationModal(confirmation));
            }
            return ExpectedConditions.invisibilityOf(rootElement).apply(d) ? Optional.empty() : null;
        });
    }
}
