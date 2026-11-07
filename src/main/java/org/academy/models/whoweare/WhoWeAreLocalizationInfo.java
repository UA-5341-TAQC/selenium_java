package org.academy.models.whoweare;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WhoWeAreLocalizationInfo {
    private Integer id;
    private String code;
}
