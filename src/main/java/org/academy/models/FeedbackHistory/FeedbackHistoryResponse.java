package org.academy.models.FeedbackHistory;

import lombok.Data;

import java.util.List;

@Data
public class FeedbackHistoryResponse {

    private Long id;
    private String title;
    private String story;
    private FeedbackHistoryImage image;
    private Integer priority;
    private Integer status;
    private List<FeedbackHistoryLocalization> localizations;
}
