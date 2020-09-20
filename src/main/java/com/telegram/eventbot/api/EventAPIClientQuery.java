package com.telegram.eventbot.api;

import com.telegram.eventbot.bean.Category;
import com.telegram.eventbot.bean.City;
import com.telegram.eventbot.bean.Sort;

import java.time.LocalDate;

public interface EventAPIClientQuery {

    EventAPIClientQuery setCity(City city);
    EventAPIClientQuery setLimit(int limit);
    EventAPIClientQuery setCategory(Category category);
    EventAPIClientQuery setSort(Sort sort);
    EventAPIClientQuery setStart(LocalDate date);
    EventAPIClientQuery setId(String id);
}
