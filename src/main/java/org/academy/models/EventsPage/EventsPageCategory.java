package org.academy.models.EventsPage;

import lombok.Data;

import java.util.List;

@Data
public class EventsPageCategory {

    private Long id;
    private String name;
    private List<EventsPageCategoryLocalization> localizations;
}
