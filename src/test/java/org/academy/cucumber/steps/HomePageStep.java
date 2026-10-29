package org.academy.cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.academy.cucumber.hooks.CucumberHook;
import org.academy.ui.pages.MainPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;


public class HomePageStep {

    private final CucumberHook cucumberHook;

    public HomePageStep(CucumberHook cucumberHook) {
        this.cucumberHook = cucumberHook;
    }


    @Given("the Victory Center home page is open")
    public void openHomePage() {
        WebDriver driver = cucumberHook.getDriver();
        driver.get(cucumberHook.getTestValueProvider().getBaseUrl());
    }

    @When("the visitor scrolls to the footer")
    public void scrollToFileter() {
        new MainPage(cucumberHook.getDriver())
                .getFooter()
                .scrollIntoView();
    }

    @When("^the visitor clicks the footer navigation link for \"([^\"]*)\"$")
    public void clickFooterNavigationLink(String linkText) {
        new MainPage(cucumberHook.getDriver())
                .getFooter()
                .clickNavigationLink(linkText);
    }

    @Then("^the page at \"([^\"]*)\" opens on the Victory Center website$")
    public void verifyPageOpens(String expectedUrl) {
        String actualUrl = cucumberHook.getDriver().getCurrentUrl();
        Assert.assertEquals(
                actualUrl,
                cucumberHook.getTestValueProvider().getBaseUrl() + expectedUrl,
                "Expected URL: " + expectedUrl + ", but got: " + actualUrl);
    }

}
