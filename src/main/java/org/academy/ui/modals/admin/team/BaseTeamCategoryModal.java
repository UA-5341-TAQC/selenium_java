package org.academy.ui.modals.admin.team;

import io.qameta.allure.Step;
import org.academy.ui.modals.BaseModal;
import org.academy.ui.modals.admin.ConfirmationModal;
import org.academy.ui.pages.Admin.TeamPageAdmin;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Objects;

/** Shared fields and interactions of the Add and Edit category dialogs. */
public abstract class BaseTeamCategoryModal<T extends BaseTeamCategoryModal<T>> extends BaseModal {
    private static final By CONFIRMATION = By.cssSelector(ConfirmationModal.ROOT_SELECTOR);
    private static final By ERROR = By.cssSelector("[class*='InputErrorWithCharacterCounter_error-section']");
    private static final By CLEAR_BUTTON = By.cssSelector("button[aria-label='Clear input']");
    private static final By INPUT_GROUP = By.xpath("./ancestor::div[contains(@class,'input-group')][1]");
    private static final String CLEAR_HIDDEN_CLASS = "clear-button--hidden";
    private static final String CLEAR_ERROR_CLASS = "clear-button--error";

    @FindBy(css = ".modal-header-text")
    private WebElement title;
    @FindBy(css = "[name='name']")
    private WebElement nameField;
    @FindBy(css = "[name='description']")
    private WebElement descriptionField;
    @FindBy(css = "output[for$='category-name']")
    private WebElement nameCounter;
    @FindBy(css = "output[for$='category-description']")
    private WebElement descriptionCounter;
    @FindBy(css = ".team-category-modal-save-button")
    private WebElement saveButton;

    protected BaseTeamCategoryModal(WebDriver driver, By modalLocator) {
        super(driver, modalLocator);
    }

    protected abstract T self();

    public String getTitle() {
        return title.getText();
    }

    public boolean isDisplayed() {
        return rootElement.isDisplayed();
    }

    public String getName() {
        return nameField.getDomProperty("value");
    }

    @Step("Enter Team category name: {name}")
    public T setName(String name) {
        fillInput(nameField, name);
        return self();
    }

    public String getDescription() {
        return descriptionField.getDomProperty("value");
    }

    @Step("Enter Team category description")
    public T setDescription(String description) {
        fillInput(descriptionField, description);
        return self();
    }

    @Step("Leave the category description field to trigger validation")
    public T blurDescription() {
        blur(descriptionField);
        return self();
    }

    public String getNameCounter() {
        return nameCounter.getText();
    }

    public String getDescriptionCounter() {
        return descriptionCounter.getText();
    }

    public boolean isSaveEnabled() {
        return saveButton.isEnabled();
    }

    protected void clickSaveButton() {
        click(saveButton);
    }

    public abstract TeamPageAdmin clickSave();

    private WebElement field(String label) {
        return switch (label) {
            case "Назва" -> nameField;
            case "Опис" -> descriptionField;
            default -> throw new IllegalArgumentException("Unknown Team category field: " + label);
        };
    }

    @Step("Read validation errors for Team category field: {label}")
    public List<String> getFieldErrors(String label) {
        return field(label).findElement(INPUT_GROUP).findElements(ERROR).stream()
                .filter(WebElement::isDisplayed).map(WebElement::getText)
                .map(String::trim).filter(text -> !text.isEmpty()).toList();
    }

    @Step("Append text to Team category field: {label}")
    public T appendToField(String label, String text) {
        Objects.requireNonNull(text, "Text must not be null");
        WebElement input = field(label);
        click(input);
        input.sendKeys(Keys.END);
        for (int offset = 0; offset < text.length(); ) {
            int end = offset + Character.charCount(text.codePointAt(offset));
            input.sendKeys(text.substring(offset, end));
            wait.until(d -> Objects.equals(input.getDomProperty("value"), input.getDomProperty("defaultValue")));
            offset = end;
        }
        return self();
    }

    @Step("Remove the last character from Team category field: {label}")
    public T removeLastCharacter(String label) {
        WebElement input = field(label);
        click(input);
        input.sendKeys(Keys.END, Keys.BACK_SPACE);
        wait.until(d -> Objects.equals(input.getDomProperty("value"), input.getDomProperty("defaultValue")));
        return self();
    }

    @Step("Clear Team category field: {label}")
    public T clearField(String label) {
        WebElement input = field(label);
        clearInput(input);
        wait.until(d -> input.getDomProperty("value").isEmpty());
        return self();
    }

    private String clearButtonClasses(String label) {
        return field(label).findElement(INPUT_GROUP).findElement(CLEAR_BUTTON).getDomAttribute("class");
    }

    @Step("Click inside the '{label}' field")
    public T focusField(String label) {
        click(field(label));
        return self();
    }

    @Step("Leave the '{label}' field")
    public T blurField(String label) {
        field(label).sendKeys(Keys.TAB);
        return self();
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
    public T clickClearIcon(String label) {
        click(field(label).findElement(INPUT_GROUP).findElement(CLEAR_BUTTON));
        wait.until(d -> getFieldValue(label).isEmpty());
        return self();
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

    @Step("Close the Team category modal with the X icon")
    public ConfirmationModal requestCloseWithConfirmation() {
        requestClose();
        return ConfirmationModal.waitFor(wait);
    }

    public boolean isConfirmationClosed() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(CONFIRMATION));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Click the X icon of the Team category modal")
    public T clickCloseIcon() {
        requestClose();
        return self();
    }

    @Step("Close Team category modal")
    public TeamPageAdmin close() {
        requestClose();
        wait.until(ExpectedConditions.stalenessOf(rootElement));
        return new TeamPageAdmin(driver);
    }
}
