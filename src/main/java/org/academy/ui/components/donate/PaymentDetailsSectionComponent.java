package org.academy.ui.components.donate;

import io.qameta.allure.Step;
import org.academy.ui.components.BaseComponent;
import org.academy.ui.components.CopyTextButtonComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class PaymentDetailsSectionComponent extends BaseComponent {

    @FindBy(css = ".locationToggleContainer .tabsContainer button.tab, .tabsContainer button.tab, button.tab")
    private List<WebElement> currencyTabs;

    @FindBy(css = "button.copyTextButton")
    private List<WebElement> copyButtons;

    @FindBy(css = ".UkrainePaymentDetails, .paymentDetails")
    private List<WebElement> ukrainePaymentDetails;

    @FindBy(css = ".abroadPaymentDetails, .abroadPaymentDetailsBlock")
    private List<WebElement> abroadPaymentDetails;

    public PaymentDetailsSectionComponent(WebElement element) {
        super(element);
    }

    @Step("Get currency tabs count")
    public int getCurrencyTabsCount() {
        return currencyTabs.size();
    }

    @Step("Get currency tab names")
    public List<String> getCurrencyTabNames() {
        return getTexts(currencyTabs);
    }

    @Step("Select currency tab: {currencyName}")
    public PaymentDetailsSectionComponent selectCurrencyTab(String currencyName) {
        for (WebElement tab : currencyTabs) {
            if (tab.getText().trim().equalsIgnoreCase(currencyName)) {
                scrollIntoView(tab);
                click(tab);
                return this;
            }
        }
        throw new IllegalArgumentException("Currency tab not found: " + currencyName);
    }

    @Step("Get list of copy button components")
    public List<CopyTextButtonComponent> getCopyButtons() {
        return copyButtons.stream()
                .map(CopyTextButtonComponent::new)
                .toList();
    }

    @Step("Get total count of copy buttons")
    public int getCopyButtonsCount() {
        return copyButtons.size();
    }

    @Step("Check if Ukraine payment details are displayed")
    public boolean isUkrainePaymentDetailsDisplayed() {
        return !ukrainePaymentDetails.isEmpty() && isDisplayed(ukrainePaymentDetails.get(0));
    }

    @Step("Check if abroad payment details are displayed")
    public boolean isAbroadPaymentDetailsDisplayed() {
        return !abroadPaymentDetails.isEmpty() && isDisplayed(abroadPaymentDetails.get(0));
    }
}
