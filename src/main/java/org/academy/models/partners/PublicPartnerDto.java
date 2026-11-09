package org.academy.models.partners;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.academy.models.mainpage.ImageDto;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublicPartnerDto {
    private Long id;
    private String name;
    private String description;
    private ImageDto image;
    private String url;
    private Integer order;
}
