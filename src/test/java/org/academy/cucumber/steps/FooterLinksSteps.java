package org.academy.cucumber.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.academy.cucumber.hooks.CucumberHook;
import org.academy.ui.pages.BasePublicPage;
import org.academy.utils.LinkChecker;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class FooterLinksSteps {

    private final CucumberHook cucumberHook;

    public FooterLinksSteps(CucumberHook cucumberHook) {
        this.cucumberHook = cucumberHook;
    }

    private BasePublicPage getPage() {
        return new BasePublicPage(cucumberHook.getDriver());
    }

    @When("the visitor scrolls the page to the bottom")
    public void scrollToBottom() {
        getPage().scrollToBottom();
    }

    @When("the visitor scrolls the page to the bottom and back to the top {int} times")
    public void scrollDownAndUp(int times) {
        BasePublicPage page = getPage();
        for (int i = 0; i < times; i++) {
            page.scrollToBottom();
            page.scrollToTop();
        }
    }

    @When("the visitor reloads the page")
    public void reloadPage() {
        cucumberHook.getDriver().navigate().refresh();
    }

    @Then("none of the links in the footer is broken")
    public void verifyNoBrokenLinks() {
        WebDriver driver = cucumberHook.getDriver();
        BasePublicPage page = getPage();
        List<String> links = page.getFooter().getAllLinks();
        Assert.assertFalse(links.isEmpty(), "No links were found in the footer");

        String baseHost = URI.create(cucumberHook.getTestValueProvider().getBaseUrl()).getHost();
        List<String> problems = new ArrayList<>();
        for (String link : links) {
            String problem = baseHost.equals(URI.create(link).getHost())
                    ? LinkChecker.checkInternalLink(driver, link)
                    : LinkChecker.checkExternalLink(link);
            if (problem != null) {
                problems.add(problem);
            }
        }
        Assert.assertTrue(problems.isEmpty(), "Broken footer links: " + problems);
    }

    @Then("the footer is displayed once, at the bottom of the page and below the page content")
    public void verifyFooterLayout() {
        BasePublicPage page = getPage();
        SoftAssert softAssert = new SoftAssert();

        int footers = page.getFooterCount();
        softAssert.assertEquals(footers, 1, "Footer should not be duplicated");
        if (footers > 0) {
            softAssert.assertTrue(page.getFooter().isDisplayed(), "Footer should be displayed");
            softAssert.assertTrue(page.isFooterAtPageBottom(), "Footer should be at the bottom of the page");
            softAssert.assertTrue(page.isFooterNotOverlappingContent(),
                    "Footer should not overlap the page content");
        }
        softAssert.assertAll();
    }
}