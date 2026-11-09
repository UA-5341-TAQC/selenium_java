package org.academy.models.FeedbackHistory;

import lombok.Data;

import java.util.List;

@Data
public class FeedbackHistoriesSearchResponse {

    private List<FeedbackHistoryResponse> items;
    private Integer totalItemsCount;
}
