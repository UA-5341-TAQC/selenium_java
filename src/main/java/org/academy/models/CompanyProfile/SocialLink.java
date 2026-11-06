package org.academy.models.CompanyProfile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SocialLink {

    private Integer socialPlatform;
    private String url;
}
