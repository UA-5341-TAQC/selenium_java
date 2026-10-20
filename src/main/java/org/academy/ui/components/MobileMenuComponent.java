package org.academy.ui.components;

import io.qameta.allure.Step;
import org.academy.ui.Base;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class MobileMenuComponent extends Base {

    @FindBy(css = ".mobile-menu")
    private WebElement mobileMenuContainer;

    @FindBy(css = ".mobile-menu a")
    private List<WebElement> navigationLinks;

    @FindBy(css = ".mobile-menu .language-switcher")
    private WebElement mobileLanguageSwitcherElement;

    @FindBy(css = "button.burger-menu-icon")
    private WebElement burgerMenuButton;

    public MobileMenuComponent(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step("Check if mobile header menu drawer is displayed")
    public boolean isDisplayed() {
        try {
            return mobileMenuContainer.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Wait for mobile header menu drawer to open")
    public MobileMenuComponent waitForOpened() {
        wait.until(ExpectedConditions.visibilityOf(mobileMenuContainer));
        return this;
    }

    @Step("Wait for mobile header menu drawer to close")
    public MobileMenuComponent waitForClosed() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".mobile-menu")));
        return this;
    }

    @Step("Get visible navigation links texts from mobile menu")
    public List<String> getNavigationLinkTexts() {
        waitForOpened();
        return navigationLinks.stream()
                .filter(WebElement::isDisplayed)
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    @Step("Click mobile navigation link with text: {linkText}")
    public void clickNavLink(String linkText) {
        waitForOpened();
        for (WebElement link : navigationLinks) {
            if (link.isDisplayed() && link.getText().trim().equalsIgnoreCase(linkText)) {
                wait.until(ExpectedConditions.elementToBeClickable(link)).click();
                return;
            }
        }
        throw new IllegalArgumentException("Link with text '" + linkText + "' not found in mobile menu");
    }

    @Step("Get mobile language switcher component")
    public LanguageSwitcherComponent getLanguageSwitcher() {
        waitForOpened();
        return new LanguageSwitcherComponent(mobileLanguageSwitcherElement);
    }

    @Step("Close mobile menu via burger button")
    public void closeByBurgerButton() {
        wait.until(ExpectedConditions.elementToBeClickable(burgerMenuButton)).click();
        waitForClosed();
    }
}
