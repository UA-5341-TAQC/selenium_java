package org.academy.ui.testrunners;

import org.academy.ui.pages.Admin.AdminPanelPage;
import org.academy.utils.AdminSession;
import org.testng.annotations.BeforeMethod;

public class UITestRunnerWithAdmin extends BaseUiTestRunner {
    protected AdminPanelPage adminPanelPage;

    @BeforeMethod
    public void beforeMethod() {
        driver = initChromeDriver();
        loginAsAdmin();
    }

    protected void loginAsAdmin() {
        adminPanelPage = new AdminSession(driver, testEnvProvider).login();
    }
}
