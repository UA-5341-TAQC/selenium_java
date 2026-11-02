package org.academy.utils;

import org.academy.ui.pages.Admin.AdminPanelPage;
import org.academy.ui.pages.LoginPage;
import org.openqa.selenium.WebDriver;

/** Shared UI login for Cucumber scenarios and TestNG UI tests. */
public class AdminSession {
    private final WebDriver driver;
    private final TestEnvProvider environment;

    public AdminSession(WebDriver driver, TestEnvProvider environment) {
        this.driver = driver;
        this.environment = environment;
    }

    public AdminPanelPage login() {
        String loginUrl = environment.getAdminLoginUrl();
        String email = environment.getAdminEmail();
        String password = environment.getAdminPassword();

        driver.get(loginUrl);
        LoginPage page = new LoginPage(driver);
        page.enterEmail(email);
        page.enterPassword(password);
        page.clickLoginButton();
        return new AdminPanelPage(driver).waitUntilVisible();
    }

}
