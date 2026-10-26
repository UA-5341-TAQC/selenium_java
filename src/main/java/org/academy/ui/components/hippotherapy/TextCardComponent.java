package org.academy.ui.components.hippotherapy;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TextCardComponent extends BaseComponent {

    @FindBy(css = "p:nth-child(1)")
    private WebElement firstParagraph;

    @FindBy(css = "p:nth-child(2)")
    private WebElement secondParagraph;

    public TextCardComponent(WebElement element) {
        super(element);
    }

    @Step("Get TextCard title")
    public String getTitleText() {
        return getText(firstParagraph);
    }

    @Step("Get TextCard body text")
    public String getBodyText() {
        return getText(secondParagraph);
    }

    @Step("Get TextCard body text")
    public String getText() {
        return getBodyText();
    }

    @Step("Get all paragraphs count")
    public int getParagraphsCount() {
        return rootElement.findElements(By.cssSelector("p")).size();
    }

    @Step("Get full text of TextCard")
    public String getFullText() {
        return getText(rootElement);
    }
}
