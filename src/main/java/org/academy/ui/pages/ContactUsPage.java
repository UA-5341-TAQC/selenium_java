package org.academy.ui.pages;

import io.qameta.allure.Step;
import org.academy.ui.components.ContactFormCardComponent;
import org.academy.ui.components.contact.ContactDetailsSectionComponent;
import org.academy.ui.components.contact.ToastItemComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ContactUsPage extends BasePublicPage {

    @FindBy(css = "section[class*='ContactDetailsSection_contact-details-section'], [class*='contact-details-section'], section:first-of-type")
    private WebElement contactDetailsSectionElement;

    @FindBy(css = "form[class*='ContactFormCard_contact-form-card'], [class*='ContactFormCard_contact-form-card']")
    private WebElement contactFormElement;

    @FindBy(css = ".toast, [class*='toast']")
    private List<WebElement> toastElements;

    public ContactUsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get ContactDetailsSection component")
    public ContactDetailsSectionComponent getContactDetailsSection() {
        return new ContactDetailsSectionComponent(waitUntilVisible(contactDetailsSectionElement));
    }

    @Step("Get embedded ContactFormCard component")
    public ContactFormCardComponent getContactFormCard() {
        return new ContactFormCardComponent(waitUntilVisible(contactFormElement));
    }

    @Step("Get Toast notification component")
    public ToastItemComponent getToast() {
        WebElement toast = waitUntilVisible(By.cssSelector(".toast, [class*='toast']"));
        return new ToastItemComponent(toast);
    }

    @Step("Check if Toast notification is present")
    public boolean isToastPresent() {
        return !toastElements.isEmpty() && isDisplayed(toastElements.get(0));
    }
}
