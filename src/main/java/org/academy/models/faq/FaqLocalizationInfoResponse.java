package org.academy.models.faq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FaqLocalizationInfoResponse {

    private Integer id;
    private String code;
}
