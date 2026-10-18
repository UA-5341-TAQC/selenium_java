package org.academy.ui.components.admin.team;

import io.qameta.allure.Step;

import org.academy.ui.components.BaseComponent;

import org.academy.ui.modals.admin.team.TeamAddCategoryModal;
import org.academy.ui.modals.admin.team.TeamDeleteCategoryModal;
import org.academy.ui.modals.admin.team.TeamEditCategoryModal;
import org.academy.ui.modals.admin.team.TeamTranslateCategoryModal;
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
    @FindBy(xpath = ".//button[@role='menuitem' and normalize-space()='Додати категорію']")
    private WebElement addCategory;

    public TeamContextMenuComponent(WebElement rootElement) {
        super(rootElement);
    }

    public boolean isOpen() {
        return options.isDisplayed();
    }

    public TeamContextMenuComponent waitUntilOpen() {
        wait.until(ExpectedConditions.visibilityOf(options));
        return this;
    }

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
        waitUntilOpen();
        wait.until(ExpectedConditions.elementToBeClickable(addCategory)).click();
        return new TeamAddCategoryModal(driver);
    }
    private void clickOption(String name) {
        waitUntilOpen();
        for (WebElement item : items) {
            if (item.getText().equals(name)) {
                wait.until(ExpectedConditions.elementToBeClickable(item)).click();
                return;
            }
        }
        throw new org.openqa.selenium.NoSuchElementException("Category menu option not found: " + name);
    }
    @Step("Open the edit Team category dialog")
    public TeamEditCategoryModal clickEditCategory() {
        clickOption("Редагувати");
        return new TeamEditCategoryModal(driver);
    }
    @Step("Open the Team category translation dialog")
    public TeamTranslateCategoryModal clickTranslateCategory() {
        clickOption("Додати переклад");
        return new TeamTranslateCategoryModal(driver);
    }
    @Step("Open the delete Team category dialog")
    public TeamDeleteCategoryModal clickDeleteCategory() {
        clickOption("Видалити");
        return new TeamDeleteCategoryModal(driver);
    }
}