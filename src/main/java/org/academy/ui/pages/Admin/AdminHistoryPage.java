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

public class AdminHistoryPage extends BaseAdminPage {

    private static final String PAGE_PATH = "admin/history";

    // Main Container Locators
    @FindBy(css = ".admin__page")
    private WebElement mainContainerElement;

    @FindBy(css = "[data-testid='history-page-content']")
    private WebElement historyPageContentElement;

    // Toolbar Locators
    @FindBy(css = "[class*='HistoryPageToolbar_history-page-toolbar']")
    private WebElement toolbarElement;

    @FindBy(css = "[class*='HistoryPageToolbar_toolbar-controls']")
    private WebElement toolbarControlsElement;

    @FindBy(css = "[data-testid='localization-statuses']")
    private WebElement localizationStatusesElement;

    @FindBy(css = "[data-testid='localization-toolkit']")
    private WebElement localizationToolkitElement;

    @FindBy(css = "[data-testid='language-toolkit'] button.select-head")
    private WebElement languageSelectButtonElement;

    @FindBy(css = "[class*='LocalizationToolkit_toolkit'] > .select button.select-head")
    private WebElement translationsSelectButtonElement;

    @FindBy(css = ".select-options")
    private WebElement selectOptionsElement;

    @FindBy(css = ".select-options-selected")
    private WebElement selectedOptionElement;

    @FindBy(css = "[class*='HistoryPageToolbar_actions-wrapper'] > button.btn-primary")
    private WebElement addSectionTopButtonElement;

    @FindBy(css = "button[aria-label='Перекласти']")
    private WebElement translateButtonElement;

    // Sections Container Locators
    @FindBy(css = "[class*='HistoryPageContent_sections-container']")
    private WebElement sectionsContainerElement;

    @FindBy(css = "[class*='HistoryForm_sections-list']")
    private WebElement sectionsListElement;

    @FindBy(css = "[class*='HistoryForm_sections-divider']")
    private List<WebElement> sectionsDividerElements;

    // Bottom Buttons Locators
    @FindBy(css = "[class*='HistoryPageContent_functional-button-container']")
    private WebElement bottomButtonsContainerElement;

    @FindBy(css = "button[class*='HistoryPageContent_btn-add']")
    private WebElement addSectionBottomButtonElement;

    @FindBy(css = "button[class*='HistoryPageContent_btn-publish']")
    private WebElement publishButtonElement;

    // Sections Locators
    @FindBy(css = "[class*='HistorySectionForm_section-container']")
    private List<WebElement> sectionContainerElements;

    @FindBy(css = "[class*='HistorySectionForm_content']")
    private List<WebElement> sectionContentElements;

    @FindBy(css = "[class*='HistorySectionForm_actions-section']")
    private List<WebElement> sectionActionsElements;

    @FindBy(css = "[class*='HistorySectionForm_order-controls']")
    private List<WebElement> sectionOrderControlsElements;

    // Section Action Buttons Locators
    @FindBy(css = "button[aria-label='Edit section']")
    private List<WebElement> editSectionButtonElements;

    @FindBy(css = "textarea[id$='-section-title']")
    private WebElement TitleFieldElement;

    @FindBy(css = "textarea[id$='-section-description']")
    private WebElement DescriptionFieldElement;

    @FindBy(css = "img[data-testid='preview-image']")
    private WebElement PreviewImageElement;

    @FindBy(css = "button[data-testid='remove-photo-button']")
    private WebElement RemovePhotoButtonElement;

    @FindBy(css = "button.btn-secondary")
    private WebElement CancelButtonElement;

    @FindBy(css = "[class*='HistorySectionForm_actions__'] > button.btn-primary")
    private WebElement SaveButtonElement;

    @FindBy(css = "button[aria-label='Delete section']")
    private List<WebElement> deleteSectionButtonElements;

    @FindBy(css = "button[aria-label='Replace section']")
    private List<WebElement> replaceSectionButtonElements;

    @FindBy(css = "button[aria-label='Move up section']")
    private List<WebElement> moveUpSectionButtonElements;

    @FindBy(css = "button[aria-label='Move down section']")
    private List<WebElement> moveDownSectionButtonElements;

    // Section Text Content Locators
    @FindBy(css = "[data-section-text='true']")
    private List<WebElement> sectionTextElements;

    @FindBy(css = "[class*='ViewTitleDescriptionSection_title__']")
    private List<WebElement> sectionTitleElements;

    @FindBy(css = "[class*='ViewTitleDescriptionSection_description__']")
    private List<WebElement> sectionDescriptionElements;

    // Gallery Locators
    @FindBy(css = "[class*='ViewSingleImageBottom_container']")
    private List<WebElement> singleImageContainerElements;

