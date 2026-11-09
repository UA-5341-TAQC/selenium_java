package org.academy.models.reportprogram;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportProgramExpendituresRecordDto {
    private Long id;
    private Integer reportingYear;
    private Long hippotherapyProgramCategoryId;
    private BigDecimal amountUah;
    private BigDecimal amountUsd;
}