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

public class TeamAddCategoryModal extends BaseComponent {
    private static final By MODAL = By.xpath(
            "//form[@id='add-team-category-form']/ancestor::div[contains(concat(' ', normalize-space(@class), ' '), ' modal-container ')][1]");

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
    @FindBy(css = "button[aria-label='Close modal']")
    private WebElement closeButton;

    public TeamAddCategoryModal(WebDriver driver) {
        super(new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(MODAL)));
    }

    public String getTitle() {
        return title.getText();
    }

    public boolean isDisplayed() {
        return rootElement.isDisplayed();
    }

    @Step("Enter Team category name: {name}")
    public TeamAddCategoryModal setName(String name) {
        nameField.click();
        nameField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        nameField.sendKeys(name);
        return this;
    }

    public String getName() {
        return nameField.getDomProperty("value");
    }

    @Step("Enter Team category description")
    public TeamAddCategoryModal setDescription(String description) {
        descriptionField.click();
        descriptionField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        descriptionField.sendKeys(description);
        return this;
    }

    public String getDescription() {
        return descriptionField.getDomProperty("value");
    }

    @Step("Leave the category description field to trigger validation")
    public TeamAddCategoryModal blurDescription() {
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

    @Step("Save a new Team category")
    public TeamPageAdmin clickSave() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
        wait.until(ExpectedConditions.stalenessOf(rootElement));
        return new TeamPageAdmin(driver);
    }

    // If fields were changed, closing may open a separate discard confirmation.
    @Step("Close the add Team category dialog")
    public void clickClose() {
        wait.until(ExpectedConditions.elementToBeClickable(closeButton)).click();
    }

    public void waitUntilClosed() {
        wait.until(ExpectedConditions.stalenessOf(rootElement));
    }
}
