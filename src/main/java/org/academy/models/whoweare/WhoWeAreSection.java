package org.academy.models.whoweare;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WhoWeAreSection {
    private Integer sectionType;
    private String title;
    private List<WhoWeAreContent> contents;
}