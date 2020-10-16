package com.telegram.eventbot.api;

import com.telegram.eventbot.bean.Category;
import com.telegram.eventbot.bean.City;
import com.telegram.eventbot.bean.Sort;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Log4j
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PredictHQQuery implements EventAPIClientQuery {

    private static final String LIMIT_PARAMETER = "limit";
    private static final String LOCATION_PARAMETER = "place.exact";
    private static final String CATEGORY_PARAMETER = "category";
    private static final String SORT_PARAMETER = "sort";
    private static final String START_PARAMETER = "start.gte";
    private static final String ID_PARAMETER = "id";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private UriBuilder uriBuilder = new DefaultUriBuilderFactory().builder();

    private Map<String, Object> queryParam = new HashMap<>();

    @Override
    public PredictHQQuery setCity(City city) {
        queryParam.put(LOCATION_PARAMETER, city.getCoordinates());
        return this;
    }

    @Override
    public PredictHQQuery setLimit(int limit) {
        queryParam.put(LIMIT_PARAMETER, limit);
        return this;
    }

    @Override
    public EventAPIClientQuery setCategory(Category category) {
        queryParam.put(CATEGORY_PARAMETER, category.getValue());
        return this;
    }

    @Override
    public EventAPIClientQuery setSort(Sort sort) {
        queryParam.put(SORT_PARAMETER, sort.getValue());
        return this;
    }

    @Override
    public EventAPIClientQuery setStart(LocalDate date) {
        queryParam.put(START_PARAMETER, date.format(DATE_TIME_FORMATTER));
        return this;
    }

    @Override
    public EventAPIClientQuery setId(String id) {
        queryParam.put(ID_PARAMETER, id);
        return this;
    }

    public void apply(UriBuilder uriBuilder) {
        log.info(String.format("Parameters for search are %s", queryParam.toString()));
        queryParam.forEach((key, value) -> {
            uriBuilder.queryParam(key, value);
        });
    }
}
