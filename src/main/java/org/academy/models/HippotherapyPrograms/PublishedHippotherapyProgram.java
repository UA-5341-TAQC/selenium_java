package org.academy.models.HippotherapyPrograms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublishedHippotherapyProgram {

    private Long id;
    private String name;
    private String slug;
    private String description;
    private OffsetDateTime createdAt;
    private PreviewImageDto previewImage;
    private List<CategoryDto> categories;
    private List<LocalizationDto> localizations;
}
