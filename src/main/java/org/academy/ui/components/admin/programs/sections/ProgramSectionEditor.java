package org.academy.ui.components.admin.programs.sections;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.academy.ui.components.admin.programs.sections.content.ProgramSectionContent;
import org.academy.ui.modals.admin.AddSectionModal;
import org.academy.ui.modals.admin.ConfirmationModal;
import org.academy.ui.types.ProgramSectionTemplate;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Optional;

/**
 * One section in the create/edit form. All controls are scoped to its outer root.
 */
public class ProgramSectionEditor extends BaseComponent {

    public static final String ROOT_SELECTOR = "[class*='ProgramSectionForm_container__']";

    @FindBy(css = "[class*='ProgramSectionForm_content__']")
    private WebElement previewContent;

    @FindBy(css = "[class*='ProgramSectionForm_content__'] > *")
    private WebElement layoutRoot;

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

    public ProgramSectionEditor(WebElement root) {
        super(root);
    }

    public ProgramSectionTemplate getTemplate() {
        return ProgramSectionTemplate.fromRoot(layoutRoot);
    }

    /**
     * Creates content for the current layout; call again after editing or replacing the template.
     */
    public ProgramSectionContent getContent() {
        return ProgramSectionContentFactory.create(layoutRoot);
    }

    /**
     * Returns this section's content in the requested type. Does not open editing automatically.
     * <pre>{@code
     * var section = editor.getSection(0);
     * section.edit();
     * section.getContent(TitleDescriptionSectionContent.class).setTitle("Section title");
     * }</pre>
     * Each call uses the current layout. Obtain content again after replacing the template;
     * do not retain content or fields from the previous layout.
     *
     * @throws IllegalArgumentException if the section has a different content type
     */
    public <T extends ProgramSectionContent> T getContent(Class<T> type) {
        return getContent().as(type);
    }

    public String getPreviewText() {
        return getText(previewContent);
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

    /**
     * Opens editing unless the section is already editable.
     */
    @Step("Edit program section")
    public ProgramSectionEditor edit() {
        if (!isEditing()) {
            click(editButton);
            wait.until(d -> isEditing());
        }
        return this;
    }

    /**
     * Returns confirmation if needed, or empty after returning to preview. Does not persist the program.
     */
    @Step("Request section save")
    public Optional<ConfirmationModal> requestSave() {
        click(saveButtons.get(0));
        return waitForConfirmationOrPreview();
    }

    /**
     * New, replaced or dirty sections require confirmation; a clean section returns straight to preview.
     */
    @Step("Request section edit cancellation")
    public Optional<ConfirmationModal> requestCancel() {
        click(cancelButton);
        return waitForConfirmationOrPreview();
    }

    /**
     * Waits for preview after confirmation. Do not use for a removed new section.
     */
    public void waitUntilPreview() {
        wait.until(d -> !isEditing());
    }

    @Step("Open section deletion confirmation")
    public ConfirmationModal openDeleteConfirmation() {
        click(deleteButton);
        return ConfirmationModal.waitFor(wait);
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

    private Optional<ConfirmationModal> waitForConfirmationOrPreview() {
        return wait.until(d -> {
            WebElement confirmation = ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(ConfirmationModal.ROOT_SELECTOR)).apply(d);
            if (confirmation != null) {
                return Optional.of(new ConfirmationModal(confirmation));
            }
            try {
                return !rootElement.isDisplayed() || !isEditing() ? Optional.empty() : null;
            } catch (StaleElementReferenceException removedSection) {
                return Optional.empty();
            }
        });
    }

}
