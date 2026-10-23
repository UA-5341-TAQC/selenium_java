package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class DonationSectionComponent extends BaseComponent {

    @FindBy(css = "h2[class*='DonationSection_title'], [class*='DonationSection_title']")
    private WebElement title;

    @FindBy(css = "p[class*='DonationSection_description'], [class*='DonationSection_description']")
    private WebElement description;

    @FindBy(css = "input.donateAmountInput")
    private WebElement amountInput;

    @FindBy(css = "button.donateFastOptionButton")
    private List<WebElement> fastDonateButtons;

    @FindBy(css = "button.donateButton")
    private WebElement submitButton;

    @FindBy(css = "a[class*='DonationSection_button']")
    private WebElement requisitesButton;

    @FindBy(css = "video, img")
    private WebElement backgroundMedia;

    public DonationSectionComponent(WebElement element) {
        super(element);
    }

    @Step("Get Donation section title")
    public String getTitleText() {
        return getText(title);
    }

    @Step("Get Donation section description")
    public String getDescriptionText() {
        return getText(description);
    }

    @Step("Enter donation amount: {amount}")
    public DonationSectionComponent enterAmount(String amount) {
        fillInput(amountInput, amount);
        return this;
    }

    @Step("Get fast donate options count")
    public int getFastOptionButtonsCount() {
        return fastDonateButtons.size();
    }

    @Step("Click fast donate button by index: {index}")
    public void clickFastDonateButton(int index) {
        if (index < 0 || index >= fastDonateButtons.size()) {
            throw new IndexOutOfBoundsException("Invalid fast donate button index: " + index + ", total buttons: " + fastDonateButtons.size());
        }
        click(fastDonateButtons.get(index));
    }

    @Step("Click submit donate button")
    public void clickDonate() {
        click(submitButton);
    }

    @Step("Click view requisites button")
    public void clickRequisitesButton() {
        click(requisitesButton);
    }

    @Step("Check if background media is displayed")
    public boolean isBackgroundMediaDisplayed() {
        return isDisplayed(backgroundMedia);
    }
}

