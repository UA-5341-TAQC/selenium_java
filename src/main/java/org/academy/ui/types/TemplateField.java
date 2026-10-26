package org.academy.ui.types;
import io.qameta.allure.Step;
import org.openqa.selenium.By;


public enum TemplateField {

    SINGLE_IMAGE_RIGHT(By.cssSelector("[class*='SingleImageRight_']"), "SingleImageRight_"),
    SINGLE_TITLE_DESCRIPTION_AUTHOR_PAIRS(By.cssSelector("[class*='SingleTitleDescriptionAuthorPairs_']"), "SingleTitleDescriptionAuthorPairs_"),
    SINGLE_TITLE_QUINTUPLE_DESCRIPTION(By.cssSelector("[class*='SingleTitleQuintupleDescription_']"), "SingleImageTop_"),
    TITLE_DESCRIPTION_CARDS_SECTION(By.cssSelector("[class*='TitleDescriptionCardsSection_']"), "TitleDescriptionCardsSection_"),
    VIEW_TITLE_DESCRIPTION_SECTION(By.cssSelector("[class*='ViewTitleDescriptionSection_']"), "ViewTitleDescriptionSection_");

    private final By locator;
    private final String classIdentifier;

    TemplateField (By locator, String classIdentifier) {
        this.locator = locator;
        this.classIdentifier = classIdentifier;
    }

    @Step("Return locator")
    public By getLocator() {
        return locator;
    }

    @Step("Return class identifier")
    public String getClassIdentifier() {
        return classIdentifier;
    }

}

