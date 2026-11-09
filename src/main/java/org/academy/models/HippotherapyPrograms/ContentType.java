package org.academy.models.HippotherapyPrograms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentType {

    private Integer id;
    private Integer contentType;
    private Integer order;
    private Integer groupIndex;
    private String title;
    private String description;

}
