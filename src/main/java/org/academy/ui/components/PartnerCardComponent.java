package org.academy.ui.components;

import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PartnerCardComponent extends BaseComponent {

    @FindBy(css = "[class*='PartnersSection_partner-logo']")
    private WebElement logo;

    @FindBy(css = "[class*='PartnersSection_partner-name']")
    private WebElement description;

    public PartnerCardComponent(WebElement rootElement) {
        super(rootElement);
    }

    public String getDescriptionText() {
        return getText(description);
    }

    public boolean isLogoLoaded() {
        scrollIntoView(logo);
        WebElement image = waitUntilVisible(logo);
        wait.until(webDriver -> Boolean.TRUE.equals(js.executeScript(
                "return arguments[0].complete;", image)));
        return Boolean.TRUE.equals(js.executeScript("return arguments[0].naturalWidth > 0;", image));
    }

    public Rectangle getBounds() {
        return waitUntilVisible(rootElement).getRect();
    }
}
