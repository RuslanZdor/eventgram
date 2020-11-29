package com.telegram.eventbot.bean;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@DynamoDBTable(tableName="eventgram_events")
public class Event {
    @DynamoDBHashKey(attributeName="event_id")
    private String id;
    @DynamoDBAttribute(attributeName="title")
    private String title;
    @DynamoDBAttribute(attributeName="description")
    private String description;
    @DynamoDBAttribute(attributeName="start")
    private Date start;
    @DynamoDBAttribute(attributeName="end")
    private Date end;
    @DynamoDBAttribute(attributeName="location")
    private List<String> location;
    @DynamoDBAttribute(attributeName="entities")
    private List<Place> entities;
}
