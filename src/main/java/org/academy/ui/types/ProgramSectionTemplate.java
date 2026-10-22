package org.academy.ui.types;

import org.academy.ui.constants.HtmlAttribute;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/** The 13 layouts, recognised by CSS class fragments without generated suffixes. */
public enum ProgramSectionTemplate {

    TITLE_DESCRIPTION_WITH_FOUR_IMAGES_BELOW("QuadImagesBottom_"),
    TITLE_DESCRIPTION_WITH_TWO_IMAGES_BELOW("DualImagesBottom_"),
    TITLE_AND_DESCRIPTION("TextOnly_"),
    TITLE_DESCRIPTION_WITH_THREE_IMAGES_BELOW("TripleImagesBottom_"),
    TITLE_DESCRIPTION_WITH_IMAGE_BELOW("SingleImageBottom_"),
    TITLE_DESCRIPTION_WITH_IMAGE_ABOVE("SingleImageTop_"),
    TITLE_DESCRIPTION_WITH_IMAGE_RIGHT("SingleImageRight_"),
    TITLE_WITH_FIVE_DESCRIPTIONS("SingleTitleQuintupleDescription_"),
    TITLE_WITH_DESCRIPTION_AUTHOR_PAIRS("SingleTitleDescriptionAuthorPairs_"),
    TWO_CARDS("TitleDescriptionCardsWrapper_container--cards-2__"),
    THREE_CARDS("TitleDescriptionCardsWrapper_container--cards-3__"),
    FOUR_CARDS("TitleDescriptionCardsWrapper_container--cards-4__"),
    FAQ("FaqProgramSection_");

    private static final String TEXT_PREVIEW_MARKER = "ViewTitleDescriptionSection_";

    // A single lookup avoids an implicit-wait delay for every non-matching template.
    private static final By TEMPLATE_MARKERS = By.cssSelector(Arrays.stream(values())
            .map(template -> "[class*='" + template.classPrefix + "']")
            .collect(Collectors.joining(", ")) + ", [class*='" + TEXT_PREVIEW_MARKER + "']");

    private final String classPrefix;

    ProgramSectionTemplate(String classPrefix) {
        this.classPrefix = classPrefix;
    }

    /**
     * Recognises one section or picker slide, including its descendants.
     * Do not pass a whole form containing multiple templates.
     * @throws IllegalStateException if no supported template is found
     */
    public static ProgramSectionTemplate fromRoot(WebElement root) {
        String rootClass = root.getDomAttribute(HtmlAttribute.CLASS.getValue());
        for (ProgramSectionTemplate template : values()) {
            if (rootClass != null && rootClass.contains(template.classPrefix)) {
                return template;
            }
        }

        List<String> markerClasses = root.findElements(TEMPLATE_MARKERS).stream()
                .map(element -> element.getDomAttribute(HtmlAttribute.CLASS.getValue()))
                .toList();
        for (ProgramSectionTemplate template : values()) {
            if (markerClasses.stream().anyMatch(classes ->
                    classes != null && classes.contains(template.classPrefix))) {
                return template;
            }
        }
        // Text-only previews use the shared text renderer; image-layout markers take precedence.
        if ((rootClass != null && rootClass.contains(TEXT_PREVIEW_MARKER))
                || markerClasses.stream().anyMatch(classes ->
                        classes != null && classes.contains(TEXT_PREVIEW_MARKER))) {
            return TITLE_AND_DESCRIPTION;
        }

        String childClasses = root.findElements(By.cssSelector("[class]")).stream()
                .map(element -> element.getDomAttribute(HtmlAttribute.CLASS.getValue()))
                .filter(classes -> classes != null && !classes.startsWith("ProgramSectionForm_")
                        && !classes.startsWith("IconButton_"))
                .distinct()
                .limit(20)
                .collect(Collectors.joining("; "));
        throw new IllegalStateException("Unsupported section template. Root: " + rootClass
                + "; descendants: " + childClasses);
    }
}
