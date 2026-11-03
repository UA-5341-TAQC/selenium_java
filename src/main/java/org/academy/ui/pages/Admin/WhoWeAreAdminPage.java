package org.academy.ui.pages.Admin;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.NoSuchElementException;

public class WhoWeAreAdminPage extends BaseAdminPage {

    private static final String PAGE_PATH = "/admin-panel/who-we-are";

    // Main Section Locators
    @FindBy(css = ".who-we-are-main-section")
    private WebElement mainSectionElement;

    // Description Section Locators
    @FindBy(css = ".description-section")
    private WebElement descriptionSectionElement;

    @FindBy(css = "[class*='OurMission_title']")
    private WebElement missionTitleElement;

    @FindBy(css = "[class*='OurMission_text']")
    private WebElement missionTextElement;

    @FindBy(css = ".OurMission_actions, a[href='/programs']")
    private WebElement programsLinkElement;

    @FindBy(css = "button[aria-label='Translate']")
    private WebElement TranslateButton;

    // Image Section Locators
    @FindBy(css = ".image-section")
    private WebElement imageSectionElement;

    @FindBy(css = ".content-wrapper")
    private WebElement contentWrapperElement;

    @FindBy(css = ".content-wrapper-description")
    private WebElement contentDescriptionElement;

    // Cards Section Locators
    @FindBy(css = ".cards-section-wrapper")
    private WebElement cardsSectionWrapperElement;

    @FindBy(css = ".cards-section-wrapper-title")
    private WebElement cardsSectionTitleElement;

    @FindBy(css = ".cards-section-wrapper-cards")
    private WebElement cardsContainerElement;

    @FindBy(css = ".card-content")
    private List<WebElement> cardContentElements;

    @FindBy(css = ".card-content-description-wrapper")
    private List<WebElement> cardDescriptionWrapperElements;

    // Rich Text Editor Locators
    @FindBy(css = "[class*='RichTextInput_root']")
    private List<WebElement> richTextEditorElements;

    @FindBy(css = "button[aria-label='Bold']")
    private List<WebElement> boldButtonElements;

    @FindBy(css = "button[aria-label='Italic']")
    private List<WebElement> italicButtonElements;

    @FindBy(css = "button[aria-label='Line break']")
    private List<WebElement> lineBreakButtonElements;

    @FindBy(css = "[contenteditable='true'][role='textbox']")
    private List<WebElement> textBoxElements;

    @FindBy(css = "[class*='RichTextInput_footer']")
    private List<WebElement> richTextFooterElements;

    @FindBy(css = ".input-error")
    private List<WebElement> inputErrorElements;

    @FindBy(css = "button[aria-label='Clear input']")
    private List<WebElement> clearInputButtonElements;

    @FindBy(css = "[class*='RichTextInput_counter']")
    private List<WebElement> characterCounterElements;

    @FindBy(css = "label.input-label")
    private List<WebElement> descriptionLabelElements;

    // Image Input Locators
    @FindBy(css = "[class*='WhoWeAreImageInput_container']")
    private List<WebElement> imageInputContainerElements;

    @FindBy(css = "[class*='WhoWeAreImageInput_image-placeholder']")
    private List<WebElement> imagePlaceholderElements;

    @FindBy(css = "[class*='WhoWeAreImageInput_placeholder-text-primary']")
    private List<WebElement> imagePlaceholderTextPrimaryElements;

    @FindBy(css = "[class*='WhoWeAreImageInput_placeholder-text-secondary']")
    private List<WebElement> imagePlaceholderTextSecondaryElements;

    @FindBy(css = "[data-testid='preview-image']")
    private List<WebElement> previewImageElements;

    @FindBy(css = "button[data-testid='remove-photo-button']")
    private List<WebElement> removePhotoButtonElements;

    // Publish Button Locators
    @FindBy(css = "button[type='submit']")
    private WebElement publishButtonElement;

    @FindBy(css = ".category-bar-button")
    private List<WebElement> categoryButtons;

