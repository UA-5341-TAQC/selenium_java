package org.academy.models.team;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Standard API error details.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProblemDetails {

    private String type;

    private String title;

    private Integer status;

    private String detail;

    private String instance;
}
