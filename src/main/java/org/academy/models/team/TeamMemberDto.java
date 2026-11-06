package org.academy.models.team;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Team member returned by the VictoryCenter API v1.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamMemberDto {

    private Long id;

    private String fullName;

    private Long categoryId;

    private Long priority;

    /** Numeric contract values: 0 or 1; semantic names are not documented. */
    private Integer status;

    private String description;

    private ImageDto image;

    private List<TeamMemberLocalizationDto> localizations;
}
