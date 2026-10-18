package org.academy.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AboutUsPage extends BasePage {

    @FindBy(css = "section[class*='IntroSection_root']")
    private WebElement introSectionElement;

    @FindBy(css = "div[class*='OurMission_root']")
    private WebElement ourMissionSectionElement;

    @FindBy(css = "div[class*='ProgramsSection'],")
    private WebElement programSectionElement;

    @FindBy(css = "div[class*='SupportSectionTablet'],")
    private WebElement supportSectionElement;

    @FindBy(css = "div[class*='ProgramCardAboutUsPage_root'],")
    private WebElement supportCardElement;

    @FindBy(css = "div[class*='CompanyValues_root']")
    private WebElement companyValuesSectionElement;

    @FindBy(css = "div[class*='ValueCard_item']")
    private WebElement valueCardSectionElement;

    @FindBy(css = "div[class*='OurTeam_root']")
    private WebElement ourTeamSectionElement;

    @FindBy(css = "div[class*='MainValue_root'],")
    private WebElement mainValueSectionElement;

    @FindBy(css = "div[class*='DonateSection_root'],")
    private WebElement donateSectionElement;

    public AboutUsPagePage(WebDriver driver) {
        super(driver);
    }

    @Step("Get IntroSection component")
    public IntroSectionComponent getIntroSection() {
        wait.until(ExpectedConditions.visibilityOf(introSectionElement));
        return new IntroSectionComponent(introSectionElement);
    }

    @Step("Get OurMission component")
    public OurMissionComponent getIntroSection() {
        wait.until(ExpectedConditions.visibilityOf(ourMissionSectionElement));
        return new IntroSectionComponent(ourMissionSectionElement);
    }


}