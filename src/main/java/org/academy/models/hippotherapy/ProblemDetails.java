package org.academy.models.hippotherapy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProblemDetails {
    private String type;
    private String title;
    private Integer status;
    private String detail;
    private String instance;
}