package org.academy.models.eventnews;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventNewsDtoPaginationResult {
    private List<EventNewsDto> items;
    private Integer totalItemsCount;
}
