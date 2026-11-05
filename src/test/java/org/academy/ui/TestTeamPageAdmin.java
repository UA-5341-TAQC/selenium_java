package org.academy.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Owner;
import org.academy.ui.components.admin.team.TeamContextMenuComponent;
import org.academy.ui.modals.admin.team.TeamAddCategoryModal;
import org.academy.ui.modals.admin.team.TeamDeleteCategoryModal;
import org.academy.ui.modals.admin.team.TeamEditCategoryModal;
import org.academy.ui.pages.Admin.TeamPageAdmin;
import org.academy.utils.testrunners.UITestRunnerWithAdmin;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Feature("Team Page Admin")
public class TestTeamPageAdmin extends UITestRunnerWithAdmin {

    @Issue("302")
    @Description("Verify that Delete Category modal opens with the correct elements and button states")
    @Owner("Svitlana Kovalova")
    @Test
    public void testDeleteCategoryModal() {

        TeamPageAdmin teamPageAdmin = adminPanelPage
                .getNavigation()
                .clickTeamLink();

        TeamDeleteCategoryModal deleteCategoryModal = teamPageAdmin
                .getCategoryBar()
                .openContextMenu()
                .clickDeleteCategory();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(deleteCategoryModal.getModalTitle(), "Видалити категорію", "Delete category modal title should be correct");
        softAssert.assertTrue(deleteCategoryModal.isCategoryClickable(), "Category field should be clickable");
        softAssert.assertTrue(deleteCategoryModal.isCategoryRequired(), "Category field should be marked as required");
        softAssert.assertFalse(deleteCategoryModal.isDeleteEnabled(), "Delete button should be disabled by default");
        softAssert.assertTrue(deleteCategoryModal.isCancelClickable(), "Cancel button should be enabled");
        softAssert.assertTrue(deleteCategoryModal.isCloseClickable(), "Close button should be enabled");

        deleteCategoryModal.clickCancel();
        softAssert.assertAll();
    }

    @Issue("320")
    @Description("Verify that selecting a Team category automatically populates its description")
    @Owner("Svitlana Kovalova")
    @Test
    public void testCategoryDescriptionAutoPopulation() {

        TeamPageAdmin teamPageAdmin = adminPanelPage
                .getNavigation()
                .clickTeamLink();

        TeamEditCategoryModal editCategoryModal = teamPageAdmin
                .getCategoryBar()
                .openContextMenu()
                .clickEditCategory();

        String mainTeamDescription =
                "Люди, які щодня координують роботу програм, " +
                        "супроводжують учасників, будують логістику, фасилітують сесії.";

        String supervisoryBoardDescription =
                "Люди, які щодня координують роботу програм, " +
                        "супроводжують учасників, будують логістику, фасилітують сесії";

        String advisorsDescription =
                "Фахівці, які консультують нас у ключових напрямах: " +
                        "психічне здоров’я, етика, безпека, комунікації, фандрейзинг.  " +
                        "Їхні поради — наш додатковий компас.";

        SoftAssert softAssert = new SoftAssert();

        editCategoryModal.selectCategory("Радники");
        editCategoryModal.waitForDescription(advisorsDescription);

        softAssert.assertEquals(editCategoryModal.getDescription(), advisorsDescription, "Incorrect description for Advisors category");

        editCategoryModal.selectCategory("Наглядова рада");
        editCategoryModal.waitForDescription(supervisoryBoardDescription);

        softAssert.assertEquals(editCategoryModal.getDescription(), supervisoryBoardDescription, "Incorrect description for Supervisory Board category");

        editCategoryModal.selectCategory("Основна командa");
        editCategoryModal.waitForDescription(mainTeamDescription);

        softAssert.assertEquals(editCategoryModal.getDescription(), mainTeamDescription, "Incorrect description for Main Team category");

        editCategoryModal.close();
        softAssert.assertAll();
    }

    @Issue("335")
    @Description("Verify Add Category option is present in the context menu and opens the Add Category modal")
    @Owner("Svitlana Kovalova")
    @Test
    public void testAddCategoryModal() {

        TeamPageAdmin teamPageAdmin = adminPanelPage
                .getNavigation()
                .clickTeamLink();

        TeamContextMenuComponent contextMenu = teamPageAdmin
                .getCategoryBar()
                .openContextMenu();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(contextMenu.isOpen(), "Team context menu should be displayed");
        softAssert.assertTrue(contextMenu.getOptionNames().contains("Додати категорію"), "Add Category option should be present in the context menu");

        TeamAddCategoryModal addCategoryModal = contextMenu.clickAddCategory();

        softAssert.assertTrue(addCategoryModal.isDisplayed(), "Add Category modal should be displayed");
        softAssert.assertEquals(addCategoryModal.getTitle(), "Додати категорію", "Incorrect Add Category modal title");

        addCategoryModal.close();
        softAssert.assertAll();
    }
}
