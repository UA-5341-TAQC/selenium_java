package org.academy.ui.admin;

import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.academy.ui.components.admin.faq.FaqListItemComponent;
import org.academy.ui.components.admin.faq.FaqTabsComponent;
import org.academy.ui.modals.admin.ConfirmationModal;
import org.academy.ui.modals.admin.DeleteConfirmationModal;
import org.academy.ui.modals.admin.faq.FaqModal;
import org.academy.ui.pages.Admin.AdminFaqPage;
import org.academy.ui.testrunners.UITestRunnerWithAdmin;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public class TestAdminFaqPage extends UITestRunnerWithAdmin {

    private static final String FAQ_PATH = "admin-panel/faq";
    private static final List<String> FAQ_TABS = List.of("Програми", "Про іпотерапію", "Донати");
    private static final String PUBLISHED_STATUS = "Опубліковано";
    private static final String TOOLTIP_TITLE = "Опубліковано на:";
    private static final String DELETE_CONFIRMATION_TEXT = "Питання буде видалено з усіх сторінок сайту";
    private static final String CANCEL_BUTTON_TEXT = "Ні";
    private static final String CONFIRM_BUTTON_TEXT = "Так";
    private static final String TEST_QUESTION = "Autotest question";
    private static final String UNSAVED_CHANGES_TEXT = "Зміни будуть втрачені. Бажаєте продовжити?";
    private static final int QUESTION_LIMIT = 150;
    private static final int ANSWER_LIMIT = 1000;
    private static final String SHORT_QUESTION = "nisiuaise";
    private static final String SHORT_ANSWER = "magna ullamco esse cupidatat id officia ea tempor";
    private static final String QUESTION_MIN_LENGTH_ERROR = "Не менше 10 символів";
    private static final String ANSWER_MIN_LENGTH_ERROR = "Не менше 50 символів";


    private AdminFaqPage faqPage;

    @BeforeMethod
    public void openFaqPage() {
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains("/admin-panel"));

        driver.get(testEnvProvider.getBaseUrl() + FAQ_PATH);
        faqPage = new AdminFaqPage(driver).waitUntilVisible();
    }

    @Test(description = "Verify if the FAQ section can be opened")
    @Issue("986")
    @Story("FAQ in Admin panel")
    public void testFaqSectionCanBeOpened() {
        List<FaqListItemComponent> questions = faqPage.getLoadedQuestions();
        Assert.assertFalse(questions.isEmpty(), "The FAQ question list should not be empty.");

        SoftAssert softAssert = new SoftAssert();
        questions.forEach(faq -> {
            softAssert.assertTrue(faq.isDisplayed(), "The FAQ row should be displayed.");
            softAssert.assertFalse(faq.getQuestion().isBlank(), "The FAQ row should have question text.");
        });
        softAssert.assertAll();
    }

    @Test(description = "Verify tooltip title and assigned web-pages list for Published questions")
    @Issue("984")
    @Story("FAQ in Admin panel")
    public void testPublishedQuestionTooltip() {
        FaqTabsComponent tabs = faqPage.getTabs();
        Assert.assertEquals(tabs.getTabNames(), FAQ_TABS, "The FAQ page should display 3 tabs.");

        SoftAssert softAssert = new SoftAssert();
        for (String tab : FAQ_TABS) {
            tabs.selectTab(tab);

            List<FaqListItemComponent> publishedQuestions = faqPage.getLoadedQuestions().stream()
                    .filter(faq -> PUBLISHED_STATUS.equals(faq.getStatusText()))
                    .toList();
            softAssert.assertFalse(publishedQuestions.isEmpty(),
                    "[" + tab + "] At least one published question should exist.");

            for (FaqListItemComponent faq : publishedQuestions) {
                String question = faq.getQuestion();

                softAssert.assertEquals(faq.getPublicationTooltipTitle(), TOOLTIP_TITLE,
                        "[" + tab + "] Tooltip title is wrong for question: " + question);
                softAssert.assertFalse(faq.getPublicationPages().isEmpty(),
                        "[" + tab + "] Tooltip pages list is empty for question: " + question);

                faq.closeInformation();
            }
        }
        softAssert.assertAll();
    }

    @Test(description = "Verify if the deletion of a published question can be cancelled")
    @Issue("989")
    @Story("FAQ in Admin panel")
    public void testDeletionOfPublishedQuestionCanBeCancelled() {
        FaqTabsComponent tabs = faqPage.getTabs();
        SoftAssert softAssert = new SoftAssert();

        for (String tab : FAQ_TABS) {
            tabs.selectTab(tab);

            FaqListItemComponent faq = faqPage.getLoadedQuestions().stream()
                    .filter(item -> PUBLISHED_STATUS.equals(item.getStatusText()))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("[" + tab + "] No published question found."));
            String question = faq.getQuestion();

            DeleteConfirmationModal modal = faq.openDeleteConfirmation();
            softAssert.assertTrue(modal.getContent().contains(DELETE_CONFIRMATION_TEXT),
                    "[" + tab + "] Confirmation text is wrong.");

            Assert.assertEquals(modal.getCancelText(), CANCEL_BUTTON_TEXT,
                    "[" + tab + "] Cancel button text is unexpected — stopping to avoid deleting data.");
            modal.cancel();

            FaqListItemComponent sameFaq = faqPage.getQuestion(question);
            softAssert.assertEquals(sameFaq.getStatusText(), PUBLISHED_STATUS,
                    "[" + tab + "] Question status changed after cancelling: " + question);
        }
        softAssert.assertAll();
    }

    @Test(description = "Verify unsaved changes pop-up displays after click the X icon in the 'Додати питання' modal")
    @Issue("977")
    @Story("FAQ in Admin panel")
    public void testUnsavedChangesPopUpOnCloseAddQuestionModal() {
        FaqModal faqModal = faqPage.openAddQuestionModal();
        faqModal.setQuestion(TEST_QUESTION);

        ConfirmationModal confirmation = faqModal.requestCloseWithConfirmation()
                .orElseThrow(() -> new AssertionError(
                        "Unsaved changes pop-up should appear after closing a modified form."));

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(confirmation.getTitle().replaceAll("\\s+", " "), UNSAVED_CHANGES_TEXT,
                "Unsaved changes pop-up text is wrong.");
        softAssert.assertEquals(confirmation.getCancelText(), CANCEL_BUTTON_TEXT, "Cancel button text is wrong.");
        softAssert.assertEquals(confirmation.getConfirmText(), CONFIRM_BUTTON_TEXT, "Confirm button text is wrong.");
        softAssert.assertAll();
    }

    @Test(description = "Verify the 'Додати питання' modal closes without confirmation when no changes were made")
    @Issue("976")
    @Story("FAQ in Admin panel")
    public void testAddQuestionModalClosesWithoutConfirmationWhenNoChanges() {
        FaqModal faqModal = faqPage.openAddQuestionModal();

        Optional<ConfirmationModal> confirmation = faqModal.requestCloseWithConfirmation();

        Assert.assertTrue(confirmation.isEmpty(),
                "No confirmation pop-up should appear when closing an unmodified form.");
        Assert.assertFalse(faqPage.getLoadedQuestions().isEmpty(),
                "FAQ page with questions should be displayed after closing the modal.");
    }

    @Test(description = "Verify typing is blocked in 'Питання' and 'Відповідь' fields once the character limit is reached")
    @Issue("973")
    @Story("FAQ in Admin panel")
    public void testCharacterLimitBlocksTyping() {
        FaqModal faqModal = faqPage.openAddQuestionModal();

        faqModal.setQuestion("q".repeat(QUESTION_LIMIT + 10))
                .typeAnswerStepByStep("a".repeat(ANSWER_LIMIT + 10));

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(faqModal.getQuestion().length(), QUESTION_LIMIT,
                "'Питання' field should accept only " + QUESTION_LIMIT + " characters.");
        softAssert.assertEquals(faqModal.getQuestionCounter(), QUESTION_LIMIT + "/" + QUESTION_LIMIT,
                "'Питання' counter is wrong.");
        softAssert.assertEquals(faqModal.getAnswer().length(), ANSWER_LIMIT,
                "'Відповідь' field should accept only " + ANSWER_LIMIT + " characters.");
        softAssert.assertEquals(faqModal.getAnswerCounter(), ANSWER_LIMIT + "/" + ANSWER_LIMIT,
                "'Відповідь' counter is wrong.");
        softAssert.assertAll();
    }

    @Test(description = "Verify minimum length validation in 'Питання' and 'Відповідь' fields in 'Додати питання' modal")
    @Issue("972")
    @Story("FAQ in Admin panel")
    public void testMinimumLengthValidation() {
        FaqModal faqModal = faqPage.openAddQuestionModal();
        SoftAssert softAssert = new SoftAssert();

        faqModal.setQuestion(SHORT_QUESTION);
        softAssert.assertEquals(faqModal.getQuestionCounter(), SHORT_QUESTION.length() + "/" + QUESTION_LIMIT,
                "'Питання' counter is wrong.");
        faqModal.blurQuestion();
        softAssert.assertTrue(faqModal.getValidationErrors().contains(QUESTION_MIN_LENGTH_ERROR),
                "'Питання' min length error is not displayed. Actual errors: " + faqModal.getValidationErrors());

        faqModal.setAnswer(SHORT_ANSWER);
        softAssert.assertEquals(faqModal.getAnswerCounter(), SHORT_ANSWER.length() + "/" + ANSWER_LIMIT,
                "'Відповідь' counter is wrong.");
        faqModal.blurAnswer();
        softAssert.assertTrue(faqModal.getValidationErrors().contains(ANSWER_MIN_LENGTH_ERROR),
                "'Відповідь' min length error is not displayed. Actual errors: " + faqModal.getValidationErrors());

        softAssert.assertAll();
    }


}
