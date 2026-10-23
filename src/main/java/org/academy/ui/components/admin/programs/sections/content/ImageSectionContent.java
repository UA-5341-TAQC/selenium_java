package org.academy.ui.components.admin.programs.sections.content;

import io.qameta.allure.Step;
import org.academy.ui.components.admin.programs.sections.elements.SectionImageComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.nio.file.Path;
import java.util.List;

/**
 * Shared content for the six image layouts. One instance represents a whole section,
 * containing a title, a description and all its image slots.
 * Use {@link #getTemplate()} to distinguish the layouts.
 */
public class ImageSectionContent extends TitleDescriptionSectionContent {

    @FindBy(css = SectionImageComponent.ROOT_SELECTOR)
    private List<WebElement> imageRoots;

    public ImageSectionContent(WebElement root) {
        super(root);
    }

    /**
     * Returns an image slot by its zero-based position within this section, in DOM order.
     * For example, {@code getImage(1)} is the second image of this section.
     * Use {@link #getImages()} to inspect how many slots the current template has.
     *
     * @throws IndexOutOfBoundsException if this section has no slot at the given position
     */
    public SectionImageComponent getImage(int index) {
        return getImages().get(index);
    }

    @Step("Read section images")
    public List<SectionImageComponent> getImages() {
        return imageRoots.stream()
                .map(SectionImageComponent::new).toList();
    }

    /** May open cropping; handle it before continuing. */
    @Step("Choose image at index {index}")
    public ImageSectionContent chooseImage(int index, Path file) {
        getImage(index).chooseFile(file);
        return this;
    }

    @Step("Choose replacement image at index {index}")
    public ImageSectionContent replaceImage(int index, Path file) {
        getImage(index).replaceFile(file);
        return this;
    }

}
