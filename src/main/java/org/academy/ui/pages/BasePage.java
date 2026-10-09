package org.academy.ui.pages;

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
}
