package org.academy.ui.components.admin.programs.sections;

import io.qameta.allure.Step;
import org.academy.ui.components.admin.programs.sections.elements.SectionFieldComponent;
import org.academy.ui.components.admin.programs.sections.elements.DescriptionAuthorCardComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/** Program section with a title and dynamic description/author pairs. */
public class DescriptionAuthorPairsSectionComponent extends BaseProgramSection {

    @FindBy(css = "textarea[id='single-title-description-author-pairs-title']")
    private WebElement titleInput;

    @FindBy(css = DescriptionAuthorCardComponent.ROOT_SELECTOR)
    private List<WebElement> blockRoots;

    @FindBy(css = "[class*='SingleTitleDescriptionAuthorPairs_add-card-button']")
    private WebElement addBlockButton;

    @FindBy(css = "button[aria-label='next']")
    private List<WebElement> nextButtons;

    @FindBy(css = "button[aria-label='previous']")
    private List<WebElement> previousButtons;

    public DescriptionAuthorPairsSectionComponent(WebElement root) {
        super(root);
    }

    @Step("Read section title field")
    public SectionFieldComponent getTitle() {
        return field(titleInput);
    }

    @Step("Read description/author blocks")
    public List<DescriptionAuthorCardComponent> getBlocks() {
        return blockRoots.stream()
                .map(DescriptionAuthorCardComponent::new)
                .toList();
    }

    /** Zero-based block position in the carousel. */
    public DescriptionAuthorCardComponent getBlock(int index) {
        return getBlocks().get(index);
    }

    /** Adds an empty author block. Re-read indexed fields after adding or removing blocks. */
    @Step("Add description/author block")
    public DescriptionAuthorPairsSectionComponent addAuthorBlock() {
        if (!canAddAuthorBlock()) {
            throw new IllegalStateException("Adding an author block is currently disabled");
        }
        int blocksBeforeAdding = getBlocks().size();
        click(addBlockButton);
        wait.until(driver -> getBlocks().size() == blocksBeforeAdding + 1);
        return this;
    }

    /** Scrolls the author-card carousel; does not change the order of its blocks. */
    @Step("Show next section carousel items")
    public DescriptionAuthorPairsSectionComponent nextItems() {
        click(nextButtons.get(0));
        return this;
    }

    public boolean canAddAuthorBlock() {
        return addBlockButton.isEnabled();
    }

    @Step("Set section title")
    public DescriptionAuthorPairsSectionComponent setTitle(String value) {
        getTitle().setValue(value).blur();
        return this;
    }

    public boolean canShowPreviousItems() {
        return carouselButtonAvailable(previousButtons);
    }

    public boolean canShowNextItems() {
        return carouselButtonAvailable(nextButtons);
    }

    @Step("Show previous section carousel items")
    public DescriptionAuthorPairsSectionComponent previousItems() {
        click(previousButtons.get(0));
        return this;
    }

    private boolean carouselButtonAvailable(List<WebElement> buttons) {
        return buttons.stream()
                .anyMatch(button -> button.isDisplayed() && button.isEnabled());
    }

}
