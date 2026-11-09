package org.academy.models.faq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FaqQuestionPageResponse {

    private List<FaqQuestionResponse> items;
    private Integer totalItemsCount;
}
