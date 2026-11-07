package org.academy.models.bankdetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UahBankDetailsDto {
    private Long id;
    private String name;
    private String receiver;
    private String edrpou;
    private String ukrainianIban;
    private String paymentPurpose;
}
