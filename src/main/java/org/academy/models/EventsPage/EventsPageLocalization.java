package org.academy.models.EventsPage;

import lombok.Data;

@Data
public class EventsPageLocalization {

    private Long entityId;
    private EventsPageLanguage localizationInfoDto;
    private String eventsBlockTitle;
    private String pageDescription;
    private Integer translationStatus;
}
