package org.academy.models.team;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Response model defined by the VictoryCenter API v1 contract.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocalizationInfoDto {

    private Long id;

    private String code;
}
