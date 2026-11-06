package org.academy.models.team;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Response model defined by the VictoryCenter API v1 contract.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamCategoryDto {

    private Long id;

    private String name;

    private String description;

    private Long teamMembersCount;

    private List<TeamCategoryLocalizationDto> localizations;
}
