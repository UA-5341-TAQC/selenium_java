package org.academy.models.mainpage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MainDonationsDto {
    private Long id;
    private String title;
    private String description;
    private ImageDto image;
    private List<MainDonationsLocalizationDto> localizations;
}
