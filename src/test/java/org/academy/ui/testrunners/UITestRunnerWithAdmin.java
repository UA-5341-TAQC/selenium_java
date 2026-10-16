package org.academy.ui.testrunners;

import org.academy.ui.pages.Admin.AdminPanelPage;
import org.academy.ui.pages.LoginPage;
import org.testng.annotations.BeforeMethod;

public class UITestRunnerWithAdmin extends BaseUiTestRunner {
    protected AdminPanelPage adminPanelPage;

    @BeforeMethod
    public void beforeMethod() {
        driver = initChromeDriver();
        driver.get(testEnvProvider.getAdminLoginUrl());
        loginAsAdmin();
    }

    protected void loginAsAdmin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(testEnvProvider.getAdminEmail());
        loginPage.enterPassword(testEnvProvider.getAdminPassword());
        loginPage.clickLoginButton();
        adminPanelPage = new AdminPanelPage(driver);
    }
}
