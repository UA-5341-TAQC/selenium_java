package org.academy.models.mainpage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImpactStatisticDto {
    private Long id;
    private String title;
    private ImageDto image;
    private List<MetricDto> metrics;
    private List<ImpactStatisticLocalizationDto> localizations;
}
