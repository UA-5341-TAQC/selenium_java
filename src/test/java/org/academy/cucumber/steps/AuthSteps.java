package org.academy.cucumber.steps;

import io.cucumber.java.en.Given;
import org.academy.cucumber.hooks.CucumberHook;
import org.academy.utils.AdminSession;

public class AuthSteps {
    private final CucumberHook hook;

    public AuthSteps(CucumberHook hook) {
        this.hook = hook;
    }

    @Given("the administrator is logged in")
    public void loginAsAdministrator() {
        new AdminSession(hook.getDriver(), hook.getTestValueProvider()).login();
    }
}
