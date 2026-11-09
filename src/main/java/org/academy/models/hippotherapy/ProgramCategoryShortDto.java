package org.academy.models.hippotherapy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProgramCategoryShortDto {
    private Long id;
    private String name;
    private String createdAt;
    private List<ProgramDto> programs;
    private List<ProgramCategoryLocalizationDto> localizations;
}