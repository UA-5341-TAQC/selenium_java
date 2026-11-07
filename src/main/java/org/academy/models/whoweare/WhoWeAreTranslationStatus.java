package org.academy.models.whoweare;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WhoWeAreTranslationStatus {
    private Integer languageId;
    private Integer translationStatus;
}