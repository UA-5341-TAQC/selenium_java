package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


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
        return isVisible(imageElement);
    }

    @Step("Get Our Team Section description")
    public String getDescriptionText() {
        return getText(descriptionElement);
    }

    @Step("Click View Team button")
    public void clickViewTeamButton() {
        click(viewTeamButton);
    }
}