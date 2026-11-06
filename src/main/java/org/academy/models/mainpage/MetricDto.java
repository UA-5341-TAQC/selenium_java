package org.academy.models.mainpage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetricDto {
    private Long id;
    private Integer value;
    private String name;
    private MetricType type;
    private MetricPrefixNullable prefix;
    private Boolean isAutoSynced;
    private Boolean isHidden;
    private Long priority;
    private String rowVersion;
    private List<MetricLocalizationDto> localizations;
}