    public WhoWeAreAdminPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open Who We Are Admin page")
    public WhoWeAreAdminPage open(String baseUrl) {
        driver.get(baseUrl + PAGE_PATH);
        return this;
    }

    @Step("Wait until all People card description editors are displayed in People category")
    public void waitForPeopleCards(int expectedCardsCount) {
        wait.until(driver -> {
            if (textBoxElements.size() != expectedCardsCount) {
                return false;
            }

            return textBoxElements.stream().allMatch(element -> {
                try {
                    return element.isDisplayed()
                            && !element.getText().trim().isEmpty();
                } catch (StaleElementReferenceException e) {
                    return false;
                }
            });
        });
    }

    @Step("Verify Who We Are Admin page is displayed")
    public boolean isPageDisplayed() {
        return wait.until(driver -> mainSectionElement.isDisplayed());
    }

    // Main Section Methods

    @Step("Get main section")
    public WebElement getMainSection() {
        return waitUntilVisible(mainSectionElement);
    }

    // Description Section Methods

    @Step("Get description section")
    public WebElement getDescriptionSection() {
        return waitUntilVisible(descriptionSectionElement);
    }

    @Step("Get mission title")
    public String getMissionTitle() {
        return getText(missionTitleElement);
    }

    @Step("Get mission text")
    public String getMissionText() {
        return getText(missionTextElement);
    }

    @Step("Get programs link")
    public WebElement getProgramsLink() {
        return waitUntilVisible(programsLinkElement);
    }

    @Step("Click programs link")
    public void clickProgramsLink() {
        click(programsLinkElement);
    }

    @Step("Click translate button")
    public void clickTranslateButton() {
        click(TranslateButton);
    }

    @Step("Select category: {categoryName}")
    public void selectCategory(String categoryName) {
        WebElement button = findByText(categoryButtons, categoryName);
        click(button);
    }

    // Image Section Methods

    @Step("Get image section")
    public WebElement getImageSection() {
        return waitUntilVisible(imageSectionElement);
    }

    @Step("Get content wrapper")
    public WebElement getContentWrapper() {
        return waitUntilVisible(contentWrapperElement);
    }

    @Step("Get content description")
    public WebElement getContentDescription() {
        return waitUntilVisible(contentDescriptionElement);
    }

    // Cards Section Methods

    @Step("Get cards section wrapper")
    public WebElement getCardsSectionWrapper() {
        return waitUntilVisible(cardsSectionWrapperElement);
    }

    @Step("Get cards section title")
    public String getCardsSectionTitle() {
        return getText(cardsSectionTitleElement);
    }

    @Step("Get cards container")
    public WebElement getCardsContainer() {
        return waitUntilVisible(cardsContainerElement);
    }

    @Step("Get all cards count")
    public int getCardsCount() {
        return cardContentElements.size();
    }

    @Step("Get card by description text: {descriptionText}")
    public WebElement getCardByDescription(String descriptionText) {
        for (int i = 0; i < cardContentElements.size(); i++) {
            WebElement description = cardDescriptionWrapperElements.get(i);
            if (getText(description).contains(descriptionText)) {
                return waitUntilVisible(cardContentElements.get(i));
            }
        }
        throw new NoSuchElementException("Card with description not found: " + descriptionText);
    }

    // Rich Text Editor Methods

    @Step("Get all rich text editors count")
    public int getRichTextEditorsCount() {
        return richTextEditorElements.size();
    }

    @Step("Get rich text editor by label text: {labelText}")
    public WebElement getRichTextEditorByLabel(String labelText) {
        for (int i = 0; i < descriptionLabelElements.size(); i++) {
            if (getText(descriptionLabelElements.get(i)).equalsIgnoreCase(labelText)) {
                return waitUntilVisible(richTextEditorElements.get(i));
            }
        }
        throw new NoSuchElementException("Rich text editor with label not found: " + labelText);
    }

    @Step("Click bold button in editor by label text: {labelText}")
    public void clickBoldButton(String labelText) {
        WebElement editor = getRichTextEditorByLabel(labelText);
        int editorIndex = richTextEditorElements.indexOf(editor);
        click(boldButtonElements.get(editorIndex));
    }

