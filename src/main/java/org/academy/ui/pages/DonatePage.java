package org.academy.ui.pages;

import io.qameta.allure.Step;
import org.academy.ui.components.FaqSectionComponent;
import org.academy.ui.components.donate.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class DonatePage extends BasePublicPage {

    @FindBy(css = ".donatePageIntro")
    private WebElement introElement;

    @FindBy(css = "form.donateSection, .donateSection")
    private WebElement donateSectionElement;

    @FindBy(css = ".rightSection, .donatePaymentDetails")
    private WebElement paymentDetailsSectionElement;

    @FindBy(css = ".alternativeSupportWays")
    private List<WebElement> alternativeSupportWaysElements;

    @FindBy(css = "div[class*='FaqSection_faq-section']")
    private WebElement faqSectionElement;

    public DonatePage(WebDriver driver) {
        super(driver);
    }

    @Step("Get DonatePageIntro component")
    public DonatePageIntroComponent getIntroSection() {
        return new DonatePageIntroComponent(waitUntilVisible(introElement));
    }

    @Step("Get DonateSection component")
    public DonateSectionComponent getDonateSection() {
        return new DonateSectionComponent(waitUntilVisible(donateSectionElement));
    }

    @Step("Get PaymentDetailsSection component")
    public PaymentDetailsSectionComponent getPaymentDetailsSection() {
        return new PaymentDetailsSectionComponent(waitUntilVisible(paymentDetailsSectionElement));
    }

    @Step("Check if AlternativeSupportWays component is present")
    public boolean hasAlternativeSupportWaysSection() {
        return !alternativeSupportWaysElements.isEmpty() && isDisplayed(alternativeSupportWaysElements.get(0));
    }

    @Step("Get AlternativeSupportWays component")
    public AlternativeSupportWaysComponent getAlternativeSupportWaysSection() {
        if (alternativeSupportWaysElements.isEmpty()) {
            throw new NoSuchElementException("AlternativeSupportWays section is not present for the selected currency");
        }
        return new AlternativeSupportWaysComponent(waitUntilVisible(alternativeSupportWaysElements.get(0)));
    }

    @Step("Get embedded FaqSection component")
    public FaqSectionComponent getFaqSection() {
        return new FaqSectionComponent(waitUntilVisible(faqSectionElement));
    }
}
