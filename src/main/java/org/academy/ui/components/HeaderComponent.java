package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HeaderComponent extends BaseComponent {

    @FindBy(css = ".logo-container a")
    private WebElement logoLink;

    @FindBy(xpath = ".//button[contains(@class, 'dropdown-button') and (contains(text(), 'Про нас') or contains(text(), 'About Us'))]")
    private WebElement aboutUsDropdownButton;

    @FindBy(xpath = ".//button[contains(@class, 'dropdown-button') and (contains(text(), 'Іпотерапія') or contains(text(), 'Hippotherapy'))]")
    private WebElement hippotherapyDropdownButton;

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
        wait.until(ExpectedConditions.elementToBeClickable(logoLink)).click();
    }

    @Step("Click 'About Us' dropdown trigger")
    public HeaderComponent clickAboutUsDropdown() {
        wait.until(ExpectedConditions.elementToBeClickable(aboutUsDropdownButton)).click();
        return this;
    }

    @Step("Click 'Hippotherapy' dropdown trigger")
    public HeaderComponent clickHippotherapyDropdown() {
        wait.until(ExpectedConditions.elementToBeClickable(hippotherapyDropdownButton)).click();
        return this;
    }

    @Step("Click 'Reporting' link")
    public void clickReports() {
        wait.until(ExpectedConditions.elementToBeClickable(reportsLink)).click();
    }

    @Step("Click 'Stories of Victory' link")
    public void clickStories() {
        wait.until(ExpectedConditions.elementToBeClickable(storiesLink)).click();
    }

    @Step("Click 'How to Support' link")
    public void clickSupportUs() {
        wait.until(ExpectedConditions.elementToBeClickable(supportUsLink)).click();
    }

    @Step("Get Language Switcher component")
    public LanguageSwitcherComponent getLanguageSwitcher() {
        return new LanguageSwitcherComponent(languageSwitcherElement);
    }

    @Step("Click 'Contact Us' button in header")
    public void clickContactUs() {
        wait.until(ExpectedConditions.elementToBeClickable(contactUsButton)).click();
    }

    @Step("Click 'Donate' button in header")
    public void clickDonate() {
        wait.until(ExpectedConditions.elementToBeClickable(donateButton)).click();
    }

    @Step("Open mobile menu via burger button")
    public MobileMenuComponent openMobileMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(burgerMenuButton)).click();
        MobileMenuComponent mobileMenu = new MobileMenuComponent(driver);
        mobileMenu.waitForOpened();
        return mobileMenu;
    }

    @Step("Check if burger menu button is displayed")
    public boolean isBurgerMenuDisplayed() {
        try {
            return burgerMenuButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
