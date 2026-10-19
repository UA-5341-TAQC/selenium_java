package org.academy.ui.pages;

import io.qameta.allure.Step;
import lombok.Getter;
import org.academy.ui.components.EventsNewsCardComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

@Getter
public class EventsNewsPage extends BasePage {

    // EventsNewsIntro

    @FindBy(css = "[class*='EventsNewsIntro_slogan__']")
    private WebElement pageTitle;

    @FindBy(css = "[class*='EventsNewsIntro_description__']")
    private WebElement pageDescription;

    // Events

    @FindBy(css = "[class*='Events_header-title__']")
    private WebElement eventsTitle;

    @FindBy(xpath = "//div[contains(@class,'Events_button-block__')]//button[normalize-space()='Новини']")
    private WebElement newsButton;

    @FindBy(xpath = "//div[contains(@class,'Events_button-block__')]//button[normalize-space()='Медіа']")
    private WebElement mediaButton;

    @FindBy(xpath = "//div[contains(@class,'Events_button-block__')]//button[normalize-space()='Програми']")
    private WebElement programsButton;

    @FindBy(css = "[data-testid='all-materials-button']")
    private WebElement allMaterialsButton;

    @FindBy(css = "[class*='SingleEventNews_event-card__']")
    private List<WebElement> eventCards;

    @FindBy(css = "[class*='Events_show-more-button__']")
    private WebElement showMoreButton;

    // ChooseProgram

    @FindBy(css = "[class*='CtaSection_title__']")
    private WebElement chooseProgramTitle;

    @FindBy(css = "div[class*='CtaSection_actions__'] a[href='/programs']")
    private WebElement chooseProgramButton;

    @FindBy(css = "div[class*='CtaSection_actions__'] a[href='/donate']")
    private WebElement supportButton;

    public EventsNewsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Verify Events and News page is displayed")
    public boolean isPageDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(pageTitle)
        ).isDisplayed();
    }

    @Step("Get page title")
    public String getPageTitleText() {
        return pageTitle.getText()
                .replaceAll("\\s+", " ")
                .trim();
    }

    @Step("Get page description")
    public String getPageDescriptionText() {
        return pageDescription.getText().trim();
    }

    // Events Methods

    @Step("Get events section title")
    public String getEventsTitleText() {
        return eventsTitle.getText().trim();
    }

    @Step("Click News button")
    public void clickNewsButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(newsButton)
        ).click();
    }

    @Step("Click Media button")
    public void clickMediaButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(mediaButton)
        ).click();
    }

    @Step("Click Programs button")
    public void clickProgramsButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(programsButton)
        ).click();
    }

    @Step("Click All Materials button")
    public void clickAllMaterialsButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(allMaterialsButton)
        ).click();
    }

    // Event Cards Methods

    @Step("Get event cards count")
    public int getEventCardsCount() {
        return eventCards.size();
    }

    @Step("Get event card by index: {index}")
    public EventsNewsCardComponent getEventCard(int index) {
        return new EventsNewsCardComponent(eventCards.get(index));
    }

    @Step("Click Show More button")
    public void clickShowMoreButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(showMoreButton)
        ).click();
    }

    // ChooseProgram Methods

    @Step("Get Choose Program title")
    public String getChooseProgramTitleText() {
        return chooseProgramTitle.getText().trim();
    }

    @Step("Click Choose Program button")
    public void clickChooseProgramButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(chooseProgramButton)
        ).click();
    }

    @Step("Click Support button")
    public void clickSupportButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(supportButton)
        ).click();
    }
}
