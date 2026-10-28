package org.academy.ui.modals.admin.history;

import io.qameta.allure.Step;
import org.academy.ui.constants.HtmlAttribute;
import org.academy.ui.modals.BaseModal;
import org.academy.ui.types.ProgramSectionTemplate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Objects;

public class HistoryTemplateModal extends BaseModal {

    public static final String ROOT_SELECTOR = "[class*='AddSectionModal_modal-container']";

    private static final String ACTIVE_TEMPLATE_CLASS = "swiper-slide-active";
    private static final By SLIDE_LAYOUT = By.cssSelector("[data-testid='add-section-modal-content'] > *");

    @FindBy(css = "[data-testid='modal-overlay']")
    private WebElement modalOverlayElement;

    @FindBy(css = ".swiper-slide:has([data-testid='add-section-modal-content'])")
    private List<WebElement> templateSlides;

    @FindBy(css = "[class*='AddSectionSwiper_buttonBlock'] > button[aria-label='previous']")
    private WebElement previousButton;

    @FindBy(css = "[class*='AddSectionSwiper_buttonBlock'] > button[aria-label='next']")
    private WebElement nextButton;

    @FindBy(css = ".modal-container button.btn-primary")
    private WebElement chooseButton;

    @FindBy(css = "[data-testid='add-section-modal-content'] img")
    private List<WebElement> templateImageElements;

    @FindBy(css = "[data-testid='add-section-modal-content'] [data-testid='image-wrapper']")
    private List<WebElement> templateImageWrapperElements;

    public HistoryTemplateModal(WebElement rootElement) {
        super(rootElement);
    }

    public static HistoryTemplateModal waitFor(WebDriverWait wait) {
        WebElement root = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(ROOT_SELECTOR)));
        return new HistoryTemplateModal(root);
    }

    @Step("Get modal overlay")
    public WebElement getModalOverlay() {
        return waitUntilVisible(modalOverlayElement);
    }

    @Step("Get section template count")
    public int getTemplateCount() {
        return templateSlides.size();
    }

    @Step("Get active section template type")
    public ProgramSectionTemplate getActiveTemplate() {
        WebElement activeSlide = templateSlides.get(readActiveTemplateIndex());
        return ProgramSectionTemplate.fromRoot(activeSlide.findElement(SLIDE_LAYOUT));
    }

    @Step("Show section template {template}")
    public HistoryTemplateModal showTemplate(ProgramSectionTemplate template) {
        Objects.requireNonNull(template, "Template must not be null");
        for (WebElement slide : templateSlides) {
            if (ProgramSectionTemplate.fromRoot(slide.findElement(SLIDE_LAYOUT)) == template) {
                return showTemplate(templateSlides.indexOf(slide));
            }
        }
        throw new IllegalArgumentException("Template is unavailable: " + template);
    }

    @Step("Check whether previous button is available")
    public boolean canGoPrevious() {
        return previousButton.isEnabled();
    }

    @Step("Check whether next button is available")
    public boolean canGoNext() {
        return nextButton.isEnabled();
    }

    @Step("Show next section template")
    public HistoryTemplateModal nextTemplate() {
        changeTemplate(nextButton);
        return this;
    }

    @Step("Show previous section template")
    public HistoryTemplateModal previousTemplate() {
        changeTemplate(previousButton);
        return this;
    }

    @Step("Show section template at index {index}")
    public HistoryTemplateModal showTemplate(int index) {
        int templateCount = templateSlides.size();
        if (index < 0 || index >= templateCount) {
            throw new IllegalArgumentException("Template index out of range: " + index);
        }
        int activeIndex = readActiveTemplateIndex();
        int steps = Math.abs(index - activeIndex);
        for (int step = 0; step < steps; step++) {
            changeTemplate(activeIndex < index ? nextButton : previousButton);
        }
        if (readActiveTemplateIndex() != index) {
            throw new IllegalStateException("Could not reach template: " + index);
        }
        return this;
    }

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

    // Template Content Methods

    @Step("Get template images count")
    public int getTemplateImagesCount() {
        return templateImageElements.size();
    }

    @Step("Get template image wrappers count")
    public int getTemplateImageWrappersCount() {
        return templateImageWrapperElements.size();
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
    }
}