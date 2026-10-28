package org.academy.ui;

import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.academy.ui.components.HeaderComponent;
import org.academy.ui.components.ContactFormCardComponent;
import org.academy.ui.components.CopyTextButtonComponent;
import org.academy.ui.components.LanguageSwitcherComponent;
import org.academy.ui.components.contact.ContactDetailsSectionComponent;
import org.academy.ui.pages.ContactUsPage;
import org.academy.ui.testrunners.BaseUiTestRunner;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.net.URI;
import java.util.List;

public class TestContactUsPage extends BaseUiTestRunner {

    private static final String CONTACT_US_PATH = "/contact-us";

    private static final String EXPECTED_PAGE_TITLE = "МИ ЗАВЖДИ ВІДКРИТІ ДЛЯ ВАС";
    private static final String EXPECTED_PAGE_DESCRIPTION =
            "Якщо у вас є запитання, пропозиції чи ви шукаєте підтримку — наша команда завжди готова допомогти.";

    private static final String SUBMIT_BUTTON_TEXT = "Надіслати";
    private static final String SUBJECT_MIN_LENGTH_ERROR = "Мінімум 5 символів";
    private static final String MESSAGE_MIN_LENGTH_ERROR = "Мінімум 10 символів";

    private static final String TARGET_BLANK = "_blank";
    private static final String HTTPS_PREFIX = "https://";

    private static final String FACEBOOK_NAME = "Facebook";
    private static final String TELEGRAM_NAME = "Telegram";
    private static final String INSTAGRAM_NAME = "Instagram";

    private static final String VALID_NAME = "Олександр Тестовий";
    private static final String VALID_EMAIL = "test.user@example.com";
    private static final String VALID_SUBJECT = "Загальне запитання";
    private static final String VALID_MESSAGE = "Доброго дня! Це перевірка форми зворотного зв'язку.";

    private static final String SHORT_SUBJECT_4_CHARS = "Тест";
    private static final String VALID_SUBJECT_5_CHARS = "Тести";
    private static final String SHORT_MESSAGE_9_CHARS = "123456789";
    private static final String VALID_MESSAGE_10_CHARS = "1234567890";
    private static final String SHORT_SUBJECT_2_CHARS = "Hi";
    private static final String SHORT_MESSAGE_5_CHARS = "Short";


    private static final String EXPECTED_PAGE_TITLE_EN = "WE ARE ALWAYS OPEN FOR YOU";
    private static final String EXPECTED_PAGE_DESCRIPTION_EN =
            "If you have questions, suggestions, or are looking for support, our team is always ready to help.";

    private static final String EXPECTED_OUR_CONTACTS_US_EN =
            "Our contacts:";

    private static final String EXPECTED_OUR_SOCIAL_MEDIA_EN =
            "Social media:";

    private static final String EXPECTED_ADDRESS_EN =
            "Kyiv, Khreshchatyk str., 1";
    private static final String SUBMIT_BUTTON_TEXT_EN = "Send";

    private static final String YOUR_NAME_PLACEHOLDER_EN = "Your name";
    private static final String EMAIL_PLACEHOLDER_EN = "E-mail";
    private static final String SUBJECT_PLACEHOLDER_EN = "Subject";
    private static final String WRITE_MESSAGE_PLACEHOLDER_EN = "Write your message";

    private ContactUsPage contactUsPage;

    @BeforeMethod
    public void openContactUsPage() {
        String contactUsUrl = URI.create(testEnvProvider.getBaseUrl()).resolve(CONTACT_US_PATH).toString();
        driver.get(contactUsUrl);
        contactUsPage = new ContactUsPage(driver);
    }

