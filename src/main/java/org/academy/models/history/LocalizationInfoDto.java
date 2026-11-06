package org.academy.models.history;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocalizationInfoDto {
    private int id;
    private String code;
}
