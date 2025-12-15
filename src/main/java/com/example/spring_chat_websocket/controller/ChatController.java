package com.example.spring_chat_websocket.controller;

import com.example.spring_chat_websocket.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    // SimpMessageSendingOperations: 메시지 브로커를 통해 메시지를 전송하는 데 사용되는 핵심 객체
    private final SimpMessageSendingOperations messagingTemplate;

    /**
     * 클라이언트로부터 /pub/chat/message 경로로 메시지가 전송되면 이 메서드가 처리합니다.
     * 클라이언트가 보낸 메시지는 ChatMessage 객체로 바인딩됩니다.
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        System.out.println(">>> 메시지 수신 (발신자: " + message.getSender() + ", 내용: " + message.getMessage() + ")");

        // 1. 유저 입장 시 처리
        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        }

        // 2. 메시지 브로커로 전달 (중계)
        // '/sub/chat/room/{roomId}'를 구독하고 있는 모든 클라이언트에게 메시지를 전달합니다.
        // 클라이언트가 이 경로로 구독해야 메시지를 받을 수 있습니다.
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}