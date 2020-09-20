package com.telegram.eventbot.api;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes = {EventAPIConfigurationForTest.class})
public class PredictHQClientTest {

    @Autowired
    private PredictHQClient predictHQClient;

    @Test
    public void getEvents() {
//        assertEquals(5, predictHQClient.getEvents(
//                predictHQClient.createQuery().setCity(City.CHICAGO)
//                        .setLimit(5))
//                .single().block().getResults().size());
    }
}