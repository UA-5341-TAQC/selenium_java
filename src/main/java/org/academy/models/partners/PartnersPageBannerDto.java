package org.academy.models.partners;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.academy.models.mainpage.ImageDto;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PartnersPageBannerDto {
    private Long id;
    private String title;
    private String description;
    private ImageDto image;
}
