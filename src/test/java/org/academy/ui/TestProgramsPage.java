package org.academy.ui;

import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.academy.ui.components.HeaderComponent;
import org.academy.ui.pages.ProgramsPage;
import org.academy.ui.testrunners.BaseUiTestRunner;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.net.URI;

public class TestProgramsPage extends BaseUiTestRunner{
    private static final String PROGRAMS_PATH = "/programs";

    private ProgramsPage programsPage;

    @BeforeMethod
    public void openProgramsPage() {
        String contactUsUrl = URI.create(testEnvProvider.getBaseUrl()).resolve(PROGRAMS_PATH).toString();
        driver.get(contactUsUrl);
        programsPage = new ProgramsPage(driver);
    }

    @Test(description = "Open and close the contact form on the Programs page")
    @Issue("773")
    @Story("Contact the company by one click")
    public void testContactFromProgramsPage(){
        HeaderComponent headerComponent = programsPage.getHeader();
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(headerComponent.isContactUsButtonDisplayed(), "The contact button should be displayed.");
        softAssert.assertTrue(headerComponent.isContactUsButtonClickable(), "The contact button should be clickable.");

    }
}
