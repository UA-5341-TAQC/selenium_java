package org.academy.models.FeedbackHistory;

import lombok.Data;

@Data
public class FeedbackHistoryImage {

    private Long id;
    private String blobName;
    private String url;
    private String mimeType;
    private String createdAt;
    private String updatedAt;
}
