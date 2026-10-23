package org.academy.ui.components.admin.team;

import io.qameta.allure.Step;

import org.academy.ui.components.BaseComponent;

import org.academy.ui.modals.admin.team.TeamAddCategoryModal;
import org.academy.ui.modals.admin.team.TeamDeleteCategoryModal;
import org.academy.ui.modals.admin.team.TeamEditCategoryModal;
import org.academy.ui.modals.admin.team.TeamTranslateCategoryModal;
import org.academy.ui.types.TeamCategoryAction;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class TeamContextMenuComponent extends BaseComponent {
    @FindBy(css = ".context-menu-button-options")
    private WebElement options;
    @FindBy(css = "[role='menuitem']")
    private List<WebElement> items;

    // Ініціалізує компонент у межах переданого кореневого елемента.
    public TeamContextMenuComponent(WebElement rootElement) {
        super(rootElement);
    }

    // Перевіряє видимість пунктів контекстного меню.
    public boolean isOpen() {
        return options.isDisplayed();
    }

    // Чекає видимості пунктів контекстного меню.
    public TeamContextMenuComponent waitUntilOpen() {
        wait.until(ExpectedConditions.visibilityOf(options));
        return this;
    }

    // Повертає назви пунктів відкритого меню.
    public List<String> getOptionNames() {
        waitUntilOpen();
        List<String> names = new ArrayList<>();
        for (WebElement item : items) {
            names.add(item.getText());
        }
        return names;
    }

    @Step("Open the add Team category dialog")
    public TeamAddCategoryModal clickAddCategory() {
        clickOption(TeamCategoryAction.ADD);
        return new TeamAddCategoryModal(driver);
    }

    // Знаходить пункт меню за дією з enum і натискає його.
    private void clickOption(TeamCategoryAction action) {
        waitUntilOpen();
        for (WebElement item : items) {
            if (item.getText().equals(action.getText())) {
                wait.until(ExpectedConditions.elementToBeClickable(item)).click();
                return;
            }
        }
        throw new NoSuchElementException("Category menu option not found: " + action.getText());
    }

    @Step("Open the edit Team category dialog")
    public TeamEditCategoryModal clickEditCategory() {
        clickOption(TeamCategoryAction.EDIT);
        return new TeamEditCategoryModal(driver);
    }

    @Step("Open the Team category translation dialog")
    public TeamTranslateCategoryModal clickTranslateCategory() {
        clickOption(TeamCategoryAction.TRANSLATE);
        return new TeamTranslateCategoryModal(driver);
    }

    @Step("Open the delete Team category dialog")
    public TeamDeleteCategoryModal clickDeleteCategory() {
        clickOption(TeamCategoryAction.DELETE);
        return new TeamDeleteCategoryModal(driver);
    }
}