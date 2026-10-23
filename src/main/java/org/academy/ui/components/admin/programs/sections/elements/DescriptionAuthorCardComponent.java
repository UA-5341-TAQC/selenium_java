package org.academy.ui.components.admin.programs.sections.elements;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * One description/author block. All fields and actions stay inside its root.
 */
public class DescriptionAuthorCardComponent extends BaseComponent {

    public static final String ROOT_SELECTOR = "[class*='DescriptionAuthorPairCard_card__']";

    @FindBy(css = "textarea[id^='pair-description-']")
    private WebElement descriptionInput;

    @FindBy(css = "textarea[id^='pair-author-']")
    private WebElement authorInput;

    @FindBy(css = "button[aria-label='delete']")
    private List<WebElement> deleteButtons;

    public DescriptionAuthorCardComponent(WebElement root) {
        super(root);
    }

    public SectionFieldComponent getDescription() {
        return SectionFieldComponent.fromInput(descriptionInput);
    }

    @Step("Set block description")
    public DescriptionAuthorCardComponent setDescription(String value) {
        getDescription().setValue(value).blur();
        return this;
    }

    public SectionFieldComponent getAuthor() {
        return SectionFieldComponent.fromInput(authorInput);
    }

    @Step("Set block author")
    public DescriptionAuthorCardComponent setAuthor(String value) {
        getAuthor().setValue(value).blur();
        return this;
    }

    public boolean canDelete() {
        return deleteButtons.stream()
                .anyMatch(button -> button.isDisplayed() && button.isEnabled());
    }

    /**
     * Opens confirmation; does not delete the block immediately.
     */
    @Step("Request description/author block deletion")
    public void requestDelete() {
        if (!canDelete()) {
            throw new IllegalStateException("Cannot delete block: the control is absent, hidden or disabled");
        }
        click(deleteButtons.get(0));
        // TODO: Return the shared confirmation component when available.
    }
}
