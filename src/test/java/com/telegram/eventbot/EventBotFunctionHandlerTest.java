package com.telegram.eventbot;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class EventBotFunctionHandlerTest {

    @Test
    public void handleRequest() throws IOException {
        EventBotFunctionHandler handler = new EventBotFunctionHandler();
        InputStream content = this.getClass().getResourceAsStream("/telegramUpdate.json");
        assertEquals("true", handler.handleRequest(content, null));
    }
}