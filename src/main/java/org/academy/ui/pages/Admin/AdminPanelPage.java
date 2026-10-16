package org.academy.ui.pages.Admin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminPanelPage extends BaseAdminPage {

    @FindBy(className = "admin-page-main-text")
    private WebElement mainText;
    @FindBy(className = "admin-page-sub-text")
    private WebElement subText;
    @FindBy(className = "admin-page-action-hint")
    private WebElement actionHint;


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
}
