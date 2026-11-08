package org.academy.api;

import io.restassured.response.Response;
import org.academy.models.bankdetails.ProblemDetails;
import org.academy.models.eventnews.EventNewsDto;
import org.academy.models.eventnews.EventNewsDtoPaginationResult;
import org.academy.utils.testrunners.BaseAPITestRunner;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class EventNewsTests extends BaseAPITestRunner {
    private static final int PUBLISHED_STATUS = 1;
    private static final int ALL_ITEMS_LIMIT = 100;
    private static final long NON_EXISTING_ID = 999_999_999L;

    private EventNewsClient publicClient;
    private EventNewsClient adminClient;

    @BeforeClass
    public void beforeClass() {
        publicClient = new EventNewsClient(testEnvProvider.getBaseApiUrl());
        adminClient = new EventNewsClient(testEnvProvider.getBaseApiUrl());
        adminClient.setToken(getAccessToken());
    }


    @Test
    public void testGetPublishedEventNews() {
        Response response = publicClient.getPublishedEventNewsResponse();
        response.then().statusCode(200);

        EventNewsDtoPaginationResult result = publicClient.getPublishedEventNews();
        Assert.assertFalse(result.getItems().isEmpty(), "Published event news list should not be empty");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(result.getTotalItemsCount() > 0, "Total items count should be positive");
        for (EventNewsDto news : result.getItems()) {
            softAssert.assertNotNull(news.getId(), "Id should not be null");
            softAssert.assertNotNull(news.getTitle(), "Title should not be null for id " + news.getId());
            softAssert.assertNotNull(news.getSlug(), "Slug should not be null for id " + news.getId());
            softAssert.assertNotNull(news.getCategory(), "Category should not be null for id " + news.getId());
        }
        softAssert.assertAll();
    }

    @Test
    public void testGetPublishedEventNewsWithLimit() {
        int limit = 2;
        EventNewsDtoPaginationResult all = publicClient.getPublishedEventNews();
        EventNewsDtoPaginationResult limited = publicClient.getPublishedEventNews(null, 0, limit);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(limited.getItems().size() <= limit,
                "Items count should not exceed limit " + limit);
        softAssert.assertEquals(limited.getTotalItemsCount(), all.getTotalItemsCount(),
                "Total items count should not depend on limit");
        softAssert.assertAll();
    }

    @Test
    public void testGetPublishedEventNewsWithOffset() {
        EventNewsDtoPaginationResult firstPage = publicClient.getPublishedEventNews(null, 0, 1);
        EventNewsDtoPaginationResult secondPage = publicClient.getPublishedEventNews(null, 1, 1);
        Assert.assertFalse(firstPage.getItems().isEmpty(), "First page should not be empty");
        Assert.assertFalse(secondPage.getItems().isEmpty(), "Second page should not be empty");

        Assert.assertNotEquals(secondPage.getItems().get(0).getId(), firstPage.getItems().get(0).getId(),
                "Offset 1 should return a different item than offset 0");
    }

    @Test
    public void testGetPublishedEventNewsByCategory() {
        List<EventNewsDto> all = publicClient.getPublishedEventNews().getItems();
        Assert.assertFalse(all.isEmpty(), "Published event news list should not be empty");
        Long categoryId = all.get(0).getCategory().getId();

        List<EventNewsDto> filtered = publicClient.getPublishedEventNews(categoryId, 0, ALL_ITEMS_LIMIT).getItems();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(filtered.isEmpty(), "Filtered list should not be empty");
        for (EventNewsDto news : filtered) {
            softAssert.assertEquals(news.getCategory().getId(), categoryId,
                    "Wrong category for news id " + news.getId());
        }
        softAssert.assertAll();
    }

    @Test
    public void testGetPublishedEventNewsBySlug() {
        List<EventNewsDto> all = publicClient.getPublishedEventNews().getItems();
        Assert.assertFalse(all.isEmpty(), "Published event news list should not be empty");
        EventNewsDto expected = all.get(0);

        Response response = publicClient.getPublishedEventNewsBySlugResponse(expected.getSlug());
        response.then().statusCode(200);

        EventNewsDto actual = publicClient.getPublishedEventNewsBySlug(expected.getSlug());
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actual.getId(), expected.getId(), "Id should match");
        softAssert.assertEquals(actual.getSlug(), expected.getSlug(), "Slug should match");
        softAssert.assertEquals(actual.getTitle(), expected.getTitle(), "Title should match");
        softAssert.assertAll();
    }


    @Test
    public void testGetEventNewsWithoutTokenReturnsUnauthorized() {
        publicClient.getEventNewsResponse().then().statusCode(401);
    }

    @Test
    public void testGetEventNewsAuthenticated() {
        Response response = adminClient.getEventNewsResponse();
        response.then().statusCode(200);

        EventNewsDtoPaginationResult adminResult = adminClient.getEventNews();
        EventNewsDtoPaginationResult publishedResult = publicClient.getPublishedEventNews();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(adminResult.getItems().isEmpty(), "Admin event news list should not be empty");
        softAssert.assertTrue(adminResult.getTotalItemsCount() >= publishedResult.getTotalItemsCount(),
                "Admin list should contain at least all published news");
        softAssert.assertAll();
    }

    @Test
    public void testGetEventNewsFilteredByPublishedStatus() {
        List<EventNewsDto> items = adminClient
                .getEventNews(0, ALL_ITEMS_LIMIT, null, PUBLISHED_STATUS, null)
                .getItems();
        Assert.assertFalse(items.isEmpty(), "Published event news list should not be empty");

        SoftAssert softAssert = new SoftAssert();
        for (EventNewsDto news : items) {
            softAssert.assertEquals(news.getStatus(), Integer.valueOf(PUBLISHED_STATUS),
                    "Wrong status for news id " + news.getId());
        }
        softAssert.assertAll();
    }

    @Test
    public void testGetEventNewsById() {
        List<EventNewsDto> all = adminClient.getEventNews().getItems();
        Assert.assertFalse(all.isEmpty(), "Admin event news list should not be empty");
        EventNewsDto expected = all.get(0);

        Response response = adminClient.getEventNewsByIdResponse(expected.getId());
        response.then().statusCode(200);

        EventNewsDto actual = adminClient.getEventNewsById(expected.getId());
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actual.getId(), expected.getId(), "Id should match");
        softAssert.assertEquals(actual.getTitle(), expected.getTitle(), "Title should match");
        softAssert.assertAll();
    }

    @Test
    public void testGetEventNewsByNonExistingIdReturnsNotFound() {
        Response response = adminClient.getEventNewsByIdResponse(NON_EXISTING_ID);
        response.then().statusCode(404);

        ProblemDetails problem = response.as(ProblemDetails.class);
        Assert.assertEquals(problem.getStatus(), Integer.valueOf(404), "ProblemDetails status should be 404");
    }

    @Test
    public void testSearchEventNewsByTitle() {
        List<EventNewsDto> all = adminClient.getEventNews().getItems();
        Assert.assertFalse(all.isEmpty(), "Admin event news list should not be empty");
        EventNewsDto expected = all.get(0);

        Response response = adminClient.searchEventNewsResponse(expected.getTitle());
        response.then().statusCode(200);

        List<EventNewsDto> found = adminClient.searchEventNews(expected.getTitle(), 0, ALL_ITEMS_LIMIT).getItems();
        Assert.assertTrue(found.stream().anyMatch(news -> news.getId().equals(expected.getId())),
                "Search by title should return news id " + expected.getId());
    }
}
