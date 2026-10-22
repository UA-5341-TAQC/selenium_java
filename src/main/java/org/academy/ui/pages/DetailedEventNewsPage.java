package org.academy.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DetailedEventNewsPage extends BasePublicPage {

    private static final String PAGE_PATH = "/events-and-news/";
    private static final String ENGLISH_PAGE_PATH = "/en/events-and-news/";

    private static final By PAGE_ROOT = By.cssSelector("[class*='DetailedEventNewsPage_detailed-event-news-page']");

    private static final By ARTICLE_HEADER = By.cssSelector("[class*='DetailedProgramHeader_background-section']");
    private static final By ARTICLE_TITLE = By.cssSelector("[class*='DetailedProgramHeader_program-name']");
    private static final By ARTICLE_DESCRIPTION = By.cssSelector("[class*='DetailedProgramHeader_description']");
    private static final By ARTICLE_HEADER_MEDIA = By.cssSelector(
            "[class*='DetailedProgramHeader_background-media-wrapper']"
    );
    private static final By ARTICLE_HEADER_BACKGROUND_MEDIA = By.cssSelector(
            "[class*='DetailedProgramHeader_background-media-wrapper'] [class*='BackgroundMedia_media']"
    );

    private static final By ARTICLE_SECTIONS = By.cssSelector(
            "[class*='DetailedProgramSection_'], [class*='SingleImageBottom_'], [class*='SingleImageTop_'], "
                    + "[class*='SingleImageRight_'], [class*='DualImagesBottom_'], [class*='TripleImagesBottom_'], "
                    + "[class*='QuadImagesBottom_'], [class*='TextOnly_'], [class*='TitleDescriptionCardsSection_']"
    );
    private static final By SINGLE_IMAGE_BOTTOM_SECTIONS = By.cssSelector("[class*='SingleImageBottom_']");
    private static final By SINGLE_IMAGE_TOP_SECTIONS = By.cssSelector("[class*='SingleImageTop_']");
    private static final By SINGLE_IMAGE_RIGHT_SECTIONS = By.cssSelector("[class*='SingleImageRight_']");
    private static final By DUAL_IMAGES_BOTTOM_SECTIONS = By.cssSelector("[class*='DualImagesBottom_']");
    private static final By TRIPLE_IMAGES_BOTTOM_SECTIONS = By.cssSelector("[class*='TripleImagesBottom_']");
    private static final By QUAD_IMAGES_BOTTOM_SECTIONS = By.cssSelector("[class*='QuadImagesBottom_']");
    private static final By TEXT_ONLY_SECTIONS = By.cssSelector("[class*='TextOnly_']");
    private static final By TITLE_DESCRIPTION_CARDS_SECTIONS =
            By.cssSelector("[class*='TitleDescriptionCardsSection_']");

    private static final By RECOMMENDED_ARTICLES = By.cssSelector(
            "[class*='RecommendedArticles_recommendations-container']"
    );
    private static final By RECOMMENDED_ARTICLES_TITLE = By.cssSelector("[class*='RecommendedArticles_heading']");
    private static final By RECOMMENDED_ARTICLE_CARDS = By.cssSelector(
            "[class*='RecommendedArticles_cards-grid'] [class*='SingleEventNews_event-card']"
    );
    private static final By RECOMMENDED_ARTICLE_IMAGES = By.cssSelector(
            "[class*='RecommendedArticles_cards-grid'] [class*='SingleEventNews_event-image']"
    );
    private static final By RECOMMENDED_ARTICLE_TITLES = By.cssSelector(
            "[class*='RecommendedArticles_cards-grid'] [class*='SingleEventNews_event-title']"
    );
    private static final By RECOMMENDED_ARTICLE_DESCRIPTIONS = By.cssSelector(
            "[class*='RecommendedArticles_cards-grid'] [class*='SingleEventNews_event-description']"
    );
    private static final By RECOMMENDED_ARTICLE_BUTTONS = By.cssSelector(
            "[class*='RecommendedArticles_cards-grid'] button[class*='Button_tertiary']"
    );

    private static final By NOT_FOUND = By.cssSelector("[class*='NotFound_root']");
    private static final By NOT_FOUND_CODE = By.cssSelector("[class*='NotFoundIntro_text']");
    private static final By NOT_FOUND_TITLE = By.cssSelector("[class*='NotFoundMessage_title']");
    private static final By NOT_FOUND_DESCRIPTION = By.cssSelector("[class*='NotFoundMessage_description']");
    private static final By NOT_FOUND_HOME_LINK = By.cssSelector("[class*='NotFoundMessage_actions'] a[href='/']");

    public DetailedEventNewsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open the Ukrainian detailed event/news page: {slug}")
    public DetailedEventNewsPage open(String baseUrl, String slug) {
        driver.get(normalizeBaseUrl(baseUrl) + PAGE_PATH + encodeSlug(slug));
        return waitUntilLoaded();
    }

    @Step("Open the English detailed event/news page: {slug}")
    public DetailedEventNewsPage openEnglish(String baseUrl, String slug) {
        driver.get(normalizeBaseUrl(baseUrl) + ENGLISH_PAGE_PATH + encodeSlug(slug));
        return waitUntilLoaded();
    }

    @Step("Wait for detailed event/news page or Not Found fallback")
    public DetailedEventNewsPage waitUntilLoaded() {
        wait.until(driver ->
                !driver.findElements(ARTICLE_HEADER).isEmpty()
                        || !driver.findElements(NOT_FOUND).isEmpty()
        );
        return this;
    }

    @Step("Get detailed event/news page root element")
    public WebElement getPageRootElement() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(PAGE_ROOT));
    }

    @Step("Get article header element")
    public WebElement getArticleHeaderElement() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(ARTICLE_HEADER));
    }

    @Step("Get article title element")
    public WebElement getArticleTitleElement() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(ARTICLE_TITLE));
    }

    @Step("Get article description element")
    public WebElement getArticleDescriptionElement() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(ARTICLE_DESCRIPTION));
    }

    @Step("Get article header media element")
    public WebElement getArticleHeaderMediaElement() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(ARTICLE_HEADER_MEDIA));
    }

    @Step("Get article header background media element")
    public WebElement getArticleHeaderBackgroundMediaElement() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(ARTICLE_HEADER_BACKGROUND_MEDIA));
    }

    @Step("Get detailed article content sections")
    public List<WebElement> getArticleSections() {
        return driver.findElements(ARTICLE_SECTIONS);
    }

    @Step("Get SingleImageBottom template sections")
    public List<WebElement> getSingleImageBottomSections() {
        return driver.findElements(SINGLE_IMAGE_BOTTOM_SECTIONS);
    }

    @Step("Get SingleImageTop template sections")
    public List<WebElement> getSingleImageTopSections() {
        return driver.findElements(SINGLE_IMAGE_TOP_SECTIONS);
    }

    @Step("Get SingleImageRight template sections")
    public List<WebElement> getSingleImageRightSections() {
        return driver.findElements(SINGLE_IMAGE_RIGHT_SECTIONS);
    }

    @Step("Get DualImagesBottom template sections")
    public List<WebElement> getDualImagesBottomSections() {
        return driver.findElements(DUAL_IMAGES_BOTTOM_SECTIONS);
    }

    @Step("Get TripleImagesBottom template sections")
    public List<WebElement> getTripleImagesBottomSections() {
        return driver.findElements(TRIPLE_IMAGES_BOTTOM_SECTIONS);
    }

    @Step("Get QuadImagesBottom template sections")
    public List<WebElement> getQuadImagesBottomSections() {
        return driver.findElements(QUAD_IMAGES_BOTTOM_SECTIONS);
    }

    @Step("Get TextOnly template sections")
    public List<WebElement> getTextOnlySections() {
        return driver.findElements(TEXT_ONLY_SECTIONS);
    }

    @Step("Get TitleDescriptionCardsSection template sections")
    public List<WebElement> getTitleDescriptionCardsSections() {
        return driver.findElements(TITLE_DESCRIPTION_CARDS_SECTIONS);
    }

    @Step("Get recommended articles section element")
    public WebElement getRecommendedArticlesElement() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(RECOMMENDED_ARTICLES));
    }

    @Step("Get recommended articles title element")
    public WebElement getRecommendedArticlesTitleElement() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(RECOMMENDED_ARTICLES_TITLE));
    }

    @Step("Get recommended article card elements")
    public List<WebElement> getRecommendedArticleCards() {
        return driver.findElements(RECOMMENDED_ARTICLE_CARDS);
    }

    @Step("Get recommended article image elements")
    public List<WebElement> getRecommendedArticleImages() {
        return driver.findElements(RECOMMENDED_ARTICLE_IMAGES);
    }

    @Step("Get recommended article title elements")
    public List<WebElement> getRecommendedArticleTitles() {
        return driver.findElements(RECOMMENDED_ARTICLE_TITLES);
    }

    @Step("Get recommended article description elements")
    public List<WebElement> getRecommendedArticleDescriptions() {
        return driver.findElements(RECOMMENDED_ARTICLE_DESCRIPTIONS);
    }

    @Step("Get recommended article button elements")
    public List<WebElement> getRecommendedArticleButtons() {
        return driver.findElements(RECOMMENDED_ARTICLE_BUTTONS);
    }

    @Step("Get Not Found fallback element")
    public WebElement getNotFoundElement() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(NOT_FOUND));
    }

    @Step("Get Not Found status code element")
    public WebElement getNotFoundCodeElement() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(NOT_FOUND_CODE));
    }

    @Step("Get Not Found title element")
    public WebElement getNotFoundTitleElement() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(NOT_FOUND_TITLE));
    }

    @Step("Get Not Found description element")
    public WebElement getNotFoundDescriptionElement() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(NOT_FOUND_DESCRIPTION));
    }

    @Step("Get Not Found home link element")
    public WebElement getNotFoundHomeLinkElement() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(NOT_FOUND_HOME_LINK));
    }

    private String normalizeBaseUrl(String baseUrl) {
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalArgumentException("Base URL must not be null or blank");
        }
        return baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
    }

    private String encodeSlug(String slug) {
        if (slug == null || slug.isBlank() || slug.contains("/") || slug.contains("\\") || slug.contains("?")) {
            throw new IllegalArgumentException("Slug must be a non-blank path segment");
        }
        return URLEncoder.encode(slug, StandardCharsets.UTF_8).replace("+", "%20");
    }
}
