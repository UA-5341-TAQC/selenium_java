package org.academy.models.faq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FaqVisitorPageResponse {

    private Integer id;
    private String slug;
    private String title;
}
