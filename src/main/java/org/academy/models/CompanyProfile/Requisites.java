package org.academy.models.CompanyProfile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Requisites {

    private String recipient;
    private String edrpou;
    private String address;
    private List<RequisitesLocalization> localizations;
}
