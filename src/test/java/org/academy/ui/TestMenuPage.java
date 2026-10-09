package org.academy.ui;

import org.academy.ui.pages.MenuPage;
import org.academy.ui.testrunners.BaseUiTestRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestMenuPage extends BaseUiTestRunner {

    @Test
    public void testMenu() {
        WebElement cap = driver.findElement(By.xpath("//div[@data-test='Espresso']/.."));
        cap.click();
        WebElement cart = driver.findElement(By.xpath("//a[@aria-label='Cart page']"));
        String text = cart.getText();
        Assert.assertTrue(text.contains("(1)"));

    }
    @Test
    public void testMenuPO() {
        MenuPage menuPage = new MenuPage(driver);
        menuPage.clickCap();
        String text = menuPage.getCardText();
        Assert.assertTrue(text.contains("(1)"));

    }
}
