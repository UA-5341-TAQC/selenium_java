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


public class TeamTranslateCategoryModal extends BaseModal {
    private static final By MODAL = By.xpath(
            "//div[@class='modal-container'][.//form[@id='translate-category-form']]");

    @FindBy(css = ".modal-header-text")
    private WebElement title;
    @FindBy(id = "name")
    private WebElement nameField;
    @FindBy(id = "description")
    private WebElement descriptionField;
    @FindBy(id = "name-character-count")
    private WebElement nameCounter;
    @FindBy(id = "description-character-count")
    private WebElement descriptionCounter;
    @FindBy(css = ".modal-footer .btn-primary")
    private WebElement saveButton;

    // Знаходить відкриту модалку та ініціалізує її поля.
    public TeamTranslateCategoryModal(WebDriver driver) {
        super(driver, MODAL);
    }

    // Повертає назву вибраної мови перекладу.
    public String getLanguage() {
        return rootElement.findElement(By.cssSelector(".language-select .select-head")).getText();
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

    @Step("Enter translated Team category name: {name}")
    public TeamTranslateCategoryModal setName(String name) {
        nameField.click();
        nameField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        nameField.sendKeys(name);
        return this;
    }

    // Повертає поточне значення опису.
    public String getDescription() {
        return descriptionField.getDomProperty("value");
    }

    @Step("Enter Team category translation description")
    public TeamTranslateCategoryModal setDescription(String description) {
        descriptionField.click();
        descriptionField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        descriptionField.sendKeys(description);
        return this;
    }

    @Step("Leave the translation description field to trigger validation")
    public TeamTranslateCategoryModal blurDescription() {
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

    @Step("Save Team category translation")
    public TeamPageAdmin clickSave() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
        wait.until(webDriver -> ExpectedConditions.stalenessOf(rootElement).apply(webDriver)
                || webDriver.findElements(By.cssSelector(".modal-container")).size() > 1);
        if (!ExpectedConditions.stalenessOf(rootElement).apply(driver)) {
            var modals = driver.findElements(By.cssSelector(".modal-container"));
            WebElement confirmation = modals.getLast();
            WebElement yes = confirmation.findElement(By.xpath(".//button[normalize-space()='Так']"));
            wait.until(ExpectedConditions.elementToBeClickable(yes)).click();
        }
        wait.until(ExpectedConditions.stalenessOf(rootElement));
        return new TeamPageAdmin(driver);
    }


    @Step("Select category to translate: {name}")
    public TeamTranslateCategoryModal selectCategory(String name) {
        WebElement select = rootElement.findElement(By.id("category-select"));
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