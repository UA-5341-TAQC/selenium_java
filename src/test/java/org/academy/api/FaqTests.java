package org.academy.api;

import io.restassured.path.json.JsonPath;
import org.academy.utils.testrunners.BaseAPITestRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

public class FaqTests extends BaseAPITestRunner {
    private FaqClient faqClient;

    @BeforeClass
    public void setUpFaqClient() {
        faqClient = new FaqClient(testEnvProvider.getBaseApiUrl());
        faqClient.setToken(getAccessToken());
    }

    @Test
    public void publishedQuestionsCanBeReadForConfiguredPages() {
        List<String> slugs = faqClient.getPages().then()
                .statusCode(200)
                .body("$", not(empty()))
                .body("id", everyItem(greaterThan(0)))
                .body("slug", everyItem(notNullValue()))
                .extract().jsonPath().getList("slug", String.class);

        FaqClient publicClient = new FaqClient(testEnvProvider.getBaseApiUrl());
        for (String slug : slugs) {
            publicClient.getPublishedQuestions(slug).then()
                    .statusCode(200)
                    .body("$", instanceOf(List.class))
                    .body("id", everyItem(greaterThan(0)));
        }
    }

    @Test
    public void questionsCanBeListedAndReadById() {
        JsonPath page = faqClient.getQuestions(0, 1).then()
                .statusCode(200)
                .body("items", hasSize(1))
                .body("totalItemsCount", greaterThan(0))
                .extract().jsonPath();
        long id = page.getLong("items[0].id");

        JsonPath question = faqClient.getQuestion(id).then()
                .statusCode(200)
                .extract().jsonPath();
        assertEquals(question.getLong("id"), id, "The detail response should match the requested FAQ ID");
    }

    @Test
    public void adjacentPagesContainDifferentQuestions() {
        long firstId = getQuestionPage(0).getLong("items[0].id");
        long secondId = getQuestionPage(1).getLong("items[0].id");

        assertNotEquals(firstId, secondId, "Adjacent pages should contain different FAQ IDs");
    }

    @Test
    public void questionsCanBeFilteredByPage() {
        long pageId = getQuestionPage(0).getLong("items[0].pageIds[0]");

        List<List<Number>> pageIds = faqClient.getQuestionsByPage(pageId, 10).then()
                .statusCode(200)
                .body("items", not(empty()))
                .extract().jsonPath().getList("items.pageIds");

        assertQuestionsBelongToPage(pageIds, pageId);
    }

    @Test
    public void questionsCanBeFilteredByStatus() {
        int status = getQuestionPage(0).getInt("items[0].status");

        faqClient.getQuestionsByStatus(status, 10).then()
                .statusCode(200)
                .body("items", not(empty()))
                .body("items.status", everyItem(is(status)));
    }

    @Test
    public void searchFindsAnExistingQuestion() {
        JsonPath page = getQuestionPage(0);
        String questionText = page.getString("items[0].questionText");
        long id = page.getLong("items[0].id");

        List<Long> matches = faqClient.searchQuestions(questionText, 0, 10).then()
                .statusCode(200)
                .extract().jsonPath().getList("items.id", Long.class);

        assertTrue(matches.contains(id), "Search should return the FAQ matching the question text");
    }

    @DataProvider
    public Object[][] questionIdsReturningNotFound() {
        return new Object[][] {
                {"abc"},
                {Long.toString(Long.MAX_VALUE)}
        };
    }

    @Test(dataProvider = "questionIdsReturningNotFound")
    public void questionIdReturnsNotFound(String id) {
        faqClient.getQuestion(id).then().statusCode(404);
    }

    @Test
    public void searchReturnsAnEmptyPageForUnknownText() {
        faqClient.searchQuestions("qa-missing-faq-" + UUID.randomUUID(), 0, 1).then()
                .statusCode(200)
                .body("items", empty())
                .body("totalItemsCount", is(0));
    }

    @Test
    public void negativeOffsetReturnsFirstPage() {
        assertEquals(getQuestionPage(-1).getLong("items[0].id"),
                getQuestionPage(0).getLong("items[0].id"));
    }

    @Test
    public void zeroLimitReturnsAllQuestions() {
        JsonPath page = faqClient.getQuestions(0, 0).then()
                .statusCode(200)
                .extract().jsonPath();

        assertEquals((long) page.getList("items").size(), page.getLong("totalItemsCount"));
    }

    @Test
    public void emptySearchQueryIsRejected() {
        faqClient.searchQuestions("", 0, 1).then().statusCode(400);
    }

    @Test
    public void questionsCanBeFilteredByPageAndStatus() {
        JsonPath firstPage = getQuestionPage(0);
        long pageId = firstPage.getLong("items[0].pageIds[0]");
        int status = firstPage.getInt("items[0].status");

        List<List<Number>> pageIds = faqClient.getQuestionsByPageAndStatus(pageId, status, 10).then()
                .statusCode(200)
                .body("items", not(empty()))
                .body("items.status", everyItem(is(status)))
                .extract().jsonPath().getList("items.pageIds");

        assertQuestionsBelongToPage(pageIds, pageId);
    }

    private JsonPath getQuestionPage(int offset) {
        return faqClient.getQuestions(offset, 1).then()
                .statusCode(200)
                .body("items", hasSize(1))
                .extract().jsonPath();
    }

    private void assertQuestionsBelongToPage(List<List<Number>> pageIds, long pageId) {
        pageIds.forEach(ids -> assertTrue(ids.stream().anyMatch(id -> id.longValue() == pageId),
                "Each question should belong to the requested page"));
    }
}