    @Step("Click italic button in editor by label text: {labelText}")
    public void clickItalicButton(String labelText) {
        WebElement editor = getRichTextEditorByLabel(labelText);
        int editorIndex = richTextEditorElements.indexOf(editor);
        click(italicButtonElements.get(editorIndex));
    }

    @Step("Click line break button in editor by label text: {labelText}")
    public void clickLineBreakButton(String labelText) {
        WebElement editor = getRichTextEditorByLabel(labelText);
        int editorIndex = richTextEditorElements.indexOf(editor);
        click(lineBreakButtonElements.get(editorIndex));
    }

    @Step("Get text box by label text: {labelText}")
    public WebElement getTextBoxByLabel(String labelText) {
        WebElement editor = getRichTextEditorByLabel(labelText);
        int editorIndex = richTextEditorElements.indexOf(editor);
        return waitUntilVisible(textBoxElements.get(editorIndex));
    }

    @Step("Fill text box by label text: {labelText} with text: {text}")
    public void fillTextBox(String labelText, String text) {
        WebElement textBox = getTextBoxByLabel(labelText);
        textBox.clear();
        textBox.sendKeys(text);
    }

    @Step("Get all card description texts")
    public List<String> getCardDescriptionTexts() {
        return textBoxElements.stream()
                .map(this::getText)
                .toList();
    }

    private int getTextBoxIndexByDescription(String description) {
        for (int i = 0; i < textBoxElements.size(); i++) {
            if (getText(textBoxElements.get(i)).equals(description)) {
                return i;
            }
        }
        throw new NoSuchElementException("Description field not found for card: " + description);
    }

    @Step("Fill description for card with text: {description}")
    public void fillCardDescription(String description, String text) {
        WebElement textBox = textBoxElements.get(getTextBoxIndexByDescription(description));
        fillInput(textBox, text);
        textBox.sendKeys(Keys.TAB);
    }

    @Step("Fill card description with a space and apply bold: {description}")
    public void fillCardDescriptionWithBoldSpace(String description) {
        int index = getTextBoxIndexByDescription(description);
        WebElement textBox = textBoxElements.get(index);
        fillInput(textBox, " ");
        textBox.click();
        actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();
        click(boldButtonElements.get(index));
        textBox.sendKeys(Keys.TAB);
    }

    @Step("Get all card character counters")
    public List<String> getCardCharacterCounterTexts() {
        return getTexts(characterCounterElements);
    }

    @Step("Get character counter for card with text: {description}")
    public String getCardCharacterCounter(String description) {
        int index = getTextBoxIndexByDescription(description);
        return getText(characterCounterElements.get(index));
    }

    @Step("Wait for validation error text: {expectedText}")
    public void waitForValidationError(String expectedText) {
        wait.until(driver -> inputErrorElements.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .anyMatch(expectedText::equals));
    }

    @Step("Check validation error text: {expectedText}")
    public boolean hasValidationError(String expectedText) {
        return inputErrorElements.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .anyMatch(expectedText::equals);
    }

    @Step("Get text box for card with description: {description}")
    public WebElement getCardTextBox(String description) {
        return waitUntilVisible(textBoxElements.get(getTextBoxIndexByDescription(description)));
    }

    @Step("Add and delete a character for card with description: {description}")
    public void addAndDeleteCardCharacter(String description) {
        WebElement textBox = getCardTextBox(description);
        textBox.click();
        textBox.sendKeys(Keys.END);
        textBox.sendKeys("x");
        textBox.sendKeys(Keys.BACK_SPACE);
        textBox.sendKeys(Keys.TAB);
    }

    @Step("Get text box text by label text: {labelText}")
    public String getTextBoxText(String labelText) {
        return getText(getTextBoxByLabel(labelText));
    }

    @Step("Get rich text footer by label text: {labelText}")
    public WebElement getRichTextFooter(String labelText) {
        WebElement editor = getRichTextEditorByLabel(labelText);
        int editorIndex = richTextEditorElements.indexOf(editor);
        return waitUntilVisible(richTextFooterElements.get(editorIndex));
    }

