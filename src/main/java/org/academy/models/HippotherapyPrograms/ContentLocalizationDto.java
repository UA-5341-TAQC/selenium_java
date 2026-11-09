package org.academy.models.HippotherapyPrograms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentLocalizationDto {
    private Long entityId;
    private LocalizationInfoDto localizationInfoDto;
    private String title;
    private String description;
    private String author;
    private String question;
    private String answer;
    private Integer translationStatus;
}
