package org.academy.ui.modals.admin.team;

import org.academy.ui.components.BaseComponent;
import org.academy.ui.pages.Admin.TeamPageAdmin;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/** Fields shared by the member form and its translation form. */
public abstract class TeamMemberFieldsModal extends BaseComponent {
    @FindBy(id = "fullName") protected WebElement nameField;
    @FindBy(id = "description") protected WebElement descriptionField;
    @FindBy(css = "button[aria-label='Close modal']") private WebElement closeButton;

    protected TeamMemberFieldsModal(WebDriver driver, String formClass) {
        super(new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//form[contains(concat(' ', normalize-space(@class), ' '), ' " + formClass
                                + " ')]/ancestor::div[contains(concat(' ', normalize-space(@class), ' '), ' modal-container ')][1]"))));
    }
    protected void enter(WebElement field, String value) {
        wait.until(ExpectedConditions.elementToBeClickable(field)).click();
        field.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        field.sendKeys(value);
    }
    public String getName() { return nameField.getDomProperty("value"); }
    public String getDescription() { return descriptionField.getDomProperty("value"); }
    public String getTitle() { return rootElement.findElement(By.cssSelector(".modal-header-text")).getText(); }

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
    @io.qameta.allure.Step("Close the member dialog without changes")
    public void clickClose() {
        wait.until(ExpectedConditions.elementToBeClickable(closeButton)).click();
        wait.until(ExpectedConditions.stalenessOf(rootElement));
    }
}
