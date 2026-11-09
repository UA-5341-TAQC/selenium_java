package org.academy.models.HippotherapyPrograms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SectionDto {
    private Long id;
    private Long programId;
    private Integer template;
    private Integer order;
    private List<SectionContentDto> contents;
}
