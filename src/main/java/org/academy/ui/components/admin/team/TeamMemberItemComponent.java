package org.academy.ui.components.admin.team;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.academy.ui.modals.admin.team.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TeamMemberItemComponent extends BaseComponent {
    @FindBy(css = ".members-profile-data > p") private WebElement name;
    @FindBy(css = ".members-position > p") private WebElement description;
    @FindBy(css = ".program-info-status") private WebElement status;
    @FindBy(css = "button[aria-label='Редагувати учасника']") private WebElement edit;
    @FindBy(css = "button[aria-label='Додати переклад для учасника']") private WebElement translate;
    @FindBy(css = "button[aria-label='Видалити учасника']") private WebElement delete;
    public TeamMemberItemComponent(WebElement element) { super(element); }
    public String getName() { return name.getText(); }
    public String getDescription() { return description.getText(); }
    public String getStatus() { return status.getText(); }
    @Step("Open the selected member for editing")
    public TeamMemberModal clickEdit() {
        wait.until(ExpectedConditions.elementToBeClickable(edit)).click();
        return new TeamMemberModal(driver);
    }
    @Step("Open translation of the selected member")
    public TeamTranslateMemberModal clickTranslate() {
        wait.until(ExpectedConditions.elementToBeClickable(translate)).click();
        return new TeamTranslateMemberModal(driver);
    }
    @Step("Open deletion confirmation for the selected member")
    public TeamDeleteMemberModal clickDelete() {
        wait.until(ExpectedConditions.elementToBeClickable(delete)).click();
        return new TeamDeleteMemberModal(driver);
    }
}
