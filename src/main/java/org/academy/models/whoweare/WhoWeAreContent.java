package org.academy.models.whoweare;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WhoWeAreContent {
    @JsonProperty("$type")
    private String type;
    private String title;
    private String description;
    private WhoWeAreImage image;
    private Long id;
    private Integer contentType;
    private List<WhoWeAreLocalization> localizations;
}
