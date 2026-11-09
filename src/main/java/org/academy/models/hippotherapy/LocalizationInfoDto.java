package org.academy.models.hippotherapy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocalizationInfoDto {
    private Integer id;
    private String code;
}