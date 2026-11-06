package org.academy.models.history;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistorySection {
    private int id;
    private int template;
    private int order;
    private List<HistoryContent> contents;
}
