package org.academy.ui.components.admin.programs.sections.content;

import io.qameta.allure.Step;
import org.academy.ui.components.admin.programs.sections.elements.SectionFieldComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Fixed 2/3/4-card layout. Matching title/description indexes belong to the same card.
 */
public class TitleDescriptionCardsSectionContent extends ProgramSectionContent {

    @FindBy(css = "input[id*='-card-title-']")
    private List<WebElement> titleInputs;

    @FindBy(css = "textarea[id*='-card-description-']")
    private List<WebElement> descriptionInputs;

    public TitleDescriptionCardsSectionContent(WebElement root) {
        super(root);
    }

    @Step("Read card title fields")
    public List<SectionFieldComponent> getTitles() {
        return fields(titleInputs);
    }

    @Step("Read card description fields")
    public List<SectionFieldComponent> getDescriptions() {
        return fields(descriptionInputs);
    }

    public SectionFieldComponent getTitle(int index) {
        return getTitles().get(index);
    }

    public SectionFieldComponent getDescription(int index) {
        return getDescriptions().get(index);
    }

    @Step("Set title of card at index {index}")
    public TitleDescriptionCardsSectionContent setTitle(int index, String value) {
        getTitle(index).setValue(value).blur();
        return this;
    }

    @Step("Set description of card at index {index}")
    public TitleDescriptionCardsSectionContent setDescription(int index, String value) {
        getDescription(index).setValue(value).blur();
        return this;
    }

}
