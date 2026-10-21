package org.academy.ui.pages;

import io.qameta.allure.Step;
import org.academy.ui.components.ContactSectionComponent;
import org.academy.ui.components.FaqSectionComponent;
import org.academy.ui.components.ProgramsIntroSectionComponent;
import org.academy.ui.components.ProgramsSectionComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProgramsPage extends BasePublicPage{

    @FindBy(css = "div[class*='IntroSection_intro-section']")
    private WebElement introSectionElement;

    @FindBy(css = "div[class*='ProgramsSection_program-block']")
    private WebElement programsSectionElement;

    @FindBy(css = "div[class*='FaqSection_faq-section']")
    private WebElement faqSectionElement;

    @FindBy(css = "div[class*='ContactSection_contact-us-block']")
    private WebElement contactSectionElement;

    public ProgramsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get Programs Intro Section component")
    public ProgramsIntroSectionComponent getIntroSection() {
        wait.until(ExpectedConditions.visibilityOf(introSectionElement));
        return new ProgramsIntroSectionComponent(introSectionElement);
    }

    @Step("Get Programs Section component")
    public ProgramsSectionComponent getProgramsSection() {
        wait.until(ExpectedConditions.visibilityOf(programsSectionElement));
        return new ProgramsSectionComponent(programsSectionElement);
    }

    @Step("Get FAQ Section component")
    public FaqSectionComponent getFaqSection() {
        wait.until(ExpectedConditions.visibilityOf(faqSectionElement));
        return new FaqSectionComponent(faqSectionElement);
    }

    @Step("Get Contact Section component")
    public ContactSectionComponent getContactSection() {
        wait.until(ExpectedConditions.visibilityOf(contactSectionElement));
        return new ContactSectionComponent(contactSectionElement);
    }
}
