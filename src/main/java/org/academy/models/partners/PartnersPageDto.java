package org.academy.models.partners;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PartnersPageDto {
    private Long id;
    private PublicPartnersPageBannerDto banner;
    private List<PublicPartnersSectionDto> sections;
}
