package org.academy.ui.testrunners;

import org.academy.ui.pages.Admin.AdminPanelPage;
import org.academy.utils.AdminSession;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class UITestRunnerWithAdmin extends BaseUiTestRunner {
    protected AdminPanelPage adminPanelPage;
    private final ThreadLocal<AdminSession> adminSession = new ThreadLocal<>();

    @BeforeMethod
    public void beforeMethod() {
        driver = initChromeDriver();
        adminSession.set(new AdminSession(driver, testEnvProvider));
        loginAsAdmin();
    }

    protected void loginAsAdmin() {
        adminPanelPage = adminSession.get().login();
    }

    @Override
    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        AdminSession session = adminSession.get();
        try {
            if (session != null) {
                session.close();
            }
        } finally {
            adminSession.remove();
            super.afterMethod();
        }
    }
}
