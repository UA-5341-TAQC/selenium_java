package org.academy.ui.pages;

import io.qameta.allure.Step;
import org.academy.ui.components.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AboutUsPage extends BasePage {

    private static final String PAGE_PATH = "about-us";

    @FindBy(css = "section[class*='IntroSection_root']")
    private WebElement introSectionElement;

    @FindBy(css = "div[class*='OurMission_root']")
    private WebElement ourMissionSectionElement;

    @FindBy(css = "div[class*='ProgramsSection'],")
    private WebElement programSectionElement;

    @FindBy(css = "div[class*='SupportSectionTablet'],")
    private WebElement supportSectionElement;

    @FindBy(css = "div[class*='CompanyValues_root']")
    private WebElement companyValuesSectionElement;

    @FindBy(css = "div[class*='OurTeam_root']")
    private WebElement ourTeamSectionElement;

    @FindBy(css = "div[class*='MainValue_root'],")
    private WebElement mainValueSectionElement;

    @FindBy(css = "div[class*='DonateSection_root'],")
    private WebElement donateSectionElement;

    public AboutUsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public AboutUsPage open(String baseUrl) {
        driver.get(baseUrl + PAGE_PATH);
        return this;
    }

    @Step("Get IntroSection component")
    public IntroSectionComponent getIntroSection() {
        wait.until(ExpectedConditions.visibilityOf(introSectionElement));
        return new IntroSectionComponent(introSectionElement);
    }

    @Step("Get OurMission component")
    public OurMissionComponent getOurMissionSection() {
        wait.until(ExpectedConditions.visibilityOf(ourMissionSectionElement));
        return new OurMissionComponent(ourMissionSectionElement);
    }

    @Step("Get Programs Section component")
    public AboutUsProgramsSectionComponent getProgramsSectionSection() {
        wait.until(ExpectedConditions.visibilityOf(programSectionElement));
        return new AboutUsProgramsSectionComponent(programSectionElement);
    }

    @Step("Get Support Section component")
    public SupportSectionComponent getSupportSectionSection() {
        wait.until(ExpectedConditions.visibilityOf(supportSectionElement));
        return new SupportSectionComponent(supportSectionElement);
    }

    @Step("Get Company Values Section component")
    public CompanyValuesSectionComponent getCompanyValuesSection() {
        wait.until(ExpectedConditions.visibilityOf(companyValuesSectionElement));
        return new CompanyValuesSectionComponent(companyValuesSectionElement);
    }

    @Step("Get Our Team Section component")
    public OurTeamSectionComponent getOurTeamSection() {
        wait.until(ExpectedConditions.visibilityOf(ourTeamSectionElement));
        return new OurTeamSectionComponent(ourTeamSectionElement);
    }

    @Step("Get Main Value component")
    public MainValueComponent getMainValueComponent() {
        wait.until(ExpectedConditions.visibilityOf(mainValueSectionElement));
        return new MainValueComponent(mainValueSectionElement);
    }

    @Step("Get About Us Donate Section component")
    public AboutUsDonateSectionComponent getAboutUsDonateSection() {
        wait.until(ExpectedConditions.visibilityOf(donateSectionElement));
        return new AboutUsDonateSectionComponent(donateSectionElement);
    }

}