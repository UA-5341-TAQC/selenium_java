package org.academy.cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.academy.cucumber.hooks.CucumberHook;
import org.academy.ui.components.HeaderComponent;
import org.academy.ui.pages.BasePublicPage;
import org.academy.ui.pages.MainPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LanguageSwitchStep {
    private final HeaderComponent header ;

    public LanguageSwitchStep(CucumberHook cucumberHook) {
        WebDriver driver = cucumberHook.getDriver();
        BasePublicPage basePublicPage = new MainPage(driver);
        this.header = basePublicPage.getHeader();
    }

    @Given("The language drop-down is set to {string}")
    public void setLanguage(String targetLanguage){
        header.getLanguageSwitcher().selectLanguage(targetLanguage);
    }

    @When("The user changes the interface language to ENG")
    public void userChangesLanguageToENG() {
        header.getLanguageSwitcher().switchToEn();
    }

    @When("The user changes the interface language to ENG")
    public void userChangesLanguageToUA() {
        header.getLanguageSwitcher().switchToUa();
    }

    @Then("The current interface language should be{string}")
    public void verifyCurrentLanguage(String expectedLanguage) {
        String actualLanguage = header.getLanguageSwitcher().getCurrentLanguage();

        Assert.assertEquals(
                actualLanguage,
                expectedLanguage,
                "The selected language does not match the expected one."
        );
    }

}
