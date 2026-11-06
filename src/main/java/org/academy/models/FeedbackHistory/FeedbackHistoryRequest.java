package org.academy.models.FeedbackHistory;

import lombok.Data;

@Data
public class FeedbackHistoryRequest {

    private String title;
    private String story;
    private Long imageId;
    private Integer status;
}
