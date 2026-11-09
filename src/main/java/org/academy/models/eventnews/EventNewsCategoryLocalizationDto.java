package org.academy.models.eventnews;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventNewsCategoryLocalizationDto {
    private Long entityId;
    private LocalizationInfoDto language;
    private String name;
    private Integer translationStatus;
}