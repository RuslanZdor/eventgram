package com.telegram.eventbot.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telegram.eventbot.bean.EventSearchResponse;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PredictHQClientTest {

    @Test
    public void getEvents() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        EventSearchResponse response = mapper.readValue(IOUtils.toString(this.getClass().getResourceAsStream("/events.json")
                , StandardCharsets.UTF_8), EventSearchResponse.class);
    }
}