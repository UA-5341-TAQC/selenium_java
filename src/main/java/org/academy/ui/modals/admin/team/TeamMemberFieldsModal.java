package org.academy.ui.modals.admin.team;

import org.academy.ui.modals.BaseModal;
import org.academy.ui.pages.Admin.TeamPageAdmin;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Fields shared by the member form and its translation form.
 */
public abstract class TeamMemberFieldsModal extends BaseModal {
    @FindBy(id = "fullName")
    protected WebElement nameField;
    @FindBy(id = "description")
    protected WebElement descriptionField;

    // Знаходить відкриту модалку та ініціалізує її поля.
    protected TeamMemberFieldsModal(WebDriver driver, By modalLocator) {
        super(driver, modalLocator);
    }

    // Очищає поле та вводить переданий текст.
    protected void enter(WebElement field, String value) {
        wait.until(ExpectedConditions.elementToBeClickable(field)).click();
        field.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        field.sendKeys(value);
    }

    // Повертає поточне значення імені або назви.
    public String getName() {
        return nameField.getDomProperty("value");
    }

    // Повертає поточне значення опису.
    public String getDescription() {
        return descriptionField.getDomProperty("value");
    }

    // Повертає заголовок модалки.
    public String getTitle() {
        return rootElement.findElement(By.cssSelector(".modal-header-text")).getText();
    }

    // Зберігає форму, за потреби підтверджує дію та чекає її закриття.
    protected TeamPageAdmin submit(WebElement button) {
        wait.until(ExpectedConditions.elementToBeClickable(button)).click();
        wait.until(webDriver -> ExpectedConditions.stalenessOf(rootElement).apply(webDriver)
                || webDriver.findElements(By.cssSelector(".modal-container")).size() > 1);
        if (!ExpectedConditions.stalenessOf(rootElement).apply(driver)) {
            WebElement confirmation = driver.findElements(By.cssSelector(".modal-container")).getLast();
            WebElement yes = confirmation.findElement(By.xpath(".//button[normalize-space()='Так']"));
            wait.until(ExpectedConditions.elementToBeClickable(yes)).click();
        }
        wait.until(ExpectedConditions.stalenessOf(rootElement));
        return new TeamPageAdmin(driver);
    }
}
