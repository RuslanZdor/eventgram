package com.telegram.eventbot;

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
    }
}
