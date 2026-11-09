package org.academy.models.team;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * One category with published team members returned by GET /api/Team/published.
 * Swagger schema: {@code CategoryWithPublishedTeamMembersDto}.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublishedTeamResponse {

    private Long id;

    private String categoryName;

    private String description;

    private List<TeamCategoryLocalizationDto> localizations;

    private List<PublishedTeamMembersDto> teamMembers;
}