    @Test(description = "Verify contacts form UI elements on the public 'Зв'язатися' web-page")
    @Issue("752")
    @Story("UI Elements on Contact Us Page")
    public void testContactsFormUiElements() {
        ContactDetailsSectionComponent detailsSection = contactUsPage.getContactDetailsSection();
        SoftAssert softAssert = new SoftAssert();

        // 1. Left Contact Information Section
        softAssert.assertEquals(detailsSection.getTitleText(), EXPECTED_PAGE_TITLE,
                "Heading text should strictly match mockup");
        softAssert.assertEquals(detailsSection.getDescriptionText(), EXPECTED_PAGE_DESCRIPTION,
                "Description text should match mockup");

        softAssert.assertTrue(detailsSection.getEmailText().contains("@"),
                "Contact email should contain @ symbol");
        softAssert.assertTrue(detailsSection.getEmailCopyButton().isDisplayed(),
                "Email copy button should be displayed");

        softAssert.assertTrue(detailsSection.getPhoneText().contains("+"),
                "Contact phone number should contain + symbol");
        softAssert.assertTrue(detailsSection.getPhoneCopyButton().isDisplayed(),
                "Phone copy button should be displayed");

        softAssert.assertFalse(detailsSection.getAddressText().isBlank(),
                "Contact address should be displayed");

        softAssert.assertEquals(detailsSection.getSocialMediaLinksCount(), 3,
                "Should display 3 social media links (Facebook, Telegram, Instagram)");
        List<String> socialNames = detailsSection.getSocialMediaNames();
        softAssert.assertTrue(socialNames.contains(FACEBOOK_NAME), "Social links should contain Facebook");
        softAssert.assertTrue(socialNames.contains(TELEGRAM_NAME), "Social links should contain Telegram");
        softAssert.assertTrue(socialNames.contains(INSTAGRAM_NAME), "Social links should contain Instagram");

        // 2. Right Contact Form Section
        ContactFormCardComponent form = contactUsPage.getContactFormCard();
        softAssert.assertTrue(form.areAllFieldsDisplayed(), "All contact form fields and submit button should be displayed");
        softAssert.assertEquals(form.getSubmitButtonText(), SUBMIT_BUTTON_TEXT,
                "Submit button text should match expected");

        softAssert.assertAll();
    }

    @Test(description = "Verify that the contact form is available on the Зв’язатися page and accepts valid input")
    @Issue("799")
    @Story("Contact Form Availability and Valid Input")
    public void testContactFormAvailableAndAcceptsInput() {
        ContactFormCardComponent form = contactUsPage.getContactFormCard();
        Assert.assertTrue(form.areAllFieldsDisplayed(), "All contact form fields should be displayed");

        ContactFormCardComponent.ContactFormData expectedData = new ContactFormCardComponent.ContactFormData(
                VALID_NAME,
                VALID_EMAIL,
                VALID_SUBJECT,
                VALID_MESSAGE
        );

        Assert.assertEquals(form.fillForm(expectedData).getFormData(), expectedData,
                "Entered form values should match expected ContactFormData");
        Assert.assertTrue(form.isSubmitButtonDisplayed(), "Submit button should be displayed");
        Assert.assertEquals(form.getSubmitButtonText(), SUBMIT_BUTTON_TEXT, "Submit button text should match expected");
    }

    @Test(description = "Verify 'Тема звернення' field length validation (min/max boundaries)")
    @Issue("749")
    @Story("Subject Field Length Validation")
    public void testSubjectFieldLengthValidationBoundaries() {
        ContactFormCardComponent form = contactUsPage.getContactFormCard();

        // Step 1: Enter 4 characters and blur -> error message should appear
        List<String> errorsAfterShortInput = form.enterSubject(SHORT_SUBJECT_4_CHARS)
                .triggerSubjectBlur()
                .waitForValidationError(SUBJECT_MIN_LENGTH_ERROR)
                .getValidationErrors();
        Assert.assertTrue(errorsAfterShortInput.contains(SUBJECT_MIN_LENGTH_ERROR),
                "Error should be displayed for under-minimum subject input");

        // Step 2: Enter 5 characters and blur -> validation error should disappear
        List<String> errorsAfterValidInput = form.enterSubject(VALID_SUBJECT_5_CHARS)
                .triggerSubjectBlur()
                .waitForValidationErrorToDisappear(SUBJECT_MIN_LENGTH_ERROR)
                .getValidationErrors();
        Assert.assertFalse(errorsAfterValidInput.contains(SUBJECT_MIN_LENGTH_ERROR),
                "Validation error should disappear for 5-character subject input");

        // Step 3: Enter 100 characters -> all 100 characters accepted
        String hundredChars = "A".repeat(100);
        Assert.assertEquals(form.enterSubject(hundredChars).getSubjectValue().length(), 100,
                "Subject field should accept 100 characters");

        // Step 4: Attempt to enter 101st character -> 101st character blocked
        Assert.assertEquals(form.enterSubject(hundredChars + "B").getSubjectValue().length(), 100,
                "101st character should be blocked by field maximum length");
    }

