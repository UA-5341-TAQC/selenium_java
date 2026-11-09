package org.academy.models.eventnews;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventNewsLocalizationDto {
    private LanguageDto language;
    private String title;
    private String description;
    private String additionalDescription;
    private Integer translationStatus;
}
