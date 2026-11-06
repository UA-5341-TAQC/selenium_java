package org.academy.models.team;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Paginated team members returned by list and search endpoints.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamMemberDtoPaginationResult {

    private List<TeamMemberDto> items;

    private Long totalItemsCount;
}
