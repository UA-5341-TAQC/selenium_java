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


public class TeamAddCategoryModal extends BaseModal {
    private static final By MODAL = By.xpath(
            "//div[@class='modal-container'][.//form[@id='add-team-category-form']]");

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
}
