package com.telegram.eventbot;

import com.telegram.api.StockTelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class EventTelegramComponent {

    @Autowired
    StockTelegramBot telegramBot;

    @PostConstruct
    public void startBot() throws TelegramApiRequestException {
        TelegramBotsApi botsApi = new TelegramBotsApi();
        botsApi.registerBot(telegramBot);
    }

    @PreDestroy
    public void stopBot() {
        telegramBot.onClosing();
    }
}
