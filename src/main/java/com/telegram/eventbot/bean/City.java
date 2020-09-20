package com.telegram.eventbot.bean;

public enum City {
    CHICAGO("ORD");

    private String url;

    City(String envUrl) {
        this.url = envUrl;
    }

    public String getCoordinates() {
        return url;
    }
}
