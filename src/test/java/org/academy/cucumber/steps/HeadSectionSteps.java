package org.academy.cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.academy.cucumber.hooks.CucumberHook;
import org.academy.ui.components.IntroSectionComponent;
import org.academy.ui.components.ProgramsIntroSectionComponent;
import org.academy.ui.pages.MainPage;
import org.academy.ui.pages.ProgramsPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class HeadSectionSteps {

    private final WebDriver driver;

    private MainPage mainPage;
    private IntroSectionComponent introSection;
    private String originalUrl;

    public HeadSectionSteps(CucumberHook cucumberHook) {
        this.driver = cucumberHook.getDriver();
    }

    @Given("user is on the Main page")
    public void userIsOnMainPage() {
        mainPage = new MainPage(driver);
        introSection = mainPage.getIntroSection();
        originalUrl = driver.getCurrentUrl();
    }

    @Then("Head section should be displayed correctly")
    public void headSectionShouldBeDisplayedCorrectly() {
        Assert.assertTrue(introSection.isImageDisplayed(), "Head section image is not displayed");
        Assert.assertFalse(introSection.getTitleText().isBlank(), "Head section title is empty");
        Assert.assertTrue(introSection.isTitleDisplayed(), "Head section title is not displayed");
        Assert.assertFalse(introSection.getDescriptionText().isBlank(), "Head section description is empty");
        Assert.assertTrue(introSection.isDescriptionDisplayed(), "Head section description is not displayed");
        Assert.assertTrue(introSection.isCtaButtonDisplayed(), "View Programs button is not displayed");
        Assert.assertEquals(introSection.getCtaButtonText(), "Переглянути програми", "Incorrect button text");
    }

    @Then("Head section text should not contain HTML tags")
    public void headSectionTextShouldNotContainHtmlTags() {
        Assert.assertFalse(introSection.getTitleText().matches("(?s).*<[^>]+>.*"), "Title contains HTML tags");
        Assert.assertFalse(introSection.getDescriptionText().matches("(?s).*<[^>]+>.*"), "Description contains HTML tags");
    }

    @When("user clicks View Programs button")
    public void userClicksViewProgramsButton() {
        introSection.clickCtaButton();
    }

    @When("user switches to the new tab")
    public void userSwitchesToNewTab() {
        mainPage.switchToOtherTab();
    }

    @Then("Programs page should be opened in a new tab")
    public void programsPageShouldBeOpenedInNewTab() {
        ProgramsIntroSectionComponent programsIntro =
                new ProgramsPage(driver).getIntroSection();

        Assert.assertEquals(driver.getWindowHandles().size(), 2, "New browser tab was not opened");
        Assert.assertTrue(driver.getCurrentUrl().endsWith("/programs"), "Programs page URL is incorrect");
        Assert.assertFalse(programsIntro.getTitleText().isBlank(), "Programs page title is empty");
        Assert.assertFalse(programsIntro.getDescriptionTexts().isEmpty(), "Programs page descriptions are missing");
    }

    @Then("Programs page should be opened")
    public void programsPageShouldBeOpened() {
        Assert.assertTrue(driver.getCurrentUrl().endsWith("/programs"), "Programs page is not opened");
    }

    @When("user switches back to the Main page tab")
    public void userSwitchesBackToMainPageTab() {
        mainPage.switchToOtherTab();
    }

    @Then("Main page should remain open")
    public void mainPageShouldRemainOpen() {
        Assert.assertEquals(driver.getCurrentUrl(), originalUrl, "Main page URL has changed");
        Assert.assertTrue(mainPage.getIntroSection().isTitleDisplayed(), "Main page Head section is not displayed");
    }
}
