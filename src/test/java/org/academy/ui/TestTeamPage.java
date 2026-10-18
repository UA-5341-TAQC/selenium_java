package org.academy.ui;

import org.academy.ui.components.TeamCategoryComponent;
import org.academy.ui.components.TeamMemberCardComponent;
import org.academy.ui.pages.TeamPage;
import org.academy.ui.testrunners.BaseUiTestRunner;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URI;
import java.time.Duration;
import java.util.List;

public class TestTeamPage extends BaseUiTestRunner {

    @Test(description = "Verify the public Team page objects can read categories and member cards")
    public void testTeamPagePom() {
        String teamUrl = URI.create(testEnvProvider.getBaseUrl()).resolve("/team").toString();
        driver.get(teamUrl);
        TeamPage teamPage = new TeamPage(driver);

        // This smoke test requires at least one published category with a member.
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(webDriver -> !teamPage.getCategories().isEmpty());

        List<TeamCategoryComponent> categories = teamPage.getCategories();
        List<String> categoryNames = teamPage.getCategoryNames();
        Assert.assertEquals(categoryNames.size(), categories.size(),
                "Each category should have a readable title");

//        for (TeamCategoryComponent category : categories) System.out.println(category.getTitle());

        for (String categoryName : categoryNames) {
            Assert.assertFalse(categoryName.isBlank(), "Category title should not be blank");
            Assert.assertTrue(teamPage.isCategoryDisplayed(categoryName),
                    "Category should be displayed: " + categoryName);
        }

        TeamCategoryComponent categoryWithMembers = null;
        for (TeamCategoryComponent category : categories) {
            if (!category.getMembers().isEmpty()) {
                categoryWithMembers = category;
                break;
            }
        }
        Assert.assertNotNull(categoryWithMembers,
                "Test data must include a category with at least one published member");

        String categoryName = categoryWithMembers.getTitle();
        TeamCategoryComponent category = teamPage.getCategoryByName(categoryName);
        Assert.assertFalse(category.getDescription().isBlank(),
                "The selected category should have a description");

        TeamMemberCardComponent member = category.getMembers().getFirst();
        String memberName = member.getName();
        Assert.assertFalse(memberName.isBlank(), "Member name should not be blank");
        Assert.assertTrue(member.isDisplayed(), "Member card should be displayed");
        Assert.assertFalse(member.getRole().isBlank(), "Member role should not be blank");
        Assert.assertNotNull(member.getPhotoUrl(),
                "Photo URL should be readable; an empty string is allowed for a placeholder");
        Assert.assertTrue(category.hasMember(memberName),
                "Member should be found inside the selected category");
        Assert.assertEquals(category.getMemberByName(memberName).getRole(), member.getRole(),
                "Member lookup should return the same role");

        teamPage.refresh();
        wait.until(webDriver -> teamPage.isCategoryDisplayed(categoryName));
        Assert.assertEquals(URI.create(teamPage.getCurrentUrl()).getPath(), "/team",
                "Refresh should keep the public Team page open");
        Assert.assertTrue(teamPage.getCategoryByName(categoryName).hasMember(memberName),
                "Published member should remain visible after refresh");
    }
}
