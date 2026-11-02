package org.academy.ui.pages.Admin;

import io.qameta.allure.Step;
import org.academy.ui.modals.admin.ConfirmationModal;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class WhoWeArePageAdmin extends BaseAdminPage {

    private static final String PAGE_PATH = "admin-panel/who-we-are";
    private static final String CONTENTEDITABLE_ATTR = "contenteditable";
    private static final String ERROR_SELECTOR = ".input-error, p.error, [data-testid='image-error']";
    private static final String IMAGE_LOADED_SCRIPT =
            "return arguments[0].complete && typeof arguments[0].naturalWidth !== 'undefined' && arguments[0].naturalWidth > 0;";

    // Main Container
    @FindBy(css = ".who-we-are-page-container")
    private WebElement pageContainer;

    @FindBy(css = ".who-we-are-main-box")
    private WebElement mainBox;

    @FindBy(css = ".who-we-are-main-section")
    private WebElement mainSection;

    // Category / Tabs Bar
    @FindBy(css = ".category-bar")
    private WebElement categoryBar;

    @FindBy(css = ".category-bar-button")
    private List<WebElement> categoryButtons;

    @FindBy(css = ".category-bar-button-selected")
    private WebElement selectedCategoryButton;

    // Cards in "Кого підтримуємо" and "Люди"
    @FindBy(css = ".cards-section-wrapper-title")
    private WebElement cardsSectionTitle;

    @FindBy(css = ".card-content")
    private List<WebElement> cardElements;

    // Editable RichText inputs
    @FindBy(css = "[contenteditable='true'], [aria-label='Rich text editor']")
    private List<WebElement> descriptionInputElements;

    // Character counters
    @FindBy(css = "output")
    private List<WebElement> characterCounterElements;

    // Validation error messages
    @FindBy(css = ERROR_SELECTOR)
    private List<WebElement> errorElements;

    // Publish button
    @FindBy(xpath = "//button[contains(@class,'button')][normalize-space()='Опублікувати'] | //button[@type='submit'][normalize-space()='Опублікувати']")
    private WebElement publishButton;

    // Images
    @FindBy(css = ".card-content img, .image-wrapper img")
    private List<WebElement> imageElements;

    public WhoWeArePageAdmin(WebDriver driver) {
        super(driver);
    }

    @Step("Open Admin 'Хто ми' page")
    public WhoWeArePageAdmin open(String baseUrl) {
        String url = baseUrl.endsWith("/") ? baseUrl + PAGE_PATH : baseUrl + "/" + PAGE_PATH;
        driver.get(url);
        waitUntilVisible(categoryBar);
        return this;
    }

    @Step("Verify 'Хто ми' page is displayed")
    public boolean isPageDisplayed() {
        return isVisible(categoryBar);
    }

    // Tabs Navigation

    @Step("Select tab: {tabName}")
    public WhoWeArePageAdmin selectTab(String tabName) {
        WebElement targetTab = categoryButtons.stream()
                .filter(btn -> btn.getText().trim().equalsIgnoreCase(tabName.trim()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Tab not found: " + tabName));

        if (!isTabSelected(tabName)) {
            click(targetTab);
            wait.until(d -> isTabSelected(tabName));
        }
        return this;
    }

    @Step("Check if tab {tabName} is selected")
    public boolean isTabSelected(String tabName) {
        try {
            return getSelectedTabText().equalsIgnoreCase(tabName.trim());
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Get selected tab text")
    public String getSelectedTabText() {
        return getText(selectedCategoryButton);
    }

    @Step("Get all category tab names")
    public List<String> getCategoryNames() {
        return getTexts(categoryButtons);
    }

    // Cards & Content

    @Step("Get cards count")
    public int getCardsCount() {
        return cardElements.size();
    }

    @Step("Get cards section title")
    public String getCardsSectionTitle() {
        return getText(cardsSectionTitle);
    }

    @Step("Check if cards section title is displayed")
    public boolean isCardsSectionTitleDisplayed() {
        return isVisible(cardsSectionTitle);
    }

    // Description Fields

    private WebElement getDescriptionElement(int cardIndex) {
        if (cardIndex < 0 || cardIndex >= descriptionInputElements.size()) {
            throw new IndexOutOfBoundsException("Card index " + cardIndex + " is out of bounds. Available inputs: " + descriptionInputElements.size());
        }
        return descriptionInputElements.get(cardIndex);
    }

    @Step("Get description text for card {cardIndex}")
    public String getDescriptionText(int cardIndex) {
        return getText(getDescriptionElement(cardIndex));
    }

    @Step("Get primary description text")
    public String getPrimaryDescriptionText() {
        return getDescriptionText(0);
    }

    @Step("Check if description field is editable for card {cardIndex}")
    public boolean isDescriptionEditable(int cardIndex) {
        WebElement element = getDescriptionElement(cardIndex);
        return isElementClickable(element) && "true".equalsIgnoreCase(element.getDomAttribute(CONTENTEDITABLE_ATTR));
    }

    @Step("Clear description field for card {cardIndex}")
    public void clearDescription(int cardIndex) {
        WebElement element = getDescriptionElement(cardIndex);
        scrollIntoView(element);
        click(element);
        Keys modifier = System.getProperty("os.name").startsWith("Mac") ? Keys.COMMAND : Keys.CONTROL;
        actions.keyDown(modifier).sendKeys("a").keyUp(modifier).sendKeys(Keys.BACK_SPACE).perform();
    }

    @Step("Clear primary description field")
    public void clearPrimaryDescription() {
        clearDescription(0);
    }

    @Step("Type text into description field for card {cardIndex}")
    public void typeDescription(int cardIndex, String text) {
        WebElement element = getDescriptionElement(cardIndex);
        scrollIntoView(element);
        element.sendKeys(text);
    }

    @Step("Type text into primary description field")
    public void typePrimaryDescription(String text) {
        typeDescription(0, text);
    }

    @Step("Set description text for card {cardIndex}")
    public void setDescription(int cardIndex, String text) {
        clearDescription(cardIndex);
        if (!text.isEmpty()) {
            typeDescription(cardIndex, text);
        }
    }

    @Step("Set primary description text")
    public void setPrimaryDescription(String text) {
        setDescription(0, text);
    }

    @Step("Delete {count} characters from description field of card {cardIndex}")
    public void deleteCharactersFromDescription(int cardIndex, int count) {
        WebElement element = getDescriptionElement(cardIndex);
        scrollIntoView(element);
        click(element);
        element.sendKeys(Keys.END);
        for (int i = 0; i < count; i++) {
            element.sendKeys(Keys.BACK_SPACE);
        }
    }

    @Step("Paste text into description field for card {cardIndex}")
    public void pasteDescription(int cardIndex, String text) {
        WebElement element = getDescriptionElement(cardIndex);
        scrollIntoView(element);
        click(element);
        js.executeScript(
                "var el = arguments[0];" +
                "var text = arguments[1];" +
                "var dataTransfer = new DataTransfer();" +
                "dataTransfer.setData('text/plain', text);" +
                "var pasteEvent = new ClipboardEvent('paste', {" +
                "  bubbles: true, cancelable: true, clipboardData: dataTransfer" +
                "});" +
                "el.dispatchEvent(pasteEvent);" +
                "if (!el.textContent.includes(text)) {" +
                "  document.execCommand('insertText', false, text);" +
                "}",
                element, text
        );
        String expectedPrefix = text.substring(0, Math.min(text.length(), 20));
        wait.until(d -> getDescriptionText(cardIndex).contains(expectedPrefix));
    }

    @Step("Blur description field for card {cardIndex}")
    public void blurDescription(int cardIndex) {
        WebElement element = getDescriptionElement(cardIndex);
        element.sendKeys(Keys.TAB);
        js.executeScript("document.activeElement && document.activeElement.blur();");
    }

    @Step("Blur primary description field")
    public void blurPrimaryDescription() {
        blurDescription(0);
    }

    // Character Counter

    @Step("Get character counter text for card {cardIndex}")
    public String getCharacterCounterText(int cardIndex) {
        if (cardIndex < 0 || cardIndex >= characterCounterElements.size()) {
            throw new IndexOutOfBoundsException("Counter index " + cardIndex + " is out of bounds");
        }
        return getText(characterCounterElements.get(cardIndex));
    }

    @Step("Get primary character counter text")
    public String getPrimaryCharacterCounterText() {
        return getCharacterCounterText(0);
    }

    @Step("Wait for character counter to match expected text")
    public void waitForCharacterCounter(int cardIndex, String expectedCounter) {
        wait.until(d -> getCharacterCounterText(cardIndex).equalsIgnoreCase(expectedCounter.trim()));
    }

    // Validation Errors

    @Step("Get validation error message for card {cardIndex}")
    public String getErrorMessage(int cardIndex) {
        if (!cardElements.isEmpty()) {
            if (cardIndex < 0 || cardIndex >= cardElements.size()) {
                throw new IndexOutOfBoundsException("Invalid card index: " + cardIndex);
            }
            WebElement card = cardElements.get(cardIndex);
            List<WebElement> cardErrors = card.findElements(By.cssSelector(ERROR_SELECTOR))
                    .stream()
                    .filter(WebElement::isDisplayed)
                    .toList();
            return cardErrors.isEmpty() ? "" : getText(cardErrors.get(0));
        }

        List<WebElement> visibleErrors = errorElements.stream()
                .filter(WebElement::isDisplayed)
                .toList();
        if (cardIndex < 0 || cardIndex >= visibleErrors.size()) {
            return "";
        }
        return getText(visibleErrors.get(cardIndex));
    }

    @Step("Get primary validation error message")
    public String getPrimaryErrorMessage() {
        return getErrorMessage(0);
    }

    @Step("Check if any validation error is displayed")
    public boolean hasErrorMessage() {
        return errorElements.stream().anyMatch(WebElement::isDisplayed);
    }

    @Step("Wait for primary error message: {expectedError}")
    public void waitForErrorMessage(String expectedError) {
        wait.until(d -> getPrimaryErrorMessage().contains(expectedError));
    }

    @Step("Wait for error message to disappear")
    public void waitForErrorMessageToDisappear() {
        wait.until(d -> !hasErrorMessage());
    }

    // Images

    @Step("Check if image is displayed and loaded in section/card {imageIndex}")
    public boolean isImageDisplayed(int imageIndex) {
        if (imageIndex < 0 || imageIndex >= imageElements.size()) {
            return false;
        }
        WebElement img = imageElements.get(imageIndex);
        return isVisible(img) && Boolean.TRUE.equals(js.executeScript(IMAGE_LOADED_SCRIPT, img));
    }

    @Step("Check if pre-uploaded default image is displayed and loaded")
    public boolean isPreUploadedImageDisplayed() {
        return isImageDisplayed(0);
    }

    // Publish Action & Modal

    @Step("Check if 'Опублікувати' button is enabled")
    public boolean isPublishButtonEnabled() {
        try {
            return publishButton.isDisplayed() && publishButton.isEnabled();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    @Step("Check if 'Опублікувати' button is displayed")
    public boolean isPublishButtonDisplayed() {
        return isVisible(publishButton);
    }

    @Step("Click 'Опублікувати' button")
    public ConfirmationModal clickPublishButton() {
        click(publishButton);
        return ConfirmationModal.waitFor(wait);
    }

    @Step("Get confirmation modal")
    public ConfirmationModal getConfirmationModal() {
        return ConfirmationModal.waitFor(wait);
    }

    @Step("Check if confirmation modal is closed")
    public boolean isConfirmationModalClosed() {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector(ConfirmationModal.ROOT_SELECTOR)));
    }
}
