package org.academy.models.HippotherapyPrograms;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HippotherapyProgramDto {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private Integer status;
    private String location;
    private String participantsCount;
    private String meetingsCount;
    private MediaAssetDto backgroundImage;
    private MediaAssetDto previewImage;
    private List<CategoryDto> categories;
    private List<SectionDto> sections;
    private List<ProgramLocalizationDto> localizations;
}
