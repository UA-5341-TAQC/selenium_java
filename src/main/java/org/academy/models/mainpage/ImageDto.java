package org.academy.models.mainpage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageDto {
    private Long id;
    private String blobName;
    private String url;
    private String mimeType;
    private String createdAt;
    private String updatedAt;
}
