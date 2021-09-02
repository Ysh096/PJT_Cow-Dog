package com.xy.config;
//import com.xy.service.WebSocketHandler;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableWebSocketMessageBroker
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		//sub로 '방' 구독
//		registry.enableSimpleBroker("/cowdog/sub");
		registry.enableSimpleBroker("/sub");
		//pub로 해당 '방' 메세지 전송
//		registry.setApplicationDestinationPrefixes("/cowdog/pub");
		registry.setApplicationDestinationPrefixes("/pub");
	}
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		//링크 "/chat" 에 연결되는 SockJS생성
//		registry.addEndpoint("/cowdog/chat").withSockJS();
		
		//크로스 오리진 설정, 위 라인 크로스 오리진 오류시에 아래 라인으로 대체 예정
		registry.addEndpoint("/chat").setAllowedOriginPatterns("*").withSockJS();
//		registry.addEndpoint("/chat").setAllowedOrigins("http://localhost:8080").withSockJS();
		
//		registry.addEndpoint("/cowdog/chat").setAllowedOrigins("*");
	}
}