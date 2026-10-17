package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class MainProgramsSectionComponent extends BaseComponent {

    @FindBy(css = "h2[class*='MainProgramsSection_heading'], [class*='MainProgramsSection_heading']")
    private WebElement heading;

    @FindBy(css = "[class*='ProgramCardProgramsPage_root'], [class*='ProgramCard_root'], [class*='swiper-slide'] [class*='ProgramCard'], [class*='ProgramCard']")
    private List<WebElement> programCards;

    public MainProgramsSectionComponent(WebElement element) {
        super(element);
    }

    @Step("Get Programs section heading")
    public String getHeadingText() {
        wait.until(ExpectedConditions.visibilityOf(heading));
        return heading.getText().trim();
    }

    @Step("Get program cards count")
    public int getProgramCardsCount() {
        return programCards.size();
    }
}
