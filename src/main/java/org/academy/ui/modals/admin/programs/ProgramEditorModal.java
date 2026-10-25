package org.academy.ui.modals.admin.programs;

import io.qameta.allure.Step;
import org.academy.ui.components.admin.programs.sections.ProgramSectionEditor;
import org.academy.ui.constants.HtmlAttribute;
import org.academy.ui.modals.admin.AddSectionModal;
import org.academy.ui.modals.admin.ConfirmationModal;
import org.academy.ui.modals.admin.CropperModal;
import org.academy.ui.types.ProgramSectionTemplate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Shared editor for creating and editing programs.
 */
public class ProgramEditorModal extends BaseProgramModal<ProgramEditorModal> {

    public static final String ROOT_SELECTOR = ".program-modal > .modal-overlay > .modal-container.fullscreen";

    @FindBy(css = "#toolbar-categories")
    private WebElement categoriesButton;

    @FindBy(css = "[role='option']")
    private List<WebElement> categoryOptions;

    @FindBy(css = ":scope > .modal-footer > .btn-secondary")
    private WebElement saveDraftButton;

    @FindBy(css = ":scope > .modal-footer > .btn-primary")
    private WebElement publishButton;

    @FindBy(css = "#previewImage")
    private WebElement previewImageInput;

    @FindBy(css = "#backgroundImage")
    private WebElement backgroundImageInput;

    @FindBy(css = "[role='button']:has(> #previewImage)")
    private WebElement previewImageControl;

    @FindBy(css = "[role='button']:has(> #backgroundImage)")
    private WebElement backgroundImageControl;

    @FindBy(css = "[role='button']:has(> #previewImage) button[aria-label='Remove image']")
    private WebElement removePreviewImageButton;

    @FindBy(css = "[role='button']:has(> #backgroundImage) button[aria-label='Remove image']")
    private WebElement removeBackgroundImageButton;

    @FindBy(css = "[role='button']:has(> #previewImage) [data-testid='preview-image']")
    private List<WebElement> previewImages;

    @FindBy(css = "[role='button']:has(> #backgroundImage) [data-testid='preview-image']")
    private List<WebElement> backgroundImages;

    @FindBy(css = "[class*='ProgramForm_empty-sections-text']")
    private List<WebElement> sectionsEmptyStateMessages;

    @FindBy(css = "[class*='ProgramForm_header-right'] button")
    private WebElement addSectionButton;

    @FindBy(css = ProgramSectionEditor.ROOT_SELECTOR)
    private List<WebElement> sectionRoots;

    public ProgramEditorModal(WebElement rootElement) {
        super(rootElement);
    }

    @Override
    protected ProgramEditorModal self() {
        return this;
    }

    /**
     * Sections in DOM order. Re-read after replacing, removing or reordering.
     */
    @Step("Read program sections in display order")
    public List<ProgramSectionEditor> getSections() {
        return sectionRoots.stream()
                .map(ProgramSectionEditor::new).toList();
    }

    /**
     * Newly created sections are returned in edit mode.
     */
    @Step("Add program section using template {template}")
    public ProgramSectionEditor addSection(ProgramSectionTemplate template) {
        int count = sectionRoots.size();
        openAddSectionModal().showTemplate(template).chooseCurrentTemplate();
        wait.until(d -> sectionRoots.size() == count + 1);
        return getSection(count);
    }

    /**
     * Zero-based editor section. Re-read after replacement, removal or reordering.
     *
     * <pre>{@code
     * var section = editor.getSection(1);
     * section.edit();
     * section.getContent(ImageSectionContent.class).chooseImage(0, imagePath);
     * }</pre>
     */
    public ProgramSectionEditor getSection(int index) {
        return new ProgramSectionEditor(sectionRoots.get(index));
    }

    @Step("Open add section modal")
    public AddSectionModal openAddSectionModal() {
        click(addSectionButton);
        WebElement modal = waitUntilVisible(By.cssSelector(AddSectionModal.ROOT_SELECTOR));
        return new AddSectionModal(modal);
    }

    @Step("Check whether the sections empty state is visible")
    public boolean isSectionsEmptyStateVisible() {
        return !sectionsEmptyStateMessages.isEmpty() && sectionsEmptyStateMessages.get(0).isDisplayed();
    }

