package org.academy.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DetailedEventNewsPage extends BasePublicPage {

    private static final String PAGE_PATH = "/events-and-news/";
    private static final String ENGLISH_PAGE_PATH = "/en/events-and-news/";

    @FindBy(css = "[class*='DetailedEventNewsPage_detailed-event-news-page']")
    private WebElement pageRoot;

    @FindBy(css = "[class*='DetailedProgramHeader_background-section']")
    private WebElement articleHeader;

    @FindBy(css = "[class*='DetailedProgramHeader_background-section']")
    private List<WebElement> articleHeaderElements;

    @FindBy(css = "[class*='DetailedProgramHeader_program-name']")
    private WebElement articleTitle;

    @FindBy(css = "[class*='DetailedProgramHeader_description']")
    private WebElement articleDescription;

    @FindBy(css = "[class*='DetailedProgramHeader_background-media-wrapper']")
    private WebElement articleHeaderMedia;

    @FindBy(css = "[class*='DetailedProgramHeader_background-media-wrapper'] [class*='BackgroundMedia_media']")
    private WebElement articleHeaderBackgroundMedia;

    @FindBy(css =
            "[class*='DetailedProgramSection_'], [class*='SingleImageBottom_'], [class*='SingleImageTop_'], "
                    + "[class*='SingleImageRight_'], [class*='DualImagesBottom_'], [class*='TripleImagesBottom_'], "
                    + "[class*='QuadImagesBottom_'], [class*='TextOnly_'], [class*='TitleDescriptionCardsSection_']"
    )
    private List<WebElement> articleSections;

    @FindBy(css = "[class*='SingleImageBottom_']")
    private List<WebElement> singleImageBottomSections;

    @FindBy(css = "[class*='SingleImageTop_']")
    private List<WebElement> singleImageTopSections;

    @FindBy(css = "[class*='SingleImageRight_']")
    private List<WebElement> singleImageRightSections;

    @FindBy(css = "[class*='DualImagesBottom_']")
    private List<WebElement> dualImagesBottomSections;

    @FindBy(css = "[class*='TripleImagesBottom_']")
    private List<WebElement> tripleImagesBottomSections;

    @FindBy(css = "[class*='QuadImagesBottom_']")
    private List<WebElement> quadImagesBottomSections;

    @FindBy(css = "[class*='TextOnly_']")
    private List<WebElement> textOnlySections;

    @FindBy(css = "[class*='TitleDescriptionCardsSection_']")
    private List<WebElement> titleDescriptionCardsSections;

    @FindBy(css = "[class*='RecommendedArticles_recommendations-container']")
    private WebElement recommendedArticles;

    @FindBy(css = "[class*='RecommendedArticles_heading']")
    private WebElement recommendedArticlesTitle;

    @FindBy(css = "[class*='RecommendedArticles_cards-grid'] [class*='SingleEventNews_event-card']")
    private List<WebElement> recommendedArticleCards;

    @FindBy(css = "[class*='RecommendedArticles_cards-grid'] [class*='SingleEventNews_event-image']")
    private List<WebElement> recommendedArticleImages;

    @FindBy(css = "[class*='RecommendedArticles_cards-grid'] [class*='SingleEventNews_event-title']")
    private List<WebElement> recommendedArticleTitles;

    @FindBy(css = "[class*='RecommendedArticles_cards-grid'] [class*='SingleEventNews_event-description']")
    private List<WebElement> recommendedArticleDescriptions;

    @FindBy(css = "[class*='RecommendedArticles_cards-grid'] button[class*='Button_tertiary']")
    private List<WebElement> recommendedArticleButtons;

    @FindBy(css = "[class*='NotFound_root']")
    private WebElement notFound;

    @FindBy(css = "[class*='NotFound_root']")
    private List<WebElement> notFoundElements;

    @FindBy(css = "[class*='NotFoundIntro_text']")
    private WebElement notFoundCode;

    @FindBy(css = "[class*='NotFoundMessage_title']")
    private WebElement notFoundTitle;

    @FindBy(css = "[class*='NotFoundMessage_description']")
    private WebElement notFoundDescription;

    @FindBy(css = "[class*='NotFoundMessage_actions'] a[href='/']")
    private WebElement notFoundHomeLink;

    public DetailedEventNewsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
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
                !articleHeaderElements.isEmpty()
                        || !notFoundElements.isEmpty()
        );
        return this;
    }

    @Step("Get detailed event/news page root element")
    public WebElement getPageRootElement() {
        return wait.until(ExpectedConditions.visibilityOf(pageRoot));
    }

    @Step("Get article header element")
    public WebElement getArticleHeaderElement() {
        return wait.until(ExpectedConditions.visibilityOf(articleHeader));
    }

    @Step("Get article title element")
    public WebElement getArticleTitleElement() {
        return wait.until(ExpectedConditions.visibilityOf(articleTitle));
    }

    @Step("Get article description element")
    public WebElement getArticleDescriptionElement() {
        return wait.until(ExpectedConditions.visibilityOf(articleDescription));
    }

    @Step("Get article header media element")
    public WebElement getArticleHeaderMediaElement() {
        return wait.until(ExpectedConditions.visibilityOf(articleHeaderMedia));
    }

    @Step("Get article header background media element")
    public WebElement getArticleHeaderBackgroundMediaElement() {
        return wait.until(ExpectedConditions.visibilityOf(articleHeaderBackgroundMedia));
    }

    @Step("Get detailed article content sections")
    public List<WebElement> getArticleSections() {
        return articleSections;
    }

    @Step("Get SingleImageBottom template sections")
    public List<WebElement> getSingleImageBottomSections() {
        return singleImageBottomSections;
    }

    @Step("Get SingleImageTop template sections")
    public List<WebElement> getSingleImageTopSections() {
        return singleImageTopSections;
    }

    @Step("Get SingleImageRight template sections")
    public List<WebElement> getSingleImageRightSections() {
        return singleImageRightSections;
    }

    @Step("Get DualImagesBottom template sections")
    public List<WebElement> getDualImagesBottomSections() {
        return dualImagesBottomSections;
    }

    @Step("Get TripleImagesBottom template sections")
    public List<WebElement> getTripleImagesBottomSections() {
        return tripleImagesBottomSections;
    }

    @Step("Get QuadImagesBottom template sections")
    public List<WebElement> getQuadImagesBottomSections() {
        return quadImagesBottomSections;
    }

    @Step("Get TextOnly template sections")
    public List<WebElement> getTextOnlySections() {
        return textOnlySections;
    }

    @Step("Get TitleDescriptionCardsSection template sections")
    public List<WebElement> getTitleDescriptionCardsSections() {
        return titleDescriptionCardsSections;
    }

    @Step("Get recommended articles section element")
    public WebElement getRecommendedArticlesElement() {
        return wait.until(ExpectedConditions.visibilityOf(recommendedArticles));
    }

    @Step("Get recommended articles title element")
    public WebElement getRecommendedArticlesTitleElement() {
        return wait.until(ExpectedConditions.visibilityOf(recommendedArticlesTitle));
    }

    @Step("Get recommended article card elements")
    public List<WebElement> getRecommendedArticleCards() {
        return recommendedArticleCards;
    }

    @Step("Get recommended article image elements")
    public List<WebElement> getRecommendedArticleImages() {
        return recommendedArticleImages;
    }

    @Step("Get recommended article title elements")
    public List<WebElement> getRecommendedArticleTitles() {
        return recommendedArticleTitles;
    }

    @Step("Get recommended article description elements")
    public List<WebElement> getRecommendedArticleDescriptions() {
        return recommendedArticleDescriptions;
    }

    @Step("Get recommended article button elements")
    public List<WebElement> getRecommendedArticleButtons() {
        return recommendedArticleButtons;
    }

    @Step("Get Not Found fallback element")
    public WebElement getNotFoundElement() {
        return wait.until(ExpectedConditions.visibilityOf(notFound));
    }

    @Step("Get Not Found status code element")
    public WebElement getNotFoundCodeElement() {
        return wait.until(ExpectedConditions.visibilityOf(notFoundCode));
    }

    @Step("Get Not Found title element")
    public WebElement getNotFoundTitleElement() {
        return wait.until(ExpectedConditions.visibilityOf(notFoundTitle));
    }

    @Step("Get Not Found description element")
    public WebElement getNotFoundDescriptionElement() {
        return wait.until(ExpectedConditions.visibilityOf(notFoundDescription));
    }

    @Step("Get Not Found home link element")
    public WebElement getNotFoundHomeLinkElement() {
        return wait.until(ExpectedConditions.visibilityOf(notFoundHomeLink));
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
