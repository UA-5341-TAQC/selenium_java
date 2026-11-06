package org.academy.models.EventsPage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class EventsPageResponse {
    private Long id;
    private String eventsBlockTitle;
    private String pageDescription;

    @JsonProperty("isEventsBlockTitleHidden")
    private boolean isEventsBlockTitleHidden;

    @JsonProperty("isPageDescriptionHidden")
    private boolean isPageDescriptionHidden;
    private List<EventsPageLocalization> localizations;
}
