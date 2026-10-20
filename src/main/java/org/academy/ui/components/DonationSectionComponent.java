package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
        wait.until(ExpectedConditions.visibilityOf(title));
        return title.getText().trim();
    }

    @Step("Get Donation section description")
    public String getDescriptionText() {
        wait.until(ExpectedConditions.visibilityOf(description));
        return description.getText().trim();
    }

    @Step("Enter donation amount: {amount}")
    public DonationSectionComponent enterAmount(String amount) {
        wait.until(ExpectedConditions.visibilityOf(amountInput));
        amountInput.clear();
        amountInput.sendKeys(amount);
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
        wait.until(ExpectedConditions.elementToBeClickable(fastDonateButtons.get(index))).click();
    }

    @Step("Click submit donate button")
    public void clickDonate() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }

    @Step("Click view requisites button")
    public void clickRequisitesButton() {
        wait.until(ExpectedConditions.elementToBeClickable(requisitesButton)).click();
    }

    @Step("Check if background media is displayed")
    public boolean isBackgroundMediaDisplayed() {
        return backgroundMedia != null && backgroundMedia.isDisplayed();
    }
}
