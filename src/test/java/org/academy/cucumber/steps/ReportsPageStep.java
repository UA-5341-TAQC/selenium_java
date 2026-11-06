package org.academy.cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.academy.cucumber.hooks.CucumberHook;
import org.academy.ui.pages.ReportsPage;
import org.testng.Assert;

import java.net.URI;


public class ReportsPageStep {
    private final CucumberHook cucumberHook;
    private static final String REPORTS_PATH = "/reports";

    public ReportsPageStep(CucumberHook cucumberHook) {
        this.cucumberHook = cucumberHook;
    }

    private ReportsPage getReportsPage() {
        return new ReportsPage(cucumberHook.getDriver());
    }

    @Given("Reports page is open")
    public void openReportsPage() {
        cucumberHook.getDriver().get(URI.create(cucumberHook.getTestValueProvider().getBaseUrl())
                .resolve(REPORTS_PATH).toString());
        getReportsPage().waitForContentText();
    }

    @When("The reports page has at least one PDF file published")
    public void countFilesCheck() {
        int countFiles = getReportsPage().getReportFilesCount();

        Assert.assertTrue(countFiles > 0,
                "Expected at least 1 PDF file, actual number of files: " + countFiles);
    }

    @Then("The PDF file-s should be displayed")
    public void arePDFFilesDisplayed(){
        Assert.assertFalse(getReportsPage().getReportFileLabels().isEmpty(),
                "Can't get any visible report file label.");
    }

    @Then("The PDF file-s should contain a download icon for each PDF file")
    public void areDownloadIconsDisplayed(){
        int countFiles = getReportsPage().getReportFilesCount();
        int downloadButtonCount = getReportsPage().getReportDownloadButtonCount();

        Assert.assertFalse(downloadButtonCount<=0,
                "There is no download button. Actual count: " + downloadButtonCount);
        Assert.assertEquals(downloadButtonCount, countFiles,
                "There is difference in count between download buttons and reports files.");
        Assert.assertTrue(getReportsPage().areReportDownloadButtonDisplayed(),
                "Download buttons are not displayed.");
    }


}
