package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
        return getText(nameElement);
    }

    @Step("Get ProgramCard subtitle")
    public String getSubtitleText() {
        return getText(subtitleElement);
    }

    @Step("Get ProgramCard description")
    public String getDescriptionText() {
        hover(rootElement);
        return getText(descriptionElement);
    }

    @Step("Check if ProgramCard image is displayed")
    public boolean isImageDisplayed() {
        return isVisible(imageElement);
    }

    @Step("Get ProgramCard categories text")
    public String getCategoriesText() {
        return getText(categoriesElement);
    }

    @Step("Get ProgramCard learn more link href")
    public String getLinkHref() {
        return getDomAttribute(learnMoreLink, "href");
    }

    @Step("Click ProgramCard learn more link")
    public void clickLearnMore() {
        click(learnMoreLink);
    }
}

