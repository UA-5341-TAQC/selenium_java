package org.academy.ui.modals.admin;

import io.qameta.allure.Step;
import org.academy.ui.constants.HtmlAttribute;
import org.academy.ui.modals.BaseModal;
import org.academy.ui.types.ProgramSectionTemplate;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class AddSectionModal extends BaseModal {

    private static final String ACTIVE_TEMPLATE_CLASS = "swiper-slide-active";

    public static final String ROOT_SELECTOR =
            ".modal-container:has([data-testid='add-section-modal-content'])";

    @FindBy(css = ".swiper-slide:has([data-testid='add-section-modal-content'])")
    private List<WebElement> templateSlides;

    @FindBy(css = "." + ACTIVE_TEMPLATE_CLASS + " [data-testid='add-section-modal-content']")
    private WebElement activeTemplateContent;

    @FindBy(css = "button[aria-label='previous'][data-icon-position='only']")
    private WebElement previousButton;

    @FindBy(css = "button[aria-label='next'][data-icon-position='only']")
    private WebElement nextButton;

    @FindBy(css = ".modal-footer button.btn-primary")
    private WebElement chooseButton;

    public AddSectionModal(WebElement rootElement) {
        super(rootElement);
    }

    @Step("Read section template count")
    public int getTemplateCount() {
        return templateSlides.size();
    }

    /** Indexes follow the current UI order, starting at zero. */
    @Step("Read active section template index")
    public int getActiveTemplateIndex() {
        return readActiveTemplateIndex();
    }

    @Step("Read active section template type")
    public ProgramSectionTemplate getActiveTemplate() {
        return ProgramSectionTemplate.fromRoot(activeTemplateContent);
    }

    /** Finds the slide by template, independently of enum order. */
    @Step("Show section template {template}")
    public AddSectionModal showTemplate(ProgramSectionTemplate template) {
        java.util.Objects.requireNonNull(template, "Template must not be null");
        for (int index = 0; index < templateSlides.size(); index++) {
            if (ProgramSectionTemplate.fromRoot(templateSlides.get(index)) == template) {
                return showTemplate(index);
            }
        }
        throw new IllegalArgumentException("Template is unavailable: " + template);
    }

    @Step("Read active section template preview")
    public String getActiveTemplateText() {
        return getText(activeTemplateContent);
    }

    @Step("Check whether previous section template is available")
    public boolean canGoPrevious() {
        return previousButton.isEnabled();
    }

    @Step("Check whether next section template is available")
    public boolean canGoNext() {
        return nextButton.isEnabled();
    }

    @Step("Show next section template")
    public AddSectionModal nextTemplate() {
        changeTemplate(nextButton);
        return this;
    }

    @Step("Show previous section template")
    public AddSectionModal previousTemplate() {
        changeTemplate(previousButton);
        return this;
    }

    @Step("Show section template at index {index}")
    public AddSectionModal showTemplate(int index) {
        int templateCount = templateSlides.size();
        if (index < 0 || index >= templateCount) {
            throw new IllegalArgumentException("Template index out of range: " + index);
        }
        int activeIndex = readActiveTemplateIndex();
        for (int transitions = 0; activeIndex != index && transitions < templateCount - 1; transitions++) {
            int previousDistance = Math.abs(activeIndex - index);
            changeTemplate(activeIndex < index ? nextButton : previousButton);
            activeIndex = readActiveTemplateIndex();
            if (Math.abs(activeIndex - index) >= previousDistance) {
                throw new IllegalStateException("Carousel did not advance toward template: " + index);
            }
        }
        if (activeIndex != index) {
            throw new IllegalStateException("Could not reach template: " + index);
        }
        return this;
    }

    /** Chooses the template and closes the picker. Re-read the section from its form. */
    @Step("Choose the selected section template")
    public void chooseCurrentTemplate() {
        click(chooseButton);
        waitUntilClosed();
    }

    @Step("Close section template selection")
    public void close() {
        requestClose();
        waitUntilClosed();
    }

    private int readActiveTemplateIndex() {
        for (int index = 0; index < templateSlides.size(); index++) {
            if (templateSlides.get(index).getDomAttribute(HtmlAttribute.CLASS.getValue()).contains(ACTIVE_TEMPLATE_CLASS)) {
                return index;
            }
        }
        throw new IllegalStateException("No active section template");
    }

    private void changeTemplate(WebElement button) {
        if (!button.isEnabled()) {
            throw new IllegalStateException("No template available in this direction");
        }
        WebElement currentSlide = templateSlides.get(readActiveTemplateIndex());
        click(button);
        waitUntilAttributeNotContains(currentSlide, HtmlAttribute.CLASS.getValue(), ACTIVE_TEMPLATE_CLASS);
        waitUntilVisible(activeTemplateContent);
    }
}
