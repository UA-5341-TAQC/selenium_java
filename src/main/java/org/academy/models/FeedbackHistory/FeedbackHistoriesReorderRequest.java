package org.academy.models.FeedbackHistory;

import lombok.Data;

import java.util.List;

@Data
public class FeedbackHistoriesReorderRequest {

    private List<Long> orderedIds;
}
