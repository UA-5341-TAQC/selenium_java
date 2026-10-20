package org.academy.ui.components.admin.programs;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class ProgramInfoTooltipComponent extends BaseComponent {

    @FindBy(css = ".program-actions-tooltip > span")
    private List<WebElement> categoryNames;

    public ProgramInfoTooltipComponent(WebElement rootElement) {
        super(rootElement);
    }

    @Step("Read program categories from information tooltip")
    public List<String> getCategoryNames() {
        waitUntilVisible(rootElement);
        List<String> names = new ArrayList<>();
        for (WebElement category : categoryNames) {
            names.add(category.getText());
        }
        return names;
    }
}
