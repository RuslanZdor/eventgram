package com.telegram.eventbot.api;

import com.telegram.eventbot.bean.EventSearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import javax.inject.Singleton;

/**
 * Implementation for PredictHQ service
 */
@Slf4j
@Component
@Singleton
public class PredictHQClient implements EventAPIClient {

    private final WebClient webClient;

    public PredictHQClient() {
        webClient = WebClient
                .builder()
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().followRedirect(true)
                ))
                .baseUrl("https://api.predicthq.com/v1/events")
                .build();
    }

    @Override
    public Mono<EventSearchResponse> getEvents(EventAPIClientQuery query) {
        return webClient.get().uri(uriBuilder -> {
            ((PredictHQQuery) query).apply(uriBuilder);
            return uriBuilder.build();
        }).header(HttpHeaders.AUTHORIZATION, "Bearer KzbKTASZMcXE38mkinEydlQOq45wLmxLyqBlMI7H")
                .retrieve()
                .bodyToMono(EventSearchResponse.class);
    }

    @Override
    public Mono<EventSearchResponse> getNext(String url) {
        return webClient.get().uri(url).header(HttpHeaders.AUTHORIZATION, "Bearer KzbKTASZMcXE38mkinEydlQOq45wLmxLyqBlMI7H")
                .retrieve()
                .bodyToMono(EventSearchResponse.class);
    }

    @Override
    public EventAPIClientQuery createQuery() {
        return new PredictHQQuery();
    }
}
