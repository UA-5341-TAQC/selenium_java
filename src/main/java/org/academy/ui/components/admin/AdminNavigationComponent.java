package org.academy.ui.components.admin;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Sidebar navigation of the admin panel (logo, 13 section links and the exit button).
 * The root element is {@link #ROOT_LOCATOR} ('.admin-navigation').
 */
public class AdminNavigationComponent extends BaseComponent {

    public static final By ROOT_LOCATOR = By.cssSelector(".admin-navigation");

    @FindBy(css = ".admin-logo")
    private WebElement logoElement;

    @FindBy(css = "a.admin-page-link")
    private List<WebElement> linkElements;

    @FindBy(css = "a.admin-pages-selected")
    private WebElement activeLinkElement;

    @FindBy(css = "button.exit-button")
    private WebElement exitButton;

    @FindBy(css = ".exit-button-text")
    private WebElement exitButtonTextElement;

    public AdminNavigationComponent(WebElement element) {
        super(element);
    }

    @Step("Check if admin logo is displayed")
    public boolean isLogoDisplayed() {
        return isVisible(logoElement);
    }

    @Step("Read number of admin navigation links")
    public int getLinksCount() {
        return waitUntilAllVisible(linkElements).size();
    }

    @Step("Read admin navigation link texts")
    public List<String> getLinkTexts() {
        return getTexts(waitUntilAllVisible(linkElements));
    }

    @Step("Read admin navigation link hrefs")
    public List<String> getLinkHrefs() {
        return waitUntilAllVisible(linkElements).stream()
                .map(link -> getDomAttribute(link, "href"))
                .toList();
    }

    @Step("Read active admin navigation link text")
    public String getActiveLinkText() {
        return getText(activeLinkElement);
    }

    @Step("Read active admin navigation link href")
    public String getActiveLinkHref() {
        return getDomAttribute(waitUntilVisible(activeLinkElement), "href");
    }

    @Step("Click admin navigation link: {linkText}")
    public void clickLinkByText(String linkText) {
        click(findLink(linkText));
    }

    @Step("Click admin navigation link with href: {href}")
    public void clickLinkByHref(String href) {
        for (WebElement link : waitUntilAllVisible(linkElements)) {
            if (href.equals(getDomAttribute(link, "href"))) {
                click(link);
                return;
            }
        }
        throw new NoSuchElementException("Admin navigation link not found by href: " + href);
    }

    @Step("Check if admin navigation link is active: {linkText}")
    public boolean isLinkActive(String linkText) {
        String classes = getDomAttribute(findLink(linkText), "class");
        return classes != null && classes.contains("admin-pages-selected");
    }

    @Step("Read exit button text")
    public String getExitButtonText() {
        return getText(exitButtonTextElement);
    }

    @Step("Click exit button")
    public void clickExit() {
        click(exitButton);
    }

    private WebElement findLink(String linkText) {
        for (WebElement link : waitUntilAllVisible(linkElements)) {
            if (linkText.equals(getText(link))) {
                return link;
            }
        }
        throw new NoSuchElementException("Admin navigation link not found: " + linkText);
    }
}