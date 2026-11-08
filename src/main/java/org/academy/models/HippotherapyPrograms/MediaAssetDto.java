package org.academy.models.HippotherapyPrograms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaAssetDto {
    private Long id;
    private String blobName;
    private String url;
    private String mimeType;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
