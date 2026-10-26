package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
        return getText(title);
    }

    @Step("Get About Us section description")
    public String getDescriptionText() {
        return getText(description);
    }

    @Step("Click Learn More link")
    public void clickLearnMoreLink() {
        click(learnMoreLink);
    }
}

