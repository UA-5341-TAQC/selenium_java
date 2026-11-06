package org.academy.ui.modals.admin.team;

import io.qameta.allure.Step;
import org.academy.ui.pages.Admin.TeamPageAdmin;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TeamAddCategoryModal extends BaseTeamCategoryModal<TeamAddCategoryModal> {
    private static final By MODAL = By.xpath(
            "//div[@class='modal-container'][.//form[@id='add-team-category-form']]");

    public TeamAddCategoryModal(WebDriver driver) {
        super(driver, MODAL);
    }

    @Override
    protected TeamAddCategoryModal self() {
        return this;
    }

    @Override
    @Step("Save a new Team category")
    public TeamPageAdmin clickSave() {
        clickSaveButton();
        wait.until(ExpectedConditions.stalenessOf(rootElement));
        return new TeamPageAdmin(driver);
    }
}
