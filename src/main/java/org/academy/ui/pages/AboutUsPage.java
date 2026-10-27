package org.academy.ui.pages;

import io.qameta.allure.Step;
import org.academy.ui.components.*;
import org.academy.ui.components.AboutUsDonateSectionComponent;
import org.academy.ui.components.AboutUsProgramsSectionComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AboutUsPage extends BasePublicPage {

    private static final String PAGE_PATH = "about-us";

    @FindBy(css = "section[class*='IntroSection_root']")
    private WebElement introSectionElement;

    @FindBy(css = "div[class*='OurMission_root']")
    private WebElement ourMissionSectionElement;

    @FindBy(css = "div[class*='ProgramsSection']")
    private WebElement programSectionElement;

    @FindBy(css = "div[class*='SupportSectionTablet']")
    private WebElement supportSectionElement;

    @FindBy(css = "div[class*='CompanyValues_root']")
    private WebElement companyValuesSectionElement;

    @FindBy(css = "div[class*='OurTeam_root']")
    private WebElement ourTeamSectionElement;

    @FindBy(css = "div[class*='MainValue_root']")
    private WebElement mainValueSectionElement;

    @FindBy(css = "div[class*='DonateSection_root']")
    private WebElement donateSectionElement;

    public AboutUsPage(WebDriver driver) {
        super(driver);
    }

    public AboutUsPage open(String baseUrl) {
        driver.get(baseUrl + PAGE_PATH);
        return this;
    }

    @Step("Get IntroSection component")
    public IntroSectionComponent getIntroSection() {
        return new IntroSectionComponent(waitUntilVisible(introSectionElement));
    }

    @Step("Get OurMission component")
    public OurMissionComponent getOurMissionSection() {
        return new OurMissionComponent(waitUntilVisible(ourMissionSectionElement));
    }

    @Step("Get Programs Section component")
    public AboutUsProgramsSectionComponent getProgramsSectionSection() {
        return new AboutUsProgramsSectionComponent(waitUntilVisible(programSectionElement));
    }

    @Step("Get Support Section component")
    public SupportSectionComponent getSupportSectionSection() {
        return new SupportSectionComponent(waitUntilVisible(supportSectionElement));
    }

    @Step("Get Company Values Section component")
    public CompanyValuesSectionComponent getCompanyValuesSection() {
        return new CompanyValuesSectionComponent(waitUntilVisible(companyValuesSectionElement));
    }

    @Step("Get Our Team Section component")
    public OurTeamSectionComponent getOurTeamSection() {
        return new OurTeamSectionComponent(waitUntilVisible(ourTeamSectionElement));
    }

    @Step("Get Main Value component")
    public MainValueComponent getMainValueComponent() {
        return new MainValueComponent(waitUntilVisible(mainValueSectionElement));
    }

    @Step("Get About Us Donate Section component")
    public AboutUsDonateSectionComponent getAboutUsDonateSection() {
        return new AboutUsDonateSectionComponent(waitUntilVisible(donateSectionElement));
    }

}