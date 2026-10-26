package org.academy.ui.components.aboutus;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.academy.ui.components.ProgramCardComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class AboutUsProgramsSectionComponent extends BaseComponent {

    @FindBy(css = "div[class*='ProgramCardAboutUsPage_root']")
    private List<WebElement> programCardElements;

    public AboutUsProgramsSectionComponent(WebElement element) {
        super(element);
    }

    @Step("Get program cards count")
    public int getProgramCardsCount() {
        return programCardElements.size();
    }

    @Step("Get list of ProgramCard components")
    public List<ProgramCardComponent> getProgramCards() {
        return programCardElements.stream().map(ProgramCardComponent::new).collect(Collectors.toList());
    }

    @Step("Get ProgramCard component by index: {index}")
    public ProgramCardComponent getProgramCard(int index) {
        if (index < 0 || index >= programCardElements.size()) {
            throw new IndexOutOfBoundsException("Invalid ProgramCard index: " + index + ", total cards: " + programCardElements.size());
        }
        return new ProgramCardComponent(programCardElements.get(index));
    }

    @Step("Get ProgramCard component by name: {cardName}")
    public ProgramCardComponent getProgramCardByName(String cardName) {
        for (WebElement cardEl : programCardElements) {
            ProgramCardComponent card = new ProgramCardComponent(cardEl);
            if (card.getNameText().toLowerCase().contains(cardName.toLowerCase())) {
                return card;
            }
        }
        throw new IllegalArgumentException("ProgramCard not found with name containing: " + cardName);

    }
}