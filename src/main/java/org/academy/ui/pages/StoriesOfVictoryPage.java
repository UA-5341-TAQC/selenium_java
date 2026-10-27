package org.academy.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class StoriesOfVictoryPage extends BasePublicPage {

    private static final String PAGE_PATH = "/stories";
    private static final String ENGLISH_PAGE_PATH = "/en/stories";

    @FindBy(css = "[class*='SloganSection_slogan']")
    private WebElement slogan;

    @FindBy(css = "section[class*='ReviewArticlesSection_container']")
    private WebElement reviewArticlesSection;

    @FindBy(css = "[class*='ReviewArticlesSection_article__']")
    private List<WebElement> reviewArticles;

    @FindBy(css = "[class*='ReviewArticlesSection_articleImage']")
    private List<WebElement> reviewArticleImages;

    @FindBy(css = "[class*='ReviewArticlesSection_titleContent']")
    private List<WebElement> reviewArticleTitles;

    @FindBy(css = "section[class*='VideoReviewsSection_container']")
    private WebElement videoReviewsSection;

    @FindBy(css = "[class*='VideoReviewsSection_title']")
    private WebElement videoReviewsTitle;

    @FindBy(css = "[class*='VideoReviewsSection_video__']")
    private List<WebElement> videoCards;

    @FindBy(css = "[class*='VideoReviewsSection_link']")
    private List<WebElement> videoLinks;

    @FindBy(css = "[class*='VideoReviewsSection_thumbnail'] img")
    private List<WebElement> videoThumbnails;

    @FindBy(css = "[class*='VideoReviewsSection_videoTitle']")
    private List<WebElement> videoTitles;

    @FindBy(css = "section[class*='ReviewsSection_root']")
    private WebElement reviewsSection;

    @FindBy(css = "[class*='ReviewsSection_titleText']")
    private WebElement reviewsTitle;

    @FindBy(css = "[class*='ReviewsSection_reviewCard']")
    private List<WebElement> reviewCards;

    @FindBy(css = "[class*='ReviewsSection_review__']")
    private List<WebElement> reviewTexts;

    @FindBy(css = "[class*='ReviewsSection_name']")
    private List<WebElement> reviewerNames;

    @FindBy(css = "[class*='ReviewsSection_left']")
    private WebElement previousReviewButton;

    @FindBy(css = "[class*='ReviewsSection_right']")
    private WebElement nextReviewButton;

    @FindBy(css = "[class*='LoadableContent_loadable-content-loader']")
    private List<WebElement> loadableContentLoaders;

    public StoriesOfVictoryPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step("Open the Ukrainian Stories of Victory page")
    public StoriesOfVictoryPage open(String baseUrl) {
        driver.get(normalizeBaseUrl(baseUrl) + PAGE_PATH);
        return waitUntilLoaded();
    }

    @Step("Open the English Stories of Victory page")
    public StoriesOfVictoryPage openEnglish(String baseUrl) {
        driver.get(normalizeBaseUrl(baseUrl) + ENGLISH_PAGE_PATH);
        return waitUntilLoaded();
    }

    @Step("Wait for the Stories of Victory page to load")
    public StoriesOfVictoryPage waitUntilLoaded() {
        wait.until(ExpectedConditions.visibilityOf(slogan));
        wait.until(ExpectedConditions.visibilityOf(reviewArticlesSection));
        wait.until(ExpectedConditions.visibilityOf(videoReviewsSection));
        wait.until(ExpectedConditions.visibilityOf(reviewsSection));
        return this;
    }

    @Step("Get Stories of Victory slogan element")
    public WebElement getSloganElement() {
        return wait.until(ExpectedConditions.visibilityOf(slogan));
    }

    @Step("Get Stories of Victory slogan text")
    public String getSloganText() {
        return getSloganElement().getText().replaceAll("\\s+", " ").trim();
    }

    @Step("Get review articles section element")
    public WebElement getReviewArticlesSectionElement() {
        return wait.until(ExpectedConditions.visibilityOf(reviewArticlesSection));
    }

    @Step("Get review article elements")
    public List<WebElement> getReviewArticles() {
        return reviewArticles;
    }

    @Step("Get review article image elements")
    public List<WebElement> getReviewArticleImages() {
        return reviewArticleImages;
    }

    @Step("Get review article title elements")
    public List<WebElement> getReviewArticleTitles() {
        return reviewArticleTitles;
    }

    @Step("Get video reviews section element")
    public WebElement getVideoReviewsSectionElement() {
        return wait.until(ExpectedConditions.visibilityOf(videoReviewsSection));
    }

    @Step("Get video reviews section title element")
    public WebElement getVideoReviewsTitleElement() {
        return wait.until(ExpectedConditions.visibilityOf(videoReviewsTitle));
    }

    @Step("Get video review card elements")
    public List<WebElement> getVideoCards() {
        return videoCards;
    }

    @Step("Get video review link elements")
    public List<WebElement> getVideoLinks() {
        return videoLinks;
    }

    @Step("Get video review thumbnail elements")
    public List<WebElement> getVideoThumbnails() {
        return videoThumbnails;
    }

    @Step("Get video review title elements")
    public List<WebElement> getVideoTitles() {
        return videoTitles;
    }

    @Step("Get reviews section element")
    public WebElement getReviewsSectionElement() {
        return wait.until(ExpectedConditions.visibilityOf(reviewsSection));
    }

    @Step("Get reviews section title element")
    public WebElement getReviewsTitleElement() {
        return wait.until(ExpectedConditions.visibilityOf(reviewsTitle));
    }

    @Step("Get review card elements")
    public List<WebElement> getReviewCards() {
        return reviewCards;
    }

    @Step("Get review text elements")
    public List<WebElement> getReviewTexts() {
        return reviewTexts;
    }

    @Step("Get reviewer name elements")
    public List<WebElement> getReviewerNames() {
        return reviewerNames;
    }

    @Step("Get previous review button")
    public WebElement getPreviousReviewButton() {
        return wait.until(ExpectedConditions.visibilityOf(previousReviewButton));
    }

    @Step("Get next review button")
    public WebElement getNextReviewButton() {
        return wait.until(ExpectedConditions.visibilityOf(nextReviewButton));
    }

    @Step("Get transient LoadableContent loader elements")
    public List<WebElement> getLoadableContentLoaders() {
        return loadableContentLoaders;
    }

    private String normalizeBaseUrl(String baseUrl) {
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalArgumentException("Base URL must not be null or blank");
        }
        return baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
    }
}
