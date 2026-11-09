package org.academy.models.eventnews;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventNewsCategoryShortDto {
    private Long id;
    private String name;
}
