package org.academy.ui.components.admin.whoweare;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class WhoWeAreCategoryBarComponent extends BaseComponent {

    @FindBy(css = ".category-bar-button")
    private List<WebElement> categoryButtons;

    @FindBy(css = ".category-bar-button-selected")
    private WebElement selectedCategoryButton;

    public WhoWeAreCategoryBarComponent(WebElement rootElement) {
        super(rootElement);
    }

    @Step("Select category tab: {tabName}")
    public WhoWeAreCategoryBarComponent selectTab(String tabName) {
        WebElement targetTab = findByText(categoryButtons, tabName);
        if (!isTabSelected(tabName)) {
            click(targetTab);
            wait.until(d -> isTabSelected(tabName));
        }
        return this;
    }

    @Step("Check if category tab '{tabName}' is selected")
    public boolean isTabSelected(String tabName) {
        try {
            return getSelectedTabText().equalsIgnoreCase(tabName.trim());
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Get selected category tab text")
    public String getSelectedTabText() {
        return getText(selectedCategoryButton);
    }

    @Step("Get all category tab names")
    public List<String> getCategoryNames() {
        return getTexts(categoryButtons);
    }
}
