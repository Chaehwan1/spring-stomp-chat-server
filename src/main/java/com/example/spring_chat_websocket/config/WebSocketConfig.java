package com.example.spring_chat_websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // 1. STOMP 엔드포인트 등록
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 웹소켓 연결을 위한 URL 경로를 지정합니다.
        // 클라이언트는 'ws://localhost:{port}/ws-stomp'로 연결을 요청합니다.
        // SockJS를 활성화하여 웹소켓을 지원하지 않는 환경에서도 폴백(Fallback)할 수 있도록 합니다.
        registry.addEndpoint("/ws-stomp")
                .setAllowedOriginPatterns("*") // 모든 출처(Origin) 허용
                .withSockJS();
    }

    // 2. 메시지 브로커 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 클라이언트에게 메시지를 전달할(발행할) 때 사용하는 접두사입니다.
        // 클라이언트는 '/sub/chat/room/{roomId}' 형식으로 구독합니다.
        config.enableSimpleBroker("/sub");

        // 클라이언트가 서버의 @MessageMapping으로 메시지를 보낼 때 사용하는 접두사입니다.
        // 메시지는 '/pub/chat/message' 형식으로 전송됩니다.
        config.setApplicationDestinationPrefixes("/pub");
    }
}