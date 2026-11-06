package org.academy.models.EventsPage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PublicEventsPageResponse {

    private String eventsBlockTitle;
    private String pageDescription;

    @JsonProperty("isEventsBlockTitleHidden")
    private boolean isEventsBlockTitleHidden;

    @JsonProperty("isPageDescriptionHidden")
    private boolean isPageDescriptionHidden;
    private List<PublicEventsPageLocalization> localizations;
    private List<EventsPageCategory> categories;
}
