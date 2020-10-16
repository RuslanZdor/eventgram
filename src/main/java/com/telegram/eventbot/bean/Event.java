package com.telegram.eventbot.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

    private String id;
    private String title;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    private List<String> location;
    private List<Place> entities;
}
