package org.academy.models.bankdetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupportOptionDto {
    private Long id;
    private String name;
    private String value;
    private BankCurrency currency;
}