    @Step("Read sections empty state message")
    public String getSectionsEmptyStateText() {
        return waitUntilAllVisible(sectionsEmptyStateMessages).get(0).getText();
    }

    @Step("Read selected program categories")
    public String getSelectedCategoriesText() {
        return getText(categoriesButton);
    }

    @Step("Read available program categories")
    public List<String> getAvailableCategories() {
        openCategories();
        List<String> names = new ArrayList<>();
        for (WebElement option : categoryOptions) {
            names.add(option.getText());
        }
        return names;
    }

    @Step("Set program category {category} selected: {selected}")
    public ProgramEditorModal setCategorySelected(String category, boolean selected) {
        Objects.requireNonNull(category, "Category must not be null");
        openCategories();
        for (WebElement option : categoryOptions) {
            if (category.equals(option.getText())) {
                if (selected != Boolean.parseBoolean(option.getDomAttribute(HtmlAttribute.ARIA_SELECTED.getValue()))) {
                    click(option);
                }
                return this;
            }
        }
        throw new IllegalArgumentException("Unknown category: " + category);
    }

    @Step("Check whether saving a draft is enabled")
    public boolean isSaveDraftEnabled() {
        return saveDraftButton.isEnabled();
    }

    @Step("Check whether publishing is enabled")
    public boolean isPublishEnabled() {
        return publishButton.isEnabled();
    }

    @Step("Check whether program preview image is present")
    public boolean hasPreviewImage() {
        return !previewImages.isEmpty() && previewImages.get(0).isDisplayed();
    }

    @Step("Check whether program background image is present")
    public boolean hasBackgroundImage() {
        return !backgroundImages.isEmpty() && backgroundImages.get(0).isDisplayed();
    }

    @Step("Read program preview image source")
    public String getPreviewImageSource() {
        return waitUntilAllVisible(previewImages)
                .get(0).getDomAttribute(HtmlAttribute.SRC.getValue());
    }

    @Step("Read program background image source")
    public String getBackgroundImageSource() {
        return waitUntilAllVisible(backgroundImages)
                .get(0).getDomAttribute(HtmlAttribute.SRC.getValue());
    }

    /**
     * File selection may open cropping, accept the image directly, or show a
     * validation error.
     * Call {@link #getCropper()} only when cropping is expected; raw selection
     * remains
     * available for validation tests and images that already have the requested
     * size.
     */
    @Step("Choose program preview image")
    public ProgramEditorModal choosePreviewImage(Path file) {
        uploadFile(previewImageInput, file);
        return this;
    }

    /**
     * File selection may open cropping, accept the image directly, or show a
     * validation error.
     * Call {@link #getCropper()} only when cropping is expected; raw selection
     * remains
     * available for validation tests and images that already have the requested
     * size.
     */
    @Step("Choose program background image")
    public ProgramEditorModal chooseBackgroundImage(Path file) {
        uploadFile(backgroundImageInput, file);
        return this;
    }

    @Step("Open preview image removal confirmation")
    public ConfirmationModal openPreviewImageRemovalConfirmation() {
        actions.moveToElement(previewImageControl).perform();
        click(removePreviewImageButton);
        return ConfirmationModal.waitFor(wait);
    }

    @Step("Open background image removal confirmation")
    public ConfirmationModal openBackgroundImageRemovalConfirmation() {
        actions.moveToElement(backgroundImageControl).perform();
        click(removeBackgroundImageButton);
        return ConfirmationModal.waitFor(wait);
    }

    @Step("Open program draft confirmation")
    public ConfirmationModal openSaveDraftConfirmation() {
        click(saveDraftButton);
        return ConfirmationModal.waitFor(wait);
    }

    @Step("Open program publication confirmation")
    public ConfirmationModal openPublishConfirmation() {
        click(publishButton);
        return ConfirmationModal.waitFor(wait);
    }

    /** Waits for the cropper opened by a valid image with different dimensions. */
    public CropperModal getCropper() {
        return CropperModal.waitFor(wait);
    }

    private void openCategories() {
        if (!Boolean.parseBoolean(categoriesButton.getDomAttribute(HtmlAttribute.ARIA_EXPANDED.getValue()))) {
            click(categoriesButton);
        }
        waitUntilAllVisible(categoryOptions);
    }
}
