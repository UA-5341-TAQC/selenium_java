package org.academy.models.team;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Response model defined by the VictoryCenter API v1 contract.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublishedTeamMembersDto {

    private Long id;

    private String fullName;

    private String description;

    private ImageDto image;

    private List<TeamMemberLocalizationDto> localizations;
}
