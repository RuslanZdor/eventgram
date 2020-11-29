package com.telegram.eventbot.service;

import com.telegram.eventbot.bean.Event;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EventServiceImplTest {

    private DataService<Event> eventService;

    private EventAPIServiceImpl<Event> eventAPIServiceImpl;
    private EventDinamoServiceImpl<Event> eventDinamoServiceImpl;

    private static final String ID = "1";

    @Before
    public void init() {
        eventAPIServiceImpl = mock(EventAPIServiceImpl.class);
        eventDinamoServiceImpl = mock(EventDinamoServiceImpl.class);
        eventService = new EventServiceImpl<Event>(eventAPIServiceImpl,
                eventDinamoServiceImpl);
    }

    @Test
    public void getDatastoreFound() {
        Event event = new Event();
        event.setId(ID);
        when(eventDinamoServiceImpl.get(ID)).thenReturn(Optional.of(event));
        Assert.assertTrue(eventService.get(ID).isPresent());
        Assert.assertEquals(event, eventService.get(ID).get());
        Assert.assertEquals(ID, eventService.get(ID).get().getId());
    }

    @Test
    public void getDatastoreNotFound() {
        Event event = new Event();
        event.setId(ID);
        when(eventDinamoServiceImpl.get(ID)).thenReturn(Optional.empty());
        when(eventAPIServiceImpl.get(ID)).thenReturn(Optional.of(event));
        Assert.assertTrue(eventService.get(ID).isPresent());
        Assert.assertEquals(event, eventService.get(ID).get());
        Assert.assertEquals(ID, eventService.get(ID).get().getId());
    }

    @Test
    public void getDatastoreNotFoundAPInotFound() {
        Event event = new Event();
        event.setId(ID);
        when(eventDinamoServiceImpl.get(ID)).thenReturn(Optional.empty());
        when(eventAPIServiceImpl.get(ID)).thenReturn(Optional.empty());
        Assert.assertFalse(eventService.get(ID).isPresent());
    }
}