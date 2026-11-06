package org.academy.api;

import org.academy.models.EventsPage.EventsPageResponse;
import org.academy.models.EventsPage.PublicEventsPageResponse;
import org.academy.utils.testrunners.BaseAPITestRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class EventsPageTests extends BaseAPITestRunner {

    protected EventsPageClient eventsPageClient;

    @BeforeClass
    public void beforeClass() {
        eventsPageClient = new EventsPageClient(testEnvProvider.getBaseApiUrl());
        eventsPageClient.setToken(getAccessToken());
    }

    @Test
    public void testGetPublicEventsPage() {

        PublicEventsPageResponse response = eventsPageClient.getPublicEventsPage();
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(response, "Public Events Page response should not be null");
        softAssert.assertNotNull(response.getEventsBlockTitle(), "Events block title should not be null");
        softAssert.assertNotNull(response.getPageDescription(), "Page description should not be null");
        softAssert.assertFalse(response.isEventsBlockTitleHidden(),"Events block title should be visible");
        softAssert.assertFalse(response.isPageDescriptionHidden(),"Page description should be visible");
        softAssert.assertNotNull(response.getLocalizations(), "Localizations should not be null");
        softAssert.assertEquals(response.getLocalizations().size(), 1,"Localizations size mismatch");
        softAssert.assertNotNull(response.getCategories(), "Categories should not be null");
        softAssert.assertEquals(response.getCategories().size(), 5,"Categories size mismatch");

        softAssert.assertAll();
    }

    @Test
    public void testGetEventsPage() {

        EventsPageResponse response = eventsPageClient.getEventsPage();
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(response, "Response should not be null");
        softAssert.assertEquals(response.getId(), Long.valueOf(1), "Events Page ID mismatch");
        softAssert.assertEquals(response.getEventsBlockTitle(), "<p>Новини та події</p>", "Events block title mismatch");
        softAssert.assertNotNull(response.getPageDescription(), "Page description should not be null");
        softAssert.assertFalse(response.isEventsBlockTitleHidden(), "Events block title should be visible");
        softAssert.assertFalse(response.isPageDescriptionHidden(), "Page description should be visible");
        softAssert.assertNotNull(response.getLocalizations(), "Localizations should not be null");
        softAssert.assertEquals(response.getLocalizations().size(), 1, "Localizations size mismatch");

        softAssert.assertAll();
    }
}