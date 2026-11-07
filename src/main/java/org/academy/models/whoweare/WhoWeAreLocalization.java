package org.academy.models.whoweare;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WhoWeAreLocalization {
    private Long entityId;
    private WhoWeAreLocalizationInfo localizationInfoDto;
    private String title;
    private String description;
    private Integer translationStatus;
}
