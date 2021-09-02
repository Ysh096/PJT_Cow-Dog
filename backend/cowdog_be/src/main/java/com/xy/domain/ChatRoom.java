package com.xy.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import java.io.IOException;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

@Getter @Setter
public class ChatRoom {
    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public static ChatRoom create(String name){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name;
        return chatRoom;
    }

    //STOMP 사용 안하는 소켓 채팅에서 필요한 코드
//    public void handleMessage(WebSocketSession session, ChatMessage chatMessage,
//                              ObjectMapper objectMapper) throws IOException {
//        if(chatMessage.getType() == MessageType.ENTER){
//            sessions.add(session);
//            chatMessage.setMessage(chatMessage.getWriter() + "님이 입장하셨습니다.");
//        }
//        else if(chatMessage.getType() == MessageType.LEAVE){
//            sessions.remove(session);
//            chatMessage.setMessage(chatMessage.getWriter() + "님임 퇴장하셨습니다.");
//        }
//        else{
//            chatMessage.setMessage(chatMessage.getWriter() + " : " + chatMessage.getMessage());
//        }
//        send(chatMessage,objectMapper);
//    }
//
//    private void send(ChatMessage chatMessage, ObjectMapper objectMapper) throws IOException {
//        TextMessage textMessage = new TextMessage(objectMapper.
//                                    writeValueAsString(chatMessage.getMessage()));
//        for(WebSocketSession sess : sessions){
//            sess.sendMessage(textMessage);
//        }
//    }
}