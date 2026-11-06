package org.academy.ui.pages.Admin;

import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class AdminPartnerPage extends BaseAdminPage{
    private static final String PAGE_PATH = "admin-panel/partners";

    // Banner form
    @FindBy(css = ".PartnerBannerForm_content__GE07h")
    private WebElement bannerFormContent;

    @FindBy(css = "#title[contenteditable='true']")
    private WebElement titleInput;

    @FindBy(css = "#description")
    private WebElement descriptionInput;

    @FindBy(css = "input[data-testid='image-input-hidden']")
    private WebElement imageInput;

    @FindBy(css = ".PartnerBannerForm_actions__L70TB button.btn-primary")
    private WebElement publishButton;

    @FindBy(css = "button[aria-label='Додати переклад']")
    private WebElement addTranslationButton;

    @FindBy(css = ".PartnerBannerForm_content__GE07h .InputErrorWithCharacterCounter_error-section")
    private List<WebElement> bannerErrorMessages;

    @FindBy(css = ".input-group:has(#title) .InputErrorWithCharacterCounter_error-section__OwyAS")
    private WebElement titleError;

    @FindBy(css = ".input-group:has(#description) .InputErrorWithCharacterCounter_error-section__OwyAS")
    private WebElement descriptionError;

    @FindBy(css = "#title-character-count")
    private WebElement titleCharacterCounter;

    @FindBy(css = "#description-character-count")
    private WebElement descriptionCharacterCounter;

    // Partner section form
    @FindBy(css = ".PartnerSectionForm_root")
    private WebElement partnerSectionForm;

    @FindBy(css = "textarea[placeholder='Введіть заголовок секції']")
    private WebElement sectionTitleInput;

    @FindBy(css = "textarea[placeholder='Введіть опис секції']")
    private WebElement sectionDescriptionInput;

    @FindBy(css = ".PartnerForm_description textarea")
    private List<WebElement> partnerDescriptionInputs;

    @FindBy(css = ".PartnerForm_image input[type='file']")
    private List<WebElement> partnerImageInputs;

    @FindBy(css = ".DefaultImageInput_container")
    private List<WebElement> partnerImageUploadAreas;

    @FindBy(css = "img[data-testid='preview-image']")
    private List<WebElement> partnerImagePreviews;

    @FindBy(css = "button[data-testid='remove-photo-button']")
    private List<WebElement> removePhotoButtons;

    @FindBy(css = ".PartnerSectionForm_add-card button")
    private WebElement addPartnerButton;

    @FindBy(css = ".PartnerSectionForm_actions button.btn-secondary")
    private WebElement deleteSectionButton;

    @FindBy(css = ".PartnerSectionForm_actions button.btn-primary")
    private WebElement publishSectionFormButton;

    @FindBy(css = "button[data-testid^='partner-form-delete-button-']")
    private List<WebElement> deletePartnerButtons;

    @FindBy(css = ".PartnerSectionForm_title-group .InputErrorWithCharacterCounter_error-section")
    private WebElement sectionTitleError;

    @FindBy(css = ".PartnerSectionForm_description-group .InputErrorWithCharacterCounter_error-section")
    private WebElement sectionDescriptionError;

    @FindBy(css = ".PartnerForm_description .InputErrorWithCharacterCounter_error-section")
    private List<WebElement> partnerDescriptionErrors;

    @FindBy(css = ".PartnerSectionForm_title-group output")
    private WebElement sectionTitleCounter;

    @FindBy(css = ".PartnerSectionForm_description-group output")
    private WebElement sectionDescriptionCounter;

    @FindBy(css = ".PartnerForm_description output")
    private List<WebElement> partnerDescriptionCounters;

    public AdminPartnerPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open Admin 'Партнери' page")
    public AdminPartnerPage open(String baseUrl) {
        String url = baseUrl.endsWith("/") ? baseUrl + PAGE_PATH : baseUrl + "/" + PAGE_PATH;
        driver.get(url);
        return waitUntilLoaded();
    }

    @Step("Wait until Admin 'Партнери' page is loaded")
    public AdminPartnerPage waitUntilLoaded() {
        waitUntilVisible(bannerFormContent);
        return this;
    }

    @Step("Verify 'Партнери' page is displayed")
    public boolean isPageDisplayed() {
        return isVisible(bannerFormContent);
    }

    @Step("Clear title")
    public AdminPartnerPage clearTitle() {
        wait.until(ExpectedConditions.visibilityOf(titleInput));
        titleInput.click();
        titleInput.sendKeys(Keys.CONTROL, "a");
        titleInput.sendKeys(Keys.BACK_SPACE);
        return this;
    }

    @Step("Enter title: {title}")
    public AdminPartnerPage enterTitle(String title) {
        wait.until(ExpectedConditions.visibilityOf(titleInput));
        titleInput.click();
        titleInput.sendKeys(title);
        return this;
    }

    @Step("Get title")
    public String getTitle() {
        return titleInput.getAttribute("innerText").trim();
    }

    @Step("Blur Banner title field")
    public AdminPartnerPage blurTitle() {
        titleInput.sendKeys(Keys.TAB);
        return this;
    }

    @Step("Clear description")
    public AdminPartnerPage clearDescription() {
        wait.until(ExpectedConditions.visibilityOf(descriptionInput));

        descriptionInput.click();
        descriptionInput.sendKeys(Keys.CONTROL, "a");
        descriptionInput.sendKeys(Keys.BACK_SPACE);

        return this;
    }

    @Step("Enter description: {description}")
    public AdminPartnerPage enterDescription(String description) {
        wait.until(ExpectedConditions.visibilityOf(descriptionInput));
        descriptionInput.sendKeys(description);
        return this;
    }

    @Step("Get description")
    public String getDescriptionValue() {
        return descriptionInput.getAttribute("value");
    }

    @Step("Upload image: {absolutePath}")
    public AdminPartnerPage uploadImage(String absolutePath) {
        imageInput.sendKeys(absolutePath);
        return this;
    }

    @Step("Click button 'Publish'")
    public AdminPartnerPage clickPublish() {
        click(publishButton);
        return this;
    }

    @Step("Click button 'Додати переклад'")
    public AdminPartnerPage clickAddTranslation() {
        click(addTranslationButton);
        return this;
    }

    @Step("Check if Banner Publish button is enabled")
    public boolean isPublishButtonEnabled() {
        return publishButton.isEnabled();
    }

    @Step("Check button 'Додати переклад'")
    public boolean isAddTranslationButtonDisplayed() {
        return addTranslationButton.isDisplayed();
    }

    @Step("Get Banner title error text")
    public String getTitleErrorText() {
        waitUntilVisible(titleError);
        return titleError.getText().trim();
    }

    @Step("Get description error")
    public String getDescriptionErrorText() {
        waitUntilVisible(descriptionError);
        return descriptionError.getText().trim();
        }

    @Step("Get all errors")
    public List<String> getAllBannerErrorTexts() {
        return bannerErrorMessages.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .filter(text -> !text.isEmpty())
                .toList();
    }

    @Step("Check if error is displayed: {expectedText}")
    public boolean isBannerErrorDisplayed(String expectedText) {
        return bannerErrorMessages.stream()
                .anyMatch(error -> error.isDisplayed()
                        && error.getText().trim().contains(expectedText));
    }

    @Step("Check if there are no errors")
    public boolean bannerHasNoErrors() {
        return bannerErrorMessages.stream().noneMatch(WebElement::isDisplayed);
    }

    @Step ("Get title character counter text")
    public String getTitleCharacterCounterText() {
        return titleCharacterCounter.getText().trim();
    }

    @Step ("Get description character counter text")
    public String getDescriptionCharacterCounterText() {
        return descriptionCharacterCounter.getText().trim();
    }

    @Step("Clear Banner title and description fields")
    public AdminPartnerPage clearBannerFields() {
        titleInput.clear();
        descriptionInput.clear();
        return this;
    }

    @Step("Append text to Banner title: {text}")
    public AdminPartnerPage appendToTitle(String text) {
        titleInput.sendKeys(text);
        return this;
    }

    @Step("Append text to Banner description: {text}")
    public AdminPartnerPage appendToDescription(String text) {
        descriptionInput.sendKeys(text);
        return this;
    }

    @Step("Blur Banner description field")
    public AdminPartnerPage blurDescription() {
        descriptionInput.sendKeys(org.openqa.selenium.Keys.TAB);
        return this;
    }

    @Step("Wait for Banner title counter: {expectedCounter}")
    public AdminPartnerPage waitForTitleCounter(String expectedCounter) {
        wait.until(ExpectedConditions.textToBePresentInElement(
                titleCharacterCounter, expectedCounter));
        return this;
    }

    @Step("Wait for Banner description counter: {expectedCounter}")
    public AdminPartnerPage waitForDescriptionCounter(String expectedCounter) {
        wait.until(ExpectedConditions.textToBePresentInElement(
                descriptionCharacterCounter, expectedCounter));
        return this;
    }
}
