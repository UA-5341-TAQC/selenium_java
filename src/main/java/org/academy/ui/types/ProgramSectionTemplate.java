package org.academy.ui.types;

import org.academy.ui.constants.HtmlAttribute;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/** The 13 program layouts and their editor/preview CSS class prefixes. */
public enum ProgramSectionTemplate {
    TITLE_DESCRIPTION_WITH_FOUR_IMAGES_BELOW("QuadImagesBottom_container__", "ViewQuadImagesBottom_container__"),
    TITLE_DESCRIPTION_WITH_TWO_IMAGES_BELOW("DualImagesBottom_container__", "ViewDualImagesBottom_container__"),
    TITLE_AND_DESCRIPTION("TextOnly_container__", "ViewTitleDescriptionSection_container__"),
    TITLE_DESCRIPTION_WITH_THREE_IMAGES_BELOW("TripleImagesBottom_container__", "TripleImagesBottom_form-container__", "ViewTripleImagesBottom_container__"),
    TITLE_DESCRIPTION_WITH_IMAGE_BELOW("SingleImageBottom_container__", "ViewSingleImageBottom_container__"),
    TITLE_DESCRIPTION_WITH_IMAGE_ABOVE("SingleImageTop_container__", "ViewSingleImageTop_container__"),
    TITLE_DESCRIPTION_WITH_IMAGE_RIGHT("SingleImageRight_container__", "ViewSingleImageRight_container__"),
    TITLE_WITH_FIVE_DESCRIPTIONS("SingleTitleQuintupleDescription_container__"),
    TITLE_WITH_DESCRIPTION_AUTHOR_PAIRS("SingleTitleDescriptionAuthorPairs_container__"),
    TWO_CARDS("TitleDescriptionCardsWrapper_container--cards-2__"),
    THREE_CARDS("TitleDescriptionCardsWrapper_container--cards-3__"),
    FOUR_CARDS("TitleDescriptionCardsWrapper_container--cards-4__"),
    FAQ("FaqProgramSection_faq-section__", "ViewFaqProgramSection_faq-section__");

    private final List<String> classPrefixes;

    ProgramSectionTemplate(String... classPrefixes) {
        this.classPrefixes = List.of(classPrefixes);
    }

    /**
     * Identifies one layout by the classes on its root element, ignoring generated CSS suffixes.
     * The editor, translation form or picker must locate the layout before calling this method.
     * Wrappers and descendants are not searched.
     *
     * @throws IllegalStateException if the classes match no template or more than one template
     */
    public static ProgramSectionTemplate fromRoot(WebElement layoutRoot) {
        Objects.requireNonNull(layoutRoot, "Layout root must not be null");
        String classes = layoutRoot.getDomAttribute(HtmlAttribute.CLASS.getValue());
        String[] cssClasses = classes == null ? new String[0] : classes.split("\\s+");
        EnumSet<ProgramSectionTemplate> matches = Arrays.stream(values())
                .filter(template -> template.matches(cssClasses))
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(ProgramSectionTemplate.class)));
        if (matches.size() != 1) {
            throw new IllegalStateException("Expected one program layout, found " + matches
                    + "; layout root classes: " + classes);
        }
        return matches.iterator().next();
    }

    private boolean matches(String[] cssClasses) {
        return classPrefixes.stream()
                .anyMatch(prefix -> Arrays.stream(cssClasses)
                        .anyMatch(cssClass -> cssClass.startsWith(prefix)));
    }
}
