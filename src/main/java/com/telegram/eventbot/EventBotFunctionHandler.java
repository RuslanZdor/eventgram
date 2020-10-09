package com.telegram.eventbot;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.model.HttpApiV2ProxyRequest;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventBotFunctionHandler implements RequestHandler<HttpApiV2ProxyRequest, AwsProxyResponse> {

	private static SpringBootLambdaContainerHandler<HttpApiV2ProxyRequest, AwsProxyResponse> handler;
	static {
		try {
			handler = SpringBootLambdaContainerHandler.getHttpApiV2ProxyHandler(EventBotApplication.class);
		} catch (ContainerInitializationException e) {
			log.error("Could not initialize Spring Boot application", e);
			throw new RuntimeException("Could not initialize Spring Boot application", e);
		}
	}

	@Override
	public AwsProxyResponse handleRequest(HttpApiV2ProxyRequest httpApiV2ProxyRequest, Context context) {
		return handler.proxy(httpApiV2ProxyRequest, context);
	}
}
