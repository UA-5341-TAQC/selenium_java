package org.academy.ui.components.admin.team;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class TeamCategoryBarComponent extends BaseComponent {
    @FindBy(css = ".category-bar-button")
    private List<WebElement> categoryButtons;
    @FindBy(css = ".category-bar-button-selected")
    private WebElement selectedCategory;
    @FindBy(css = "[data-testid='context-menu']")
    private WebElement contextMenu;

    // Ініціалізує компонент у межах переданого кореневого елемента.
    public TeamCategoryBarComponent(WebElement rootElement) {
        super(rootElement);
    }

    // Повертає назви категорій із панелі.
    public List<String> getCategoryNames() {
        List<String> names = new ArrayList<>();
        for (WebElement button : categoryButtons) {
            names.add(button.getText());
        }
        return names;
    }

    // Повертає назву активної категорії.
    public String getSelectedCategoryName() {
        return wait.until(ExpectedConditions.visibilityOf(selectedCategory)).getText();
    }

    @Step("Select Team category: {categoryName}")
    public TeamCategoryBarComponent selectCategory(String categoryName) {
        for (WebElement button : categoryButtons) {
            if (button.getText().equals(categoryName)) {
                actions.moveToElement(button).perform();
                wait.until(ExpectedConditions.elementToBeClickable(button)).click();
                wait.until(webDriver -> getSelectedCategoryName().equals(categoryName));
                return this;
            }
        }
        throw new NoSuchElementException("Team category not found: " + categoryName);
    }

    @Step("Open Team category context menu")
    public TeamContextMenuComponent openContextMenu() {
        TeamContextMenuComponent menu =
                new TeamContextMenuComponent(contextMenu);
        if (!menu.isOpen()) {
            wait.until(ExpectedConditions.elementToBeClickable(contextMenu)).click();
        }
        return menu.waitUntilOpen();
    }
}
