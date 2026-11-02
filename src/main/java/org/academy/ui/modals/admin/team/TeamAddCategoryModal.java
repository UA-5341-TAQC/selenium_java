package org.academy.ui.modals.admin.team;

import io.qameta.allure.Step;
import org.academy.ui.modals.BaseModal;
import org.academy.ui.modals.admin.ConfirmationModal;
import org.academy.ui.pages.Admin.TeamPageAdmin;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;


public class TeamAddCategoryModal extends BaseModal {
    private static final By MODAL = By.xpath(
            "//div[@class='modal-container'][.//form[@id='add-team-category-form']]");
    private static final By ERROR = By.cssSelector("[class*='InputErrorWithCharacterCounter_error-section']");
    private static final By CLEAR_BUTTON = By.cssSelector("button[aria-label='Clear input']");
    private static final By INPUT_GROUP = By.xpath("./ancestor::div[contains(@class,'input-group')][1]");
    private static final String CLEAR_HIDDEN_CLASS = "clear-button--hidden";
    private static final String CLEAR_ERROR_CLASS = "clear-button--error";

    @FindBy(css = ".modal-header-text")
    private WebElement title;
    @FindBy(id = "add-category-name")
    private WebElement nameField;
    @FindBy(id = "add-category-description")
    private WebElement descriptionField;
    @FindBy(id = "add-category-name-character-count")
    private WebElement nameCounter;
    @FindBy(id = "add-category-description-character-count")
    private WebElement descriptionCounter;
    @FindBy(css = ".team-category-modal-save-button")
    private WebElement saveButton;

    // Знаходить відкриту модалку та ініціалізує її поля.
    public TeamAddCategoryModal(WebDriver driver) {
        super(driver, MODAL);
    }

    // Повертає заголовок модалки.
    public String getTitle() {
        return title.getText();
    }

    // Перевіряє видимість модалки.
    public boolean isDisplayed() {
        return rootElement.isDisplayed();
    }

    // Повертає поточне значення імені або назви.
    public String getName() {
        return nameField.getDomProperty("value");
    }

    @Step("Enter Team category name: {name}")
    public TeamAddCategoryModal setName(String name) {
        nameField.click();
        nameField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        nameField.sendKeys(name);
        return this;
    }

    // Повертає поточне значення опису.
    public String getDescription() {
        return descriptionField.getDomProperty("value");
    }

    @Step("Enter Team category description")
    public TeamAddCategoryModal setDescription(String description) {
        descriptionField.click();
        descriptionField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        descriptionField.sendKeys(description);
        return this;
    }

    @Step("Leave the category description field to trigger validation")
    public TeamAddCategoryModal blurDescription() {
        descriptionField.sendKeys(Keys.TAB);
        return this;
    }

    // Повертає текст лічильника символів назви.
    public String getNameCounter() {
        return nameCounter.getText();
    }

    // Повертає текст лічильника символів опису.
    public String getDescriptionCounter() {
        return descriptionCounter.getText();
    }

    // Перевіряє доступність кнопки збереження.
    public boolean isSaveEnabled() {
        return saveButton.isEnabled();
    }

    @Step("Save a new Team category")
    public TeamPageAdmin clickSave() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
        wait.until(ExpectedConditions.stalenessOf(rootElement));
        return new TeamPageAdmin(driver);
    }

    private WebElement field(String label) {
        return switch (label) {
            case "Назва" -> nameField;
            case "Опис" -> descriptionField;
            default -> throw new IllegalArgumentException("Unknown Team category field: " + label);
        };
    }

    private String clearButtonClasses(String label) {
        return field(label).findElement(INPUT_GROUP).findElement(CLEAR_BUTTON).getDomAttribute("class");
    }

    @Step("Click inside the '{label}' field")
    public TeamAddCategoryModal focusField(String label) {
        click(field(label));
        return this;
    }

    @Step("Leave the '{label}' field")
    public TeamAddCategoryModal blurField(String label) {
        field(label).sendKeys(Keys.TAB);
        return this;
    }

    @Step("Read error of the '{label}' field")
    public String getFieldError(String label) {
        return getText(field(label).findElement(INPUT_GROUP).findElement(ERROR));
    }

    private boolean isClearIconCurrentlyShown(String label) {
        List<WebElement> buttons = field(label).findElement(INPUT_GROUP).findElements(CLEAR_BUTTON);
        if (buttons.isEmpty()) {
            return false;
        }
        String classes = buttons.getFirst().getDomAttribute("class");
        return classes == null || !classes.contains(CLEAR_HIDDEN_CLASS);
    }

    @Step("Check clean-up icon is shown in the '{label}' field")
    public boolean isClearIconDisplayed(String label) {
        try {
            wait.until(d -> isClearIconCurrentlyShown(label));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Check clean-up icon is hidden in the '{label}' field")
    public boolean isClearIconHidden(String label) {
        try {
            wait.until(d -> !isClearIconCurrentlyShown(label));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isClearIconInErrorState(String label) {
        String classes = clearButtonClasses(label);
        return classes != null && classes.contains(CLEAR_ERROR_CLASS);
    }

    @Step("Click the clean-up icon in the '{label}' field")
    public TeamAddCategoryModal clickClearIcon(String label) {
        click(field(label).findElement(INPUT_GROUP).findElement(CLEAR_BUTTON));
        return this;
    }

    public String getFieldValue(String label) {
        return field(label).getDomProperty("value");
    }

    public String getFieldCounter(String label) {
        return switch (label) {
            case "Назва" -> getNameCounter();
            case "Опис" -> getDescriptionCounter();
            default -> throw new IllegalArgumentException("Unknown Team category field: " + label);
        };
    }

    public boolean isClosed() {
        try {
            waitUntilClosed();
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Close the Add category modal with the X icon")
    public ConfirmationModal requestCloseWithConfirmation() {
        requestClose();
        return ConfirmationModal.waitFor(wait);
    }

    public boolean isConfirmationClosed() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.cssSelector(ConfirmationModal.ROOT_SELECTOR)));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Click the X icon of the Add category modal")
    public TeamAddCategoryModal clickCloseIcon() {
        requestClose();
        return this;
    }
}
