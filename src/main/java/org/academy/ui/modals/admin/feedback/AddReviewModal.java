package org.academy.ui.modals.admin.feedback;

import io.qameta.allure.Step;
import org.academy.ui.modals.BaseModal;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddReviewModal extends BaseModal {

    public static final String ROOT_SELECTOR = "div.modal-container.feedback-form-modal";

    @FindBy(css = ".modal-header-text")
    private WebElement modalTitleElement;

    @FindBy(css = "input[name='authorName']")
    private WebElement authorNameInputElement;

    @FindBy(css = "#feedback-review-author-name-character-count")
    private WebElement authorNameCharacterCountElement;

    @FindBy(css = "button[aria-label='Clear input']")
    private WebElement clearInputButtonElement;

    @FindBy(css = "textarea[name='text']")
    private WebElement reviewTextareaElement;

    @FindBy(css = "#feedback-review-text-character-count")
    private WebElement reviewCharacterCountElement;

    @FindBy(css = ".input-group:has(#feedback-review-author-name) [class*='error-section']")
    private WebElement authorNameErrorElement;

    @FindBy(css = ".input-group:has(#feedback-review-text) [class*='error-section']")
    private WebElement reviewTextErrorElement;

    @FindBy(css = ".modal-footer button.btn-primary")
    private WebElement publishButtonElement;

    public AddReviewModal(WebElement rootElement) {
        super(rootElement);
    }

    public boolean isModalDisplayed() {
        try {
            waitUntilVisible(modalTitleElement);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Get modal title")
    public String getModalTitle() {
        return getText(modalTitleElement);
    }

    @Step("Fill author name: {authorName}")
    public void fillAuthorName(String authorName) {
        fillInput(authorNameInputElement, authorName);
    }

    @Step("Get author name")
    public String getAuthorName() {
        return getValue(authorNameInputElement);
    }

    @Step("Get author name character count")
    public String getAuthorNameCharacterCount() {
        return getText(authorNameCharacterCountElement);
    }

    @Step("Click clear input button")
    public void clickClearInputButton() {
        click(clearInputButtonElement);
    }

    @Step("Fill review text: {reviewText}")
    public void fillReviewText(String reviewText) {
        fillInput(reviewTextareaElement, reviewText);
    }

    @Step("Get review text")
    public String getReviewText() {
        return getValue(reviewTextareaElement);
    }

    @Step("Get review character count")
    public String getReviewCharacterCount() {
        return getText(reviewCharacterCountElement);
    }

    @Step("Get author name error message")
    public String getAuthorNameError() {
        return getText(authorNameErrorElement);
    }

    @Step("Check if author name error is displayed")
    public boolean isAuthorNameErrorDisplayed() {
        return isDisplayed(authorNameErrorElement);
    }

    @Step("Get review text error message")
    public String getReviewTextError() {
        return getText(reviewTextErrorElement);
    }

    @Step("Check if review text error is displayed")
    public boolean isReviewTextErrorDisplayed() {
        return isDisplayed(reviewTextErrorElement);
    }

    @Step("Click publish button")
    public void clickPublishButton() {
        click(publishButtonElement);
    }


    @Step("Close modal")
    public void close() {
        requestClose();
        waitUntilClosed();
    }
}