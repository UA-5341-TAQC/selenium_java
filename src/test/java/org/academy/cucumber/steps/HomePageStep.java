package org.academy.cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.academy.cucumber.hooks.CucumberHook;
import org.academy.ui.pages.MainPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.net.URI;
import java.time.Duration;


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
    public void verifyPageOpens(String path) {
        WebDriverWait wait = new WebDriverWait(cucumberHook.getDriver(), Duration.ofSeconds(20));
        String expectedUrl = URI.create(cucumberHook.getTestValueProvider().getBaseUrl())
                .resolve(path).toString();
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        String actualUrl = cucumberHook.getDriver().getCurrentUrl();

        Assert.assertEquals(
                actualUrl,
                expectedUrl,
                "Expected URL: " + expectedUrl + ", but got: " + actualUrl);
    }

}
