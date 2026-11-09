package org.academy.models.bankdetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForeignBankDetailsDto {
    private Long id;
    private String name;
    private String receiver;
    private String ukrainianIban;
    private String swift;
    private String address;
    private BankCurrency currency;
    private List<CorrespondentBankDto> correspondentBanks;
}
