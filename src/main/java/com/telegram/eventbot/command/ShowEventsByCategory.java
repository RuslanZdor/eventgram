package com.telegram.eventbot.command;

import com.telegram.eventbot.api.EventAPIClient;
import com.telegram.eventbot.api.EventAPIClientQuery;
import com.telegram.eventbot.bean.Category;
import lombok.extern.log4j.Log4j;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Search events by specific category
 */
@Log4j
public class ShowEventsByCategory extends ShowEventsCommand {

    private final static String COMMAND = "categorysearch";

    @Inject
    public ShowEventsByCategory(EventAPIClient predictHQClient) {
        super(predictHQClient);
    }

    /**
     * Search all events for specific city for one category
     *
     * @return search query for specific events
     */
    @Override
    EventAPIClientQuery search(String text) {
        EventAPIClientQuery query = super.search(text);
        findCategory(text).ifPresent( category -> {
            log.info(String.format("Search for specific category %s", category.getValue()));
            query.setCategory(category);
        });
        return query;
    }

    /**
     * extract category name from telegram command
     *
     * @param text of message
     * @return category if it exist
     */
    static Optional<Category> findCategory(String text) {
        String[] words = splitMessage(text);
        if (words.length > 1 && Category.find(words[1]).isPresent()) {
            return Optional.of(Category.find(words[1]).get());
        }
        return Optional.empty();
    }

    @Override
    public String getCommand() {
        return COMMAND;
    }
}
