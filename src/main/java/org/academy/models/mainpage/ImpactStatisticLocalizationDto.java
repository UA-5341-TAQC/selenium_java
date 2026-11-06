package org.academy.models.mainpage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImpactStatisticLocalizationDto {
    private String title;
    private Long entityId;
    private Long languageId;
    private LocalizationInfoDto localizationInfoDto;
}
