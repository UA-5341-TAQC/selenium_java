package org.academy.ui.components.admin.programs.sections.content;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.academy.ui.components.admin.programs.sections.elements.SectionFieldComponent;
import org.academy.ui.types.ProgramSectionTemplate;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Objects;

/** Template content shared by the editor and translation form; no section toolbar actions. */
public abstract class ProgramSectionContent extends BaseComponent {

    @FindBy(css = "input:not([type='file']), textarea")
    private List<WebElement> textInputs;

    protected ProgramSectionContent(WebElement root) {
        super(root);
    }

    public ProgramSectionTemplate getTemplate() {
        return ProgramSectionTemplate.fromRoot(rootElement);
    }

    /**
     * Returns this same object as the requested content type; does not create or change a section.
     * Several layouts share a content type, so use {@link #getTemplate()} to check the exact layout.
     *
     * @throws IllegalArgumentException if this object is not an instance of the requested type
     */
    public final <T extends ProgramSectionContent> T as(Class<T> type) {
        Objects.requireNonNull(type, "Section content type must not be null");
        if (!type.isInstance(this)) {
            throw new IllegalArgumentException("Template " + getTemplate()
                    + " uses " + getClass().getSimpleName() + ", requested " + type.getSimpleName());
        }
        return type.cast(this);
    }

    /** Visible editable fields in DOM order; excludes collapsed FAQ answers. */
    @Step("Read editable section text fields")
    public List<SectionFieldComponent> getTextFields() {
        return textInputs.stream()
                .filter(element -> Objects.requireNonNull(element).isDisplayed())
                .map(SectionFieldComponent::fromInput)
                .filter(field -> Objects.requireNonNull(field).isEnabled())
                .toList();
    }

    /** Wraps the input and its counter/error container as one field. */
    protected SectionFieldComponent field(WebElement input) {
        return SectionFieldComponent.fromInput(input);
    }

    protected List<SectionFieldComponent> fields(List<WebElement> inputs) {
        return inputs.stream()
                .map(SectionFieldComponent::fromInput).toList();
    }
}
