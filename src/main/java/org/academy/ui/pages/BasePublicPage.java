package org.academy.ui.pages;

import io.qameta.allure.Step;
import org.academy.ui.components.FooterComponent;
import org.academy.ui.components.HeaderComponent;
import org.openqa.selenium.By;
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
}
