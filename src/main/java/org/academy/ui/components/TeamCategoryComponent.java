package org.academy.ui.components;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class TeamCategoryComponent extends BaseComponent {
    @FindBy(css = ".team-category-description h2")
    private WebElement title;

    @FindBy(css = ".team-category-description p")
    private WebElement description;

    @FindBy(css = ".team-member")
    private List<WebElement> memberCards;

    public TeamCategoryComponent(WebElement rootElement) {
        super(rootElement);
    }

    public String getTitle() {
        return title.getText();
    }

    public String getDescription() {
        return description.getText();
    }

    public boolean isDisplayed() {
        return rootElement.isDisplayed();
    }

    public List<TeamMemberCardComponent> getMembers() {
        List<TeamMemberCardComponent> members = new ArrayList<>();

        for (WebElement card : memberCards) {
            members.add(new TeamMemberCardComponent(card));
        }

        return members;
    }

    public TeamMemberCardComponent getMemberByName(String memberName) {
        for (TeamMemberCardComponent member : getMembers()) {
            if (member.getName().equals(memberName)) {
                return member;
            }
        }

        throw new NoSuchElementException(
                "Team member not found in category '"
                        + getTitle() + "': " + memberName
        );
    }

    public boolean hasMember(String memberName) {
        for (TeamMemberCardComponent member : getMembers()) {
            if (member.getName().equals(memberName) && member.isDisplayed()) {
                return true;
            }
        }

        return false;
    }
}