    @Test(description = "Verify 'Повідомлення' field length validation (min/max boundaries)")
    @Issue("754")
    @Story("Message Field Length Validation")
    public void testMessageFieldLengthValidationBoundaries() {
        ContactFormCardComponent form = contactUsPage.getContactFormCard();

        // Step 1: Enter 9 characters and blur -> error message should appear
        List<String> errorsAfterShortInput = form.enterMessage(SHORT_MESSAGE_9_CHARS)
                .triggerMessageBlur()
                .waitForValidationError(MESSAGE_MIN_LENGTH_ERROR)
                .getValidationErrors();
        Assert.assertTrue(errorsAfterShortInput.contains(MESSAGE_MIN_LENGTH_ERROR),
                "Error should be displayed for under-minimum message input");

        // Step 2: Enter 10 characters and blur -> validation error should disappear
        List<String> errorsAfterValidInput = form.enterMessage(VALID_MESSAGE_10_CHARS)
                .triggerMessageBlur()
                .waitForValidationErrorToDisappear(MESSAGE_MIN_LENGTH_ERROR)
                .getValidationErrors();
        Assert.assertFalse(errorsAfterValidInput.contains(MESSAGE_MIN_LENGTH_ERROR),
                "Validation error should disappear for 10-character message input");

        // Step 3: Enter 2000 characters -> all 2000 characters accepted
        String twoThousandChars = "M".repeat(2000);
        Assert.assertEquals(form.enterMessage(twoThousandChars).getMessageValue().length(), 2000,
                "Message textarea should accept 2000 characters");

        // Step 4: Attempt to enter 2001st character -> 2001st character blocked
        Assert.assertEquals(form.enterMessage(twoThousandChars + "X").getMessageValue().length(), 2000,
                "2001st character should be blocked by textarea maximum length");
    }

    @Test(description = "Verify validation errors for under-minimum input in Subject and Message fields")
    @Issue("757")
    @Story("Under Minimum Input Validation Errors")
    public void testUnderMinimumInputValidationErrors() {
        ContactFormCardComponent form = contactUsPage.getContactFormCard();

        List<String> validationErrors = form.enterSubject(SHORT_SUBJECT_2_CHARS)
                .triggerSubjectBlur()
                .waitForValidationError(SUBJECT_MIN_LENGTH_ERROR)
                .enterMessage(SHORT_MESSAGE_5_CHARS)
                .triggerMessageBlur()
                .waitForValidationError(MESSAGE_MIN_LENGTH_ERROR)
                .getValidationErrors();

        Assert.assertTrue(validationErrors.contains(SUBJECT_MIN_LENGTH_ERROR),
                "Should display minimum length error when subject is shorter than 5 chars");
        Assert.assertTrue(validationErrors.contains(MESSAGE_MIN_LENGTH_ERROR),
                "Should display minimum length error when message is shorter than 10 chars");
    }

    @Test(description = "Verify social media links navigation and target attributes on Contact Us page")
    @Issue("761")
    @Story("Social Media Links Navigation")
    public void testSocialMediaLinksAttributes() {
        ContactDetailsSectionComponent detailsSection = contactUsPage.getContactDetailsSection();

        List<String> socialUrls = detailsSection.getSocialMediaUrls();
        Assert.assertEquals(socialUrls.size(), 3, "Should have 3 social media links");

        for (String url : socialUrls) {
            Assert.assertNotNull(url, "Social link URL should not be null");
            Assert.assertTrue(url.startsWith(HTTPS_PREFIX),
                    "Social link URL should start with https://, found: " + url);
        }

        List<String> socialTargets = detailsSection.getSocialMediaTargets();
        Assert.assertEquals(socialTargets.size(), 3, "Should have 3 social media target attributes");
        for (String target : socialTargets) {
            Assert.assertEquals(target, TARGET_BLANK,
                    "Social media link target attribute should be '_blank', found: " + target);
        }
    }

