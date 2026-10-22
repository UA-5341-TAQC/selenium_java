package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProgramCardComponent extends BaseComponent {

    @FindBy(css = "[class*='ProgramCardProgramsPage_name'], [class*='ProgramCardAboutUsPage_name']")
    private WebElement nameElement;

    @FindBy(css = "[class*='ProgramCardProgramsPage_subtitle-content'], [class*='ProgramCardAboutUsPage_subtitle-content'], [class*='ProgramCardProgramsPage_subtitle'], [class*='ProgramCardAboutUsPage_subtitle']")
    private WebElement subtitleElement;

    @FindBy(css = "[class*='ProgramCardProgramsPage_description'], [class*='ProgramCardAboutUsPage_description']")
    private WebElement descriptionElement;

    @FindBy(css = "img[class*='ProgramCardProgramsPage_image'], img[class*='ProgramCardAboutUsPage_image']")
    private WebElement imageElement;

    @FindBy(css = "[class*='ProgramCardProgramsPage_categories'], [class*='ProgramCardAboutUsPage_categories']")
    private WebElement categoriesElement;

    @FindBy(css = "[class*='ProgramCardProgramsPage_link'], [class*='ProgramCardAboutUsPage_link']")
    private WebElement learnMoreLink;

    public ProgramCardComponent(WebElement element) {
        super(element);
    }

    @Step("Get ProgramCard title/name")
    public String getNameText() {
        wait.until(ExpectedConditions.visibilityOf(nameElement));
        return nameElement.getText().trim();
    }

    @Step("Get ProgramCard subtitle")
    public String getSubtitleText() {
        wait.until(ExpectedConditions.visibilityOf(subtitleElement));
        return subtitleElement.getText().trim();
    }

    @Step("Get ProgramCard description")
    public String getDescriptionText() {
        wait.until(ExpectedConditions.visibilityOf(descriptionElement));
        return descriptionElement.getText().trim();
    }

    @Step("Check if ProgramCard image is displayed")
    public boolean isImageDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(imageElement));
        return imageElement.isDisplayed();
    }

    @Step("Get ProgramCard categories text")
    public String getCategoriesText() {
        wait.until(ExpectedConditions.visibilityOf(categoriesElement));
        return categoriesElement.getText().trim();
    }

    @Step("Get ProgramCard learn more link href")
    public String getLinkHref() {
        wait.until(ExpectedConditions.visibilityOf(learnMoreLink));
        return learnMoreLink.getAttribute("href");
    }

    @Step("Click ProgramCard learn more link")
    public void clickLearnMore() {
        wait.until(ExpectedConditions.elementToBeClickable(learnMoreLink)).click();
    }
}
