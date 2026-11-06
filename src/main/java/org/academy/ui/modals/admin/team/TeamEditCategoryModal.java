package org.academy.ui.modals.admin.team;

import io.qameta.allure.Step;
import org.academy.ui.constants.HtmlAttribute;
import org.academy.ui.modals.admin.ConfirmationModal;
import org.academy.ui.pages.Admin.TeamPageAdmin;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Objects;

public class TeamEditCategoryModal extends BaseTeamCategoryModal<TeamEditCategoryModal> {
    private static final By MODAL = By.xpath(
            "//div[@class='modal-container'][.//form[@id='edit-team-category-form']]");
    private static final By ALL_MODALS = By.cssSelector(".modal-container");

    @FindBy(id = "edit-category-select")
    private WebElement categorySelect;
    @FindBy(css = "[role='option']")
    private List<WebElement> categoryOptions;

    public TeamEditCategoryModal(WebDriver driver) {
        super(driver, MODAL);
    }

    @Override
    protected TeamEditCategoryModal self() {
        return this;
    }

    /** Preserves the existing API: saving an edit also accepts its save confirmation. */
    @Override
    @Step("Save Team category changes")
    public TeamPageAdmin clickSave() {
        clickSaveButton();
        ConfirmationModal.waitFor(wait).confirm();
        wait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(ALL_MODALS)));
        return new TeamPageAdmin(driver);
    }

    @Step("Select category to edit: {name}")
    public TeamEditCategoryModal selectCategory(String name) {
        Objects.requireNonNull(name, "Category name must not be null");
        openCategories();
        for (WebElement option : categoryOptions) {
            if (name.equals(option.getText())) {
                click(option);
                wait.until(d -> name.equals(getSelectedCategory()) && !isCategorySelectOpen());
                return this;
            }
        }
        click(categorySelect);
        wait.until(d -> !isCategorySelectOpen());
        throw new NoSuchElementException("Category not found: " + name);
    }

    @Step("Read available Team categories to edit")
    public List<String> getAvailableCategories() {
        openCategories();
        List<String> names = getTexts(categoryOptions);
        click(categorySelect);
        wait.until(d -> !isCategorySelectOpen());
        return names;
    }

    public String getSelectedCategory() {
        return getText(categorySelect);
    }

    private boolean isCategorySelectOpen() {
        return Boolean.parseBoolean(categorySelect.getDomAttribute(HtmlAttribute.ARIA_EXPANDED.getValue()));
    }

    private void openCategories() {
        if (!isCategorySelectOpen()) click(categorySelect);
        waitUntilAllVisible(categoryOptions);
    }

    @Step("Wait for Team category description to update")
    public TeamEditCategoryModal waitForDescription(String expectedDescription) {
        wait.until(d -> expectedDescription.equals(getDescription()));
        return this;
    }
}
