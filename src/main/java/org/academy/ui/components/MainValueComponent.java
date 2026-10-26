package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainValueComponent extends BaseComponent {


    @FindBy(css = "div[class*='MainValue_title']")
    private WebElement mainValueTitleElement;


    public MainValueComponent(WebElement element) {
        super(element);
    }

    @Step("Get Main Value title text")
    public String getTitleText() {
        return getText(mainValueTitleElement);
    }

}

