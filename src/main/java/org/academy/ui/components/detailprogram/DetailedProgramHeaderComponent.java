package org.academy.ui.components.detailprogram;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;


public class DetailedProgramHeaderComponent extends BaseComponent {

    @FindBy(css = "h1[class*='DetailedProgramHeader_program-name']")
    private WebElement nameElement;

    @FindBy(css = "div[class*='DetailedProgramHeader_program-info']")
    private List <WebElement> programInfoElements;

    @FindBy(css = "div[class*='DetailedProgramHeader_program-meetings']")
    private WebElement programMeetingsElement;

    @FindBy(css = "p[class*='DetailedProgramHeader_description']")
    private WebElement descriptionElement;

    @FindBy(css = "div[class*='DetailedProgramHeader'] div[class*='BackgroundMedia_root']")
    private WebElement imageElement;


    public DetailedProgramHeaderComponent(WebElement element) {
        super(element);
    }


    @Step("Get Detail Program Header description")
    public String getDescriptionText() {
        wait.until(ExpectedConditions.visibilityOf(descriptionElement));
        return descriptionElement.getText().trim();
    }

    @Step("Get Detail Program Header name")
    public String getNameText() {
        wait.until(ExpectedConditions.visibilityOf(nameElement));
        return nameElement.getText().trim();
    }

    @Step("Get Detail Program Header information")
    public List<String> getProgramInfoElementsText() {
        wait.until(ExpectedConditions.visibilityOfAllElements(programInfoElements));
        return programInfoElements.stream()
                .map(element -> element.getText().trim())
                .toList();
    }

    @Step("Get Detail Program Header meeting information")
    public String getProgramMeetingElementsText() {
        wait.until(ExpectedConditions.visibilityOf(programMeetingsElement));
        return programMeetingsElement.getText().trim();
    }

    @Step("Check if Program Header background image is displayed")
    public boolean isBackgroundImageDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(imageElement));
        return imageElement.isDisplayed();
    }
}
