package org.academy.ui.components.admin.programs.sections.content;

import io.qameta.allure.Step;
import org.academy.ui.components.admin.programs.sections.elements.SectionFieldComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Title/description editor, also used as the text part of image layouts.
 */
public class TitleDescriptionSectionContent extends ProgramSectionContent {

    @FindBy(css = "textarea[id$='section-title']")
    private WebElement titleInput;

    @FindBy(css = "textarea[id$='section-description']")
    private WebElement descriptionInput;

    public TitleDescriptionSectionContent(WebElement root) {
        super(root);
    }

    @Step("Read section title field")
    public SectionFieldComponent getTitle() {
        return field(titleInput);
    }

    @Step("Set section title")
    public TitleDescriptionSectionContent setTitle(String value) {
        getTitle().setValue(value).blur();
        return this;
    }

    @Step("Read section description field")
    public SectionFieldComponent getDescription() {
        return field(descriptionInput);
    }

    @Step("Set section description")
    public TitleDescriptionSectionContent setDescription(String value) {
        getDescription().setValue(value).blur();
        return this;
    }

}
