package com.telegram.eventbot.service;

import com.telegram.eventbot.bean.Event;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.inject.Inject;
import java.util.Optional;

/**
 * COmbine dinamoDB and API requests to get event details information
 * @param <T> event class
 */
@Log4j
@AllArgsConstructor(onConstructor = @__(@Inject))
public class EventServiceImpl<T> implements DataService<Event> {

    private final EventAPIServiceImpl<Event> eventAPIServiceImpl;
    private final EventDinamoServiceImpl<Event> eventEventDinamoService;

    /**
     * Check if this event is already stored in datastore, if not - use API to get information
     * @param id to search
     * @return event details
     */
    public Optional<Event> get(String id) {
        Optional<Event> result = eventEventDinamoService.get(id);
        if (result.isPresent()) {
            return result;
        }
        result = eventAPIServiceImpl.get(id);
        result.ifPresent(eventEventDinamoService::save);
        return result;
    }

    /**
     * Save object into datastore
     * @param object to save
     */
    @Override
    public void save(Event object) {
        eventEventDinamoService.save(object);
    }
}
