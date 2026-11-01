package org.academy.ui;

import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.academy.ui.components.ContactFormCardComponent;
import org.academy.ui.pages.ContactUsPage;
import org.academy.ui.testrunners.BaseUiTestRunner;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.net.URI;

public class TestContactFormEmailValidation extends BaseUiTestRunner {

    private static final String CONTACT_US_PATH = "/contact-us";
    private static final String INVALID_EMAIL_ERROR = "Некоректний E-mail";

    private static final String VALID_NAME = "Test QA";
    private static final String VALID_SUBJECT = "Test subject";
    private static final String VALID_MESSAGE = "Test message";

    private ContactUsPage contactUsPage;

    @BeforeMethod
    public void openContactUsPage() {
        driver.get(URI.create(testEnvProvider.getBaseUrl()).resolve(CONTACT_US_PATH).toString());
        contactUsPage = new ContactUsPage(driver);
    }

    // rows 1-3, 6-17 of the test data: rejected with the error message
    @DataProvider(name = "invalidEmails")
    public Object[][] invalidEmails() {
        return new Object[][]{
                {"userexample.com"},          // 1: no @
                {"user@example"},             // 2: no dot in domain
                {"user@localhost"},           // 3: no dot in domain
                {"@mail.com"},                // 6: empty local part
                {"user@"},                    // 7: empty domain
                {"user@.com"},                // 8: empty domain label
                {"user@mail.c"},              // 9: TLD shorter than 2 chars
                {"test@test@mail.com"},       // 10: more than one @
                {"test..test@mail.com"},      // 11: two dots in a row (bug #4167 in the run of 23.09.2026)
                {"test!#$%test@mail.com"},    // 12: disallowed special characters (bug #4167)
                {".test@mail.com"},           // 13: starts with a dot (bug #4167)
                {"test.@mail.com"},           // 14: local part ends with a dot (bug #4167)
                {"test@mail.com."},           // 15: ends with a dot
                {"test@.mail.com"},           // 16: dot right after @
                {"testmail.com@"}             // 17: @ is the last character
        };
    }

    // rows 4, 5 and 18: the application removes spaces from the e-mail field while typing
    @DataProvider(name = "emailsWithSpaces")
    public Object[][] emailsWithSpaces() {
        return new Object[][]{
                {"us er@mail.com", "user@mail.com"},         // 4
                {"user name@mail.com", "username@mail.com"}, // 5
                {"test@ mail.com", "test@mail.com"}          // 18
        };
    }

    @Test(dataProvider = "invalidEmails",
            description = "Verify that the Contact form rejects invalid e-mail addresses and shows «Некоректний E-mail»")
    @Issue("2420")
    @Story("Contact form e-mail validation")
    public void testInvalidEmailIsRejected(String email) {
        checkEmailIsRejected(email);
    }

    @Test(dataProvider = "emailsWithSpaces",
            description = "Verify that spaces cannot be entered into the «E-mail» field of the Contact form")
    @Issue("2420")
    @Story("Contact form e-mail validation")
    public void testSpacesAreRemovedFromEmail(String emailWithSpaces, String expectedValue) {
        ContactFormCardComponent form = contactUsPage.getContactFormCard();
        form.enterEmail(emailWithSpaces);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(form.getEmailValue(), expectedValue,
                "The application should remove spaces from the e-mail: " + emailWithSpaces);
        softAssert.assertAll();
    }

    private void checkEmailIsRejected(String email) {
        ContactFormCardComponent form = contactUsPage.getContactFormCard();
        form.fillForm(VALID_NAME, email, VALID_SUBJECT, VALID_MESSAGE);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(form.getEmailValue(), email, "Entered e-mail should be displayed in the field");

        submitForm(form);

        softAssert.assertTrue(waitForInvalidEmailError(form),
                "Error '" + INVALID_EMAIL_ERROR + "' should be displayed for: " + email);
        softAssert.assertFalse(contactUsPage.isToastPresent(),
                "Form must not be submitted for: " + email);
        softAssert.assertAll();
    }

    // The submit button can stay disabled in an automated browser (the Cloudflare Turnstile token is
    // not issued), so when it is disabled the form is submitted the same way the button does it.
    private void submitForm(ContactFormCardComponent form) {
        if (form.isSubmitButtonEnabled()) {
            form.clickSubmit();
        } else {
            ((JavascriptExecutor) driver).executeScript(
                    "document.querySelector(\"input[name='email']\").form.requestSubmit();");
        }
    }

    private boolean waitForInvalidEmailError(ContactFormCardComponent form) {
        try {
            form.waitForValidationError(INVALID_EMAIL_ERROR);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }
}