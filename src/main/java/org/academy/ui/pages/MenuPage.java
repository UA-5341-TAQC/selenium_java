package org.academy.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MenuPage extends BasePage {

    @FindBy(xpath = "//div[@data-test='Espresso']/..")
    private WebElement cap;
    @FindBy(xpath = "//a[@aria-label='Cart page']")
    private WebElement cart;


    public MenuPage(WebDriver driver) {
        super(driver);
    }

    public MenuPage clickCap() {

        cap.click();
        return this;
    }

    public String getCardText() {
        return cart.getText();
    }
}
