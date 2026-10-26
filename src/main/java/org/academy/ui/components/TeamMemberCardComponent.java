package org.academy.ui.components;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class TeamMemberCardComponent extends BaseComponent {
    @FindBy(css = ".member-name")
    private WebElement name;

    @FindBy(css = ".member-role")
    private WebElement role;

    // A profile without an uploaded photo has an SVG placeholder instead of an img.
    @FindBy(css = "img.member-photo")
    private List<WebElement> photos;

    public TeamMemberCardComponent(WebElement rootElement) {
        super(rootElement);
    }

    public String getName() {
        return getText(name);
    }

    public String getRole() {
        return getText(role);
    }

    public boolean isDisplayed() {
        return isDisplayed(rootElement);
    }

    /**
     * Returns an empty string when the card shows a placeholder instead of a photo.
     */
    public String getPhotoUrl() {
        return photos.isEmpty() ? "" : photos.getFirst().getDomAttribute("src");
    }
}

