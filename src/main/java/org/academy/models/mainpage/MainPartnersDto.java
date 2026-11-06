package org.academy.models.mainpage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MainPartnersDto {
    private Long id;
    private String title;
    private String description;
    private List<MainPartnersLocalizationDto> localizations;
}
