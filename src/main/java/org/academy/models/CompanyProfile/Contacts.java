package org.academy.models.CompanyProfile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contacts {

    private String phone;
    private String address;
    private String email;
    private String correspondenceEmail;
    private String motto;
    private List<ContactLocalization> localizations;
}
