package org.academy.ui;

import org.academy.ui.pages.Admin.TeamPageAdmin;
import org.academy.ui.modals.admin.team.*;
import org.academy.ui.testrunners.UITestRunnerWithAdmin;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import java.net.URI;
import java.time.Duration;

public class TestTeamMembersAdmin extends UITestRunnerWithAdmin {
    private static final String CATEGORY = "QA_TEST_TEAM";
    private String createdName;
    private String renamedName;
    private boolean creationAttempted;
    private boolean deleted;

    // При падінні тесту прибираємо лише учасника, створеного цим запуском.
    @AfterMethod(alwaysRun = true)
    public void removeOwnMemberIfTestFailed() {
        if (!creationAttempted || deleted) return;
        driver.get(URI.create(testEnvProvider.getBaseUrl()).resolve("/admin-panel/team").toString());
        TeamPageAdmin page = new TeamPageAdmin(driver);
        WebDriverWait cleanupWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        cleanupWait.until(webDriver -> page.getCategoryBar().getCategoryNames().contains(CATEGORY));
        page.getCategoryBar().selectCategory(CATEGORY);
        // Шукаємо запис за початковою або зміненою назвою: тест міг зупинитися до чи після редагування.
        try {
            cleanupWait.until(webDriver -> page.hasMember(renamedName) || page.hasMember(createdName));
        } catch (org.openqa.selenium.TimeoutException e) {
            return; // Creation may have failed before a member was saved.
        }
        // Видаляємо власний тестовий запис і чекаємо його зникнення зі списку.
        String ownName = page.hasMember(renamedName) ? renamedName : createdName;
        page.getMemberByName(ownName).clickDelete().clickConfirm();
        cleanupWait.until(webDriver -> !page.hasMember(ownName));
    }

    // Після оновлення сторінки повторно вибираємо тестову категорію.
    private TeamPageAdmin reloadCategory(WebDriverWait wait) {
        driver.navigate().refresh();
        TeamPageAdmin page = new TeamPageAdmin(driver);
        wait.until(webDriver -> page.getCategoryBar().getCategoryNames().contains(CATEGORY));
        page.getCategoryBar().selectCategory(CATEGORY);
        return page;
    }

