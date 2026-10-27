package org.academy.ui.pages;

import io.qameta.allure.Step;
import org.academy.ui.components.FooterComponent;
import org.academy.ui.components.HeaderComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BasePublicPage extends BasePage {

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
