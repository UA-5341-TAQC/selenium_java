package org.academy.models.CompanyProfile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Localization {
    public Integer entityId;
    public String address;
    public Integer languageId;
    public String motto;
    public String recipient;
}