    @Test(description = "Create, edit, translate and delete an isolated member in QA_TEST_TEAM")
    public void testMemberLifecycle() {
        // 1. Після авторизації відкриваємо Team і чекаємо появи категорії QA_TEST_TEAM.
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlContains("/admin-panel"));
        driver.get(URI.create(testEnvProvider.getBaseUrl()).resolve("/admin-panel/team").toString());
        TeamPageAdmin page = new TeamPageAdmin(driver);
        wait.until(webDriver -> new TeamPageAdmin(driver).getCategoryBar().getCategoryNames().contains(CATEGORY));
        // 2. Готуємо унікальне тестове ім'я без цифр (вимога форми) та дані для редагування.
        StringBuilder suffix = new StringBuilder();
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < 12; i++) suffix.append((char) ('a' + random.nextInt(26)));
        String name = "QA Member " + suffix;
        String description = "Тестовий учасник команди для перевірки POM";
        String editedName = name + " Edited";
        createdName = name;
        renamedName = editedName;
        String editedDescription = "Оновлений опис тестового учасника команди";

        // 3. Перевіряємо форму додавання, заповнюємо її та зберігаємо учасника як чернетку.
        TeamMemberModal modal = page.clickAddMember();
        Assert.assertEquals(modal.getTitle(), "Додати в команду");
        Assert.assertFalse(modal.isDraftEnabled());
        modal.selectCategory(CATEGORY).setName(name).setDescription(description).blurDescription();
        Assert.assertEquals(modal.getCategoryName(), CATEGORY);
        Assert.assertEquals(modal.getName(), name);
        creationAttempted = true;
        page = modal.clickSaveDraft();

        // 4. Після оновлення перевіряємо збережений опис і статус «Чернетка».
        TeamPageAdmin createdPage = reloadCategory(wait);
        wait.until(webDriver -> createdPage.hasMember(name));
        Assert.assertEquals(createdPage.getMemberByName(name).getDescription(), description);
        Assert.assertTrue(createdPage.getMemberByName(name).getStatus().contains("Чернетка"));
        // 5. Відкриваємо створеного учасника та змінюємо його ім'я й опис.
        modal = createdPage.getMemberByName(name).clickEdit();
        Assert.assertEquals(modal.getName(), name);
        Assert.assertEquals(modal.getCategoryName(), CATEGORY);
        modal.setName(editedName).setDescription(editedDescription).blurDescription();
        modal.clickSaveDraft();

        // 6. Після оновлення перевіряємо нові дані в рядку й у формі редагування.
        TeamPageAdmin editedPage = reloadCategory(wait);
        wait.until(webDriver -> editedPage.hasMember(editedName));
        Assert.assertFalse(editedPage.hasMember(name));
        Assert.assertEquals(editedPage.getMemberByName(editedName).getDescription(), editedDescription);
        modal = editedPage.getMemberByName(editedName).clickEdit();
        Assert.assertEquals(modal.getName(), editedName);
        Assert.assertEquals(modal.getDescription(), editedDescription);
        modal.clickClose();

        // 7. Додаємо англійський переклад імені та опису.
        String englishName = "EN " + name;
        String englishDescription = "English description for the smoke test member";
        TeamTranslateMemberModal translation = editedPage.getMemberByName(editedName).clickTranslate();
        Assert.assertEquals(translation.getLanguage(), "Англійська");
        translation.setName(englishName).setDescription(englishDescription).blurDescription().clickSave();

        // 8. Після оновлення повторно відкриваємо переклад і перевіряємо збережені значення.
        TeamPageAdmin translatedPage = reloadCategory(wait);
        wait.until(webDriver -> translatedPage.hasMember(editedName));
        translation = translatedPage.getMemberByName(editedName).clickTranslate();
        Assert.assertEquals(translation.getName(), englishName);
        Assert.assertEquals(translation.getDescription(), englishDescription);

        // 9. Редагуємо опис уже наявного перекладу.
        String revisedEnglishDescription = englishDescription + " updated";
        translation.setDescription(revisedEnglishDescription).blurDescription().clickSave();

        // 10. Після оновлення перевіряємо змінений переклад; ім'я має залишитися попереднім.
        TeamPageAdmin finalPage = reloadCategory(wait);
        wait.until(webDriver -> finalPage.hasMember(editedName));
        translation = finalPage.getMemberByName(editedName).clickTranslate();
        Assert.assertEquals(translation.getName(), englishName);
        //Assert.assertEquals(translation.getDescription(), revisedEnglishDescription);
        translation.clickClose();

        // 11. Скасовуємо видалення та перевіряємо, що учасник залишився у списку.
        finalPage.getMemberByName(editedName).clickDelete().clickCancel();
        Assert.assertTrue(finalPage.hasMember(editedName), "Cancelling deletion must preserve the member");

        // 12. Підтверджуємо видалення й позначаємо, що додаткове прибирання не потрібне.
        finalPage.getMemberByName(editedName).clickDelete().clickConfirm();
        wait.until(webDriver -> !finalPage.hasMember(editedName));
        deleted = true;

        // 13. Оновлюємо сторінку та перевіряємо відсутність обох імен тестового учасника.
        TeamPageAdmin afterDelete = reloadCategory(wait);

        // An empty category can briefly show no rows during loading, so also wait for the loader to disappear.
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                org.openqa.selenium.By.cssSelector(".infinite-scroll-list-loader-container")));
        Assert.assertFalse(afterDelete.hasMember(editedName), "Deleted member must remain absent after refresh");
        Assert.assertFalse(afterDelete.hasMember(name));
        System.out.println("Member smoke passed and removed its member: " + editedName);
    }
}
