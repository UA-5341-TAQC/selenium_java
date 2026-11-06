package org.academy.models.history;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryContent {
    private int id;
    private int contentType;
    private int order;
    private String title;
    private String description;
    private HistoryImage image;
    private List<HistoryLocalization> localizations;
}