    @FindBy(css = "[class*='ViewSingleImageBottom_image__']")
    private List<WebElement> singleImageElements;

    @FindBy(css = "[class*='ViewDualImagesBottom_container']")
    private List<WebElement> dualImagesContainerElements;

    @FindBy(css = "[class*='ViewQuadImagesBottom_container']")
    private List<WebElement> quadImagesContainerElements;

    @FindBy(css = "[data-testid='image-wrapper']")
    private List<WebElement> imageWrapperElements;

    @FindBy(css = "[class*='ViewImagesBottomSection_image__']")
    private List<WebElement> galleryImageElements;

    @FindBy(css = ".admin__page img")
    private List<WebElement> allPageImageElements;

    // Gallery Navigation Buttons Locators
    @FindBy(css = "button[aria-label='previous']")
    private List<WebElement> previousImageButtonElements;

    @FindBy(css = "button[aria-label='next']")
    private List<WebElement> nextImageButtonElements;

    public AdminHistoryPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open Admin History page")
    public AdminHistoryPage open(String baseUrl) {
        driver.get(baseUrl + PAGE_PATH);
        return this;
    }

    @Step("Verify Admin History page is displayed")
    public boolean isPageDisplayed() {
        return isVisible(mainContainerElement);
    }

    // Main Container Methods

    @Step("Get main container")
    public WebElement getMainContainer() {
        return waitUntilVisible(mainContainerElement);
    }

    @Step("Get history page content")
    public WebElement getHistoryPageContent() {
        return waitUntilVisible(historyPageContentElement);
    }

    // Toolbar Methods

    @Step("Get toolbar")
    public WebElement getToolbar() {
        return waitUntilVisible(toolbarElement);
    }

    @Step("Get toolbar controls")
    public WebElement getToolbarControls() {
        return waitUntilVisible(toolbarControlsElement);
    }

    @Step("Get localization statuses")
    public WebElement getLocalizationStatuses() {
        return waitUntilVisible(localizationStatusesElement);
    }

    @Step("Get localization toolkit")
    public WebElement getLocalizationToolkit() {
        return waitUntilVisible(localizationToolkitElement);
    }

    @Step("Get language select button")
    public WebElement getLanguageSelectButton() {
        return waitUntilVisible(languageSelectButtonElement);
    }

    @Step("Click language select button")
    public void clickLanguageSelectButton() {
        click(languageSelectButtonElement);
    }

    @Step("Get translations select button")
    public WebElement getTranslationsSelectButton() {
        return waitUntilVisible(translationsSelectButtonElement);
    }

    @Step("Click translations select button")
    public void clickTranslationsSelectButton() {
        click(translationsSelectButtonElement);
    }

    @Step("Get select options")
    public WebElement getSelectOptions() {
        return waitUntilVisible(selectOptionsElement);
    }

    @Step("Get selected option")
    public WebElement getSelectedOption() {
        return waitUntilVisible(selectedOptionElement);
    }

    @Step("Get selected option text")
    public String getSelectedOptionText() {
        return getText(selectedOptionElement);
    }

    @Step("Click add section top button")
    public void clickAddSectionTopButton() {
        click(addSectionTopButtonElement);
    }

    @Step("Click translate button")
    public void clickTranslateButton() {
        click(translateButtonElement);
    }

    // Sections Container Methods

    @Step("Get sections container")
    public WebElement getSectionsContainer() {
        return waitUntilVisible(sectionsContainerElement);
    }

    @Step("Get sections list")
    public WebElement getSectionsList() {
        return waitUntilVisible(sectionsListElement);
    }

    @Step("Get sections dividers count")
    public int getSectionsDividersCount() {
        return sectionsDividerElements.size();
    }

    // Bottom Buttons Methods

    @Step("Get bottom buttons container")
    public WebElement getBottomButtonsContainer() {
        return waitUntilVisible(bottomButtonsContainerElement);
    }

    @Step("Click add section bottom button")
    public void clickAddSectionBottomButton() {
        click(addSectionBottomButtonElement);
    }

    @Step("Click publish button")
    public void clickPublishButton() {
        click(publishButtonElement);
    }

    // Sections Methods

    @Step("Get all sections count")
    public int getSectionsCount() {
        return sectionContainerElements.size();
    }

    @Step("Get section by title: {title}")
    public WebElement getSection(String title) {
        return waitUntilVisible(findSectionByTitle(title));
    }

    @Step("Get section content by section title: {title}")
    public WebElement getSectionContent(String title) {
        return waitUntilVisible(findInSection(title, sectionContentElements));
    }

    @Step("Get section actions by section title: {title}")
    public WebElement getSectionActions(String title) {
        return waitUntilVisible(findInSection(title, sectionActionsElements));
    }

