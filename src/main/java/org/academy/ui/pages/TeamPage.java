package org.academy.ui.pages;

import io.qameta.allure.Step;
import org.academy.ui.components.TeamCategoryComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class TeamPage extends BasePage {
    private static final By TEAM_CONTAINER = By.cssSelector(".team-page-container");
    private static final By CATEGORIES = By.cssSelector(".team_info > .members-grid");

    public TeamPage(WebDriver driver) {
        super(driver);
        waitUntilLoaded();
    }

    @Step("Wait for the public Team page to load")
    public TeamPage waitUntilLoaded() {
        wait.until(ExpectedConditions.presenceOfElementLocated(TEAM_CONTAINER));
        return this;
    }

    @Step("Get categories on the public Team page")
    public List<TeamCategoryComponent> getCategories() {
        List<WebElement> elements = driver.findElements(CATEGORIES);
        List<TeamCategoryComponent> categories = new ArrayList<>();

        for (WebElement element : elements) {
            TeamCategoryComponent category = new TeamCategoryComponent(element);
            categories.add(category);
        }
        return categories;
    }

    public List<String> getCategoryNames() {
        List<String> categoryNames = new ArrayList<>();

        for (TeamCategoryComponent category : getCategories()) {
            categoryNames.add(category.getTitle());
        }
        return categoryNames;
    }

    @Step("Find Team category: {categoryName}")
    public TeamCategoryComponent getCategoryByName(String categoryName) {
        for (TeamCategoryComponent category : getCategories()) {
            if (category.getTitle().equals(categoryName)) {
                return category;
            }
        }
        throw new NoSuchElementException(
                "Team category not found: " + categoryName
        );
    }

    @Step("Check whether Team category is displayed: {categoryName}")
    public boolean isCategoryDisplayed(String categoryName) {
        for (TeamCategoryComponent category : getCategories()) {
            if (category.getTitle().equals(categoryName) && category.isDisplayed()) {
                return true;
            }
        }
        return false;
    }

    @Step("Refresh the public Team page")
    public TeamPage refresh() {
        driver.navigate().refresh();
        return waitUntilLoaded();
    }
}
