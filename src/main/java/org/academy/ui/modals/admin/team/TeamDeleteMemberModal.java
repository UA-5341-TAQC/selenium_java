package org.academy.ui.modals.admin.team;

import io.qameta.allure.Step;
import org.academy.ui.modals.BaseModal;
import org.academy.ui.pages.Admin.TeamPageAdmin;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.*;

public class TeamDeleteMemberModal extends BaseModal {
    private static final By MODAL = By.xpath("//div[contains(concat(' ', normalize-space(@class), ' '), ' modal-container ')][.//span[@class='modal-header-text' and normalize-space()='Видалити члена команди?']]");
    @FindBy(xpath = ".//button[normalize-space()='Так']") private WebElement yes;
    @FindBy(xpath = ".//button[normalize-space()='Ні']") private WebElement no;
    // Знаходить відкриту модалку та ініціалізує її поля.
    public TeamDeleteMemberModal(WebDriver driver) {
        super(driver, MODAL);
    }
    @Step("Confirm deletion of the selected member")
    public TeamPageAdmin clickConfirm() {
        wait.until(ExpectedConditions.elementToBeClickable(yes)).click();
        wait.until(ExpectedConditions.stalenessOf(rootElement));
        return new TeamPageAdmin(driver);
    }
    @Step("Cancel member deletion")
    public TeamPageAdmin clickCancel() {
        wait.until(ExpectedConditions.elementToBeClickable(no)).click();
        wait.until(ExpectedConditions.stalenessOf(rootElement));
        return new TeamPageAdmin(driver);
    }
}
