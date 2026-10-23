package org.academy.ui.pages.Admin;

import org.academy.ui.components.admin.team.TeamCategoryBarComponent;
import org.academy.ui.components.admin.team.TeamMemberItemComponent;
import org.academy.ui.modals.admin.team.TeamMemberModal;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import java.util.ArrayList;
import java.util.List;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TeamPageAdmin extends BaseAdminPage {
    @FindBy(css = "[data-testid='team-page-content'] .category-bar")
    private WebElement categoryBar;

    public TeamPageAdmin(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(categoryBar));
    }

    public TeamCategoryBarComponent getCategoryBar() {
        return new TeamCategoryBarComponent(((WrapsElement) categoryBar).getWrappedElement());
    }

    @Step("Open the add Team member dialog")
    public TeamMemberModal clickAddMember() {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space()='Додати в команду']"))).click();
        return new TeamMemberModal(driver);
    }

    /** Returns the currently loaded member rows; use search to locate a member in a long list. */
    public List<TeamMemberItemComponent> getMembers() {
        List<TeamMemberItemComponent> members = new ArrayList<>();
        for (WebElement element : driver.findElements(By.cssSelector(".members-item"))) {
            members.add(new TeamMemberItemComponent(element));
        }
        return members;
    }

    public TeamMemberItemComponent getMemberByName(String name) {
        for (TeamMemberItemComponent member : getMembers()) {
            if (member.getName().equals(name)) return member;
        }
        throw new NoSuchElementException("Loaded Team member not found: " + name);
    }

    public boolean hasMember(String name) {
        for (TeamMemberItemComponent member : getMembers()) {
            if (member.getName().equals(name)) return true;
        }
        return false;
    }
}