    @Step("Click clear input button by label text: {labelText}")
    public void clickClearInputButton(String labelText) {
        WebElement editor = getRichTextEditorByLabel(labelText);
        int editorIndex = richTextEditorElements.indexOf(editor);
        click(clearInputButtonElements.get(editorIndex));
    }

    @Step("Get character counter by label text: {labelText}")
    public String getCharacterCounterText(String labelText) {
        WebElement editor = getRichTextEditorByLabel(labelText);
        int editorIndex = richTextEditorElements.indexOf(editor);
        return getText(characterCounterElements.get(editorIndex));
    }

    @Step("Get input label by text: {labelText}")
    public String getInputLabelText(String labelText) {
        for (WebElement label : descriptionLabelElements) {
            if (getText(label).equalsIgnoreCase(labelText)) {
                return getText(label);
            }
        }
        throw new NoSuchElementException("Input label not found: " + labelText);
    }

    // Image Input Methods

    @Step("Get all image input containers count")
    public int getImageInputContainersCount() {
        return imageInputContainerElements.size();
    }

    @Step("Get image input container by placeholder text: {placeholderText}")
    public WebElement getImageInputContainer(String placeholderText) {
        for (int i = 0; i < imagePlaceholderTextPrimaryElements.size(); i++) {
            if (getText(imagePlaceholderTextPrimaryElements.get(i)).contains(placeholderText)) {
                return waitUntilVisible(imageInputContainerElements.get(i));
            }
        }
        throw new NoSuchElementException("Image input container with placeholder not found: " + placeholderText);
    }

    @Step("Get image placeholder by placeholder text: {placeholderText}")
    public WebElement getImagePlaceholder(String placeholderText) {
        for (int i = 0; i < imagePlaceholderTextPrimaryElements.size(); i++) {
            if (getText(imagePlaceholderTextPrimaryElements.get(i)).contains(placeholderText)) {
                return waitUntilVisible(imagePlaceholderElements.get(i));
            }
        }
        throw new NoSuchElementException("Image placeholder with text not found: " + placeholderText);
    }

    @Step("Get image placeholder primary text by placeholder text: {placeholderText}")
    public String getImagePlaceholderPrimaryText(String placeholderText) {
        for (int i = 0; i < imagePlaceholderTextPrimaryElements.size(); i++) {
            if (getText(imagePlaceholderTextPrimaryElements.get(i)).contains(placeholderText)) {
                return getText(imagePlaceholderTextPrimaryElements.get(i));
            }
        }
        throw new NoSuchElementException("Image placeholder primary text not found: " + placeholderText);
    }

    @Step("Get image placeholder secondary text by placeholder text: {placeholderText}")
    public String getImagePlaceholderSecondaryText(String placeholderText) {
        for (int i = 0; i < imagePlaceholderTextPrimaryElements.size(); i++) {
            if (getText(imagePlaceholderTextPrimaryElements.get(i)).contains(placeholderText)) {
                return getText(imagePlaceholderTextSecondaryElements.get(i));
            }
        }
        throw new NoSuchElementException("Image placeholder secondary text not found: " + placeholderText);
    }

    @Step("Get preview image by placeholder text: {placeholderText}")
    public WebElement getPreviewImage(String placeholderText) {
        WebElement container = getImageInputContainer(placeholderText);
        int containerIndex = imageInputContainerElements.indexOf(container);
        return waitUntilVisible(previewImageElements.get(containerIndex));
    }

    @Step("Click remove photo button by placeholder text: {placeholderText}")
    public void clickRemovePhotoButton(String placeholderText) {
        WebElement container = getImageInputContainer(placeholderText);
        int containerIndex = imageInputContainerElements.indexOf(container);
        click(removePhotoButtonElements.get(containerIndex));
    }

    // Publish Button Methods

    @Step("Click publish button")
    public void clickPublishButton() {
        click(publishButtonElement);
    }

    @Step("Check if publish button is enabled")
    public boolean isPublishButtonEnabled() {
        return publishButtonElement.isEnabled();
    }
}