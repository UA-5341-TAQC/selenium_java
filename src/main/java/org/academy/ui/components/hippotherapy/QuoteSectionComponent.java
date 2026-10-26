package org.academy.ui.components.hippotherapy;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class QuoteSectionComponent extends BaseComponent {

    @FindBy(css = "p[data-testid='quote-text'], [class*='QuoteSection_text']")
    private WebElement quoteText;

    @FindBy(css = "img[class*='QuoteSection_image'], img")
    private WebElement image;

    public QuoteSectionComponent(WebElement element) {
        super(element);
    }

    @Step("Get quote text")
    public String getQuoteText() {
        return getText(quoteText);
    }

    @Step("Check if quote image is displayed")
    public boolean isImageDisplayed() {
        return isVisible(image);
    }

    @Step("Get quote image src")
    public String getImageSrc() {
        return getDomAttribute(image, "src");
    }
}
