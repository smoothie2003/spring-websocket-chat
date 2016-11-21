package com.asapp.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addReturnValueHandlers(
			List<HandlerMethodReturnValueHandler> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configureClientOutboundChannel(ChannelRegistration arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/queue", "/topic");
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public boolean configureMessageConverters(List<MessageConverter> arg0) {
		return true;
	}

	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry arg0) {
		//arg0.addEndpoint("/chatEx", "/activeUsers").withSockJS();
		arg0.addEndpoint("/gs-guide-websocket").withSockJS();

	}

	@Bean
	public ActiveUserService activeUserService() {
		return new ActiveUserService();
	}

}
