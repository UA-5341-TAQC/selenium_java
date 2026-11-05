package org.academy.models.CompanyProfile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyProfileResponse {

    private Contacts contacts;
    private Requisites requisites;
    private List<SocialLink> socialLinks;
}