    @Step("Get section order controls by section title: {title}")
    public WebElement getSectionOrderControls(String title) {
        return waitUntilVisible(findInSection(title, sectionOrderControlsElements));
    }

    // Section Action Buttons Methods

    @Step("Click edit section button by section title: {title}")
    public void clickEditSectionButton(String title) {
        click(findInSection(title, editSectionButtonElements));
    }


    @Step("Get edit section title field")
    public WebElement getTitleField() {
        return waitUntilVisible(TitleFieldElement);
    }

    @Step("Fill edit section title: {title}")
    public void fillTitle(String title) {
        fillInput(TitleFieldElement, title);
    }

    @Step("Get edit section title text")
    public String getTitle() {
        return getValue(TitleFieldElement);
    }

    @Step("Get edit section description field")
    public WebElement getDescriptionField() {
        return waitUntilVisible(DescriptionFieldElement);
    }

    @Step("Fill edit section description: {description}")
    public void fillDescription(String description) {
        fillInput(DescriptionFieldElement, description);
    }

    @Step("Get edit section description text")
    public String getDescription() {
        return getValue(DescriptionFieldElement);
    }

    @Step("Get edit section preview image")
    public WebElement getPreviewImage() {
        return waitUntilVisible(PreviewImageElement);
    }

    @Step("Click remove photo button in edit section")
    public void clickRemovePhotoButton() {
        click(RemovePhotoButtonElement);
    }

    @Step("Click cancel button in edit section")
    public void clickCancelButton() {
        click(CancelButtonElement);
    }

    @Step("Click save button in edit section")
    public void clickSaveButton() {
        click(SaveButtonElement);
    }

    @Step("Click delete section button by section title: {title}")
    public void clickDeleteSectionButton(String title) {
        click(findInSection(title, deleteSectionButtonElements));
    }

    @Step("Click replace section button by section title: {title}")
    public void clickReplaceSectionButton(String title) {
        click(findInSection(title, replaceSectionButtonElements));
    }

    @Step("Click move up section button by section title: {title}")
    public void clickMoveUpSectionButton(String title) {
        click(findInSection(title, moveUpSectionButtonElements));
    }

    @Step("Click move down section button by section title: {title}")
    public void clickMoveDownSectionButton(String title) {
        click(findInSection(title, moveDownSectionButtonElements));
    }

    // Section Text Content Methods

    @Step("Get all section text blocks count")
    public int getSectionTextBlocksCount() {
        return sectionTextElements.size();
    }

    @Step("Get all section titles")
    public List<String> getSectionTitles() {
        return getTexts(sectionTitleElements);
    }

    @Step("Get section description by section title: {title}")
    public String getSectionDescription(String title) {
        return getText(findInSection(title, sectionDescriptionElements));
    }


    // Gallery Methods

    @Step("Get single image containers count")
    public int getSingleImageContainersCount() {
        return singleImageContainerElements.size();
    }

    @Step("Get single images count")
    public int getSingleImagesCount() {
        return singleImageElements.size();
    }

    @Step("Get dual images containers count")
    public int getDualImagesContainersCount() {
        return dualImagesContainerElements.size();
    }

    @Step("Get quad images containers count")
    public int getQuadImagesContainersCount() {
        return quadImagesContainerElements.size();
    }

    @Step("Get image wrappers count")
    public int getImageWrappersCount() {
        return imageWrapperElements.size();
    }

    @Step("Get gallery images count")
    public int getGalleryImagesCount() {
        return galleryImageElements.size();
    }

    @Step("Get all page images count")
    public int getAllPageImagesCount() {
        return allPageImageElements.size();
    }

    // Gallery Navigation Methods

    @Step("Click previous image button in section: {sectionTitle}")
    public void clickPreviousImageButton(String sectionTitle) {
        click(findInSection(sectionTitle, previousImageButtonElements));
    }

    @Step("Click next image button in section: {sectionTitle}")
    public void clickNextImageButton(String sectionTitle) {
        click(findInSection(sectionTitle, nextImageButtonElements));
    }

    // Private Helpers

    private WebElement findSectionByTitle(String title) {
        Objects.requireNonNull(title, "Section title must not be null");
        WebElement titleElement = findByText(sectionTitleElements, title);
        try {
            return wait.until(currentDriver -> sectionContainerElements.stream()
                    .filter(section -> isInside(section, titleElement))
                    .findFirst()
                    .orElse(null));
        } catch (TimeoutException e) {
            throw new NoSuchElementException("Section not found: " + title);
        }
    }

    private List<WebElement> findAllInSection(String sectionTitle, List<WebElement> elements) {
        return filterInside(findSectionByTitle(sectionTitle), elements);
    }

    private WebElement findInSection(String sectionTitle, List<WebElement> elements) {
        return findAllInSection(sectionTitle, elements).stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Element not found in section: " + sectionTitle));
    }
}