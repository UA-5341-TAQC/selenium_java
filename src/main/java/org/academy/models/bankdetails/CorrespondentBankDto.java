package org.academy.models.bankdetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CorrespondentBankDto {
    private Long id;
    private String name;
    private String swift;
    private String account;
    private String foreignIban;
    private Long foreignBankDetailsId;
}
