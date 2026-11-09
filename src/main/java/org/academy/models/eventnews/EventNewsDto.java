package org.academy.models.eventnews;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventNewsDto {
    private Long id;
    private String title;
    private String description;
    private String additionalDescription;
    private String slug;
    private String resource;
    private String resourceEn;
    private String publishedAt;
    private ImageDto previewImage;
    private EventNewsCategoryShortDto category;
    private List<EventNewsLocalizationDto> localizations;
    private Integer status;
}
