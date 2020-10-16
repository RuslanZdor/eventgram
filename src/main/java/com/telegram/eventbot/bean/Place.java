package com.telegram.eventbot.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Place {
    private String type;
    private String name;
    private String formatted_address;
    private String entity_id;
}
