package org.academy.ui.modals.admin.team;

import io.qameta.allure.Step;
import org.academy.ui.modals.BaseModal;
import org.academy.ui.pages.Admin.TeamPageAdmin;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class TeamEditCategoryModal extends BaseModal {
    private static final By MODAL = By.xpath(
            "//div[@class='modal-container'][.//form[@id='edit-team-category-form']]");

    @FindBy(css = ".modal-header-text")
    private WebElement title;
    @FindBy(id = "edit-category-name")
    private WebElement nameField;
    @FindBy(id = "edit-category-description")
    private WebElement descriptionField;
    @FindBy(id = "edit-category-name-character-count")
    private WebElement nameCounter;
    @FindBy(id = "edit-category-description-character-count")
    private WebElement descriptionCounter;
    @FindBy(css = ".team-category-modal-save-button")
    private WebElement saveButton;

    // Знаходить відкриту модалку та ініціалізує її поля.
    public TeamEditCategoryModal(WebDriver driver) {
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
    public TeamEditCategoryModal setName(String name) {
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
    public TeamEditCategoryModal setDescription(String description) {
        descriptionField.click();
        descriptionField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        descriptionField.sendKeys(description);
        return this;
    }

    @Step("Leave the category description field to trigger validation")
    public TeamEditCategoryModal blurDescription() {
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

    @Step("Save Team category changes")
    public TeamPageAdmin clickSave() {
        // 1. Натискаємо «Зберегти».
        wait.until(
                ExpectedConditions.elementToBeClickable(saveButton)
        ).click();
        // 2–3. Чекаємо кнопку «Так» у модалці підтвердження й натискаємо.
        By confirmButton = By.cssSelector(".confirmation-modal button.btn-primary");
        wait.until(
                ExpectedConditions.elementToBeClickable(confirmButton)
        ).click();
        // 4. Чекаємо, поки всі модалки стануть невидимими або зникнуть із DOM.
        wait.until(ExpectedConditions.invisibilityOfAllElements(
                driver.findElements(By.cssSelector(".modal-container"))
        ));
        // 5. Повертаємо об’єкт сторінки Team.
        return new TeamPageAdmin(driver);
    }


    @Step("Select category to edit: {name}")
    public TeamEditCategoryModal selectCategory(String name) {
        WebElement select = rootElement.findElement(By.id("edit-category-select"));
        wait.until(ExpectedConditions.elementToBeClickable(select)).click();
        wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(rootElement, By.cssSelector("[role='option']")));
        for (WebElement option : rootElement.findElements(By.cssSelector("[role='option']"))) {
            if (option.getText().equals(name)) {
                wait.until(ExpectedConditions.elementToBeClickable(option)).click();
                wait.until(webDriver -> select.getText().equals(name));
                return this;
            }
        }
        throw new org.openqa.selenium.NoSuchElementException("Category not found: " + name);
    }
}