package org.academy.ui.components.admin;

import io.qameta.allure.Step;
import lombok.Getter;
import org.academy.ui.components.BaseComponent;
import org.academy.ui.pages.Admin.AdminFaqPage;
import org.academy.ui.pages.Admin.AdminHistoryPage;
import org.academy.ui.pages.Admin.EventsPageAdmin;
import org.academy.ui.pages.Admin.TeamPageAdmin;
import org.academy.ui.pages.Admin.WhoWeArePageAdmin;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class AdminPanelNavigationComponent extends BaseComponent {

    // Navigation Links

    @FindBy(css = "a[href='/admin-panel/main']")
    private WebElement mainPageLink;

    @FindBy(css = "a[href='/admin-panel/company-profile']")
    private WebElement companyProfileLink;

    @FindBy(css = "a[href='/admin-panel/reports']")
    private WebElement reportsLink;

    @FindBy(css = "a[href='/admin-panel/team']")
    private WebElement teamLink;

    @FindBy(css = "a[href='/admin-panel/programs']")
    private WebElement programsLink;

    @FindBy(css = "a[href='/admin-panel/donate']")
    private WebElement donateLink;

    @FindBy(css = "a[href='/admin-panel/history']")
    private WebElement historyLink;

    @FindBy(css = "a[href='/admin-panel/faq']")
    private WebElement faqLink;

    @FindBy(css = "a[href='/admin-panel/who-we-are']")
    private WebElement whoWeAreLink;

    @FindBy(css = "a[href='/admin-panel/events']")
    private WebElement eventsLink;

    @FindBy(css = "a[href='/admin-panel/partners']")
    private WebElement partnersLink;

    @FindBy(css = "a[href='/admin-panel/hippotherapy']")
    private WebElement hippotherapyLink;

    @FindBy(css = "a[href='/admin-panel/feedback']")
    private WebElement feedbackLink;

    // Logout Button

    @FindBy(css = "button.exit-button")
    private WebElement logoutButton;

    public AdminPanelNavigationComponent (WebElement rootElement) {
        super(rootElement);
    }

    @Step("Click Main Page link")
    public void clickMainPageLink() {
        click(mainPageLink);
    }

    @Step("Click Company Profile link")
    public void clickCompanyProfileLink() {
        click(companyProfileLink);
    }

    @Step("Click Reports link")
    public void clickReportsLink() {
        click(reportsLink);
    }

    @Step("Click Team link")
    public TeamPageAdmin clickTeamLink() {
        click(teamLink);
        return new TeamPageAdmin (driver);
    }

    @Step("Click Programs link")
    public void clickProgramsLink() {
        click(programsLink);
    }

    @Step("Click Donate link")
    public void clickDonateLink() {
        click(donateLink);
    }

    @Step("Click History link")
    public AdminHistoryPage clickHistoryLink() {
        click(historyLink);
        return new AdminHistoryPage(driver);
    }

    @Step("Click FAQ link")
    public AdminFaqPage clickFaqLink() {
        click(faqLink);
        return new AdminFaqPage(driver);
    }

    @Step("Click Who We Are link")
    public WhoWeArePageAdmin clickWhoWeAreLink() {
        click(whoWeAreLink);
        return new WhoWeArePageAdmin(driver);
    }

    @Step("Click Events link")
    public EventsPageAdmin clickEventsLink() {
        click(eventsLink);
        return new EventsPageAdmin(driver);
    }

    @Step("Click Partners link")
    public void clickPartnersLink() {
        click(partnersLink);
    }

    @Step("Click Hippotherapy link")
    public void clickHippotherapyLink() {
        click(hippotherapyLink);
    }

    @Step("Click Feedback link")
    public void clickFeedbackLink() {
        click(feedbackLink);
    }

    @Step("Click Logout button")
    public void clickLogoutButton() {
        click(logoutButton);
    }
}
