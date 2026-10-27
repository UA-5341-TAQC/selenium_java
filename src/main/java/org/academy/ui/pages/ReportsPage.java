package org.academy.ui.pages;

import io.qameta.allure.Step;
import org.academy.ui.components.ExpensesBreakdownChartComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ReportsPage extends BasePublicPage {

    @FindBy(css = "[class*='ReportsSection_title']")
    private WebElement summaryTitleElement;

    @FindBy(css = "[class*='StatCard_card']")
    private List<WebElement> statCardElements;

    @FindBy(css = "[class*='ExpensesBreakdownChart_container']")
    private WebElement expensesBreakdownChartElement;

    @FindBy(css = "[class*='ReportsSection_title']")
    private WebElement reportsSectionTitleElement;

    @FindBy(css = "[class*='ReportItem_year']")
    private List<WebElement> reportFileLabelElements;

    @FindBy(css = "[class*='ReportItem_root'] button")
    private List<WebElement> reportDownloadButtonElements;

    @FindBy(css = ".ReportsSection_toggle__VXAKA button")
    private WebElement showMoreReportsButtonElement;

    @FindBy(css = "[class*='CtaSection_root'] a[href='/donate']")
    private WebElement ctaDonateLinkElement;

    public ReportsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Read summary section title")
    public String getSummaryTitle() {
        return getText(summaryTitleElement);
    }

    @Step("Read stat card texts")
    public List<String> getStatCardTexts() {
        return getTexts(waitUntilAllVisible(statCardElements));
    }

    @Step("Get ExpensesBreakdownChart component")
    public ExpensesBreakdownChartComponent getExpensesBreakdownChart() {
        scrollIntoView(expensesBreakdownChartElement);

        return new ExpensesBreakdownChartComponent(
                waitUntilVisible(expensesBreakdownChartElement)
        );
    }

    @Step("Read reports section title")
    public String getReportsSectionTitle() {
        scrollIntoView(reportsSectionTitleElement);
        return getText(reportsSectionTitleElement);
    }

    @Step("Read report file labels")
    public List<String> getReportFileLabels() {
        scrollIntoView(reportsSectionTitleElement);
        return getTexts(waitUntilAllVisible(reportFileLabelElements));
    }

    @Step("Read report download button texts")
    public List<String> getReportDownloadButtonTexts() {
        scrollIntoView(reportsSectionTitleElement);
        return getTexts(waitUntilAllVisible(reportDownloadButtonElements));
    }

    @Step("Click report download button")
    public void clickReportDownloadButton(int index) {
        List<WebElement> buttons =
                waitUntilAllVisible(reportDownloadButtonElements);

        if (index < 0 || index >= buttons.size()) {
            throw new IndexOutOfBoundsException(
                    "Report download button index: " + index
                            + ", available buttons: " + buttons.size()
            );
        }

        WebElement button = buttons.get(index);
        scrollIntoView(button);
        click(button);
    }

    @Step("Check if show more reports button is displayed")
    public boolean isShowMoreReportsButtonDisplayed() {
        scrollIntoView(reportsSectionTitleElement);
        return isVisible(showMoreReportsButtonElement);
    }

    @Step("Read show more/less reports button text")
    public String getShowMoreReportsButtonText() {
        return getText(showMoreReportsButtonElement);
    }

    @Step("Click show more reports button")
    public void clickShowMoreReportsButton() {
        scrollIntoView(showMoreReportsButtonElement);
        click(showMoreReportsButtonElement);
    }

    @Step("Check if donate link is displayed")
    public boolean isCtaDonateLinkDisplayed() {
        return isVisible(ctaDonateLinkElement);
    }

    @Step("Read CTA donate link href")
    public String getCtaDonateLinkHref() {
        return getDomAttribute(ctaDonateLinkElement, "href");
    }

    @Step("Click donate link in the CTA section")
    public void clickCtaDonateLink() {
        scrollIntoView(ctaDonateLinkElement);
        click(ctaDonateLinkElement);
    }
}
