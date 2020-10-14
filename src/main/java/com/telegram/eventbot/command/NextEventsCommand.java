package com.telegram.eventbot.command;

import com.telegram.api.ICommandProcessor;
import com.telegram.eventbot.NextLinkStorage;
import com.telegram.eventbot.api.EventAPIClient;
import com.telegram.eventbot.bean.Event;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor(onConstructor = @__(@Inject))
public class NextEventsCommand extends ICommandProcessor {

    private final static String COMMAND = "next";

    private final EventAPIClient predictHQClient;

    @Override
    public void process(Update update, Function<PartialBotApiMethod<Message>, PartialBotApiMethod<Message>> callback) {
        try {
            predictHQClient.getNext(getNextURL(getText(update)))
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
                    }, () -> {
                    });
        } catch (IllegalStateException ex) {
            log.error("Session is expired");
            callback.apply(createSendMessage(getMessage(update).getChatId(), "Session is expired"));
        }
    }

    /**
     * extract category name from telegram command
     *
     * @param text of message
     * @return category if it exist
     */
    static String getNextURL(String text) throws IllegalStateException {
        String[] words = text.split("(\\s+)");
        log.info(text);
        if (words.length > 1) {
            Optional<String> url = NextLinkStorage.getURL(Integer.parseInt(words[1]));
            return url.orElseThrow(IllegalStateException::new);
        }
        throw new IllegalStateException();
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
