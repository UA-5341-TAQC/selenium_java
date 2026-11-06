package org.academy.api;

import org.academy.models.EventsPage.EventsPageResponse;
import org.academy.models.EventsPage.PublicEventsPageResponse;

import java.util.Map;

public class EventsPageClient extends BaseClient {

    private static final String ENDPOINT = "/EventsPage";

    public EventsPageClient(String baseAPIUrl) {
        super(baseAPIUrl);
    }

    public PublicEventsPageResponse getPublicEventsPage() {
        return prepareRequest()
                .get(ENDPOINT + "/public")
                .then()
                .statusCode(200)
                .extract()
                .as(PublicEventsPageResponse.class);
    }

    public EventsPageResponse getEventsPage() {
        return prepareRequest()
                .get(ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .as(EventsPageResponse.class);
    }

    public EventsPageResponse updateDescription(String description) {
        return prepareRequest()
                .body(Map.of("pageDescription", description))
                .put(ENDPOINT + "/description")
                .then()
                .statusCode(200)
                .extract()
                .as(EventsPageResponse.class);
    }

    public EventsPageResponse updateEventsBlockTitle(String title) {
        return prepareRequest()
                .body(Map.of("eventsBlockTitle", title))
                .put(ENDPOINT + "/events-block-title")
                .then()
                .statusCode(200)
                .extract()
                .as(EventsPageResponse.class);
    }

    public EventsPageResponse toggleEventsBlockTitleVisibility() {
        return prepareRequest()
                .post(ENDPOINT + "/events-block-title/toggle-visibility")
                .then()
                .statusCode(200)
                .extract()
                .as(EventsPageResponse.class);
    }

    public EventsPageResponse toggleDescriptionVisibility() {
        return prepareRequest()
                .post(ENDPOINT + "/description/toggle-visibility")
                .then()
                .statusCode(200)
                .extract()
                .as(EventsPageResponse.class);
    }
}
