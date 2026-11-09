package org.academy.models.localization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocalizationLanguageDto {
    private Integer id;
    private String code;
    private String name;
}