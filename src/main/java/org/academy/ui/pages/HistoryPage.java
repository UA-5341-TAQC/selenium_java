package org.academy.ui.pages;

import io.qameta.allure.Step;
import org.academy.ui.components.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import java.util.Objects;
import java.util.List;

public class HistoryPage extends BasePublicPage {

    private static final String PAGE_PATH = "history";

    @FindBy(css = "[class*='HistoryHero_hero-section']")
    private WebElement heroSectionElement;

    @FindBy(css = "[class*='HistoryHero_title']")
    private WebElement heroTitleElement;

    @FindBy(css = "[class*='HistoryHero_description']")
    private WebElement heroDescriptionElement;

    @FindBy(css = "[class*='HistoryTimeline_timeline']")
    private WebElement timelineElement;

    @FindBy(css = "[class*='HistoryTimeline_dates-row']")
    private WebElement timelineDatesRowElement;

    @FindBy(css = "[class*='HistoryTimeline_date__']")
    private List<WebElement> timelineDateElements;

    @FindBy(css = "[class*='HistoryTimeline_date--active']")
    private WebElement activeTimelineDateElement;

    @FindBy(css = "[class*='HistoryTimeline_photo-card']")
    private List<WebElement> timelinePhotoCardElements;

    @FindBy(css = "[class*='HistoryTimeline_photo-img']")
    private List<WebElement> timelinePhotoImgElements;

    @FindBy(css = "[class*='HistoryTimeline_photo-caption__']")
    private List<WebElement> timelinePhotoCaptionElements;

    @FindBy(css = "[class*='HistoryPage_sections-list']")
    private WebElement sectionsListElement;

    @FindBy(css = "[class*='HistorySection_section-wrapper']")
    private List<WebElement> sectionWrapperElements;

    @FindBy(css = "[class*='HistorySection_section-title']")
    private List<WebElement> sectionTitleElements;

    @FindBy(css = "[class*='HistorySection_section-description']")
    private List<WebElement> sectionDescriptionElements;

    @FindBy(css = "[class*='HistoryTripleImages_grid']")
    private List<WebElement> tripleImagesGridElements;

    @FindBy(css = "[class*='HistoryDualImages_grid']")
    private List<WebElement> dualImagesGridElements;

    @FindBy(css = "[class*='HistoryQuadImages_grid']")
    private List<WebElement> quadImagesGridElements;

    @FindBy(css = "[class*='CtaSection_root']")
    private WebElement ctaSectionElement;

    @FindBy(css = "[class*='CtaSection_title__']")
    private WebElement ctaTitleElement;

    @FindBy(css = "[class*='CtaSection_description']")
    private WebElement ctaDescriptionElement;

    @FindBy(css = "a.Button_button__wTBFJ.CtaSection_button__M98k6[href='/donate']")
    private WebElement donateButtonElement;

    @FindBy(css = "a.Button_button__wTBFJ.CtaSection_button__M98k6[href='/support-us']")
    private WebElement becomePartnerButtonElement;

    public HistoryPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open History page")
    public HistoryPage open(String baseUrl) {
        driver.get(baseUrl + PAGE_PATH);
        return this;
    }

    @Step("Verify History page is displayed")
    public boolean isPageDisplayed() {
        return isVisible(heroSectionElement);
    }

    // Hero Section Methods

    @Step("Get Hero section title text")
    public String getHeroTitleText() {
        return getText(heroTitleElement);
    }

    @Step("Get Hero section description text")
    public String getHeroDescriptionText() {
        return getText(heroDescriptionElement);
    }

    // Timeline Methods

    @Step("Check if timeline dates row is visible")
    public boolean isTimelineDatesRowVisible() {
        return isVisible(timelineDatesRowElement);
    }

    @Step("Get Timeline component")
    public WebElement getTimeline() {
        return waitUntilVisible(timelineElement);
    }

    @Step("Get all timeline dates count")
    public int getTimelineDatesCount() {
        return timelineDateElements.size();
    }

    @Step("Get all timeline dates texts")
    public List<String> getTimelineDatesTexts() {
        return getTexts(timelineDateElements);
    }

    @Step("Get active timeline date text")
    public String getActiveTimelineDateText() {
        return getText(activeTimelineDateElement);
    }

    @Step("Click timeline date: {date}")
    public void clickTimelineDate(String date) {
        click(findByText(timelineDateElements, date));
    }

    @Step("Get timeline photo cards count")
    public int getTimelinePhotoCardsCount() {
        return timelinePhotoCardElements.size();
    }

    @Step("Get timeline photos count")
    public int getTimelinePhotosCount() {
        return timelinePhotoImgElements.size();
    }

    @Step("Get timeline photo captions count")
    public int getTimelinePhotoCaptionsCount() {
        return timelinePhotoCaptionElements.size();
    }

    @Step("Get timeline photo caption by photo alt text: {altText}")
    public String getTimelinePhotoCaption(String altText) {
        for (int i = 0; i < timelinePhotoImgElements.size(); i++) {
            if (Objects.equals(timelinePhotoImgElements.get(i).getDomAttribute("alt"), altText)) {
                return getText(timelinePhotoCaptionElements.get(i));
            }
        }
        throw new NoSuchElementException("Photo with alt text not found: " + altText);
    }

    @Step("Get timeline photo by alt text: {altText}")
    public WebElement getTimelinePhotoByAlt(String altText) {
        for (WebElement photo : timelinePhotoImgElements) {
            if (Objects.equals(photo.getDomAttribute("alt"), altText)) {
                return waitUntilVisible(photo);
            }
        }
        throw new NoSuchElementException("Photo with alt text not found: " + altText);
    }

    // History Sections Methods

    @Step("Check if sections list is displayed")
    public boolean isSectionsListDisplayed() {
        return isDisplayed(sectionsListElement);
    }

    @Step("Get all history sections count")
    public int getSectionsCount() {
        return sectionWrapperElements.size();
    }

    @Step("Get section description by section title: {title}")
    public String getSectionDescription(String title) {
        int index = sectionTitleElements.indexOf(findSectionByTitle(title));
        return getText(sectionDescriptionElements.get(index));
    }

    @Step("Find section by title: {title}")
    public WebElement findSectionByTitle(String title) {
        return findByText(sectionTitleElements, title);
    }

    // Gallery Methods

    @Step("Get triple images grids count")
    public int getTripleImagesGridsCount() {
        return tripleImagesGridElements.size();
    }

    @Step("Get dual images grids count")
    public int getDualImagesGridsCount() {
        return dualImagesGridElements.size();
    }

    @Step("Get quad images grids count")
    public int getQuadImagesGridsCount() {
        return quadImagesGridElements.size();
    }

    // CTA Section Methods

    @Step("Get CTA section component")
    public WebElement getCtaSection() {
        return waitUntilVisible(ctaSectionElement);
    }

    @Step("Get CTA title text")
    public String getCtaTitleText() {
        return getText(ctaTitleElement);
    }

    @Step("Get CTA description text")
    public String getCtaDescriptionText() {
        return getText(ctaDescriptionElement);
    }

    @Step("Click Donate button")
    public void clickDonateButton() {
        click(donateButtonElement);
    }

    @Step("Click Become Partner button")
    public void clickBecomePartnerButton() {
        click(becomePartnerButtonElement);
    }
}