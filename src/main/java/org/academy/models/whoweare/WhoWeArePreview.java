package org.academy.models.whoweare;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WhoWeArePreview {
    private Long id;
    private String sectionType;
    private List<WhoWeAreTranslationStatus> translationStatuses;
    private String title;
}