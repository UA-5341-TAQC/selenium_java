package org.academy.models.team;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Response model defined by the VictoryCenter API v1 contract.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageDto {

    private Long id;

    private String blobName;

    private String url;

    private String mimeType;

    /** Date-time string as returned by the API. */
    private String createdAt;

    /** Date-time string as returned by the API. */
    private String updatedAt;
}
