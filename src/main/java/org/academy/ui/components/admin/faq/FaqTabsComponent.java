package org.academy.ui.components.admin.faq;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class FaqTabsComponent extends BaseComponent {

    private static final String SELECTED_CLASS = "category-bar-button-selected";

    @FindBy(css = ".category-bar-button")
    private List<WebElement> tabs;
    @FindBy(css = ".category-bar-button-selected")
    private WebElement activeTab;

    public FaqTabsComponent(WebElement rootElement) {
        super(rootElement);
    }

    public List<String> getTabNames() {
        return getTexts(tabs);
    }

    public String getActiveTabName() {
        return getText(activeTab);
    }

    @Step("Select FAQ tab")
    public void selectTab(String tabName) {
        WebElement tab = findByText(tabs, tabName);
        if (isSelected(tab)) {
            return;
        }
        click(tab);
        wait.until(ExpectedConditions.attributeContains(tab, "class", SELECTED_CLASS));
    }

    private boolean isSelected(WebElement tab) {
        String classes = tab.getDomAttribute("class");
        return classes != null && classes.contains(SELECTED_CLASS);

    }
}
