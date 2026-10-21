package org.academy.ui.components.hippotherapy;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HippotherapyIntroComponent extends BaseComponent {

    @FindBy(css = "h1[class*='HippotherapyIntro_title'], [class*='HippotherapyIntro_title']")
    private WebElement title;

    @FindBy(css = "p[class*='HippotherapyIntro_description'], [class*='HippotherapyIntro_description']")
    private WebElement description;

    @FindBy(css = "img[class*='HippotherapyIntro_image'], img")
    private WebElement image;

    public HippotherapyIntroComponent(WebElement element) {
        super(element);
    }

    @Step("Get Hippotherapy Intro title")
    public String getTitleText() {
        return getText(title);
    }

    @Step("Get Hippotherapy Intro description")
    public String getDescriptionText() {
        return getText(description);
    }

    @Step("Check if Hippotherapy Intro image is displayed")
    public boolean isImageDisplayed() {
        return isVisible(image);
    }

    @Step("Get Hippotherapy Intro image src")
    public String getImageSrc() {
        return getDomAttribute(image, "src");
    }
}
