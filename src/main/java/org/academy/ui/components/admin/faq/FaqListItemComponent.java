package org.academy.ui.components.admin.faq;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.academy.ui.constants.HtmlAttribute;
import org.academy.ui.modals.admin.DeleteConfirmationModal;
import org.academy.ui.modals.admin.faq.FaqModal;
import org.academy.ui.modals.admin.faq.TranslateFaqModal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/** One FAQ row, including its drag handle. Re-read after list mutations. */
public class FaqListItemComponent extends BaseComponent {

    private static final By PUBLICATION_PAGES = By.cssSelector(".faq-actions-tooltip > span");

    @FindBy(css = ".faq-info-question > p")
    private WebElement question;
    @FindBy(css = ".faq-info-answer > p")
    private WebElement answer;
    @FindBy(css = ".faq-info-status .status > span:last-child")
    private WebElement status;
    @FindBy(css = "[data-testid='localization-statuses'] > span")
    private List<WebElement> translationBadges;
    @FindBy(css = ".faq-edit-btn")
    private WebElement editButton;
    @FindBy(css = ".faq-translate-btn")
    private WebElement translateButton;
    @FindBy(css = ".faq-delete-btn")
    private WebElement deleteButton;
    @FindBy(css = "button[aria-label='Show additional information']")
    private WebElement informationButton;
    @FindBy(css = ":scope > .dragger")
    private List<WebElement> dragHandles;

    public FaqListItemComponent(WebElement rootElement) {
        super(rootElement);
    }

    /** Reads the current row text without an additional wait. */
    public String getQuestion() {
        return question.getText();
    }

    public String getAnswer() {
        return getText(answer);
    }

    public String getStatusText() {
        return getText(status);
    }

    public List<String> getTranslationBadges() {
        return getTexts(translationBadges);
    }

    @Step("Open FAQ editor")
    public FaqModal openEditModal() {
        click(editButton);
        return FaqModal.waitFor(wait);
    }

    @Step("Open FAQ translation")
    public TranslateFaqModal openTranslateModal() {
        click(translateButton);
        return TranslateFaqModal.waitFor(wait);
    }

    @Step("Open FAQ deletion confirmation")
    public DeleteConfirmationModal openDeleteConfirmation() {
        click(deleteButton);
        return DeleteConfirmationModal.waitFor(wait, "Видалити питання?");
    }

    public boolean isInformationOpen() {
        return Boolean.parseBoolean(informationButton.getDomAttribute(HtmlAttribute.ARIA_EXPANDED.getValue()));
    }

    @Step("Read FAQ publication pages from information tooltip")
    public List<String> getPublicationPages() {
        if (!isInformationOpen()) {
            click(informationButton);
        }
        String tooltipId = waitUntilAttributeNotBlank(informationButton, HtmlAttribute.ARIA_DESCRIBED_BY.getValue());
        // The tooltip is rendered in a portal; its generated ID comes from the trigger.
        WebElement tooltip = waitUntilVisible(By.id(tooltipId));
        return getTexts(tooltip.findElements(PUBLICATION_PAGES));
    }

    public FaqListItemComponent closeInformation() {
        if (isInformationOpen()) {
            click(question);
        }
        waitUntilAttributeEquals(informationButton, HtmlAttribute.ARIA_EXPANDED.getValue(), "false");
        return this;
    }

    public boolean canReorder() {
        return !dragHandles.isEmpty() && dragHandles.get(0).isDisplayed()
                && "true".equals(dragHandles.get(0).getDomAttribute(HtmlAttribute.DRAGGABLE.getValue()));
    }

    /** Changes server-side ordering. Call explicitly, and re-read rows afterwards. */
    @Step("Move FAQ row to the position of another loaded row")
    public void moveTo(FaqListItemComponent target) {
        java.util.Objects.requireNonNull(target, "Target FAQ row must not be null");
        if (!canReorder()) {
            throw new IllegalStateException("FAQ reordering is disabled in the current view");
        }
        scrollIntoView(target.rootElement);
        actions.dragAndDrop(dragHandles.get(0), target.rootElement).perform();
    }
}
