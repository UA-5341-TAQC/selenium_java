package org.academy.models.HippotherapyPrograms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HippotherapyProgramsResponse {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String createdAt;
    //private  previewImage;
    //private List<CategoryDto> categories;
    //private List<LocalizationDto> localizations;
}
