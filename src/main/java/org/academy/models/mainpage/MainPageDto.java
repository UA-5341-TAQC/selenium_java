package org.academy.models.mainpage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MainPageDto {
    private Long id;
    private String title;
    private String description;
    private ImageDto image;
    private MainAboutUsDto mainAboutUs;
    private MainPartnersDto mainPartners;
    private MainDonationsDto mainDonations;
    private ImpactStatisticDto impactStatistics;
    private List<MainPageLocalizationDto> localizations;
}
