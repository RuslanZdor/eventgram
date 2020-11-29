package com.telegram.eventbot;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.telegram.api.StockTelegramBot;
import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;

public class EventgramModuleTest {

    @Test
    public void configure() {
        Injector injector = Guice.createInjector(new EventgramModule());
        StockTelegramBot bot = injector.getInstance(StockTelegramBot.class);
        Assert.assertFalse(Objects.isNull(bot));
    }
}