package org.academy.ui.pages.Admin;

import io.qameta.allure.Step;
import org.academy.ui.components.admin.whoweare.WhoWeAreCardComponent;
import org.academy.ui.components.admin.whoweare.WhoWeAreCategoryBarComponent;
import org.academy.ui.components.admin.whoweare.WhoWeAreDescriptionEditorComponent;
import org.academy.ui.modals.admin.ConfirmationModal;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class WhoWeArePageAdmin extends BaseAdminPage {

    private static final String PAGE_PATH = "admin-panel/who-we-are";
    private static final String IMAGE_LOADED_SCRIPT =
            "return arguments[0].complete && typeof arguments[0].naturalWidth !== 'undefined' && arguments[0].naturalWidth > 0;";

    @FindBy(css = ".who-we-are-main-section")
    private WebElement mainSection;

    @FindBy(css = ".category-bar")
    private WebElement categoryBarElement;

    @FindBy(css = ".cards-section-wrapper-title")
    private WebElement cardsSectionTitle;

    @FindBy(css = ".card-content")
    private List<WebElement> cardElements;

    @FindBy(css = ".image-wrapper [class*='WhoWeAreImageInput_container'], .card-content [class*='WhoWeAreImageInput_container']")
    private List<WebElement> imageContainerElements;

    @FindBy(css = ".image-wrapper img, .card-content img")
    private List<WebElement> imageElements;

    @FindBy(xpath = "//button[contains(@class,'button')][normalize-space()='Опублікувати'] | //button[@type='submit'][normalize-space()='Опублікувати']")
    private WebElement publishButton;

    @FindBy(css = ".content-wrapper-title")
    private WebElement titleContainer;

    @FindBy(css = ".content-wrapper-description")
    private WebElement descriptionContainer;

    public WhoWeArePageAdmin(WebDriver driver) {
        super(driver);
    }

    @Step("Open Admin 'Хто ми' page")
    public WhoWeArePageAdmin open(String baseUrl) {
        String url = baseUrl.endsWith("/") ? baseUrl + PAGE_PATH : baseUrl + "/" + PAGE_PATH;
        driver.get(url);
        return waitUntilLoaded();
    }

    @Step("Wait until Admin 'Хто ми' page is loaded")
    public WhoWeArePageAdmin waitUntilLoaded() {
        waitUntilVisible(categoryBarElement);
        waitUntilVisible(mainSection);
        return this;
    }

    @Step("Verify 'Хто ми' page is displayed")
    public boolean isPageDisplayed() {
        return isVisible(categoryBarElement);
    }

    // Components

    public WhoWeAreCategoryBarComponent getCategoryBar() {
        return new WhoWeAreCategoryBarComponent(waitUntilVisible(categoryBarElement));
    }

    public List<WhoWeAreCardComponent> getCards() {
        return cardElements.stream()
                .map(WhoWeAreCardComponent::new)
                .toList();
    }

    public WhoWeAreCardComponent getCard(int cardIndex) {
        if (cardIndex < 0 || cardIndex >= cardElements.size()) {
            throw new IndexOutOfBoundsException(
                    "Card index " + cardIndex + " is out of bounds. Available cards: " + cardElements.size());
        }
        return new WhoWeAreCardComponent(cardElements.get(cardIndex));
    }

    public WhoWeAreDescriptionEditorComponent getEditor(int index) {
        if (!cardElements.isEmpty()) {
            return getCard(index);
        }
        return new WhoWeAreDescriptionEditorComponent(waitUntilVisible(mainSection));
    }

    public WhoWeAreDescriptionEditorComponent getPrimaryEditor() {
        return getEditor(0);
    }

    // Page-level actions & queries

    @Step("Select tab: {tabName}")
    public WhoWeArePageAdmin selectTab(String tabName) {
        getCategoryBar().selectTab(tabName);
        waitUntilVisible(mainSection);
        return this;
    }

    @Step("Get title editor")
    public WhoWeAreDescriptionEditorComponent getTitleEditor() {
        return new WhoWeAreDescriptionEditorComponent(
                waitUntilVisible(titleContainer)
        );
    }

    @Step("Get description editor")
    public WhoWeAreDescriptionEditorComponent getMainDescriptionEditor() {
        return new WhoWeAreDescriptionEditorComponent(
                waitUntilVisible(descriptionContainer)
        );
    }

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

    @Step("Check if pre-uploaded default image is displayed and loaded")
    public boolean isPreUploadedImageDisplayed() {
        wait.until(d -> !imageContainerElements.isEmpty() || !imageElements.isEmpty());
        if (!imageElements.isEmpty() && isVisible(imageElements.get(0))) {
            WebElement img = imageElements.get(0);
            return wait.until(d -> Boolean.TRUE.equals(js.executeScript(IMAGE_LOADED_SCRIPT, img)));
        }
        if (!imageContainerElements.isEmpty()) {
            WebElement container = waitUntilVisible(imageContainerElements.get(0));
            String bgImage = container.getCssValue("background-image");
            String styleAttr = getDomAttribute(container, "style");
            return (bgImage != null && bgImage.contains("url("))
                    || (styleAttr != null && styleAttr.contains("url("));
        }
        return false;
    }

    @Step("Check if 'Опублікувати' button is enabled")
    public boolean isPublishButtonEnabled() {
        return isElementClickable(publishButton);
    }

    @Step("Wait for 'Опублікувати' button enabled state: {enabled}")
    public WhoWeArePageAdmin waitForPublishButtonEnabled(boolean enabled) {
        wait.until(d -> isPublishButtonEnabled() == enabled);
        return this;
    }

    @Step("Wait for people cards to be loaded: {expectedCount}")
    public WhoWeArePageAdmin waitForPeopleCards(int expectedCount) {
        wait.until(d -> {
            try {
                return cardElements.size() == expectedCount;
            } catch (StaleElementReferenceException e) {
                return false;
            }
        });
        return this;
    }

    @Step("Upload image to People card {cardIndex}")
    public void uploadImageToCard(int cardIndex, String filePath) {
        getCard(cardIndex).uploadImage(filePath);
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
        waitUntilInvisible(By.cssSelector(ConfirmationModal.ROOT_SELECTOR));
        return true;
    }
}
