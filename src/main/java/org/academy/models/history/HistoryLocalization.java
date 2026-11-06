package org.academy.models.history;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryLocalization {
    private int entityId;
    private int languageId;
    private LocalizationInfoDto localizationInfoDto;
    private String title;
    private String description;
    private int translationStatus;
}
