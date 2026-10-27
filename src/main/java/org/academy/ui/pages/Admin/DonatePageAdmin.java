package org.academy.ui.pages.Admin;

import io.qameta.allure.Step;
import lombok.Getter;
import org.academy.ui.components.admin.donate.DonateBankComponentAdmin;
import org.academy.ui.components.admin.donate.DonateSupportOptionComponentAdmin;
import org.academy.ui.modals.admin.DonateConfirmationModalAdmin;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

@Getter
public class DonatePageAdmin extends BaseAdminPage {

    // Page

    @FindBy(css = ".donate-page-wrapper")
    private WebElement pageContent;

    // Currency tabs

    @FindBy(css = ".category-bar-button")
    private List<WebElement> currencyButtons;

    @FindBy(css = ".category-bar-button-selected")
    private WebElement selectedCurrencyButton;

    // Bank details

    @FindBy(css = ".donate-page-item > .generic-details "
            + "> .generic-details.body > .generic-details-item "
            + "> .generic-form:not(.child)")
    private List<WebElement> bankItems;

    @FindBy(css = ".donate-page-item > .generic-details "
            + "> .generic-details-buttons .btn-add-new")
    private WebElement addBankButton;

    // Support options

    @FindBy(css = ".support-options-form .support-option")
    private List<WebElement> supportOptionItems;

    @FindBy(css = ".support-options-form > button.btn-add")
    private WebElement addSupportOptionButton;

    @FindBy(css = ".modal-container.confirmation-modal")
    private WebElement confirmationModal;

    public DonatePageAdmin(WebDriver driver) {
        super(driver);
    }

    // Page methods

    @Step("Check Donate admin page is displayed")
    public boolean isPageDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(pageContent)
        ).isDisplayed();
    }

    // Currency methods

    @Step("Select currency: {currency}")
    public void selectCurrency(String currency) {
        WebElement button = currencyButtons.stream()
                .filter(element -> element.getText().trim()
                        .equalsIgnoreCase(currency))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Currency not found: " + currency));

        wait.until(
                ExpectedConditions.elementToBeClickable(button)
        ).click();

        wait.until(driver ->
                currency.equalsIgnoreCase(
                        selectedCurrencyButton.getText().trim()));
    }

    @Step("Select UAH")
    public void selectUah() {
        selectCurrency("UAH");
    }

    @Step("Select USD")
    public void selectUsd() {
        selectCurrency("USD");
    }

    @Step("Select EUR")
    public void selectEur() {
        selectCurrency("EUR");
    }

    @Step("Get selected currency")
    public String getSelectedCurrency() {
        return selectedCurrencyButton.getText().trim();
    }

    // Bank methods

    @Step("Get bank items count")
    public int getBankItemsCount() {
        return bankItems.size();
    }

    @Step("Get bank by index: {index}")
    public DonateBankComponentAdmin getBank(int index) {
        return new DonateBankComponentAdmin(bankItems.get(index));
    }

    @Step("Get all bank components")
    public List<DonateBankComponentAdmin> getBankComponents() {
        return bankItems.stream()
                .map(DonateBankComponentAdmin::new)
                .toList();
    }

    @Step("Get bank by name: {name}")
    public DonateBankComponentAdmin getBankByName(String name) {
        return getBankComponents().stream()
                .filter(bank -> bank.getBankNameText()
                        .equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Bank not found: " + name));
    }

    @Step("Click Add Bank")
    public void clickAddBank() {
        click(addBankButton);
    }

    // Support options methods

    @Step("Get support options count")
    public int getSupportOptionsCount() {
        return supportOptionItems.size();
    }

    @Step("Get support option by index: {index}")
    public DonateSupportOptionComponentAdmin getSupportOption(
            int index) {
        return new DonateSupportOptionComponentAdmin(
                supportOptionItems.get(index));
    }

    @Step("Get all support option components")
    public List<DonateSupportOptionComponentAdmin>
    getSupportOptionComponents() {
        return supportOptionItems.stream()
                .map(DonateSupportOptionComponentAdmin::new)
                .toList();
    }

    @Step("Get support option by title: {title}")
    public DonateSupportOptionComponentAdmin
    getSupportOptionByTitle(String title) {
        return getSupportOptionComponents().stream()
                .filter(option -> option.getTitle()
                        .equalsIgnoreCase(title))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Support option not found: " + title));
    }

    @Step("Click Add Support Option")
    public void clickAddSupportOption() {
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        addSupportOptionButton)
        ).click();
    }

    @Step("Get Donate confirmation modal")
    public DonateConfirmationModalAdmin getConfirmationModal() {
        WebElement modal = wait.until(
                ExpectedConditions.visibilityOf(confirmationModal)
        );

        return new DonateConfirmationModalAdmin(modal);
    }

    @Step("Get new support option form")
    public DonateSupportOptionComponentAdmin getNewSupportOption() {
        return wait.until(driver ->
                getSupportOptionComponents().stream()
                        .filter(DonateSupportOptionComponentAdmin::isCreateMode)
                        .findFirst()
                        .orElse(null)
        );
    }
}
