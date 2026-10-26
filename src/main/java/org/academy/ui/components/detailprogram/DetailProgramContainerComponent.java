package org.academy.ui.components.detailprogram;

import io.qameta.allure.Step;
import org.academy.ui.Base;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;

import org.academy.ui.types.TemplateField;
import java.util.Optional;

public class DetailProgramContainerComponent extends BaseComponent {

    private final WebElement containerRoot;

    public DetailProgramContainerComponent(WebElement element) {
        super(element);
        this.containerRoot = element;
    }

    @Step("Return template section inside the container. Return Optional.empty(), if can't recognise the template.")
    public Optional <TemplateField> getTemplateSectionType(){
        for (TemplateField type : TemplateField.values()) {
            if (!containerRoot.findElements(type.getLocator()).isEmpty()) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }

    private Base createSubSectionInstance(TemplateField type) {
        WebElement subElement = this.containerRoot.findElement(type.getLocator());

        return switch (type) {
            case SINGLE_IMAGE_RIGHT -> new SingleImageRightComponent(subElement);
            case SINGLE_TITLE_DESCRIPTION_AUTHOR_PAIRS -> new SingleTitleDescriptionAuthorPairsComponent(subElement);
            case SINGLE_TITLE_QUINTUPLE_DESCRIPTION -> new SingleTitleQuintupleDescriptionComponent(subElement);
            case TITLE_DESCRIPTION_CARDS_SECTION -> new TitleDescriptionCardSectionComponent(subElement);
            case VIEW_TITLE_DESCRIPTION_SECTION -> new ViewTitleDescriptionSectionComponent(subElement);
        };
    }

}
