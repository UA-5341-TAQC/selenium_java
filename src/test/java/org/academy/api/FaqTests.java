package org.academy.api;

import io.restassured.response.Response;
import org.academy.models.faq.FaqQuestionPageResponse;
import org.academy.models.faq.FaqQuestionResponse;
import org.academy.models.faq.FaqVisitorPageResponse;
import org.academy.models.faq.PublishedFaqQuestionResponse;
import org.academy.utils.testrunners.BaseAPITestRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class FaqTests extends BaseAPITestRunner {

    private FaqClient adminClient;
    private FaqClient publicClient;

    @BeforeClass
    public void beforeClass() {
        adminClient = new FaqClient(testEnvProvider.getBaseApiUrl());
        publicClient = new FaqClient(testEnvProvider.getBaseApiUrl());
        adminClient.setToken(getAccessToken());
    }

    @Test(description = "API-FAQ-001 - Get FAQ pages")
    public void testGetFaqPages() {
        List<FaqVisitorPageResponse> pages = adminClient.getPages();

        assertNotNull(pages, "FAQ pages response should not be null");
        assertTrue(!pages.isEmpty(), "FAQ pages should not be empty");

        FaqVisitorPageResponse page = pages.getFirst();
        assertNotNull(page, "First FAQ page should not be null");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(page.getId(), "Page ID should not be null");
        softAssert.assertNotNull(page.getSlug(), "Page slug should not be null");
        softAssert.assertNotNull(page.getTitle(), "Page title should not be null");
        softAssert.assertAll();
    }

    @Test(description = "API-FAQ-002 - Published questions are available without a token")
    public void testGetPublishedQuestions() {
        SoftAssert softAssert = new SoftAssert();
        int publishedCount = 0;

        for (FaqVisitorPageResponse page : adminClient.getPages()) {
            List<PublishedFaqQuestionResponse> questions = publicClient.getPublishedQuestions(page.getSlug());
            publishedCount += questions.size();

            for (PublishedFaqQuestionResponse question : questions) {
                softAssert.assertNotNull(question, "Published question should exist for " + page.getSlug());
                if (question == null) {
                    continue;
                }
                softAssert.assertTrue(question.getId() != null && question.getId() > 0, "Published question ID");
                softAssert.assertTrue(question.getQuestionText() != null && !question.getQuestionText().isBlank(),
                        "Published question text for ID " + question.getId());
                softAssert.assertTrue(question.getAnswerText() != null && !question.getAnswerText().isBlank(),
                        "Published answer text for ID " + question.getId());
            }
        }

        softAssert.assertTrue(publishedCount > 0, "Expected published questions on at least one page");
        softAssert.assertAll();
    }

    @Test(description = "API-FAQ-003 - FAQ list respects the limit")
    public void testGetQuestions() {
        FaqQuestionPageResponse page = adminClient.getQuestions(0, 1);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(page.getItems() != null && page.getItems().size() == 1, "Limit should be one question");
        softAssert.assertTrue(page.getTotalItemsCount() != null && page.getTotalItemsCount() > 0, "Total question count");
        softAssert.assertAll();
    }

    @Test(description = "API-FAQ-004 - FAQ details contain the requested question")
    public void testGetQuestionById() {
        int id = firstQuestion().getId();
        FaqQuestionResponse question = adminClient.getQuestionDetails(id);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(question.getId(), Integer.valueOf(id), "Question ID");
        softAssert.assertNotNull(question.getQuestionText(), "Question text");
        softAssert.assertNotNull(question.getAnswerText(), "Answer text");
        softAssert.assertNotNull(question.getStatus(), "Question status");
        softAssert.assertAll();
    }

    @Test(description = "API-FAQ-005 - FAQ filters by page")
    public void testFilterByPage() {
        int pageId = firstQuestion().getPageIds().getFirst();
        List<FaqQuestionResponse> questions = adminClient.getQuestionsByPage(pageId, 10).getItems();
        assertTrue(questions != null && !questions.isEmpty(), "Page filter should return questions");

        SoftAssert softAssert = new SoftAssert();
        for (FaqQuestionResponse question : questions) {
            softAssert.assertTrue(question.getPageIds() != null && question.getPageIds().contains(pageId),
                    "Page filter mismatch for question " + question.getId());
        }
        softAssert.assertAll();
    }

    @Test(description = "API-FAQ-006 - FAQ filters by status")
    public void testFilterByStatus() {
        Integer status = firstQuestion().getStatus();
        List<FaqQuestionResponse> questions = adminClient.getQuestionsByStatus(status, 10).getItems();
        assertTrue(questions != null && !questions.isEmpty(), "Status filter should return questions");

        SoftAssert softAssert = new SoftAssert();
        for (FaqQuestionResponse question : questions) {
            softAssert.assertEquals(question.getStatus(), status, "Status filter mismatch for question " + question.getId());
        }
        softAssert.assertAll();
    }

    @Test(description = "API-FAQ-007 - FAQ filters by page and status")
    public void testFilterByPageAndStatus() {
        FaqQuestionResponse seed = firstQuestion();
        int pageId = seed.getPageIds().getFirst();
        Integer status = seed.getStatus();
        List<FaqQuestionResponse> questions = adminClient.getQuestionsByPageAndStatus(pageId, status, 10).getItems();
        assertTrue(questions != null && !questions.isEmpty(), "Combined filter should return questions");

        SoftAssert softAssert = new SoftAssert();
        for (FaqQuestionResponse question : questions) {
            softAssert.assertEquals(question.getStatus(), status, "Status mismatch for question " + question.getId());
            softAssert.assertTrue(question.getPageIds() != null && question.getPageIds().contains(pageId),
                    "Page mismatch for question " + question.getId());
        }
        softAssert.assertAll();
    }

    @Test(description = "API-FAQ-008 - Search finds an existing FAQ")
    public void testSearchFindsQuestion() {
        FaqQuestionResponse question = firstQuestion();
        FaqQuestionPageResponse results = adminClient.searchQuestions(question.getQuestionText(), 0, 10);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(results.getItems() != null && results.getItems().stream()
                        .anyMatch(item -> question.getId().equals(item.getId())),
                "Search should include question " + question.getId());
        softAssert.assertAll();
    }

    @Test(description = "API-FAQ-009 - Unknown search text returns no FAQs")
    public void testSearchWithoutMatches() {
        FaqQuestionPageResponse results = adminClient.searchQuestions("qa-missing-faq-" + UUID.randomUUID(), 0, 1);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(results.getItems() != null && results.getItems().isEmpty(), "Search results should be empty");
        softAssert.assertEquals(results.getTotalItemsCount(), Integer.valueOf(0), "Search result count");
        softAssert.assertAll();
    }

    @Test(description = "API-FAQ-010 - Empty search returns 400")
    public void testEmptySearchReturnsBadRequest() {
        adminClient.searchQuestionsResponse("", 0, 1).then().statusCode(400);
    }

    @Test(description = "API-FAQ-011 - Invalid FAQ ID returns 404")
    public void testInvalidIdReturnsNotFound() {
        adminClient.getQuestion("abc").then().statusCode(404);
    }

    @DataProvider
    public Object[][] protectedReads() {
        return new Object[][] {
                {"/Faq/pages", (Function<FaqClient, Response>) FaqClient::getPagesResponse},
                {"/Faq", (Function<FaqClient, Response>) client -> client.getQuestionsResponse(0, 1)},
                {"/Faq/{id}", (Function<FaqClient, Response>) client -> client.getQuestion(1)},
                {"/Faq/search", (Function<FaqClient, Response>) client -> client.searchQuestionsResponse("faq", 0, 1)}
        };
    }

    @Test(dataProvider = "protectedReads", description = "API-FAQ-012 - Administrative FAQ requires a token")
    public void testProtectedReadRequiresToken(String endpoint, Function<FaqClient, Response> read) {
        Response response = read.apply(publicClient);
        org.testng.Assert.assertEquals(response.statusCode(), 401, "GET " + endpoint);
    }

    private FaqQuestionResponse firstQuestion() {
        List<FaqQuestionResponse> questions = adminClient.getQuestions(0, 1).getItems();
        assertTrue(questions != null && !questions.isEmpty(), "Expected a FAQ question for test data");
        return questions.getFirst();
    }
}
