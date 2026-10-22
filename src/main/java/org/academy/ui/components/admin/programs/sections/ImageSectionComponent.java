package org.academy.ui.components.admin.programs.sections;

import io.qameta.allure.Step;
import org.academy.ui.components.admin.programs.sections.components.SectionImageComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.nio.file.Path;
import java.util.List;

/** Image controls are available only on layouts containing images. */
public class ImageSectionComponent extends TitleDescriptionSectionComponent {

    @FindBy(css = SectionImageComponent.ROOT_SELECTOR)
    private List<WebElement> imageRoots;

    public ImageSectionComponent(WebElement root) {
        super(root);
    }

    /** Zero-based image position; the template determines the number and layout. */
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
    public ImageSectionComponent chooseImage(int index, Path file) {
        getImage(index).chooseFile(file);
        return this;
    }

    @Step("Choose replacement image at index {index}")
    public ImageSectionComponent replaceImage(int index, Path file) {
        getImage(index).replaceFile(file);
        return this;
    }

}
