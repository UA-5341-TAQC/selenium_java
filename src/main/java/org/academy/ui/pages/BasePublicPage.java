package org.academy.ui.pages;

import io.qameta.allure.Step;
import org.academy.ui.components.FooterComponent;
import org.academy.ui.components.HeaderComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BasePublicPage extends BasePage {

    @FindBy(css = ".page-container")
    private WebElement pageContent;

    @Step("Wait for non-empty public page content")
    public String waitForContentText() {
        waitUntilVisible(pageContent);
        return wait.until(currentDriver -> {
            String text = pageContent.getText();
            return text.isBlank() ? null : text;
        });
    }

    public BasePublicPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get Header component")
    public HeaderComponent getHeader() {
        WebElement header = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("header.header-block, .header-container")));
        return new HeaderComponent(header);
    }

    @Step("Get Footer component")
    public FooterComponent getFooter() {
        WebElement footer = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".footer-container")));
        return new FooterComponent(footer);
    }

    @Step("Check if the footer is displayed")
    public boolean isFooterDisplayed() {
        try {
            waitUntilVisible(By.cssSelector(".footer-container"));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Count footers on the page")
    public int getFooterCount() {
        return ((Number) js.executeScript(
                "return document.querySelectorAll('.footer-container').length;")).intValue();
    }

    @Step("Check if the footer is located at the bottom of the page")
    public boolean isFooterAtPageBottom() {
        return Boolean.TRUE.equals(js.executeScript(
                "const f = document.querySelector('.footer-container');"
                        + "const bottom = f.getBoundingClientRect().bottom + window.scrollY;"
                        + "return Math.abs(bottom - document.documentElement.scrollHeight) <= 2;"));
    }

    // the element right before the footer (page content) must end above the footer start
    @Step("Check if the footer does not overlap the page content")
    public boolean isFooterNotOverlappingContent() {
        return Boolean.TRUE.equals(js.executeScript(
                "const f = document.querySelector('.footer-container');"
                        + "const prev = f.previousElementSibling;"
                        + "if (!prev) return true;"
                        + "return f.getBoundingClientRect().top >= prev.getBoundingClientRect().bottom - 1;"));
    }

    // marker of the SPA 404 page (NotFoundPage)
    @Step("Check if the page shows the 404 message")
    public boolean isNotFoundMessageDisplayed() {
        return Boolean.TRUE.equals(js.executeScript(
                "return document.querySelector(\"[class*='NotFoundMessage_title']\") !== null;"));
    }

    @Step("Switch to another browser tab")
    public void switchToOtherTab() {
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        String currentTab = driver.getWindowHandle();

        driver.switchTo().window(
                driver.getWindowHandles().stream()
                        .filter(tab -> !tab.equals(currentTab))
                        .findFirst()
                        .orElseThrow()
        );
    }
}