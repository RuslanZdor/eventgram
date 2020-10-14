package com.telegram.eventbot.command;

import com.telegram.api.ICommandProcessor;
import com.telegram.eventbot.bean.Category;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Search events by specific category
 */
@Slf4j
public class StartCommand extends ICommandProcessor {

    private final static String COMMAND = "start";


    @Override
    public void process(Update update, Function<PartialBotApiMethod<Message>, PartialBotApiMethod<Message>> callback) {
        SendMessage sendMessage = createSendMessage(getMessage(update).getChatId(), "Please choose category to search");
        sendMessage.setReplyMarkup(createButtons(Arrays.stream(Category.values()).collect(Collectors.toMap(Category::name, category -> String.format("categorysearch %s", category.getValue())))));
        callback.apply(sendMessage);
    }

    @Override
    public String getCommand() {
        return COMMAND;
    }
}
