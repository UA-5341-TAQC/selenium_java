package org.academy.ui.modals.admin.feedback;

import io.qameta.allure.Step;
import org.academy.ui.modals.BaseModal;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddVideoReviewModal extends BaseModal {

    public static final String ROOT_SELECTOR = "div.modal-container.feedback-form-modal";

    @FindBy(css = ".modal-header-text")
    private WebElement modalTitleElement;

    @FindBy(css = "input[name='video-review-title']")
    private WebElement titleInputElement;

    @FindBy(css = "#video-review-title-character-count")
    private WebElement titleCharacterCountElement;

    @FindBy(css = "button[aria-label='Clear input']")
    private WebElement clearInputButtonElement;

    @FindBy(css = "textarea[name='video-review-link']")
    private WebElement videoReviewLinkTextareaElement;

    @FindBy(css = ".modal-footer button.btn-primary")
    private WebElement publishButtonElement;

    @FindBy(css = ".input-error")
    private WebElement videoReviewLinkErrorElement;

    @FindBy(css = "[class*='InputErrorWithCharacterCounter_error-section']")
    private WebElement titleErrorElement;

    public AddVideoReviewModal(WebElement rootElement) {
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

    @Step("Fill title: {title}")
    public void fillTitle(String title) {
        fillInput(titleInputElement, title);
    }

    @Step("Get title")
    public String getTitle() {
        return getValue(titleInputElement);
    }

    @Step("Get title character count")
    public String getTitleCharacterCount() {
        return getText(titleCharacterCountElement);
    }

    @Step("Click clear input button")
    public void clickClearInputButton() {
        click(clearInputButtonElement);
    }

    @Step("Fill video review link: {link}")
    public void fillVideoReviewLink(String link) {
        fillInput(videoReviewLinkTextareaElement, link);
    }

    @Step("Get video review link")
    public String getVideoReviewLink() {
        return getValue(videoReviewLinkTextareaElement);
    }

    @Step("Get video review link error message")
    public String getVideoReviewLinkError() {
        return getText(videoReviewLinkErrorElement);
    }

    @Step("Check if video review link error is displayed")
    public boolean isVideoReviewLinkErrorDisplayed() {
        return isDisplayed(videoReviewLinkErrorElement);
    }

    @Step("Get title error message")
    public String getTitleError() {
        return getText(titleErrorElement);
    }

    @Step("Check if title error is displayed")
    public boolean isTitleErrorDisplayed() {
        return isDisplayed(titleErrorElement);
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