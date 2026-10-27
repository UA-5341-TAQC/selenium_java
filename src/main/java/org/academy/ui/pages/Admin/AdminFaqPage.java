package org.academy.ui.pages.Admin;

import io.qameta.allure.Step;
import org.academy.ui.components.admin.faq.FaqListItemComponent;
import org.academy.ui.modals.admin.faq.FaqModal;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Objects;

public class AdminFaqPage extends BaseAdminPage {

    @FindBy(css = "[data-testid='faq-panel-content']")
    private WebElement pageContent;

    // Use the draggable wrapper: drag previews can contain another copy of the card.
    @FindBy(css = "[data-testid='faq-panel-content'] .draggable-item-wrapper > .draggable-item")
    private List<WebElement> items;

    @FindBy(css = "[data-testid='faq-panel-content'] .admin-panel-toolbar-actions > button.btn-primary")
    private WebElement addQuestionButton;

    public AdminFaqPage(WebDriver driver) {
        super(driver);
    }

    public AdminFaqPage waitUntilVisible() {
        waitUntilVisible(pageContent);
        return this;
    }

    @Step("Open add FAQ question modal")
    public FaqModal openAddQuestionModal() {
        click(addQuestionButton);
        return FaqModal.waitFor(wait);
    }

    /** Loaded items only; does not change search, category, language or scroll. */
    public List<FaqListItemComponent> getLoadedQuestions() {
        waitUntilVisible();
        return items.stream().map(FaqListItemComponent::new).toList();
    }

    @Step("Find loaded FAQ question: {question}")
    public FaqListItemComponent getQuestion(String question) {
        Objects.requireNonNull(question, "Question must not be null");
        return getLoadedQuestions().stream()
                .filter(faq -> question.equals(faq.getQuestion()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("FAQ question not found: " + question));
    }
}
