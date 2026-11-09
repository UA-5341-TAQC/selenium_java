package org.academy.models.HippotherapyPrograms;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SectionContentDto {
    private Long id;
    private Integer contentType;
    private Integer order;
    private Integer groupIndex;
    private String title;
    private String description;
    private MediaAssetDto image;
    private String author;
    private FaqQuestionDto faqQuestion;
    private List<ContentLocalizationDto> localizations;
}
