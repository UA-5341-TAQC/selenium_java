package org.academy.models.hippotherapy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProgramCategoryLocalizationDto {
    private Long entityId;
    private LocalizationInfoDto localizationInfoDto;
    private String name;
    private Integer translationStatus;
}