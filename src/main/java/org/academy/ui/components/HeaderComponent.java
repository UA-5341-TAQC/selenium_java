package org.academy.ui.components;

import io.qameta.allure.Step;
import org.academy.ui.pages.ContactUsPage;
import org.academy.ui.pages.ProgramsPage;
import org.academy.ui.pages.ReportsPage;
import org.academy.ui.pages.StoriesOfVictoryPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HeaderComponent extends BaseComponent {

    @FindBy(css = ".logo-container a")
    private WebElement logoLink;

    @FindBy(xpath = ".//button[contains(@class, 'dropdown-button') and (contains(text(), 'Про нас') or contains(text(), 'About Us'))]")
    private WebElement aboutUsDropdownButton;

    @FindBy(xpath = ".//button[contains(@class, 'dropdown-button') and (contains(text(), 'Іпотерапія') or contains(text(), 'Hippotherapy'))]")
    private WebElement hippotherapyDropdownButton;


    @FindBy(css = "a[href*='/programs']")
    private WebElement programsLink;

    @FindBy(css = "a[href*='/reports']")
    private WebElement reportsLink;

    @FindBy(css = "a[href*='/stories']")
    private WebElement storiesLink;

    @FindBy(css = "a[href*='/support-us']")
    private WebElement supportUsLink;

    @FindBy(css = ".button-container .language-switcher")
    private WebElement languageSwitcherElement;

    @FindBy(css = "a.contact-us-button")
    private WebElement contactUsButton;

    @FindBy(css = "a[href*='/donate']")
    private WebElement donateButton;

    @FindBy(css = "button.burger-menu-icon")
    private WebElement burgerMenuButton;

    public HeaderComponent(WebElement element) {
        super(element);
    }

    @Step("Click Victory Center logo")
    public void clickLogo() {
        click(logoLink);
    }

    @Step("Click 'About Us' dropdown trigger")
    public HeaderComponent clickAboutUsDropdown() {
        click(aboutUsDropdownButton);
        return this;
    }

    @Step("Click 'Hippotherapy' dropdown trigger")
    public HeaderComponent clickHippotherapyDropdown() {
        click(hippotherapyDropdownButton);
        return this;
    }

    @Step("Click 'Programs' link")
    public ProgramsPage clickPrograms() {
        clickHippotherapyDropdown();
        click(programsLink);
        return new ProgramsPage(driver);
    }

    @Step("Click 'Reporting' link")
    public ReportsPage clickReports() {
        click(reportsLink);
        return new ReportsPage(driver);
    }

    @Step("Click 'Stories of Victory' link")
    public StoriesOfVictoryPage clickStories() {
        click(storiesLink);
        return new StoriesOfVictoryPage(driver);
    }

    @Step("Click 'How to Support' link")
    public void clickSupportUs() {
        click(supportUsLink);
    }

    @Step("Get Language Switcher component")
    public LanguageSwitcherComponent getLanguageSwitcher() {
        return new LanguageSwitcherComponent(languageSwitcherElement);
    }

    @Step("Click 'Contact Us' button in header")
    public ContactUsPage clickContactUs() {
        click(contactUsButton);
        return new ContactUsPage(driver);
    }

    @Step("Check if 'Contact Us' button in header is displayed")
    public boolean isContactUsButtonDisplayed() {
        return isDisplayed(contactUsButton);
    }

    @Step("Check if 'Contact Us' button is clickable")
    public boolean isContactUsButtonClickable() {
        return isElementClickable(contactUsButton);
    }

    @Step("Click 'Donate' button in header")
    public void clickDonate() {
        click(donateButton);
    }

    @Step("Open mobile menu via burger button")
    public MobileMenuComponent openMobileMenu() {
        click(burgerMenuButton);
        MobileMenuComponent mobileMenu = new MobileMenuComponent(driver);
        mobileMenu.waitForOpened();
        return mobileMenu;
    }

    @Step("Check if burger menu button is displayed")
    public boolean isBurgerMenuDisplayed() {
        return isDisplayed(burgerMenuButton);
    }
}

