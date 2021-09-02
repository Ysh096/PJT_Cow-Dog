package com.xy.controller;

import com.xy.entity.ChatMessage;
import com.xy.entity.Member;
import com.xy.entity.chatMessageReq;
import com.xy.repository.ChatRoomRepository;
import com.xy.repository.MemberRepository;
import com.xy.service.ChatMessageService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/cowdog/chat")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class ChatMessageController {
	private final SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	MemberRepository memRepo;
	
	@Autowired 
	ChatRoomRepository chatRoomRepo;
	
    @Autowired
    ChatMessageService chatMessageSer;
    
//    @MessageMapping("/chat/send")
//    public void sendMsg(ChatMessage message) throws Exception {
//        chatMessageSer.save(message);
////        simpMessagingTemplate.convertAndSend("/sub/" + message.getChatRoom().getId(), message);
//        simpMessagingTemplate.convertAndSend("/sub/" + message.getChatRoom().getId(), message);
//        System.out.println("message 전송 백엔드 펑션 수행");
//    }
    @MessageMapping("/send")
    public void sendMsg(@RequestBody chatMessageReq message) throws Exception {
    	Member writer=memRepo.getBymemberid(message.getSender());
    	ChatMessage msg = new ChatMessage();
    	msg.setWriter(writer);
    	msg.setMessage(message.getMessage());
    	msg.setChatRoom(chatRoomRepo.getById(Long.parseLong(message.getChatroomId())));
    	msg.setTime(LocalDateTime.now());
    	
    	
//    	String receiver=message.getWriter();
        chatMessageSer.save(msg);
//        simpMessagingTemplate.convertAndSend("/sub/" + message.getChatRoom().getId(), message);
        simpMessagingTemplate.convertAndSend("/sub/" + writer.getId(), msg);
        System.out.println("message 전송 백엔드 펑션 수행");
    }
    
//    @MessageMapping("/chat")
//    public List<ChatMessage> getMessages(@RequestBody long id) throws Exception {
//    	
//    }
}