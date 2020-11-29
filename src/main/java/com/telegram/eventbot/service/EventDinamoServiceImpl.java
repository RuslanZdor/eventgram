package com.telegram.eventbot.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.telegram.eventbot.bean.Event;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Implementation for request events from external API
 * @param <T> Event class
 */
@Log4j
@AllArgsConstructor(onConstructor = @__(@Inject))
public class EventDinamoServiceImpl<T> implements DataService<Event> {

    private final DynamoDBMapper mapper;

    public Optional<Event> get(String id) {
        Event partitionKey = new Event();
        partitionKey.setId(id);
        DynamoDBQueryExpression<Event> queryExpression = new DynamoDBQueryExpression<Event>()
                .withHashKeyValues(partitionKey);
        List<Event> result = mapper.query(Event.class, queryExpression);
        if (result.isEmpty()) {
            log.info(String.format("Event with id %s is nto found", id));
            return Optional.empty();
        } else {
            return Optional.of(result.get(0));
        }
    }

    @Override
    public void save(Event object) {
        mapper.save(object);
    }
}
