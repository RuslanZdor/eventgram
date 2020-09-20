package com.telegram.eventbot.bean;

import lombok.Data;

import java.util.List;

@Data
public class EventSearchResponse {
    private String next;
    private List<Event> results;
}
