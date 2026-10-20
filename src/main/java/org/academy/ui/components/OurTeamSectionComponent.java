package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class OurTeamSectionComponent extends BaseComponent {

    @FindBy(css = "img[class*='OurTeam_image']")
    private WebElement imageElement;

    @FindBy(css = "p[class*='OurTeam_description']")
    private WebElement descriptionElement;

    @FindBy(css = "div[class*='OurTeam_info'] a[class*='Button_button'][href*='team']")
    private WebElement viewTeamButton;

    public OurTeamSectionComponent(WebElement element) {
        super(element);
    }

    @Step("Check if OurTeam section image is displayed")
    public boolean isImageDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(imageElement));
        return imageElement.isDisplayed();
    }

    @Step("Get Our Team Section description")
    public String getDescriptionText() {
        wait.until(ExpectedConditions.visibilityOf(descriptionElement));
        return descriptionElement.getText().trim();
    }

    @Step("Click View Team button")
    public void clickViewTeamButton() {
        wait.until(ExpectedConditions.elementToBeClickable(viewTeamButton)).click();
    }
}