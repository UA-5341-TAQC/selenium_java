package org.academy.ui.pages.Admin;

import io.qameta.allure.Step;
import org.academy.ui.components.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;
import java.util.Objects;

public class FeedbackPageAdmin extends BaseAdminPage {

    private static final String PAGE_PATH = "admin/feedback";

    // Main Container Locators
    @FindBy(css = "[data-testid='feedback-page-content']")
    private WebElement feedbackPageContentElement;

    @FindBy(css = ".feedback-page-list-container")
    private WebElement feedbackListContainerElement;

    @FindBy(css = "[data-testid='infinite-scroll-list']")
    private WebElement infiniteScrollListElement;

    @FindBy(css = ".infinite-scroll-list-to-top")
    private WebElement scrollToTopButtonElement;


    // Category Bar Locators
    @FindBy(css = ".category-bar")
    private WebElement categoryBarElement;

    @FindBy(css = ".category-bar-button")
    private List<WebElement> categoryButtonElements;

    @FindBy(css = ".category-bar-button-selected")
    private WebElement selectedCategoryButtonElement;

    // Feedback Card Locators
    @FindBy(css = ".draggable-item-wrapper")
    private List<WebElement> draggableItemWrapperElements;

    @FindBy(css = ".draggable-item")
    private List<WebElement> draggableItemElements;

    @FindBy(css = ".dragger")
    private List<WebElement> draggerElements;

    @FindBy(css = ".feedback-item")
    private List<WebElement> feedbackItemElements;

    @FindBy(css = ".feedback-profile")
    private List<WebElement> feedbackProfileElements;

    @FindBy(css = ".feedback-profile-data")
    private List<WebElement> feedbackProfileDataElements;

    @FindBy(css = ".feedback-profile-data > p")
    private List<WebElement> feedbackTitleElements;

    @FindBy(css = "[data-testid='localization-statuses']")
    private List<WebElement> localizationStatusElements;

    @FindBy(css = "[data-testid='localization-statuses'] span")
    private List<WebElement> languageLabelElements;

    @FindBy(css = ".feedback-position")
    private List<WebElement> feedbackPositionElements;

    @FindBy(css = ".feedback-actions")
    private List<WebElement> feedbackActionsElements;

    // Action Buttons Locators
    @FindBy(css = "button[aria-label='Перекласти']")
    private List<WebElement> translateButtonElements;

    @FindBy(css = "button[aria-label='Редагувати']")
    private List<WebElement> editButtonElements;

    @FindBy(css = "button[aria-label='Видалити']")
    private List<WebElement> deleteButtonElements;

    @FindBy(css = "[role='button'][aria-label='Змінити порядок елемента']")
    private List<WebElement> reorderButtonElements;

    @FindBy(css = ".feedback-position a[href]")
    private List<WebElement> feedbackLinkElements;

    // Localization Status Locators
    @FindBy(css = "[class*='LocalizationStatuses_relevant']")
    private List<WebElement> relevantTranslationCardElements;

    @FindBy(css = "[class*='LocalizationStatuses_outdated']")
    private List<WebElement> outdatedTranslationCardElements;

    public FeedbackPageAdmin(WebDriver driver) {
        super(driver);
    }

    @Step("Open Admin Feedback page")
    public FeedbackPageAdmin open(String baseUrl) {
        driver.get(baseUrl + PAGE_PATH);
        return this;
    }

    @Step("Verify Admin Feedback page is displayed")
    public boolean isPageDisplayed() {
        return isVisible(feedbackPageContentElement);
    }

    // Main Container Methods

    @Step("Get feedback page content")
    public WebElement getFeedbackPageContent() {
        return waitUntilVisible(feedbackPageContentElement);
    }

    @Step("Get feedback list container")
    public WebElement getFeedbackListContainer() {
        return waitUntilVisible(feedbackListContainerElement);
    }

    @Step("Get infinite scroll list")
    public WebElement getInfiniteScrollList() {
        return waitUntilVisible(infiniteScrollListElement);
    }

    @Step("Click scroll to top button")
    public void clickScrollToTopButton() {
        click(scrollToTopButtonElement);
    }

    @Step("Check if scroll to top button is displayed")
    public boolean isScrollToTopButtonDisplayed() {
        return isDisplayed(scrollToTopButtonElement);
    }

    // Category Bar Methods

    @Step("Get category bar")
    public WebElement getCategoryBar() {
        return waitUntilVisible(categoryBarElement);
    }

    @Step("Get all category buttons count")
    public int getCategoryButtonsCount() {
        return categoryButtonElements.size();
    }

    @Step("Get all category buttons texts")
    public List<String> getCategoryButtonsTexts() {
        return getTexts(categoryButtonElements);
    }

