package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
        fillInput(amountInput, amount);
        return this;
    }

    @Step("Click quick donate submit")
    public void clickSubmit() {
        click(submitButton);
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
        click(fastDonateButtons.get(index));
        return this;
    }
}

