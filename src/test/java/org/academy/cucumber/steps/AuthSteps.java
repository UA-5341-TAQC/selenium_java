package org.academy.cucumber.steps;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import org.academy.cucumber.hooks.CucumberHook;
import org.academy.utils.AdminSession;

public class AuthSteps {
    private final CucumberHook hook;
    private AdminSession adminSession;

    public AuthSteps(CucumberHook hook) {
        this.hook = hook;
    }

    @Given("the administrator is logged in")
    public void loginAsAdministrator() {
        if (adminSession == null) {
            adminSession = new AdminSession(hook.getDriver(), hook.getTestValueProvider());
        }
        adminSession.login();
    }

    // Stop interception before the shared hook closes the browser.
    @After(order = 10001)
    public void releaseAdminSession() {
        if (adminSession != null) {
            adminSession.close();
        }
    }
}
