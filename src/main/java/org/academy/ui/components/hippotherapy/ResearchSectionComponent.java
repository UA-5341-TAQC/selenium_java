package org.academy.ui.components.hippotherapy;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ResearchSectionComponent extends BaseComponent {

    @FindBy(css = "h2[class*='ResearchSection_research-title'], [class*='ResearchSection_research-title']")
    private WebElement title;

    @FindBy(css = "div[class*='ResearchSection_research-description'] p, [class*='ResearchSection_research-description'] p")
    private WebElement description;

    @FindBy(css = "ul li a[class*='ResearchSection_research'], a[class*='ResearchSection_research']")
    private List<WebElement> researchLinks;

    public ResearchSectionComponent(WebElement element) {
        super(element);
    }

    @Step("Check if Research section title is displayed")
    public boolean isTitleDisplayed() {
        return isDisplayed(title);
    }

    @Step("Get Research section title")
    public String getTitleText() {
        return getText(title);
    }

    @Step("Get Research section description")
    public String getDescriptionText() {
        return getText(description);
    }

    @Step("Get total research links count")
    public int getResearchCount() {
        return researchLinks.size();
    }

    @Step("Get research paper titles/citations")
    public List<String> getResearchTitles() {
        return getTexts(researchLinks);
    }

    @Step("Get research paper external URLs")
    public List<String> getResearchUrls() {
        return researchLinks.stream()
                .map(el -> getDomAttribute(el, "href"))
                .toList();
    }

    @Step("Click research paper link at index: {index}")
    public void clickResearchLink(int index) {
        if (index < 0 || index >= researchLinks.size()) {
            throw new IndexOutOfBoundsException("Invalid research link index: " + index + ", total links: " + researchLinks.size());
        }
        click(researchLinks.get(index));
    }
}
