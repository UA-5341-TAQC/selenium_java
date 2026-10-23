package org.academy.ui.components.hippotherapy;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SloganSectionComponent extends BaseComponent {

    @FindBy(css = "[class*='SloganSection_yellow']")
    private List<WebElement> yellowHighlights;

    @FindBy(css = "[class*='SloganSection_blue']")
    private List<WebElement> blueHighlights;

    public SloganSectionComponent(WebElement element) {
        super(element);
    }

    @Step("Get slogan text")
    public String getSloganText() {
        return getText(rootElement);
    }

    @Step("Check if slogan is displayed")
    public boolean isDisplayed() {
        return isVisible(rootElement);
    }

    @Step("Check if yellow highlighted words exist")
    public boolean hasYellowHighlights() {
        return !yellowHighlights.isEmpty();
    }

    @Step("Check if blue highlighted words exist")
    public boolean hasBlueHighlights() {
        return !blueHighlights.isEmpty();
    }
}
