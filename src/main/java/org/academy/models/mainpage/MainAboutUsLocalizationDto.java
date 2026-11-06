package org.academy.models.mainpage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MainAboutUsLocalizationDto {
    private String title;
    private String description;
    private Long entityId;
    private LocalizationInfoDto localizationInfoDto;
    private Integer translationStatus;
}
