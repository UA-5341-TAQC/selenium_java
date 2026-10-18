package org.academy.ui.components.admin;

import io.qameta.allure.Step;
import lombok.Getter;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

@Getter
public class AdminEventNewsItemComponent extends BaseComponent {

    @FindBy(css = ".event-item-date")
    private WebElement eventDate;

    @FindBy(css = ".event-info-title p")
    private WebElement eventTitle;

    @FindBy(css = ".event-info-description p")
    private WebElement eventDescription;

    @FindBy(css = ".event-item-info-status .status")
    private WebElement eventStatus;

    @FindBy(css = ".status-published")
    private List<WebElement> publishedStatus;

    @FindBy(css = ".status-draft")
    private List<WebElement> draftStatus;

    @FindBy(css = "[data-testid='localization-statuses'] span")
    private List<WebElement> localizationBadges;

    @FindBy(css = "button[aria-label='Перекласти']")
    private WebElement translateButton;

    @FindBy(css = "button[aria-label='Редагувати матеріал']")
    private WebElement editButton;

    @FindBy(css = "button[aria-label='Видалити матеріал']")
    private WebElement deleteButton;

    public AdminEventNewsItemComponent(WebElement element) {
        super(element);
    }

    @Step("Get event date")
    public String getDate() {
        return eventDate.getText().trim();
    }

    @Step("Get event title")
    public String getTitle() {
        return eventTitle.getText().trim();
    }

    @Step("Get event description")
    public String getDescription() {
        return eventDescription.getText().trim();
    }

    @Step("Get event status")
    public String getStatus() {
        return eventStatus.getText().replace("•", "").trim();
    }

    @Step("Check if event is published")
    public boolean isPublished() {
        return !publishedStatus.isEmpty();
    }

    @Step("Check if event is draft")
    public boolean isDraft() {
        return !draftStatus.isEmpty();
    }

    @Step("Get localization languages")
    public List<String> getLocalizationLanguages() {
        return localizationBadges.stream()
                .map(element -> element.getText().trim())
                .toList();
    }

    @Step("Click Translate")
    public void clickTranslateButton() {
        wait.until(ExpectedConditions.elementToBeClickable(translateButton)).click();
    }

    @Step("Click Edit")
    public void clickEditButton() {
        wait.until(ExpectedConditions.elementToBeClickable(editButton)).click();
    }

    @Step("Click Delete")
    public void clickDeleteButton() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
    }
}
