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

public class TestTeamCategoriesAdmin extends UITestRunnerWithAdmin {
    @Test(description = "Create, edit, translate and delete a category")
    public void testAddCategoryModal() {
        // 1. Після авторизації відкриваємо сторінку керування командою.
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlContains("/admin-panel"));
        driver.get(URI.create(testEnvProvider.getBaseUrl()).resolve("/admin-panel/team").toString());

        // 2. Чекаємо завантаження категорій, вибираємо першу та перевіряємо активну вкладку.
        TeamPageAdmin page = new TeamPageAdmin(driver);
        TeamCategoryBarComponent bar = page.getCategoryBar();
        TeamCategoryBarComponent initialBar = bar;
        wait.until(webDriver -> !initialBar.getCategoryNames().isEmpty());
        String categoryName = bar.getCategoryNames().getFirst();
        bar.selectCategory(categoryName);
        Assert.assertEquals(bar.getSelectedCategoryName(), categoryName);

        // 3. Відкриваємо контекстне меню та перевіряємо перелік доступних дій.
        TeamContextMenuComponent menu = bar.openContextMenu();
        Assert.assertEquals(menu.getOptionNames(),
                List.of("Додати категорію", "Редагувати", "Видалити", "Додати переклад"));

        // 4. Відкриваємо форму додавання та перевіряємо її видимість і заголовок.
        TeamAddCategoryModal modal = menu.clickAddCategory();
        Assert.assertTrue(modal.isDisplayed());
        Assert.assertEquals(modal.getTitle(), "Додати категорію");

        // 5. Генеруємо унікальну назву, заповнюємо назву й опис та перевіряємо введені дані.
        String newCategoryName = "QA " + Long.toString(System.currentTimeMillis(), 36);
        modal.setName(newCategoryName).setDescription("Опис тестової категорії").blurDescription();
        Assert.assertEquals(modal.getName(), newCategoryName);
        Assert.assertEquals(modal.getDescription(), "Опис тестової категорії");

        // 6. Зберігаємо категорію та чекаємо її появи на панелі категорій.
        page = modal.clickSave();
        TeamCategoryBarComponent updatedBar = page.getCategoryBar();
        wait.until(webDriver -> updatedBar.getCategoryNames().contains(newCategoryName));
        Assert.assertTrue(updatedBar.getCategoryNames().contains(newCategoryName),
                "Created category must appear on the category bar");


        // 7. Вибираємо створену категорію, перевіряємо початкові дані та змінюємо назву й опис.
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
        // 8. Після оновлення перевіряємо нову назву та відсутність попередньої назви.
        TeamCategoryBarComponent editedBar = bar;
        wait.until(webDriver -> editedBar.getCategoryNames().contains(editedName));
        Assert.assertFalse(bar.getCategoryNames().contains(newCategoryName));

        // 9. Повторно відкриваємо редагування та перевіряємо збережені назву й опис.
        edit = bar.openContextMenu().clickEditCategory();
        edit.selectCategory(editedName);
        Assert.assertEquals(edit.getName(), editedName);
        Assert.assertEquals(edit.getDescription(), editedDescription);
        edit.clickClose();
        edit.waitUntilClosed();

        // 10. Вибираємо створену категорію та додаємо англійський переклад назви й опису.
        TeamTranslateCategoryModal translation = bar.openContextMenu().clickTranslateCategory();
        translation.selectCategory(editedName);
        Assert.assertEquals(translation.getLanguage(), "Англійська");
        String translatedName = "EN " + newCategoryName;
        String translatedDescription = "English description for the test category";
        translation.setName(translatedName).setDescription(translatedDescription).blurDescription();
        page = translation.clickSave();

        driver.navigate().refresh();
        bar = new TeamPageAdmin(driver).getCategoryBar();
        // 11. Після оновлення чекаємо появи категорії перед перевіркою перекладу.
        TeamCategoryBarComponent translatedBar = bar;
        wait.until(webDriver -> translatedBar.getCategoryNames().contains(editedName));

        // 12. Повторно відкриваємо переклад і перевіряємо збережені значення.
        translation = bar.openContextMenu().clickTranslateCategory();
        translation.selectCategory(editedName);
        Assert.assertEquals(translation.getName(), translatedName);
        Assert.assertEquals(translation.getDescription(), translatedDescription);
        translation.clickClose();
        translation.waitUntilClosed();

        // 13. Перевіряємо вибрану категорію й доступність видалення, потім видаляємо її з підтвердженням.
        TeamDeleteCategoryModal deletion = bar.openContextMenu().clickDeleteCategory();
        deletion.selectCategory(editedName);
        Assert.assertEquals(deletion.getCategoryName(), editedName);
        Assert.assertTrue(deletion.isDeleteEnabled(),
                "Empty test category must allow deletion. " + deletion.getHint());
        page = deletion.clickDelete();

        // 14. Чекаємо зникнення видаленої категорії та перевіряємо її відсутність у списку.
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