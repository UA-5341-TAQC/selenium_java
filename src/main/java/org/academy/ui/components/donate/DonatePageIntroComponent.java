package org.academy.ui.components.donate;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DonatePageIntroComponent extends BaseComponent {

    @FindBy(css = "h1")
    private WebElement heading;

    @FindBy(css = "p")
    private WebElement description;

    public DonatePageIntroComponent(WebElement element) {
        super(element);
    }

    @Step("Get Donate intro heading")
    public String getHeadingText() {
        return getText(heading);
    }

    @Step("Get Donate intro description")
    public String getDescriptionText() {
        return getText(description);
    }
}
