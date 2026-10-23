package org.academy.ui.components.admin.programs.sections.content;

import io.qameta.allure.Step;
import org.academy.ui.components.admin.programs.sections.elements.SectionFieldComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Program template with one title and five description fields.
 */
public class QuintupleDescriptionSectionContent extends ProgramSectionContent {

    @FindBy(css = "textarea[id='single-title-quintuple-title']")
    private WebElement titleInput;

    @FindBy(css = "textarea[id^='single-title-quintuple-desc-']")
    private List<WebElement> descriptionInputs;

    public QuintupleDescriptionSectionContent(WebElement root) {
        super(root);
    }

    @Step("Read section title field")
    public SectionFieldComponent getTitle() {
        return field(titleInput);
    }

    @Step("Set section title")
    public QuintupleDescriptionSectionContent setTitle(String value) {
        getTitle().setValue(value).blur();
        return this;
    }

    @Step("Read section description fields")
    public List<SectionFieldComponent> getDescriptions() {
        return fields(descriptionInputs);
    }

    public SectionFieldComponent getDescription(int index) {
        return getDescriptions().get(index);
    }

    @Step("Set description at index {index}")
    public QuintupleDescriptionSectionContent setDescription(int index, String value) {
        getDescription(index).setValue(value).blur();
        return this;
    }

}
