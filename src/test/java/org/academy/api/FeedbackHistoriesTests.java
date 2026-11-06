package org.academy.api;

import io.restassured.response.Response;
import org.academy.models.FeedbackHistory.FeedbackHistoryResponse;
import org.academy.utils.testrunners.BaseAPITestRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class FeedbackHistoriesTests extends BaseAPITestRunner {

    protected FeedbackHistoriesClient feedbackHistoriesClient;

    @BeforeClass
    public void beforeClass() {
        feedbackHistoriesClient = new FeedbackHistoriesClient(testEnvProvider.getBaseApiUrl());
        feedbackHistoriesClient.setToken(getAccessToken());
    }

    @Test
    public void testGetPublishedFeedbackHistories() {
        List<FeedbackHistoryResponse> response =
                feedbackHistoriesClient.getPublishedFeedbackHistories();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(response, "Published histories should not be null");
        softAssert.assertTrue(response.stream().allMatch(history -> history.getId() != null), "History ID should not be null");
        softAssert.assertTrue(response.stream().allMatch(history -> history.getLocalizations() != null), "Localizations should not be null");
        softAssert.assertAll();
    }

    @Test
    public void testGetLatestPublishedFeedbackHistories() {
        List<FeedbackHistoryResponse> response = feedbackHistoriesClient.getLatestPublishedFeedbackHistories();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(response, "Latest published histories should not be null");
        softAssert.assertTrue(response.stream().allMatch(history -> history.getId() != null), "History ID should not be null");
        softAssert.assertAll();
    }

    @DataProvider(name = "translationStatusFilters")
    public Object[][] translationStatusFilters() {
        return new Object[][]{
                {0},
                {1},
                {2},
                {null}
        };
    }

    @Test(dataProvider = "translationStatusFilters")
    public void testGetFeedbackHistoriesByTranslationStatus(Integer translationStatusFilter) {

        List<FeedbackHistoryResponse> feedbackHistories = feedbackHistoriesClient.getFeedbackHistories(translationStatusFilter);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(feedbackHistories, "Feedback histories list should not be null");
        softAssert.assertAll();
    }

    @DataProvider(name = "invalidSearchQueries")
    public Object[][] invalidSearchQueries() {
        return new Object[][]{
                {"к", 0, 10, "SearchQuery field must have a minimum length of 3 characters"},
                {null, 0, 10, "The SearchQuery field is required."}
        };
    }

    @Test(dataProvider = "invalidSearchQueries")
    public void testSearchFeedbackHistoriesWithInvalidQuery(String searchQuery, Integer offset, Integer limit, String expectedMessage) {

        Response response = feedbackHistoriesClient.searchFeedbackHistories(searchQuery, offset, limit);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 400, "Status code should be 400");

        String actualMessage = searchQuery == null ? response.jsonPath().getString("errors.SearchQuery[0]") : response.jsonPath().getString("detail");
        softAssert.assertEquals(actualMessage, expectedMessage, "Validation message is incorrect");
        softAssert.assertAll();
    }
}
