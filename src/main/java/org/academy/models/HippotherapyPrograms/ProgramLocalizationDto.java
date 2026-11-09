package org.academy.models.HippotherapyPrograms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProgramLocalizationDto {
    private String name;
    private String description;
    private String location;
    private String participantsCount;
    private String meetingsCount;
    private Long entityId;
    private LocalizationInfoDto localizationInfoDto;
    private Integer translationStatus;
    private List<LocalizedSectionDto> sections;
}
