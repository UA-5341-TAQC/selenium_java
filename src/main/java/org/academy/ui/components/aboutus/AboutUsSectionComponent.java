package org.academy.ui.components.aboutus;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AboutUsSectionComponent extends BaseComponent {

    @FindBy(css = "h2[class*='AboutUsSection_title'], [class*='AboutUsSection_title']")
    private WebElement title;

    @FindBy(css = "p[class*='AboutUsSection_description'], [class*='AboutUsSection_description']")
    private WebElement description;

    @FindBy(css = "a[href*='about-us'], a")
    private WebElement learnMoreLink;

    public AboutUsSectionComponent(WebElement element) {
        super(element);
    }

    @Step("Get About Us section title")
    public String getTitleText() {
        wait.until(ExpectedConditions.visibilityOf(title));
        return title.getText().trim();
    }

    @Step("Get About Us section description")
    public String getDescriptionText() {
        wait.until(ExpectedConditions.visibilityOf(description));
        return description.getText().trim();
    }

    @Step("Click Learn More link")
    public void clickLearnMoreLink() {
        wait.until(ExpectedConditions.elementToBeClickable(learnMoreLink)).click();
    }
}
