package org.academy.models.FeedbackHistory;

import lombok.Data;

@Data
public class FeedbackHistoryLocalization {

    private Long entityId;
    private FeedbackHistoryLanguage language;
    private String title;
    private String story;
    private Integer translationStatus;
}
