package org.academy.models.CompanyProfile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequisitesLocalization {

    private String recipient;
    private String address;
    private Integer entityId;
    private Integer languageId;
}