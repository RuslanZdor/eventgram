package com.telegram.eventbot;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.telegram.api.StockTelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

@Slf4j
public class EventBotFunctionHandler implements RequestHandler<String, String> {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Override
	public String handleRequest(String event, Context context) {
		Injector injector = Guice.createInjector(new EventgramModule());
		StockTelegramBot bot = injector.getInstance(StockTelegramBot.class);
		try {
			Update update = MAPPER.readValue(event, Update.class);
			bot.onUpdateReceived(update);
		} catch (IOException e) {
			log.error("Error to parse event", e);
		}
		return "true";
	}
}
