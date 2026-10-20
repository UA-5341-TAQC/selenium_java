package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class DonateSectionComponent extends BaseComponent {

    @FindBy(css = "input.donateAmountInput")
    private WebElement amountInput;

    @FindBy(css = "button.donateFastOptionButton")
    private List<WebElement> fastDonateButtons;

    @FindBy(css = "button.donateButton")
    private WebElement submitButton;

    public DonateSectionComponent(WebElement element) {
        super(element);
    }

    @Step("Enter donate amount: {amount}")
    public DonateSectionComponent enterAmount(String amount) {
        wait.until(ExpectedConditions.visibilityOf(amountInput));
        amountInput.clear();
        amountInput.sendKeys(amount);
        return this;
    }

    @Step("Click quick donate submit")
    public void clickSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }

    @Step("Get fast donate buttons count")
    public int getFastDonateButtonsCount() {
        return fastDonateButtons.size();
    }

    @Step("Click fast donate button at index: {index}")
    public DonateSectionComponent clickFastDonateButton(int index) {
        if (index < 0 || index >= fastDonateButtons.size()) {
            throw new IndexOutOfBoundsException("Invalid fast donate button index: " + index + ", total buttons: " + fastDonateButtons.size());
        }
        wait.until(ExpectedConditions.elementToBeClickable(fastDonateButtons.get(index))).click();
        return this;
    }
}
