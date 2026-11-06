package org.academy.models.mainpage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocalizedMainPageDto {
    private Long id;
    private Long languageId;
    private String title;
    private String description;
    private ImageDto image;
    private LocalizedMainAboutUsDto mainAboutUs;
    private LocalizedMainPartnersDto mainPartners;
    private LocalizedMainDonationsDto mainDonations;
    private LocalizedImpactStatisticDto impactStatistics;
}
