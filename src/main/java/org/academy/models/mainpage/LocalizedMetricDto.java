package org.academy.models.mainpage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocalizedMetricDto {
    private Long id;
    private String value;
    private String name;
    private MetricType type;
    private MetricPrefixNullable prefix;
    private Boolean isAutoSynced;
    private Boolean isHidden;
    private Long priority;
}
