package org.academy.models.HippotherapyPrograms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaAssetDto {
    private Long id;
    private String blobName;
    private String url;
    private String mimeType;
    private String createdAt;
    private String updatedAt;
}
