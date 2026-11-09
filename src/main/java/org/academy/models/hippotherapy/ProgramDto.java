package org.academy.models.hippotherapy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProgramDto {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private Integer status;
    private String location;
    private String participantsCount;
    private String meetingsCount;
}