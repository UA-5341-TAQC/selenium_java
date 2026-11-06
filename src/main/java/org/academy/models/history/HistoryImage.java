package org.academy.models.history;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryImage {
    private int id;
    private String blobName;
    private String url;
    private String mimeType;
    private String createdAt;
    private String updatedAt;
}
