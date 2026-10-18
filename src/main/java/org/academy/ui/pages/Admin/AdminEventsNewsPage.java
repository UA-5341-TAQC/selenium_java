package org.academy.ui.pages.Admin;

import io.qameta.allure.Step;
import lombok.Getter;
import org.academy.ui.components.admin.AdminEventNewsItemComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

@Getter
public class AdminEventsNewsPage extends BaseAdminPage {

    // Toolbar

    @FindBy(css = "[data-testid='events-page-content']")
    private WebElement pageContent;

    @FindBy(css = "input.search-bar__input")
    private WebElement searchInput;

    @FindBy(css = ".admin-panel-toolbar-actions .btn-primary")
    private WebElement addEventNewsButton;

    @FindBy(css = "[data-testid='language-toolkit'] .select-head")
    private WebElement languageButton;

    @FindBy(xpath = "//div[contains(@class,'admin-panel-toolbar-actions')]//button[.//span[normalize-space()='Переклади']]")
    private WebElement translationsFilter;

    @FindBy(xpath = "//div[contains(@class,'admin-panel-toolbar-actions')]//button[.//span[normalize-space()='Статус']]")
    private WebElement statusFilter;

    // Page description

    @FindBy(css = "[data-testid='events-page-description-section'] p")
    private WebElement pageDescription;

    @FindBy(css = "button[aria-label='Редагувати секцію: Опис сторінки для користувачів']")
    private WebElement editDescriptionButton;

    @FindBy(css = "button[aria-label='Сховати секцію: Опис сторінки для користувачів']")
    private WebElement hideDescriptionButton;

    @FindBy(css = "[data-testid='events-page-description-section'] [contenteditable='true']")
    private WebElement descriptionEditor;

    @FindBy(css = "[data-testid='events-page-description-section'] .btn-secondary")
    private WebElement cancelDescriptionButton;

    @FindBy(css = "[data-testid='events-page-description-section'] .btn-primary")
    private WebElement publishDescriptionButton;

    // Events block title

    @FindBy(css = "[data-testid='events-block-title-section'] p")
    private WebElement eventsTitle;

    @FindBy(css = "button[aria-label='Редагувати секцію: Заголовок блоку подій']")
    private WebElement editEventsTitleButton;

    @FindBy(css = "button[aria-label='Сховати секцію: Заголовок блоку подій']")
    private WebElement hideEventsTitleButton;

    @FindBy(css = "[data-testid='events-block-title-section'] [contenteditable='true']")
    private WebElement eventsTitleEditor;

    @FindBy(css = "[data-testid='events-block-title-section'] .btn-secondary")
    private WebElement cancelEventsTitleButton;

    @FindBy(css = "[data-testid='events-block-title-section'] .btn-primary")
    private WebElement publishEventsTitleButton;

    // Categories

    @FindBy(css = ".category-bar-button")
    private List<WebElement> categoryButtons;

    @FindBy(css = "[data-testid='context-menu']")
    private WebElement categoryMenu;

    @FindBy(xpath = "//div[@data-testid='context-menu']//button[normalize-space()='Додати категорію']")
    private WebElement addCategoryButton;

    @FindBy(xpath = "//div[@data-testid='context-menu']//button[normalize-space()='Редагувати']")
    private WebElement editCategoryButton;

    @FindBy(xpath = "//div[@data-testid='context-menu']//button[normalize-space()='Видалити']")
    private WebElement deleteCategoryButton;

    @FindBy(xpath = "//div[@data-testid='context-menu']//button[normalize-space()='Додати переклад']")
    private WebElement addCategoryTranslationButton;

    // Events list

    @FindBy(css = "[data-testid='infinite-scroll-list']")
    private WebElement eventsList;

    @FindBy(css = "[data-testid='infinite-scroll-list'] .item-data")
    private List<WebElement> eventItems;

    public AdminEventsNewsPage(WebDriver driver) {
        super(driver);
    }

    // Toolbar methods

