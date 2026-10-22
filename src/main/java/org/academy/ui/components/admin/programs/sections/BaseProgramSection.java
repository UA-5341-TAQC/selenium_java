package org.academy.ui.components.admin.programs.sections;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.academy.ui.components.admin.programs.sections.components.SectionFieldComponent;
import org.academy.ui.modals.admin.AddSectionModal;
import org.academy.ui.types.ProgramSectionTemplate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Objects;

/**
 * One section in the create/edit form. All controls are scoped to its outer root.
 */
public abstract class BaseProgramSection extends BaseComponent {

    public static final String ROOT_SELECTOR = "[class*='ProgramSectionForm_container__']";

    @FindBy(css = "[class*='ProgramSectionForm_content__']")
    private WebElement content;

    @FindBy(css = "button[aria-label='Edit section']")
    private WebElement editButton;

    @FindBy(css = "[class*='ProgramSectionForm_actions__'] > .btn-primary")
    private List<WebElement> saveButtons;

    @FindBy(css = "[class*='ProgramSectionForm_actions__'] > .btn-secondary")
    private WebElement cancelButton;

    @FindBy(css = "button[aria-label='Delete section']")
    private WebElement deleteButton;

    @FindBy(css = "button[aria-label='Replace section']")
    private WebElement replaceButton;

    @FindBy(css = "button[aria-label='Move up section']")
    private List<WebElement> moveUpButtons;

    @FindBy(css = "button[aria-label='Move down section']")
    private List<WebElement> moveDownButtons;

    @FindBy(css = "input:not([type='file']), textarea")
    private List<WebElement> textInputs;

    protected BaseProgramSection(WebElement root) {
        super(root);
    }

    public ProgramSectionTemplate getTemplate() {
        return ProgramSectionTemplate.fromRoot(rootElement);
    }

    public String getPreviewText() {
        return getText(content);
    }

    public boolean isEditing() {
        return !saveButtons.isEmpty();
    }

    public boolean isSaveEnabled() {
        return saveButtons.get(0).isEnabled();
    }

    public boolean canMoveUp() {
        return available(moveUpButtons);
    }

    public boolean canMoveDown() {
        return available(moveDownButtons);
    }

    /** Opens editing unless the section is already editable. */
    @Step("Edit program section")
    public BaseProgramSection edit() {
        if (!isEditing()) {
            click(editButton);
            wait.until(d -> isEditing());
        }
        return this;
    }

    /** Opens the save confirmation; program persistence is a separate action. */
    @Step("Request section save")
    public void requestSave() {
        click(saveButtons.get(0));
        // TODO: Return the shared confirmation component when available.
    }

    /** Dirty sections require confirmation before returning to preview. */
    @Step("Request section edit cancellation")
    public void requestCancel() {
        click(cancelButton);
        // TODO: Return the shared confirmation for new or dirty sections when available.
    }

    /** Waits for preview after confirmation. Do not use for a removed new section. */
    public void waitUntilPreview() {
        wait.until(d -> !isEditing());
    }

    @Step("Open section deletion confirmation")
    public void openDeleteConfirmation() {
        click(deleteButton);
        // TODO: Return the shared confirmation component when available.
    }

    @Step("Replace section template")
    public AddSectionModal openReplacement() {
        click(replaceButton);
        return new AddSectionModal(waitUntilVisible(By.cssSelector(AddSectionModal.ROOT_SELECTOR)));
    }

    @Step("Move section up")
    public void moveUp() {
        if (!canMoveUp()) {
            throw new IllegalStateException("Cannot move section up: the control is absent, hidden or disabled");
        }
        click(moveUpButtons.get(0));
    }

    @Step("Move section down")
    public void moveDown() {
        if (!canMoveDown()) {
            throw new IllegalStateException("Cannot move section down: the control is absent, hidden or disabled");
        }
        click(moveDownButtons.get(0));
    }

    private boolean available(List<WebElement> buttons) {
        return buttons.stream()
                .anyMatch(e -> e.isDisplayed() && e.isEnabled());
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
