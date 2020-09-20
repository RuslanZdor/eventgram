package com.telegram.eventbot.api;

import com.telegram.eventbot.bean.EventSearchResponse;
import reactor.core.publisher.Mono;

/**
 * Imterface for public API to get event information
 */
public interface EventAPIClient {

    Mono<EventSearchResponse> getEvents(EventAPIClientQuery query);

    Mono<EventSearchResponse> getNext(String url);

    EventAPIClientQuery createQuery();
}
