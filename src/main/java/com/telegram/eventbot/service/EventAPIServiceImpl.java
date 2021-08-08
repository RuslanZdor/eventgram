package com.telegram.eventbot.service;

import com.telegram.eventbot.api.PredictHQClient;
import com.telegram.eventbot.bean.Event;
import com.telegram.eventbot.bean.EventSearchResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementation for request events from external API
 * @param <T> Event class
 */
@Log4j
@AllArgsConstructor(onConstructor = @__(@Inject))
public class EventAPIServiceImpl<T> implements DataService<Event> {

    private final PredictHQClient predictHQClient;

    /**
     * Request for specific event by id, contain addition event details
     * @param id to search
     * @return event with details
     */
    @Override
    public Optional<Event> get(String id) {
        EventSearchResponse response = predictHQClient.getEvents(predictHQClient.createQuery()
                .setId(id)).block();
        if (Objects.isNull(response) || Objects.isNull(response.getResults()) || response.getResults().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(response.getResults().get(0));
    }

    @Override
    public void save(Event object) {
        throw new UnsupportedOperationException();
    }
}
