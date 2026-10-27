package org.academy.ui.components.detailprogram;

import io.qameta.allure.Step;
import org.academy.ui.Base;
import org.academy.ui.components.BaseComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class DetailProgramContainerComponent extends BaseComponent {

    @FindBy(css = "div[class*='SingleImageRight_']")
    private WebElement SingleImageRightComponentElement;

    @FindBy(css = "div[class*='SingleTitleDescriptionAuthorPairs_']")
    private WebElement SingleTitleDescriptionAuthorPairsComponentElement;

    @FindBy(css = "div[class*=''SingleTitleQuintupleDescription_']")
    private WebElement SingleTitleQuintupleDescriptionComponentElement;

    @FindBy(css = "div[class*='TitleDescriptionCardsSection_']")
    private WebElement TitleDescriptionCardSectionComponentElement;

    @FindBy(css = "div[class*='ViewTitleDescriptionSection_']")
    private WebElement ViewTitleDescriptionSectionComponentElement;

    public DetailProgramContainerComponent(WebElement element) {
        super(element);
    }
    @Step("Get SingleImageRight component")
    public SingleImageRightComponent getSingleImageRightComponent(){
        return  new SingleImageRightComponent(waitUntilVisible(SingleImageRightComponentElement));
    }
    @Step("Get SingleTitleDescriptionAuthorPairs component")
    public SingleTitleDescriptionAuthorPairsComponent getSingleTitleDescriptionAuthorPairsComponent(){
        return  new SingleTitleDescriptionAuthorPairsComponent(waitUntilVisible(SingleTitleDescriptionAuthorPairsComponentElement));
    }
    @Step("Get SingleTitleQuintupleDescription component")
    public SingleTitleQuintupleDescriptionComponent getSingleTitleQuintupleDescriptionComponent(){
        return  new SingleTitleQuintupleDescriptionComponent(waitUntilVisible(SingleTitleQuintupleDescriptionComponentElement));
    }
    @Step("Get TitleDescriptionCardSection component")
    public TitleDescriptionCardSectionComponent getTitleDescriptionCardSectionComponent(){
        return  new TitleDescriptionCardSectionComponent(waitUntilVisible(TitleDescriptionCardSectionComponentElement));
    }
    @Step("Get ViewTitleDescriptionSection component")
    public ViewTitleDescriptionSectionComponent getViewTitleDescriptionSectionComponent(){
        return  new ViewTitleDescriptionSectionComponent(waitUntilVisible(ViewTitleDescriptionSectionComponentElement));
    }
}
