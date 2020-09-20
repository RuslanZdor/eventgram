package com.telegram.eventbot.command;

import com.telegram.api.ICommandProcessor;
import com.telegram.eventbot.NextLinkStorage;
import com.telegram.eventbot.api.EventAPIClient;
import com.telegram.eventbot.api.EventAPIClientQuery;
import com.telegram.eventbot.bean.City;
import com.telegram.eventbot.bean.Event;
import com.telegram.eventbot.bean.Sort;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;
import java.util.function.Function;

/**
 * Show top 10 events provided by PredictHQ API
 */
@Slf4j
@Component
@AllArgsConstructor
public class ShowEventsCommand extends ICommandProcessor {

    private final static String COMMAND = "showevents";

    @Getter
    private final EventAPIClient predictHQClient;

    @Override
    public void process(Update update, Function<PartialBotApiMethod<Message>, PartialBotApiMethod<Message>> callback) {
        predictHQClient.getEvents(search(getText(update)))
        .subscribe(eventSearchResponse -> {
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
        }, throwable -> {
            log.error("Communication error with predictHQ service", throwable);
            callback.apply(createSendMessage(getMessage(update).getChatId(), "Error, Cannot find any events"));
        }, () -> {});
    }

    /**
     * Search all events for specific city
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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("<b>%s</b>\n", event.getTitle()));
        stringBuilder.append(String.format("Date and time: <i>%s - %s</i>\n", event.getStart().toString(), event.getEnd().toString()));
        return stringBuilder.toString();
    }
}
