package com.telegram.eventbot;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class EventBotFunctionHandlerTest {

    @Test
    public void handleRequest() throws IOException {
        EventBotFunctionHandler handler = new EventBotFunctionHandler();
        String content = IOUtils.toString(
                this.getClass().getResourceAsStream("/telegramUpdate.json"),
                "UTF-8"
        );
        assertEquals("true", handler.handleRequest(content, null));
    }
}