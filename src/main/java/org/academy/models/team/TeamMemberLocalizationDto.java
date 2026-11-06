package org.academy.models.team;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Response model defined by the VictoryCenter API v1 contract.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamMemberLocalizationDto {

    private Long entityId;

    private LocalizationInfoDto localizationInfoDto;

    private String fullName;

    private String description;

    /** Numeric contract values: 0 or 1; semantic names are not documented. */
    private Integer translationStatus;
}
