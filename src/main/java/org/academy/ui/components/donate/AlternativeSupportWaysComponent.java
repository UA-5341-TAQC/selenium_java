package org.academy.ui.components.donate;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.academy.ui.components.CopyTextButtonComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class AlternativeSupportWaysComponent extends BaseComponent {

    @FindBy(css = "h2")
    private WebElement title;

    @FindBy(css = "div.labelContainer")
    private List<WebElement> supportOptionItems;

    @FindBy(css = "div.labelContainer h3")
    private List<WebElement> optionNames;

    @FindBy(css = "button.copyTextButton")
    private List<WebElement> copyButtons;

    public AlternativeSupportWaysComponent(WebElement element) {
        super(element);
    }

    @Step("Get Alternative Support section title")
    public String getTitleText() {
        return getText(title);
    }

    @Step("Get support options count")
    public int getSupportOptionsCount() {
        return supportOptionItems.size();
    }

    @Step("Get support option names")
    public List<String> getSupportOptionNames() {
        return getTexts(optionNames);
    }

    @Step("Get list of copy button components")
    public List<CopyTextButtonComponent> getCopyButtons() {
        return copyButtons.stream()
                .map(CopyTextButtonComponent::new)
                .toList();
    }

    @Step("Check if Alternative Support section is displayed")
    public boolean isDisplayed() {
        return isVisible(title);
    }
}
