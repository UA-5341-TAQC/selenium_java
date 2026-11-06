package org.academy.models.faq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FaqQuestionLocalizationResponse {

    private Integer entityId;
    private FaqLocalizationInfoResponse localizationInfoDto;
    private String questionText;
    private String answerText;
    private Integer translationStatus;
}
