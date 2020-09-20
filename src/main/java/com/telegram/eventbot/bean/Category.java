package com.telegram.eventbot.bean;

import java.util.Arrays;
import java.util.Optional;

public enum Category {
    SCHOOL_HOLIDAYS("school-holidays"),
    PUBLIC_HOLIDAYS("public-holidays"),
    OBSERVANCE("observances"),
    POLITIC("politics"),
    CONFERENCE("conferences"),
    EXPO("expos"),
    CONCERT("concerts"),
    FESTIVALS("festivals"),
    PERFORMING_ART("performing-arts"),
    SPORT("sports"),
    COMMUNITY("community"),
    DAYLIGHT_SAVING("daylight-savings"),
    AIRPORT_DELAY("airport-delays"),
    SEVERE_WEATHER("severe-weather"),
    DISASTER("disasters"),
    TERROR("terror");

    private String value;

    Category(String envUrl) {
        this.value = envUrl;
    }

    public String getValue() {
        return value;
    }

    public static Optional<Category> find(String value) {
        return Arrays.stream(Category.values()).filter(category -> category.getValue().equals(value)).findFirst();
    }
}

