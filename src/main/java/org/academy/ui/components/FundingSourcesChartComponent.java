package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.math.BigDecimal;
import java.util.List;

/**
 * "Звідки прийшли кошти" block on ReportsPage: horizontal bars (FundingSourcesRow) with a label and an amount.
 * The root element is [class*='FundingSourcesChart_root'].
 */
public class FundingSourcesChartComponent extends BaseComponent {

    @FindBy(css = "[class*='FundingSourcesChart_title']")
    private WebElement titleElement;

    @FindBy(css = "[class*='FundingSourcesRow_row']")
    private List<WebElement> rowElements;

    @FindBy(css = "[class*='FundingSourcesRow_label']")
    private List<WebElement> labelElements;

    @FindBy(css = "[class*='FundingSourcesRow_amount']")
    private List<WebElement> amountElements;

    @FindBy(css = "[class*='FundingSourcesRow_bar']")
    private List<WebElement> barElements;

    public FundingSourcesChartComponent(WebElement element) {
        super(element);
    }

    @Step("Read funding sources chart title")
    public String getTitle() {
        return getText(titleElement);
    }

    @Step("Read number of funding sources")
    public int getRowsCount() {
        return rowElements.size();
    }

    @Step("Read funding source labels")
    public List<String> getLabels() {
        return getTexts(waitUntilAllVisible(labelElements));
    }

    @Step("Read funding source amounts as shown on the page")
    public List<String> getAmountTexts() {
        return waitUntilAllVisible(amountElements).stream()
                .map(element -> element.getText().replace(' ', ' ').trim())
                .toList();
    }

    @Step("Read funding source amounts as numbers")
    public List<BigDecimal> getAmounts() {
        return getAmountTexts().stream()
                .map(FundingSourcesChartComponent::parseAmount)
                .toList();
    }

    @Step("Calculate total of all funding sources")
    public BigDecimal getTotalAmount() {
        return getAmounts().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Step("Read bar widths")
    public List<String> getBarWidths() {
        return barElements.stream()
                .map(bar -> bar.getCssValue("width"))
                .toList();
    }

    // "1 249 854,99 грн" -> 1249854.99
    private static BigDecimal parseAmount(String text) {
        String digits = text.replace("грн", "")
                .replaceAll("\\s", "")
                .replace(',', '.');
        return new BigDecimal(digits);
    }
}