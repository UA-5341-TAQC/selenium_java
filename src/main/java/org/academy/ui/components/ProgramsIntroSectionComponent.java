package org.academy.ui.components;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import io.qameta.allure.Step;

import java.util.List;

public class ProgramsIntroSectionComponent extends BaseComponent{
    @FindBy(css = "h1")
    private WebElement title;

    @FindBy(css = "div[class*='IntroSection_additional-info'] p")
    private List<WebElement> description;

    public ProgramsIntroSectionComponent(WebElement element) {
        super(element);
    }

    private String normalize(String text){
        return text.replaceAll("\\s+", " ").trim();
    }
    @Step("Get Programs intro title")
    public String getTitleText (){
        return normalize(getText(title));
    }
    @Step("Get Programs intro descriptions")
    public List<String> getDescriptionTexts(){
        return waitUntilAllVisible(description).stream()
                .map(element -> normalize(element.getText()))
                .toList();
    }
}
