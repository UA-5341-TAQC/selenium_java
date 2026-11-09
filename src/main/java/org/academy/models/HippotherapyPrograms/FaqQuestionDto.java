package org.academy.models.HippotherapyPrograms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FaqQuestionDto {
    private Long id;
    private String questionText;
    private String answerText;
}
