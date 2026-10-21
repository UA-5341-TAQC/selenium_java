package org.academy.ui.components.hippotherapy;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class EthicalPrinciplesComponent extends BaseComponent {

    @FindBy(css = "div[class*='EthicalPrinciples_heading'] h3, h3")
    private WebElement heading;

    @FindBy(css = "div[class*='EthicalPrinciples_heading'] p, [class*='EthicalPrinciples_heading'] p")
    private WebElement description;

    @FindBy(css = "div[class*='EthicalPrinciples_text'] ul li, ul li")
    private List<WebElement> principlesList;

    @FindBy(css = "img[class*='EthicalPrinciples_image'], img")
    private WebElement image;

    public EthicalPrinciplesComponent(WebElement element) {
        super(element);
    }

    @Step("Get Ethical Principles heading")
    public String getHeadingText() {
        return getText(heading);
    }

    @Step("Get Ethical Principles description")
    public String getDescriptionText() {
        return getText(description);
    }

    @Step("Get list of ethical principles")
    public List<String> getPrinciplesList() {
        return getTexts(principlesList);
    }

    @Step("Get ethical principles count")
    public int getPrinciplesCount() {
        return principlesList.size();
    }

    @Step("Check if Ethical Principles image is displayed")
    public boolean isImageDisplayed() {
        return isVisible(image);
    }

    @Step("Get Ethical Principles image src")
    public String getImageSrc() {
        return getDomAttribute(image, "src");
    }
}
