package org.academy.models.mainpage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocalizedImpactStatisticDto {
    private Long id;
    private String title;
    private ImageDto image;
    private List<LocalizedMetricDto> metrics;
}
