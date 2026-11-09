package org.academy.models.eventnews;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminEventNewsCategoryDto {
    private Long id;
    private String name;
    private String createdAt;
    private Integer relatedEventNewsCount;
    private List<EventNewsCategoryLocalizationDto> localizations;
}