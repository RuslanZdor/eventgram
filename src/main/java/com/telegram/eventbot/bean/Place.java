package com.telegram.eventbot.bean;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@DynamoDBDocument
public class Place {
    @DynamoDBAttribute(attributeName="type")
    private String type;
    @DynamoDBAttribute(attributeName="name")
    private String name;
    @DynamoDBAttribute(attributeName="formatted_address")
    private String formatted_address;
    @DynamoDBAttribute(attributeName="entity_id")
    private String entity_id;
}
