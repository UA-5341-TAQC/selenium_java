package org.academy.ui.components.admin.whoweare;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class WhoWeAreDescriptionEditorComponent extends BaseComponent {

    private static final String CONTENTEDITABLE_ATTR = "contenteditable";

    @FindBy(css = "[contenteditable='true'], [aria-label='Rich text editor']")
    protected WebElement editorElement;

    @FindBy(css = "output")
    protected WebElement counterElement;

    @FindBy(css = "button[aria-label='Bold']")
    private WebElement boldButton;

    @FindBy(css = "button[aria-label='Italic']")
    protected WebElement italicButton;

    @FindBy(css = ".input-error, p.error, [data-testid='image-error']")
    private List<WebElement> errorElements;

    public WhoWeAreDescriptionEditorComponent(WebElement rootElement) {
        super(rootElement);
    }

    @Step("Get description text")
    public String getDescriptionText() {
        return getText(editorElement);
    }

    @Step("Check if description editor is editable")
    public boolean isEditable() {
        scrollIntoView(editorElement);
        return "true".equalsIgnoreCase(editorElement.getDomAttribute(CONTENTEDITABLE_ATTR))
                && !"true".equalsIgnoreCase(editorElement.getDomAttribute("aria-disabled"));
    }

    @Step("Clear description editor")
    public WhoWeAreDescriptionEditorComponent clear() {
        clearInput(editorElement);
        return this;
    }

    @Step("Set description text: {text}")
    public WhoWeAreDescriptionEditorComponent setText(String text) {
        fillInput(editorElement, text);
        return this;
    }

    @Step("Type text into description editor: {text}")
    public WhoWeAreDescriptionEditorComponent typeText(String text) {
        scrollIntoView(editorElement);
        editorElement.sendKeys(text);
        return this;
    }

    @Step("Delete {count} characters from description editor")
    public WhoWeAreDescriptionEditorComponent deleteCharacters(int count) {
        scrollIntoView(editorElement);
        click(editorElement);
        editorElement.sendKeys(Keys.END);
        for (int i = 0; i < count; i++) {
            editorElement.sendKeys(Keys.BACK_SPACE);
        }
        return this;
    }

    @Step("Replace description text temporarily and restore it at the end")
    public WhoWeAreDescriptionEditorComponent addAndDeleteCharacter(String character) {
        scrollIntoView(editorElement);
        click(editorElement);
        editorElement.sendKeys(Keys.END);
        editorElement.sendKeys(character);
        editorElement.sendKeys(Keys.BACK_SPACE);
        blurEditor();
        return this;
    }

    @Step("Enter a bold space into the description editor")
    public WhoWeAreDescriptionEditorComponent enterBoldSpace() {
        clear();
        scrollIntoView(editorElement);
        click(editorElement);

        editorElement.sendKeys(" ");
        editorElement.sendKeys(Keys.CONTROL, "a");

        click(boldButton);

        blurEditor();

        return this;
    }

    @Step("Paste text into description editor")
    public WhoWeAreDescriptionEditorComponent pasteText(String text) {
        scrollIntoView(editorElement);
        click(editorElement);
        editorElement.sendKeys(Keys.END);
        boolean inserted = false;
        try {
            Object result = js.executeScript(
                    "var el = arguments[0];" +
                    "var text = arguments[1];" +
                    "var oldText = el.innerText || el.textContent || '';" +
                    "var dataTransfer = new DataTransfer();" +
                    "dataTransfer.setData('text/plain', text);" +
                    "var pasteEvent = new ClipboardEvent('paste', {" +
                    "  bubbles: true, cancelable: true, clipboardData: dataTransfer" +
                    "});" +
                    "el.dispatchEvent(pasteEvent);" +
                    "if (!pasteEvent.defaultPrevented) {" +
                    "  var currentText = el.innerText || el.textContent || '';" +
                    "  if (!currentText.includes(text)) {" +
                    "    document.execCommand('insertText', false, text);" +
                    "    el.dispatchEvent(new Event('input', { bubbles: true }));" +
                    "  }" +
                    "}" +
                    "return true;",
                    editorElement, text
            );
            inserted = Boolean.TRUE.equals(result);
        } catch (Exception ignored) {
        }
        if (!inserted) {
            typeText(text);
        }
        return this;
    }

    @Step("Trigger blur on description editor")
    public WhoWeAreDescriptionEditorComponent blurEditor() {
        js.executeScript(
                "var el = arguments[0];" +
                "if (el && el.blur) { el.blur(); }" +
                "if (document.activeElement && document.activeElement.blur) { document.activeElement.blur(); }",
                editorElement
        );
        return this;
    }

    @Step("Get character counter text")
    public String getCounterText() {
        return getText(counterElement);
    }

    @Step("Wait for character counter to equal '{expectedCounter}'")
    public WhoWeAreDescriptionEditorComponent waitForCounter(String expectedCounter) {
        wait.until(d -> getCounterText().equalsIgnoreCase(expectedCounter.trim()));
        return this;
    }

    @Step("Get validation error message")
    public String getErrorMessage() {
        List<WebElement> visibleErrors = errorElements.stream()
                .filter(this::isDisplayed)
                .toList();

        return visibleErrors.isEmpty() ? "" : getText(visibleErrors.get(0));
    }

    @Step("Get number of visible validation error messages")
    public int getErrorMessagesCount() {
        return (int) errorElements.stream()
                .filter(WebElement::isDisplayed)
                .count();
    }

    @Step("Check if validation error message is displayed")
    public boolean hasErrorMessage() {
        return errorElements.stream().anyMatch(this::isDisplayed);
    }

    @Step("Wait for validation error message: {expectedError}")
    public WhoWeAreDescriptionEditorComponent waitForErrorMessage(String expectedError) {
        wait.until(d -> getErrorMessage().contains(expectedError));
        return this;
    }

    @Step("Wait for validation error message to disappear")
    public WhoWeAreDescriptionEditorComponent waitForErrorMessageToDisappear() {
        wait.until(d -> !hasErrorMessage());
        return this;
    }

    @Step("Select all description text and click the Bold button")
    public WhoWeAreDescriptionEditorComponent selectAllAndMakeBold() {
        scrollIntoView(editorElement);
        click(editorElement);

        editorElement.sendKeys(Keys.CONTROL, "a");

        click(boldButton);

        blurEditor();

        return this;
    }

    @Step("Apply bold formatting")
    public WhoWeAreDescriptionEditorComponent applyBold() {
        click(editorElement);
        editorElement.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        click(boldButton);
        return this;
    }

    @Step("Apply italic formatting")
    public WhoWeAreDescriptionEditorComponent applyItalic() {
        click(editorElement);
        editorElement.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        click(italicButton);
        return this;
    }
}
