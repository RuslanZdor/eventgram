package com.telegram.eventbot.command;

import com.telegram.api.ICommandProcessor;
import com.telegram.api.exception.UnexpectedCommandException;
import lombok.extern.log4j.Log4j;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.inject.Inject;
import java.util.function.Function;

/**
 * default implementation for command processor
 */
@Log4j
public class CommandProcessorImpl extends ICommandProcessor {

    private final StartCommand startCommand;

    /**
     * required constructor for dependency injection
     */
    @Inject
    public CommandProcessorImpl(ShowEventsCommand showEventsCommand,
                                ShowEventsByCategory showEventsByCategory,
                                NextEventsCommand nextEventsCommand,
                                EventDetailCommand eventDetailCommand,
                                StartCommand startCommand) {
        registerCommand(showEventsCommand);
        registerCommand(showEventsByCategory);
        registerCommand(nextEventsCommand);
        registerCommand(eventDetailCommand);
        this.startCommand = startCommand;
    }

    /**
     * Search command base on telegram message, in case of wrong command - send default answer
     * @param update message from telegram
     * @param callback function to send result
     */
    @Override
    public void process(Update update, Function<PartialBotApiMethod<Message>, PartialBotApiMethod<Message>> callback) {
        try {
            log.info(String.format("processing command %s", update.toString()));
            ICommandProcessor processor = findCommand(update);
            processor.process(update, callback);
        } catch (UnexpectedCommandException ex) {
            log.error("Wrong command to process", ex);
            startCommand.process(update, callback);
        }
    }

    /**
     * command name
     * @return command name
     */
    public String getCommand() {
        return "implementation";
    }

}
