package org.academy.ui.pages.Admin;

import org.academy.ui.components.admin.team.TeamCategoryBarComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TeamPageAdmin extends BaseAdminPage {
    @FindBy(css = "[data-testid='team-page-content'] .category-bar")
    private WebElement categoryBar;

    public TeamPageAdmin(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(categoryBar));
    }

    public TeamCategoryBarComponent getCategoryBar() {
        return new TeamCategoryBarComponent(((WrapsElement) categoryBar).getWrappedElement());
    }
}
