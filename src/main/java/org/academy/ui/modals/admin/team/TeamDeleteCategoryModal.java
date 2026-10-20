package org.academy.ui.modals.admin.team;

import io.qameta.allure.Step;

import org.academy.ui.components.BaseComponent;
import org.academy.ui.pages.Admin.TeamPageAdmin;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class TeamDeleteCategoryModal extends BaseComponent {
    private static final By MODAL = By.xpath("//*[@id='delete-category-select']/ancestor::div[contains(concat(' ', normalize-space(@class), ' '), ' modal-container ')][1]");
    @FindBy(id = "delete-category-select")
    private WebElement category;
    @FindBy(css = ".modal-footer .btn-primary")
    private WebElement deleteButton;
    @FindBy(css = ".modal-footer .btn-secondary")
    private WebElement cancelButton;

    public TeamDeleteCategoryModal(WebDriver driver) {
        super(new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(MODAL)));
    }
    public String getCategoryName() { return category.getText(); }
    @Step("Check whether Team category deletion is enabled")
    public boolean isDeleteEnabled() { return deleteButton.isEnabled(); }
    public String getHint() {
        var hints = rootElement.findElements(By.cssSelector(".hint-box"));
        return hints.isEmpty() ? "" : hints.getFirst().getText();
    }
    @Step("Delete the selected Team category and confirm")
    public TeamPageAdmin clickDelete() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
        By confirmButton = By.xpath(
                "//button[contains(@class, 'btn-primary') and normalize-space()='Так']"
        );
        wait.until(ExpectedConditions.elementToBeClickable(confirmButton)).click();
        wait.until(ExpectedConditions.stalenessOf(rootElement));
        return new TeamPageAdmin(driver);
    }
    @Step("Cancel Team category deletion")
    public TeamPageAdmin clickCancel() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton)).click();
        wait.until(ExpectedConditions.stalenessOf(rootElement));
        return new TeamPageAdmin(driver);
    }
    @Step("Select category to delete: {name}")
    public TeamDeleteCategoryModal selectCategory(String name) {
        WebElement select = rootElement.findElement(By.id("delete-category-select"));
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