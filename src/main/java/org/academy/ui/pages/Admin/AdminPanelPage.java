package org.academy.ui.pages.Admin;

import io.qameta.allure.Step;
import org.academy.ui.components.admin.AdminPanelNavigationComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AdminPanelPage extends BaseAdminPage {

    @FindBy(className = "admin-page-main-text")
    private WebElement mainText;
    @FindBy(className = "admin-page-sub-text")
    private WebElement subText;
    @FindBy(className = "admin-page-action-hint")
    private WebElement actionHint;
    @FindBy(css = ".admin-navigation")
    private WebElement navigationElement;

    public AdminPanelPage(WebDriver driver) {
        super(driver);
    }

    public String getMainText() {
        return mainText.getText();
    }

    public String getSubText() {
        return subText.getText();
    }

    public String getActionHint() {
        return actionHint.getText();
    }

    @Step("Get admin navigation")
    public AdminPanelNavigationComponent getNavigation() {
        wait.until(ExpectedConditions.visibilityOf(navigationElement));
        return new AdminPanelNavigationComponent(navigationElement);
    }
}
