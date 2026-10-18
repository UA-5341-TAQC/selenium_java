package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class OurMissionComponent extends BaseComponent {

    @FindBy(css = "h2[class*='OurMission_title'], [class*='OurMission_title']")
    private WebElement title;

    @FindBy(css = "p[class*='OurMission_text']")
    private WebElement description;

    @FindBy(css = "button[class*='Button_content'], a[class*='Button_content'], button, a")
    private WebElement goToProgramsButton;


    public OurMissionComponent(WebElement element) {
        super(element);
    }

    @Step("Get Our mission title text")
    public String getTitleText() {
        wait.until(ExpectedConditions.visibilityOf(title));
        return title.getText().trim();
    }

    @Step("Get Our mission description")
    public String getDescriptionText() {
        wait.until(ExpectedConditions.visibilityOf(description));
        return description.getText().trim();
    }

    @Step("Click Go To Programs button")
    public void clickGoToProgramsButton() {
        wait.until(ExpectedConditions.elementToBeClickable(goToProgramsButtonn)).click();
    }

}