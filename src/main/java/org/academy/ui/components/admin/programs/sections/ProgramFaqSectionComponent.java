package org.academy.ui.components.admin.programs.sections;

import io.qameta.allure.Step;
import org.academy.ui.components.admin.programs.sections.components.ProgramFaqCardComponent;
import org.academy.ui.components.admin.programs.sections.components.SectionFieldComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/** Program FAQ template: title and editable question/answer cards. */
public class ProgramFaqSectionComponent extends BaseProgramSection {

    @FindBy(css = "textarea[id$='-faq-title']")
    private WebElement titleInput;

    @FindBy(css = "[class*='EditableFaqCard_card__']")
    private List<WebElement> cardRoots;

    @FindBy(css = "[class*='FaqProgramSection_add-button__']")
    private WebElement addCardButton;

    public ProgramFaqSectionComponent(WebElement root) {
        super(root);
    }

    @Step("Read FAQ section title field")
    public SectionFieldComponent getTitle() {
        return field(titleInput);
    }

    @Step("Read program FAQ cards")
    public List<ProgramFaqCardComponent> getFaqCards() {
        return cardRoots.stream()
                .map(ProgramFaqCardComponent::new).toList();
    }

    public boolean canAddFaqCard() {
        return addCardButton.isEnabled();
    }

    /** Adds an empty card when the current questions/answers pass frontend validation. */
    @Step("Add program FAQ card")
    public ProgramFaqSectionComponent addFaqCard() {
        if (!canAddFaqCard()) {
            throw new IllegalStateException("Complete the existing FAQ cards before adding another");
        }
        int count = getFaqCards().size();
        click(addCardButton);
        wait.until(d -> getFaqCards().size() == count + 1);
        return this;
    }

    public ProgramFaqCardComponent getFaqCard(int index) {
        return getFaqCards().get(index);
    }

    @Step("Set FAQ section title")
    public ProgramFaqSectionComponent setTitle(String value) {
        getTitle().setValue(value).blur();
        return this;
    }

}
