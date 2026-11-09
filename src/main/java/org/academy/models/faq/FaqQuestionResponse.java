package org.academy.models.faq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FaqQuestionResponse {

    private Integer id;
    private String questionText;
    private String answerText;
    private Integer status;
    private List<Integer> pageIds;
    private List<FaqQuestionLocalizationResponse> localizations;
}
