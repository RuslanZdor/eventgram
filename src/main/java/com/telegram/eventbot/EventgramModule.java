package com.telegram.eventbot;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.telegram.api.ICommandProcessor;
import com.telegram.eventbot.api.EventAPIClient;
import com.telegram.eventbot.api.PredictHQClient;
import com.telegram.eventbot.command.CommandProcessorImpl;

public class EventgramModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(EventAPIClient.class).to(PredictHQClient.class);
        bind(ICommandProcessor.class).to(CommandProcessorImpl.class);
        bind(ObjectMapper.class).toInstance(new ObjectMapper());
        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.defaultClient();
        bind(AmazonDynamoDB.class).toInstance(amazonDynamoDB);
        bind(DynamoDBMapper.class).toInstance(new DynamoDBMapper(amazonDynamoDB));
    }
}
