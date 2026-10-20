package org.academy.ui.modals.admin.team;

import io.qameta.allure.Step;
import org.academy.ui.pages.Admin.TeamPageAdmin;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public class TeamTranslateMemberModal extends TeamMemberFieldsModal {
    @FindBy(css = ".modal-footer .btn-primary") private WebElement saveButton;
    @FindBy(css = ".language-select .select-head") private WebElement language;
    public TeamTranslateMemberModal(WebDriver driver) { super(driver, "translate-member-form"); }
    public String getLanguage() { return language.getText(); }
    @Step("Enter translated member name: {name}")
    public TeamTranslateMemberModal setName(String name) { enter(nameField, name); return this; }
    @Step("Enter translated member description")
    public TeamTranslateMemberModal setDescription(String description) { enter(descriptionField, description); return this; }
    @Step("Leave translated member description to trigger validation")
    public TeamTranslateMemberModal blurDescription() { descriptionField.sendKeys(Keys.TAB); return this; }
    @Step("Save member translation and confirm")
    public TeamPageAdmin clickSave() { return submit(saveButton); }
}
