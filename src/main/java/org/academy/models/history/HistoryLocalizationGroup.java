package org.academy.models.history;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryLocalizationGroup {
    private long entityId;
    private List<HistoryLocalization> contents;
}
