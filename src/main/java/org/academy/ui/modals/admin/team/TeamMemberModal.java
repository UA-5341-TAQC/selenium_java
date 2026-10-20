package org.academy.ui.modals.admin.team;

import io.qameta.allure.Step;
import org.academy.ui.pages.Admin.TeamPageAdmin;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.nio.file.Path;

/** The same form is used for adding and editing a member. */
public class TeamMemberModal extends TeamMemberFieldsModal {
    private static final By MODAL = By.xpath(
            "//form[contains(concat(' ', normalize-space(@class), ' '), ' team-member-form-main ')]"
            + "/ancestor::div[contains(concat(' ', normalize-space(@class), ' '), ' modal-container ')][1]");
    @FindBy(css = ".singleselect .select-input") private WebElement category;
    @FindBy(css = ".modal-footer .btn-secondary") private WebElement draftButton;
    @FindBy(css = ".modal-footer .btn-primary") private WebElement publishButton;
    @FindBy(css = "input[type='file']") private WebElement photoInput;

    // Знаходить відкриту модалку та ініціалізує її поля.
    public TeamMemberModal(WebDriver driver) { super(driver, MODAL); }
    // Повертає назву категорії, вибраної у формі.
    public String getCategoryName() { return category.getText(); }
    @Step("Select member category: {name}")
    public TeamMemberModal selectCategory(String name) {
        wait.until(ExpectedConditions.elementToBeClickable(category)).click();
        var options = wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(
                rootElement, By.cssSelector("[role='option']")));
        for (WebElement option : options) {
            if (option.getText().equals(name)) {
                wait.until(ExpectedConditions.elementToBeClickable(option)).click();
                wait.until(webDriver -> category.getText().equals(name));
                return this;
            }
        }
        throw new NoSuchElementException("Member category not found: " + name);
    }
    @Step("Enter member name: {name}")
    public TeamMemberModal setName(String name) { enter(nameField, name); return this; }
    @Step("Enter member description")
    public TeamMemberModal setDescription(String description) { enter(descriptionField, description); return this; }
    @Step("Leave member description to trigger validation")
    public TeamMemberModal blurDescription() { descriptionField.sendKeys(Keys.TAB); return this; }
    @Step("Upload member photo: {path}")
    public TeamMemberModal uploadPhoto(Path path) { photoInput.sendKeys(path.toAbsolutePath().toString()); return this; }
    // Перевіряє доступність збереження чернетки.
    public boolean isDraftEnabled() { return draftButton.isEnabled(); }
    // Перевіряє доступність кнопки публікації.
    public boolean isPublishEnabled() { return publishButton.isEnabled(); }
    @Step("Save member as a draft")
    public TeamPageAdmin clickSaveDraft() { return submit(draftButton); }
    @Step("Publish member and confirm")
    public TeamPageAdmin clickPublish() { return submit(publishButton); }
}
