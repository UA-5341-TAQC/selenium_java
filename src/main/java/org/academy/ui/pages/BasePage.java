package org.academy.ui.pages;

import io.qameta.allure.Step;
import org.academy.ui.Base;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage extends Base {

    public BasePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);

    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Step("Scroll page to the bottom")
    public void scrollToBottom() {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    @Step("Scroll page to the top")
    public void scrollToTop() {
        js.executeScript("window.scrollTo(0, 0);");
    }
}