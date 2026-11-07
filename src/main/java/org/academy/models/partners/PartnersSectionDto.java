package org.academy.models.partners;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PartnersSectionDto {
    private Long id;
    private Integer order;
    private String title;
    private String description;
    private List<PartnerDto> partners;
}
