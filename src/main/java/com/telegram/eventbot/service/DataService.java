package com.telegram.eventbot.service;

import java.util.Optional;

public interface DataService<T> {
    Optional<T> get(String id);
    void save(T object);
}
