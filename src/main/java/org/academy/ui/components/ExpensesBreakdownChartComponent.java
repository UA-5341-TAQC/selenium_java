package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpensesBreakdownChartComponent extends BaseComponent {

    private static final Pattern PERCENT =
            Pattern.compile("(\\d+(?:[.,]\\d+)?)\\s*%");

    @FindBy(css = "[class*='ExpensesBreakdownChart_title']")
    private WebElement chartTitleElement;

    @FindBy(css = "svg[class*='ChartGraphic_chart']")
    private WebElement chartGraphic;

    @FindBy(css = "path[class*='ChartGraphic_arc']")
    private List<WebElement> segmentElements;

    @FindBy(css = "text[class*='ChartGraphic_label']")
    private List<WebElement> chartLabelElements;

    @FindBy(css = "tspan[class*='ChartGraphic_percent']")
    private List<WebElement> chartPercentElements;

    @FindBy(css = "tspan[class*='ChartGraphic_amount']")
    private List<WebElement> chartAmountElements;

    @FindBy(css = "[class*='ChartLegend_item']")
    private List<WebElement> legendItemElements;

    @FindBy(css = "[class*='ChartLegend_label']")
    private List<WebElement> legendLabelElements;

    public ExpensesBreakdownChartComponent(WebElement element) {
        super(element);
    }

    @Step("Read expenses chart title")
    public String getChartTitle() {
        return getText(chartTitleElement);
    }

    @Step("Check if expenses chart is displayed")
    public boolean isChartDisplayed() {
        scrollIntoView(rootElement);
        return isVisible(chartGraphic);
    }

    @Step("Read number of chart segments")
    public int getSegmentsCount() {
        return segmentElements.size();
    }

    @Step("Read chart labels")
    public List<String> getChartLabels() {
        return getTexts(waitUntilAllVisible(chartLabelElements));
    }

    @Step("Read chart percentages")
    public List<Double> getChartPercentages() {
        return waitUntilAllVisible(chartPercentElements)
                .stream()
                .map(WebElement::getText)
                .map(this::parsePercentage)
                .toList();
    }

    @Step("Read chart amounts")
    public List<String> getChartAmounts() {
        return getTexts(waitUntilAllVisible(chartAmountElements));
    }

    @Step("Read legend item texts")
    public List<String> getLegendTexts() {
        return getTexts(waitUntilAllVisible(legendItemElements));
    }

    @Step("Read legend labels")
    public List<String> getLegendLabels() {
        return getTexts(waitUntilAllVisible(legendLabelElements));
    }

    @Step("Calculate sum of chart percentages")
    public double getChartPercentagesSum() {
        return getChartPercentages()
                .stream()
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    private double parsePercentage(String text) {
        Matcher matcher = PERCENT.matcher(text);

        if (!matcher.find()) {
            throw new IllegalStateException(
                    "No percentage found in chart element: " + text
            );
        }

        return Double.parseDouble(
                matcher.group(1).replace(',', '.')
        );
    }
}