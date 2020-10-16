package com.telegram.eventbot.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventSearchResponse {
    private String next;
    private List<Event> results;
}
