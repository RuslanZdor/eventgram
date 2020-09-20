package com.telegram.eventbot.bean;

import lombok.Data;

@Data
public class Place {
    private String type;
    private String name;
    private String formatted_address;
    private String entity_id;
}