    @Step("Get selected category button")
    public WebElement getSelectedCategoryButton() {
        return waitUntilVisible(selectedCategoryButtonElement);
    }

    @Step("Get selected category text")
    public String getSelectedCategoryText() {
        return getText(selectedCategoryButtonElement);
    }

    @Step("Click category button by text: {categoryText}")
    public void clickCategoryButton(String categoryText) {
        click(findByText(categoryButtonElements, categoryText));
    }

    // Feedback Card Methods

    @Step("Get all feedback cards count")
    public int getFeedbackCardsCount() {
        return draggableItemElements.size();
    }

    @Step("Get draggable item wrapper by feedback title: {title}")
    public WebElement getDraggableItemWrapper(String title) {
        return waitUntilVisible(findFeedbackCardByTitle(title));
    }

    @Step("Get draggable item by feedback title: {title}")
    public WebElement getDraggableItem(String title) {
        return waitUntilVisible(findInCard(title, draggableItemElements));
    }

    @Step("Get dragger by feedback title: {title}")
    public WebElement getDragger(String title) {
        return waitUntilVisible(findInCard(title, draggerElements));
    }

    @Step("Get feedback item by feedback title: {title}")
    public WebElement getFeedbackItem(String title) {
        return waitUntilVisible(findInCard(title, feedbackItemElements));
    }

    @Step("Get feedback profile by feedback title: {title}")
    public WebElement getFeedbackProfile(String title) {
        return waitUntilVisible(findInCard(title, feedbackProfileElements));
    }

    @Step("Get feedback profile data by feedback title: {title}")
    public WebElement getFeedbackProfileData(String title) {
        return waitUntilVisible(findInCard(title, feedbackProfileDataElements));
    }

    @Step("Get feedback title by feedback title: {title}")
    public String getFeedbackTitle(String title) {
        return getText(findInCard(title, feedbackTitleElements));
    }

    @Step("Get localization status by feedback title: {title}")
    public WebElement getLocalizationStatus(String title) {
        return waitUntilVisible(findInCard(title, localizationStatusElements));
    }

    @Step("Get language label by feedback title: {title}")
    public String getLanguageLabel(String title) {
        return getText(findInCard(title, languageLabelElements));
    }

    @Step("Get feedback position by feedback title: {title}")
    public WebElement getFeedbackPosition(String title) {
        return waitUntilVisible(findInCard(title, feedbackPositionElements));
    }

    @Step("Get feedback actions by feedback title: {title}")
    public WebElement getFeedbackActions(String title) {
        return waitUntilVisible(findInCard(title, feedbackActionsElements));
    }

    // Action Buttons Methods

    @Step("Click translate button by feedback title: {title}")
    public void clickTranslateButton(String title) {
        click(findInCard(title, translateButtonElements));
    }

    @Step("Click edit button by feedback title: {title}")
    public void clickEditButton(String title) {
        click(findInCard(title, editButtonElements));
    }

    @Step("Click delete button by feedback title: {title}")
    public void clickDeleteButton(String title) {
        click(findInCard(title, deleteButtonElements));
    }

    @Step("Click reorder button by feedback title: {title}")
    public void clickReorderButton(String title) {
        click(findInCard(title, reorderButtonElements));
    }

    @Step("Get feedback link by feedback title: {title}")
    public WebElement getFeedbackLink(String title) {
        return waitUntilVisible(findInCard(title, feedbackLinkElements));
    }

    @Step("Get feedback link href by feedback title: {title}")
    public String getFeedbackLinkHref(String title) {
        return getDomAttribute(findInCard(title, feedbackLinkElements), "href");
    }

    // Localization Status Methods

    @Step("Get relevant translation cards count")
    public int getRelevantTranslationCardsCount() {
        return relevantTranslationCardElements.size();
    }

    @Step("Get outdated translation cards count")
    public int getOutdatedTranslationCardsCount() {
        return outdatedTranslationCardElements.size();
    }

    // Private Helpers

    private WebElement findFeedbackCardByTitle(String title) {
        Objects.requireNonNull(title, "Feedback title must not be null");
        WebElement titleElement = findByText(feedbackTitleElements, title);
        try {
            return wait.until(currentDriver -> draggableItemElements.stream()
                    .filter(card -> isInside(card, titleElement))
                    .findFirst()
                    .orElse(null));
        } catch (TimeoutException e) {
            throw new NoSuchElementException("Feedback card not found: " + title);
        }
    }

    private List<WebElement> findAllInCard(String cardTitle, List<WebElement> elements) {
        return filterInside(findFeedbackCardByTitle(cardTitle), elements);
    }

    private WebElement findInCard(String cardTitle, List<WebElement> elements) {
        return findAllInCard(cardTitle, elements).stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Element not found in card: " + cardTitle));
    }
}