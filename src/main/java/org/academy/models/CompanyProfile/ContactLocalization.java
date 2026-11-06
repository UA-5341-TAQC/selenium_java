package org.academy.models.CompanyProfile;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactLocalization {

    private String address;
    private String motto;
    private Integer entityId;
    private Integer languageId;
}