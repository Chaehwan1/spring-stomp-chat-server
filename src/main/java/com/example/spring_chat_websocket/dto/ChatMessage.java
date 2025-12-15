package com.example.spring_chat_websocket.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

    // 메시지 타입 정의
    public enum MessageType {
        // 입장, 채팅, 퇴장 등 메시지의 상태를 구분
        ENTER, TALK, QUIT
    }

    private MessageType type;    // 메시지 타입
    private String roomId;      // 채팅방 ID (메시지를 보낼 대상 방을 지정)
    private String sender;      // 메시지 발신자 닉네임 또는 ID
    private String message;     // 실제 메시지 내용
}