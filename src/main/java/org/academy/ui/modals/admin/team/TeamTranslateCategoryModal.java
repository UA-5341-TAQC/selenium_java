package org.academy.ui.modals.admin.team;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.academy.ui.pages.Admin.TeamPageAdmin;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TeamTranslateCategoryModal extends BaseComponent {
    private static final By MODAL = By.xpath(
            "//form[@id='translate-category-form']/ancestor::div[contains(concat(' ', normalize-space(@class), ' '), ' modal-container ')][1]");

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
    @FindBy(css = "button[aria-label='Close modal']")
    private WebElement closeButton;

    public TeamTranslateCategoryModal(WebDriver driver) {
        super(new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(MODAL)));
    }

    public String getLanguage() { return rootElement.findElement(By.cssSelector(".language-select .select-head")).getText(); }
    public String getTitle() {
        return title.getText();
    }

    public boolean isDisplayed() {
        return rootElement.isDisplayed();
    }

    @Step("Enter translated Team category name: {name}")
    public TeamTranslateCategoryModal setName(String name) {
        nameField.click();
        nameField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        nameField.sendKeys(name);
        return this;
    }

    public String getName() {
        return nameField.getDomProperty("value");
    }

    @Step("Enter Team category translation description")
    public TeamTranslateCategoryModal setDescription(String description) {
        descriptionField.click();
        descriptionField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        descriptionField.sendKeys(description);
        return this;
    }

    public String getDescription() {
        return descriptionField.getDomProperty("value");
    }

    @Step("Leave the translation description field to trigger validation")
    public TeamTranslateCategoryModal blurDescription() {
        descriptionField.sendKeys(Keys.TAB);
        return this;
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

    // If fields were changed, closing may open a separate discard confirmation.
    @Step("Close the Team category translation dialog")
    public void clickClose() {
        wait.until(ExpectedConditions.elementToBeClickable(closeButton)).click();
    }

    public void waitUntilClosed() {
        wait.until(ExpectedConditions.stalenessOf(rootElement));
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