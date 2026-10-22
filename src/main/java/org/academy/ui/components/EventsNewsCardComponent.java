package org.academy.ui.components;

import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Getter
public class EventsNewsCardComponent extends BaseComponent {

    @FindBy(css = "[class*='SingleEventNews_event-image__']")
    private WebElement eventImage;

    @FindBy(css = "[class*='SingleEventNews_event-tag__']")
    private WebElement eventTag;

    @FindBy(css = "[class*='SingleEventNews_event-date__']")
    private WebElement eventDate;

    @FindBy(css = "[class*='SingleEventNews_event-title__']")
    private WebElement eventTitle;

    @FindBy(css = "[class*='SingleEventNews_event-description__']")
    private WebElement eventDescription;

    @FindBy(css = "button[class*='Button_tertiary__']")
    private WebElement articleButton;

    public EventsNewsCardComponent(WebElement element) {
        super(element);
    }

    @Step("Get event news title")
    public String getTitle() {
        return eventTitle.getText().trim();
    }

    @Step("Get event news category")
    public String getCategory() {
        return eventTag.getText().trim();
    }

    @Step("Get event news date")
    public String getDate() {
        return eventDate.getText().trim();
    }

    @Step("Get event news description")
    public String getDescription() {
        return eventDescription.getText().trim();
    }

    @Step("Verify event news image is displayed")
    public boolean isImageDisplayed() {
        return eventImage.isDisplayed();
    }

    @Step("Click article button")
    public void clickArticleButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(articleButton)
        ).click();
    }
}
