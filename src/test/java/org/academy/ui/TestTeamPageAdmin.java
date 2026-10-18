package org.academy.ui;

import org.academy.ui.components.admin.team.TeamCategoryBarComponent;
import org.academy.ui.components.admin.team.TeamContextMenuComponent;
import org.academy.ui.modals.admin.team.TeamAddCategoryModal;
import org.academy.ui.modals.admin.team.TeamDeleteCategoryModal;
import org.academy.ui.modals.admin.team.TeamEditCategoryModal;
import org.academy.ui.modals.admin.team.TeamTranslateCategoryModal;
import org.academy.ui.pages.Admin.TeamPageAdmin;
import org.academy.ui.testrunners.UITestRunnerWithAdmin;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URI;
import java.time.Duration;
import java.util.List;

public class TestTeamPageAdmin extends UITestRunnerWithAdmin {
    @Test(description = "Create, edit, translate and delete a category")
    public void testAddCategoryModal() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlContains("/admin-panel"));
        driver.get(URI.create(testEnvProvider.getBaseUrl()).resolve("/admin-panel/team").toString());

        TeamPageAdmin page = new TeamPageAdmin(driver);
        TeamCategoryBarComponent bar = page.getCategoryBar();
        TeamCategoryBarComponent initialBar = bar;
        wait.until(webDriver -> !initialBar.getCategoryNames().isEmpty());
        String categoryName = bar.getCategoryNames().getFirst();
        bar.selectCategory(categoryName);
        Assert.assertEquals(bar.getSelectedCategoryName(), categoryName);

        TeamContextMenuComponent menu = bar.openContextMenu();
        Assert.assertEquals(menu.getOptionNames(),
                List.of("Додати категорію", "Редагувати", "Видалити", "Додати переклад"));

        TeamAddCategoryModal modal = menu.clickAddCategory();
        Assert.assertTrue(modal.isDisplayed());
        Assert.assertEquals(modal.getTitle(), "Додати категорію");

        String newCategoryName = "QA " + Long.toString(System.currentTimeMillis(), 36);
        modal.setName(newCategoryName).setDescription("Опис тестової категорії").blurDescription();
        Assert.assertEquals(modal.getName(), newCategoryName);
        Assert.assertEquals(modal.getDescription(), "Опис тестової категорії");

        page = modal.clickSave();
        TeamCategoryBarComponent updatedBar = page.getCategoryBar();
        wait.until(webDriver -> updatedBar.getCategoryNames().contains(newCategoryName));
        Assert.assertTrue(updatedBar.getCategoryNames().contains(newCategoryName),
                "Created category must appear on the category bar");


        TeamEditCategoryModal edit = updatedBar.openContextMenu().clickEditCategory();
        edit.selectCategory(newCategoryName);
        Assert.assertEquals(edit.getName(), newCategoryName);
        Assert.assertEquals(edit.getDescription(), "Опис тестової категорії");
        String editedName = newCategoryName + " E";
        String editedDescription = "Оновлений опис тестової категорії";
        edit.setName(editedName).setDescription(editedDescription).blurDescription();
        page = edit.clickSave();

        driver.navigate().refresh();
        bar = new TeamPageAdmin(driver).getCategoryBar();
        TeamCategoryBarComponent editedBar = bar;
        wait.until(webDriver -> editedBar.getCategoryNames().contains(editedName));
        Assert.assertFalse(bar.getCategoryNames().contains(newCategoryName));

        edit = bar.openContextMenu().clickEditCategory();
        edit.selectCategory(editedName);
        Assert.assertEquals(edit.getName(), editedName);
        Assert.assertEquals(edit.getDescription(), editedDescription);
        edit.clickClose();
        edit.waitUntilClosed();

        TeamTranslateCategoryModal translation = bar.openContextMenu().clickTranslateCategory();
        translation.selectCategory(editedName);
        Assert.assertEquals(translation.getLanguage(), "Англійська");
        String translatedName = "EN " + newCategoryName;
        String translatedDescription = "English description for the test category";
        translation.setName(translatedName).setDescription(translatedDescription).blurDescription();
        page = translation.clickSave();

        driver.navigate().refresh();
        bar = new TeamPageAdmin(driver).getCategoryBar();
        TeamCategoryBarComponent translatedBar = bar;
        wait.until(webDriver -> translatedBar.getCategoryNames().contains(editedName));

        translation = bar.openContextMenu().clickTranslateCategory();
        translation.selectCategory(editedName);
        Assert.assertEquals(translation.getName(), translatedName);
        Assert.assertEquals(translation.getDescription(), translatedDescription);
        translation.clickClose();
        translation.waitUntilClosed();

        TeamDeleteCategoryModal deletion = bar.openContextMenu().clickDeleteCategory();
        deletion.selectCategory(editedName);
        Assert.assertEquals(deletion.getCategoryName(), editedName);
        Assert.assertTrue(deletion.isDeleteEnabled(),
                "Empty test category must allow deletion. " + deletion.getHint());
        page = deletion.clickDelete();

        TeamCategoryBarComponent barAfterDelete = page.getCategoryBar();

        wait.until(webDriver ->
                !barAfterDelete.getCategoryNames().contains(editedName)
        );

        Assert.assertFalse(
                barAfterDelete.getCategoryNames().contains(editedName),
                "Deleted category must not appear on the category bar"
        );
    }
}