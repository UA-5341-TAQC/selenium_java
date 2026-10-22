package org.academy.ui.components.admin.programs.sections;

import org.academy.ui.types.ProgramSectionTemplate;
import org.openqa.selenium.WebElement;

/** Maps a section's template to its editor class. Layouts with the same controls share a class. */
public final class ProgramSectionComponentFactory {

    private ProgramSectionComponentFactory() {
    }

    public static BaseProgramSection create(WebElement root) {
        return switch (ProgramSectionTemplate.fromRoot(root)) {
            case TITLE_WITH_FIVE_DESCRIPTIONS -> new QuintupleDescriptionSectionComponent(root);
            case TWO_CARDS, THREE_CARDS,
                    FOUR_CARDS -> new TitleDescriptionCardsSectionComponent(root);
            case TITLE_WITH_DESCRIPTION_AUTHOR_PAIRS -> new DescriptionAuthorPairsSectionComponent(root);
            case FAQ -> new ProgramFaqSectionComponent(root);
            case TITLE_AND_DESCRIPTION -> new TitleDescriptionSectionComponent(root);
            case TITLE_DESCRIPTION_WITH_IMAGE_ABOVE,
                    TITLE_DESCRIPTION_WITH_IMAGE_BELOW,
                    TITLE_DESCRIPTION_WITH_IMAGE_RIGHT,
                    TITLE_DESCRIPTION_WITH_TWO_IMAGES_BELOW,
                    TITLE_DESCRIPTION_WITH_THREE_IMAGES_BELOW,
                    TITLE_DESCRIPTION_WITH_FOUR_IMAGES_BELOW -> new ImageSectionComponent(root);
        };
    }
}
