package com.telegram.eventbot;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.telegram.api.StockTelegramBot;
import lombok.extern.log4j.Log4j;
import org.apache.commons.io.IOUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Log4j
public class EventBotFunctionHandler implements RequestHandler<InputStream, String> {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Override
	public String handleRequest(InputStream input, Context context) {
		Injector injector = Guice.createInjector(new EventgramModule());
		StockTelegramBot bot = injector.getInstance(StockTelegramBot.class);
		try {
			Update update = MAPPER.readValue(
					IOUtils.toString(input, StandardCharsets.UTF_8), Update.class);
			bot.onUpdateReceived(update);
		} catch (IOException e) {
			log.error("Error to parse event", e);
		}
		return "true";
	}
}
