package org.academy.ui.components.admin.programs.sections.components;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.academy.ui.constants.HtmlAttribute;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Objects;

/**
 * A text field with its counter and errors. The root is the field container, not the input.
 * <pre>{@code
 * section.getTitle().setValue("New title").blur();
 * }</pre>
 */
public class SectionFieldComponent extends BaseComponent {

    private static final String MAX_LENGTH_PROPERTY = "maxLength";

    private static final By FIELD_CONTAINER = By.xpath("ancestor::div[.//output][1]");

    @FindBy(css = "input:not([type='file']), textarea")
    private WebElement textInput;

    @FindBy(css = "output")
    private WebElement counter;

    @FindBy(css = ".input-error")
    private List<WebElement> validationErrors;

    @FindBy(css = "label .required-field")
    private List<WebElement> requiredMarkers;

    public SectionFieldComponent(WebElement root) {
        super(root);
    }

    /** Finds the input's field container; PageFactory handles its controls. */
    public static SectionFieldComponent fromInput(WebElement input) {
        return new SectionFieldComponent(input.findElement(FIELD_CONTAINER));
    }

    @Step("Read section field value")
    public String getValue() {
        return getValue(textInput);
    }

    /** Replaces text. Call blur() to trigger validation. */
    @Step("Set section field value")
    public SectionFieldComponent setValue(String value) {
        Objects.requireNonNull(value, "Field value must not be null");
        fillInput(textInput, value);
        if (!value.equals(getValue())) {
            // Fall back to settled keystrokes when a controlled input drops characters.
            fillInput(textInput, "");
            for (int offset = 0; offset < value.length();) {
                int nextOffset = offset + Character.charCount(value.codePointAt(offset));
                textInput.sendKeys(value.substring(offset, nextOffset));
                String expected = value.substring(0, nextOffset);
                wait.until(driver -> expected.equals(getValue()));
                offset = nextOffset;
            }
        }
        return this;
    }

    @Step("Clear section field")
    public SectionFieldComponent clear() {
        return setValue("");
    }

    /** Checks required attributes and the label marker. */
    public boolean isRequired() {
        return textInput.getDomAttribute(HtmlAttribute.REQUIRED.getValue()) != null
                || Boolean.parseBoolean(textInput.getDomAttribute(HtmlAttribute.ARIA_REQUIRED.getValue()))
                || requiredMarkers.stream().anyMatch(element -> Objects.requireNonNull(element).isDisplayed());
    }

    /** Returns the DOM limit, or -1 when the input has no HTML maxlength constraint. */
    public int getMaxLength() {
        return Integer.parseInt(textInput.getDomProperty(MAX_LENGTH_PROPERTY));
    }

    public String getPlaceholder() {
        return textInput.getDomAttribute(HtmlAttribute.PLACEHOLDER.getValue());
    }

    @Step("Blur section field")
    public SectionFieldComponent blur() {
        textInput.sendKeys(Keys.TAB);
        return this;
    }

    @Step("Read section field character counter")
    public String getCharacterCounter() {
        return getText(counter);
    }

    public boolean isInvalid() {
        return Boolean.parseBoolean(textInput.getDomAttribute(HtmlAttribute.ARIA_INVALID.getValue()));
    }

    public boolean isEnabled() {
        return textInput.isEnabled() && textInput.getDomAttribute(HtmlAttribute.READ_ONLY.getValue()) == null;
    }

    @Step("Read section field validation errors")
    public List<String> getValidationErrors() {
        return validationErrors.stream()
                .filter(element -> Objects.requireNonNull(element).isDisplayed())
                .map(element -> Objects.requireNonNull(element).getText())
                .toList();
    }
}