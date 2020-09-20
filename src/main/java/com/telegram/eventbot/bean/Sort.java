package com.telegram.eventbot.bean;

public enum Sort {
    RANK("rank"),
    START("start"),
    LOCAL_RANK("local_rank");

    private String url;

    Sort(String envUrl) {
        this.url = envUrl;
    }

    public String getValue() {
        return url;
    }
}
