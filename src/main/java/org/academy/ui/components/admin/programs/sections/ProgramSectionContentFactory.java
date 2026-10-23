package org.academy.ui.components.admin.programs.sections;

import org.academy.ui.types.ProgramSectionTemplate;
import org.academy.ui.components.admin.programs.sections.content.DescriptionAuthorPairsSectionContent;
import org.academy.ui.components.admin.programs.sections.content.ImageSectionContent;
import org.academy.ui.components.admin.programs.sections.content.ProgramFaqSectionContent;
import org.academy.ui.components.admin.programs.sections.content.ProgramSectionContent;
import org.academy.ui.components.admin.programs.sections.content.QuintupleDescriptionSectionContent;
import org.academy.ui.components.admin.programs.sections.content.TitleDescriptionCardsSectionContent;
import org.academy.ui.components.admin.programs.sections.content.TitleDescriptionSectionContent;
import org.openqa.selenium.WebElement;

/** Creates shared section content for both program editing and translation. */
public final class ProgramSectionContentFactory {

    private ProgramSectionContentFactory() {
    }

    /** Creates content for one layout root, not its editor or translation wrapper. */
    public static ProgramSectionContent create(WebElement layoutRoot) {
        return switch (ProgramSectionTemplate.fromRoot(layoutRoot)) {
            case TITLE_WITH_FIVE_DESCRIPTIONS -> new QuintupleDescriptionSectionContent(layoutRoot);
            case TWO_CARDS, THREE_CARDS,
                    FOUR_CARDS -> new TitleDescriptionCardsSectionContent(layoutRoot);
            case TITLE_WITH_DESCRIPTION_AUTHOR_PAIRS -> new DescriptionAuthorPairsSectionContent(layoutRoot);
            case FAQ -> new ProgramFaqSectionContent(layoutRoot);
            case TITLE_AND_DESCRIPTION -> new TitleDescriptionSectionContent(layoutRoot);
            case TITLE_DESCRIPTION_WITH_IMAGE_ABOVE,
                    TITLE_DESCRIPTION_WITH_IMAGE_BELOW,
                    TITLE_DESCRIPTION_WITH_IMAGE_RIGHT,
                    TITLE_DESCRIPTION_WITH_TWO_IMAGES_BELOW,
                    TITLE_DESCRIPTION_WITH_THREE_IMAGES_BELOW,
                    TITLE_DESCRIPTION_WITH_FOUR_IMAGES_BELOW -> new ImageSectionContent(layoutRoot);
        };
    }
}