    @Step("Check Events News page is displayed")
    public boolean isPageDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(pageContent)
        ).isDisplayed();
    }

    @Step("Search event or news: {text}")
    public void searchEventNews(String text) {
        wait.until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.clear();
        searchInput.sendKeys(text);
    }

    @Step("Click Add Event News")
    public void clickAddEventNewsButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(addEventNewsButton)
        ).click();
    }

    @Step("Select language: {language}")
    public void selectLanguage(String language) {
        languageButton.click();
        selectOption("Українська", language);
    }

    @Step("Select translations filter: {option}")
    public void selectTranslationsFilter(String option) {
        translationsFilter.click();
        selectOption("Неактуальні", option);
    }

    @Step("Select status filter: {status}")
    public void selectStatusFilter(String status) {
        statusFilter.click();
        selectOption("Опубліковано", status);
    }

    private void selectOption(String uniqueOption, String value) {
        By locator = By.xpath(
                "//div[contains(@class,'select-options')]"
                        + "[.//span[normalize-space()='" + uniqueOption + "']]"
                        + "//button[.//span[normalize-space()='" + value + "']]"
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        ).click();
    }

    // Page description methods

    @Step("Get page description")
    public String getPageDescriptionText() {
        return pageDescription.getText().trim();
    }

    @Step("Click Edit Description")
    public void clickEditDescriptionButton() {
        editDescriptionButton.click();
    }

    @Step("Click Hide Description")
    public void clickHideDescriptionButton() {
        hideDescriptionButton.click();
    }

    @Step("Update page description: {text}")
    public void updatePageDescription(String text) {
        clickEditDescriptionButton();

        wait.until(ExpectedConditions.visibilityOf(descriptionEditor)).click();
        descriptionEditor.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        descriptionEditor.sendKeys(text);

        wait.until(
                ExpectedConditions.elementToBeClickable(publishDescriptionButton)
        ).click();
    }

    @Step("Cancel description editing")
    public void cancelDescriptionEditing() {
        cancelDescriptionButton.click();
    }

    // Events block title methods

    @Step("Get events block title")
    public String getEventsTitleText() {
        return eventsTitle.getText().trim();
    }

    @Step("Click Edit Events Title")
    public void clickEditEventsTitleButton() {
        editEventsTitleButton.click();
    }

    @Step("Click Hide Events Title")
    public void clickHideEventsTitleButton() {
        hideEventsTitleButton.click();
    }

    @Step("Update events block title: {text}")
    public void updateEventsTitle(String text) {
        clickEditEventsTitleButton();

        wait.until(ExpectedConditions.visibilityOf(eventsTitleEditor)).click();
        eventsTitleEditor.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        eventsTitleEditor.sendKeys(text);

        wait.until(
                ExpectedConditions.elementToBeClickable(publishEventsTitleButton)
        ).click();
    }

    @Step("Cancel events title editing")
    public void cancelEventsTitleEditing() {
        cancelEventsTitleButton.click();
    }

    // Category methods

    @Step("Select category: {categoryName}")
    public void selectCategory(String categoryName) {
        WebElement category = categoryButtons.stream()
                .filter(element ->
                        element.getText().trim().equals(categoryName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Category not found: " + categoryName));

        wait.until(
                ExpectedConditions.elementToBeClickable(category)
        ).click();
    }

    @Step("Open category menu")
    public void openCategoryMenu() {
        wait.until(
                ExpectedConditions.elementToBeClickable(categoryMenu)
        ).click();
    }

    @Step("Add category")
    public void addCategory() {
        openCategoryMenu();
        wait.until(
                ExpectedConditions.elementToBeClickable(addCategoryButton)
        ).click();
    }

    @Step("Edit category")
    public void editCategory() {
        openCategoryMenu();
        wait.until(
                ExpectedConditions.elementToBeClickable(editCategoryButton)
        ).click();
    }

    @Step("Delete category")
    public void deleteCategory() {
        openCategoryMenu();
        wait.until(
                ExpectedConditions.elementToBeClickable(deleteCategoryButton)
        ).click();
    }

    @Step("Add category translation")
    public void addCategoryTranslation() {
        openCategoryMenu();
        wait.until(
                ExpectedConditions.elementToBeClickable(addCategoryTranslationButton)
        ).click();
    }

    // Event list methods

    @Step("Get loaded events count")
    public int getEventItemsCount() {
        return eventItems.size();
    }

    @Step("Get event item by index: {index}")
    public AdminEventNewsItemComponent getEventItem(int index) {
        return new AdminEventNewsItemComponent(eventItems.get(index));
    }

    @Step("Get event item by title: {title}")
    public AdminEventNewsItemComponent getEventItemByTitle(String title) {
        searchEventNews(title);

        By locator = By.xpath(
                "//div[@data-testid='infinite-scroll-list']"
                        + "//div[contains(@class,'item-data')]"
                        + "[.//div[contains(@class,'event-info-title')]"
                        + "/p[normalize-space()='" + title + "']]"
        );

        WebElement item = wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );

        return new AdminEventNewsItemComponent(item);
    }
}