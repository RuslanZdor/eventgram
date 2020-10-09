package com.telegram.eventbot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Slf4j
@Configuration
@ComponentScan(basePackages = "com.telegram")
@SpringBootApplication
public class EventBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventBotApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			log.debug("Let's inspect the beans provided by Spring Boot:");
			Arrays.stream(ctx.getBeanDefinitionNames()).forEach(log::debug);
		};
	}

	@Bean
	public EventTelegramComponent stockTelegramComponent() {
		return new EventTelegramComponent();
	}
}
