package org.academy.ui.pages;

import io.qameta.allure.Step;
import org.academy.ui.components.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainPage extends BasePublicPage {

    @FindBy(css = "div[class*='IntroSection_intro-block'], [class*='IntroSection_root'], [class*='IntroSection_intro-section']")
    private WebElement introSectionElement;

    @FindBy(css = "section[class*='AboutUsSection_root'], [class*='AboutUsSection_root']")
    private WebElement aboutUsSectionElement;

    @FindBy(css = "section[class*='MainProgramsSection_root'], [class*='MainProgramsSection_root']")
    private WebElement mainProgramsSectionElement;

    @FindBy(css = "section[class*='MainStatisticsSection_root'], [class*='MainStatisticsSection_root']")
    private WebElement mainStatisticsSectionElement;

    @FindBy(css = "div[class*='DonationSection_donation-section'], [class*='DonationSection_donation-section']")
    private WebElement donationSectionElement;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get IntroSection component")
    public IntroSectionComponent getIntroSection() {
        wait.until(ExpectedConditions.visibilityOf(introSectionElement));
        return new IntroSectionComponent(introSectionElement);
    }

    @Step("Get AboutUsSection component")
    public AboutUsSectionComponent getAboutUsSection() {
        wait.until(ExpectedConditions.visibilityOf(aboutUsSectionElement));
        return new AboutUsSectionComponent(aboutUsSectionElement);
    }

    @Step("Get MainProgramsSection component")
    public MainProgramsSectionComponent getProgramsSection() {
        wait.until(ExpectedConditions.visibilityOf(mainProgramsSectionElement));
        return new MainProgramsSectionComponent(mainProgramsSectionElement);
    }

    @Step("Get MainStatisticsSection component")
    public MainStatisticsSectionComponent getStatisticsSection() {
        wait.until(ExpectedConditions.visibilityOf(mainStatisticsSectionElement));
        return new MainStatisticsSectionComponent(mainStatisticsSectionElement);
    }

    @Step("Get DonationSection component")
    public DonationSectionComponent getDonationSection() {
        wait.until(ExpectedConditions.visibilityOf(donationSectionElement));
        return new DonationSectionComponent(donationSectionElement);
    }
}
