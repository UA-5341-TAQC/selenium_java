package org.academy.ui.modals.admin.feedback;

import io.qameta.allure.Step;
import org.academy.ui.modals.BaseModal;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddHistoryModal extends BaseModal {

    public static final String ROOT_SELECTOR = "div.modal-container.feedback-form-modal";

    @FindBy(css = ".modal-header-text")
    private WebElement modalTitleElement;

    @FindBy(css = "input[name='title']")
    private WebElement titleInputElement;

    @FindBy(css = "#history-title-character-count")
    private WebElement titleCharacterCountElement;

    @FindBy(css = "button[aria-label='Clear input']")
    private WebElement clearInputButtonElement;

    @FindBy(css = "textarea[name='story']")
    private WebElement storyTextareaElement;

    @FindBy(css = "#history-story-character-count")
    private WebElement storyCharacterCountElement;

    @FindBy(css = "div[class*='DefaultImageInput_container']")
    private WebElement imageInputContainerElement;

    @FindBy(css = ".input-group:has(#history-title) [class*='error-section']")
    private WebElement titleErrorElement;

    @FindBy(css = ".input-group:has(#history-story) [class*='error-section']")
    private WebElement storyErrorElement;

    @FindBy(css = "[class*='FeedbackHistoryForm_error']")
    private WebElement imageErrorElement;

    @FindBy(css = ".modal-footer button.btn-primary")
    private WebElement publishButtonElement;

    public AddHistoryModal(WebElement rootElement) {
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

    @Step("Get title text")
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

    @Step("Fill story: {story}")
    public void fillStory(String story) {
        fillInput(storyTextareaElement, story);
    }

    @Step("Get story text")
    public String getStory() {
        return getValue(storyTextareaElement);
    }

    @Step("Get story character count")
    public String getStoryCharacterCount() {
        return getText(storyCharacterCountElement);
    }

    @Step("Get image input container")
    public WebElement getImageInputContainer() {
        return waitUntilVisible(imageInputContainerElement);
    }

    @Step("Get title error message")
    public String getTitleError() {
        return getText(titleErrorElement);
    }

    @Step("Check if title error is displayed")
    public boolean isTitleErrorDisplayed() {
        return isDisplayed(titleErrorElement);
    }

    @Step("Get story error message")
    public String getStoryError() {
        return getText(storyErrorElement);
    }

    @Step("Check if story error is displayed")
    public boolean isStoryErrorDisplayed() {
        return isDisplayed(storyErrorElement);
    }

    @Step("Get image error message")
    public String getImageError() {
        return getText(imageErrorElement);
    }

    @Step("Check if image error is displayed")
    public boolean isImageErrorDisplayed() {
        return isDisplayed(imageErrorElement);
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