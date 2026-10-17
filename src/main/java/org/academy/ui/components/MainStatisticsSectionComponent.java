package org.academy.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class MainStatisticsSectionComponent extends BaseComponent {

    @FindBy(css = "h2[class*='MainStatisticsSection_title'], [class*='MainStatisticsSection_title']")
    private WebElement title;

    @FindBy(css = "[class*='MainStatisticsSection_metric__']")
    private List<WebElement> metricItems;

    public MainStatisticsSectionComponent(WebElement element) {
        super(element);
    }

    @Step("Get Statistics section title")
    public String getTitleText() {
        wait.until(ExpectedConditions.visibilityOf(title));
        return title.getText().trim();
    }

    @Step("Get metrics count in Statistics section")
    public int getMetricsCount() {
        return metricItems.size();
    }

    @Step("Get metric names from Statistics section")
    public List<String> getMetricNames() {
        return metricItems.stream()
                .map(item -> {
                    try {
                        return item.findElement(By.cssSelector("[class*='metric-name']")).getText().trim();
                    } catch (Exception e) {
                        return item.getText().trim();
                    }
                })
                .collect(Collectors.toList());
    }

    @Step("Get metric values from Statistics section")
    public List<String> getMetricValues() {
        return metricItems.stream()
                .map(item -> {
                    try {
                        return item.findElement(By.cssSelector("[class*='metric-value']")).getText().trim();
                    } catch (Exception e) {
                        return "";
                    }
                })
                .collect(Collectors.toList());
    }
}
