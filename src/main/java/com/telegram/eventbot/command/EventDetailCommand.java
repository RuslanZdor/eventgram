package com.telegram.eventbot.command;

import com.telegram.eventbot.api.EventAPIClient;
import com.telegram.eventbot.api.EventAPIClientQuery;
import com.telegram.eventbot.bean.Event;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.inject.Inject;
import java.util.Optional;
import java.util.function.Function;

/**
 * Show top 10 events provided by PredictHQ API
 */
@Slf4j
public class EventDetailCommand extends ShowEventsCommand {

    private final static String COMMAND = "event";

    @Inject
    public EventDetailCommand(EventAPIClient predictHQClient) {
        super(predictHQClient);
    }

    @Override
    public void process(Update update, Function<PartialBotApiMethod<Message>, PartialBotApiMethod<Message>> callback) {
        getId(getText(update)).ifPresent(id -> {
            getPredictHQClient().getEvents(search(id))
                    .subscribe(eventSearchResponse -> {
                        eventSearchResponse.getResults().forEach(event -> {
                            SendMessage sendMessage = createSendMessage(getMessage(update).getChatId(), buildEventMessage(event));
                            callback.apply(sendMessage);
                        });
                    }, throwable -> {
                        log.error("Communication error with predictHQ service", throwable);
                        callback.apply(createSendMessage(getMessage(update).getChatId(), "Error, Cannot find any events"));
                    }, () -> { }
                    );
        });
    }

    /**
     * Search all events for specific city for one category
     *
     * @return search query for specific events
     */
    @Override
    EventAPIClientQuery search(String id) {
        return getPredictHQClient().createQuery()
                .setId(id);
    }

    private static Optional<String> getId(String text) {
        String[] words = splitMessage(text);
        if (words.length < 2 || words[1].length() == 0) {
            return Optional.empty();
        }
        return Optional.of(words[1]);
    }

    @Override
    public String getCommand() {
        return COMMAND;
    }

    private String buildEventMessage(Event event) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("<b>%s</b>\n", event.getTitle()));
        stringBuilder.append(String.format("Date and time: <i>%s - %s</i>\n", event.getStart().toString(), event.getEnd().toString()));
        if (StringUtils.isNotBlank(event.getDescription())) {
            stringBuilder.append(String.format("Description: %s\n", event.getDescription()));
        }
        stringBuilder.append(String.format("<b>Location</b>\n"));
        if (!event.getEntities().isEmpty()) {
            stringBuilder.append(String.format("Address: %s\n", event.getEntities().get(0).getFormatted_address()));
        }
        if (event.getLocation().size() > 1) {
            stringBuilder.append(String.format("Map: <a href=\"https://maps.google.com/?q=%s,%s\">Google Map</a>\n", event.getLocation().get(1), event.getLocation().get(0)));
        }
        return stringBuilder.toString();
    }
}
