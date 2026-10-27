package org.academy.ui.modals.admin.faq;

import io.qameta.allure.Step;
import org.academy.ui.constants.HtmlAttribute;
import org.academy.ui.modals.admin.ConfirmationModal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Objects;

/** The same form creates and edits a question. Submission is always explicit. */
public class FaqModal extends BaseFaqModal<FaqModal> {

    public static final String ROOT_SELECTOR = ".modal-container:has(> .modal-body > form.faq-form-main)";

    @FindBy(css = "#pages")
    private WebElement pagesButton;
    @FindBy(css = ".multiselect [role='option']")
    private List<WebElement> pageOptions;
    @FindBy(css = ":scope > .modal-footer > .btn-secondary")
    private WebElement draftButton;
    @FindBy(css = ":scope > .modal-footer > .btn-primary")
    private WebElement publishButton;

    @FindBy(css = ".modal-content-error-container")
    private List<WebElement> submissionErrors;

    public FaqModal(WebElement rootElement) {
        super(rootElement);
    }

    public static FaqModal waitFor(WebDriverWait wait) {
        WebElement root = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(ROOT_SELECTOR)));
        return new FaqModal(root);
    }

    @Override
    protected FaqModal self() {
        return this;
    }

    public String getSubmissionError() {
        return submissionErrors.stream()
                .filter(WebElement::isDisplayed).map(WebElement::getText)
                .findFirst().orElse("");
    }

    public List<String> getAvailablePages() {
        openPages();
        List<String> names = getTexts(pageOptions);
        closePages();
        return names;
    }

    public List<String> getSelectedPages() {
        openPages();
        List<String> names = pageOptions.stream()
                .filter(option -> "true".equals(option.getDomAttribute(HtmlAttribute.ARIA_SELECTED.getValue())))
                .map(WebElement::getText).map(String::trim).toList();
        closePages();
        return names;
    }

    /** Replaces the selection, including deselection of pages no longer wanted. */
    @Step("Select FAQ publication pages: {names}")
    public FaqModal setPages(List<String> names) {
        Objects.requireNonNull(names, "Page names must not be null");
        openPages();
        if (!getTexts(pageOptions).containsAll(names)) {
            closePages();
            throw new IllegalArgumentException("Unknown FAQ publication page: " + names);
        }
        for (WebElement option : pageOptions) {
            boolean selected = "true".equals(option.getDomAttribute(HtmlAttribute.ARIA_SELECTED.getValue()));
            if (selected != names.contains(option.getText().trim())) {
                click(option);
            }
        }
        closePages();
        return this;
    }

    private void openPages() {
        if (!"true".equals(pagesButton.getDomAttribute(HtmlAttribute.ARIA_EXPANDED.getValue()))) {
            click(pagesButton);
        }
        waitUntilAllVisible(pageOptions);
    }

    private void closePages() {
        if ("true".equals(pagesButton.getDomAttribute(HtmlAttribute.ARIA_EXPANDED.getValue()))) {
            click(pagesButton);
        }
        waitUntilAttributeEquals(pagesButton, HtmlAttribute.ARIA_EXPANDED.getValue(), "false");
    }

    public boolean isDraftEnabled() { return draftButton.isEnabled(); }
    public boolean isPublishEnabled() { return publishButton.isEnabled(); }

    @Step("Open FAQ draft confirmation")
    public ConfirmationModal openSaveDraftConfirmation() {
        click(draftButton);
        return ConfirmationModal.waitFor(wait);
    }

    @Step("Open FAQ publication confirmation")
    public ConfirmationModal openPublishConfirmation() {
        click(publishButton);
        return ConfirmationModal.waitFor(wait);
    }

}
