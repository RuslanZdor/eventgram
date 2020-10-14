package com.telegram.eventbot.command;

import com.telegram.api.ICommandProcessor;
import com.telegram.api.exception.UnexpectedCommandException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class CommandProcessorImpl extends ICommandProcessor {

    private final ShowEventsCommand showEventsCommand;
    private final ShowEventsByCategory showEventsByCategory;
    private final NextEventsCommand nextEventsCommand;
    private final EventDetailCommand eventDetailCommand;
    private final StartCommand startCommand;

    @Override
    public void process(Update update, Function<PartialBotApiMethod<Message>, PartialBotApiMethod<Message>> callback) {
        try {
            ICommandProcessor processor = findCommand(update);
            processor.process(update, callback);
        } catch (UnexpectedCommandException ex) {
            log.error("Wrong command to process", ex);
            startCommand.process(update, callback);
        }
    }

    @PostConstruct
    public void init() {
        registerCommand(showEventsCommand);
        registerCommand(showEventsByCategory);
        registerCommand(nextEventsCommand);
        registerCommand(eventDetailCommand);
    }

    /**
     * command name
     * @return command name
     */
    public String getCommand() {
        return "implementation";
    }

}