    @Test(description = "Verify copy email and phone number buttons functionality on Contact Us page")
    @Issue("760")
    @Story("Copy Email and Phone Number")
    public void testCopyEmailAndPhoneButtons() {
        ContactDetailsSectionComponent detailsSection = contactUsPage.getContactDetailsSection();

        CopyTextButtonComponent emailCopyButton = detailsSection.getEmailCopyButton().clickCopy();
        Assert.assertTrue(emailCopyButton.isDisplayed(), "Email copy button should be displayed");
        Assert.assertTrue(emailCopyButton.isEnabled(), "Email copy button should be enabled");

        CopyTextButtonComponent phoneCopyButton = detailsSection.getPhoneCopyButton().clickCopy();
        Assert.assertTrue(phoneCopyButton.isDisplayed(), "Phone copy button should be displayed");
        Assert.assertTrue(phoneCopyButton.isEnabled(), "Phone copy button should be enabled");
    }

    @Test(description = "Verify Contact Us page displays all localized content in English after selecting")
    @Issue("820")
    @Story("Contact Us page in English")
    public void testLocalizationPageEN() {
        HeaderComponent headerComponent =contactUsPage.getHeader();
        LanguageSwitcherComponent languageSwitcher = headerComponent.getLanguageSwitcher();
        languageSwitcher.switchToEn();

        ContactDetailsSectionComponent detailsSection = contactUsPage.getContactDetailsSection();
        SoftAssert softAssert = new SoftAssert();

        // 1. Left Contact Information Section
        softAssert.assertEquals(detailsSection.getTitleText(), EXPECTED_PAGE_TITLE_EN,
                "Heading text should strictly match mockup");
        softAssert.assertEquals(detailsSection.getDescriptionText(), EXPECTED_PAGE_DESCRIPTION_EN,
                "Description text should match mockup");

        softAssert.assertTrue(detailsSection.getEmailText().contains("@"),
                "Contact email should contain @ symbol");
        softAssert.assertTrue(detailsSection.getEmailCopyButton().isDisplayed(),
                "Email copy button should be displayed");

        softAssert.assertTrue(detailsSection.getPhoneText().contains("+"),
                "Contact phone number should contain + symbol");
        softAssert.assertTrue(detailsSection.getPhoneCopyButton().isDisplayed(),
                "Phone copy button should be displayed");

        softAssert.assertFalse(detailsSection.getAddressText().isBlank(),
                "Contact address should be displayed");

        List<String> secondTitles = detailsSection.getSecondTitles();
        softAssert.assertTrue(secondTitles.contains(EXPECTED_OUR_CONTACTS_US_EN), "Second title should contain expected contact us text");
        softAssert.assertTrue(secondTitles.contains(EXPECTED_OUR_SOCIAL_MEDIA_EN), "Social links should expected our social media text");

        softAssert.assertEquals(detailsSection.getAddressText(),EXPECTED_ADDRESS_EN,
                "Contact address text should strictly match mockup");

        softAssert.assertEquals(detailsSection.getSocialMediaLinksCount(), 3,
                "Should display 3 social media links (Facebook, Telegram, Instagram)");
        List<String> socialNames = detailsSection.getSocialMediaNames();
        softAssert.assertTrue(socialNames.contains(FACEBOOK_NAME), "Social links should contain Facebook");
        softAssert.assertTrue(socialNames.contains(TELEGRAM_NAME), "Social links should contain Telegram");
        softAssert.assertTrue(socialNames.contains(INSTAGRAM_NAME), "Social links should contain Instagram");

        // 2. Right Contact Form Section
        ContactFormCardComponent form = contactUsPage.getContactFormCard();
        softAssert.assertTrue(form.areAllFieldsDisplayed(), "All contact form fields and submit button should be displayed");
        softAssert.assertEquals(form.getSubmitButtonText(), SUBMIT_BUTTON_TEXT_EN,
                "Submit button text should match expected");

        List<String> placeholdersText = form.getFormPlaceholdersText();
        softAssert.assertTrue(placeholdersText.contains(YOUR_NAME_PLACEHOLDER_EN), "Placeholder for name should contain translated text");
        softAssert.assertTrue(placeholdersText.contains(EMAIL_PLACEHOLDER_EN), "Placeholder for email should contain translated text");
        softAssert.assertTrue(placeholdersText.contains(SUBJECT_PLACEHOLDER_EN), "Placeholder for subject should contain translated text");
        softAssert.assertTrue(placeholdersText.contains(WRITE_MESSAGE_PLACEHOLDER_EN), "Placeholder for write message field should contain translated text");

        softAssert.assertAll();

    }
}
