package org.academy.ui.components;
import io.qameta.allure.Step;
import org.academy.ui.types.ProgramTypes;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;
import java.util.List;

public class ProgramsSectionComponent extends BaseComponent{
    private static final String ACTIVE_BUTTON_CLASS = "black-button";

    @FindBy(css = "div[class*='ProgramCardProgramsPage_root']")
    private List<WebElement> cardElements;

    @FindBy(css = "div[class*='ProgramsSection_menu-block'] h2")
    private WebElement title;

    @FindBy(css = "div[class*='ProgramsSection_button-block'] button")
    private List<WebElement> filterButtons;

    public ProgramsSectionComponent(WebElement element) {
        super(element);
    }

    @Step("Get program cards")
    public List<ProgramCardComponent> getCards() {
        return waitUntilAllVisible(cardElements).stream()
                .map(ProgramCardComponent::new)
                .toList();
    }

    @Step("Get Programs section title")
    public String getTitleText() {
        return getText(title).trim();
    }

    @Step("Select program filter")
    public ProgramsSectionComponent selectFilter(ProgramTypes type) {
        WebElement button = getFilterButton(type);
        click(button);
        wait.until(driver -> isActive(button));
        return this;
    }

    @Step("Get active program filter")
    public ProgramTypes getActiveFilter() {
        return Arrays.stream(ProgramTypes.values())
                .filter(type -> isActive(getFilterButton(type)))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No active program filter"));
    }

    @Step("Get filter button text")
    public String getFilterText(ProgramTypes type) {
        return getText(getFilterButton(type)).trim();
    }

    private WebElement getFilterButton(ProgramTypes type) {
        List<WebElement> buttons = waitUntilAllVisible(filterButtons);
        if (buttons.size() <= type.getPosition()) {
            throw new IllegalStateException(
                    "Expected filter button at position " + type.getPosition()
                            + ", but found only " + buttons.size() + " buttons");
        }
        return buttons.get(type.getPosition());
    }

    private boolean isActive(WebElement button) {
        String classes = button.getDomAttribute("class");
        return classes != null && classes.contains(ACTIVE_BUTTON_CLASS);
    }
}
