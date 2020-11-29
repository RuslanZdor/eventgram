package com.telegram.eventbot.command;

import com.telegram.api.ICommandProcessor;
import com.telegram.eventbot.NextLinkStorage;
import com.telegram.eventbot.api.EventAPIClient;
import com.telegram.eventbot.api.EventAPIClientQuery;
import com.telegram.eventbot.bean.City;
import com.telegram.eventbot.bean.Event;
import com.telegram.eventbot.bean.EventSearchResponse;
import com.telegram.eventbot.bean.Sort;
import com.telegram.eventbot.service.EventServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.function.Function;

/**
 * Show top 10 events provided by PredictHQ API
 */
@Log4j
@AllArgsConstructor(onConstructor = @__(@Inject))
public class ShowEventsCommand extends ICommandProcessor {

    private final static String COMMAND = "showevents";

    @Getter
    private final EventAPIClient predictHQClient;
    @Getter
    private final EventServiceImpl<Event> eventService;

    @Override
    public void process(Update update, Function<PartialBotApiMethod<Message>, PartialBotApiMethod<Message>> callback) {
        log.info("Before search");
        EventSearchResponse eventSearchResponse = predictHQClient.getEvents(search(getText(update))).block();
        log.info(String.format("Seqrch finished %s", eventSearchResponse));
        assert eventSearchResponse != null;
        if (eventSearchResponse.getResults().isEmpty()) {
            SendMessage sendMessage = createSendMessage(getMessage(update).getChatId(), "No events was found");
            callback.apply(sendMessage);
        }
        eventSearchResponse.getResults().forEach(event -> {
            SendMessage sendMessage = createSendMessage(getMessage(update).getChatId(), buildEventMessage(event));
            sendMessage.setReplyMarkup(createButton("Show information", String.format("event %s", event.getId())));
            callback.apply(sendMessage);
        });
        if (StringUtils.isNoneBlank(eventSearchResponse.getNext())) {
            SendMessage sendMessage = createSendMessage(getMessage(update).getChatId(), "To see more events use button below");
            sendMessage.setReplyMarkup(createButton("More Events", String.format("next %s", NextLinkStorage.saveURL(eventSearchResponse.getNext()))));
            callback.apply(sendMessage);
        }
    }

    /**
     * Search all events for specific city
     *
     * @return search query for all events
     */
    EventAPIClientQuery search(String text) {
        return predictHQClient.createQuery()
                .setCity(City.CHICAGO)
                .setLimit(5)
                .setSort(Sort.START)
                .setStart(LocalDate.now());
    }

    @Override
    public String getCommand() {
        return COMMAND;
    }

    private String buildEventMessage(Event event) {
        return String.format("<b>%s</b>\n", event.getTitle()) +
                String.format("Date and time: <i>%s - %s</i>\n", event.getStart().toString(), event.getEnd().toString());
    }
}
