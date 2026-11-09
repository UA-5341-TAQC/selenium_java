package org.academy.models.HippotherapyPrograms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryLocalizationDto {

    private Long entityId;
    private LocalizationInfoDto localizationInfoDto;
    private String name;
    private Integer translationStatus;
}
