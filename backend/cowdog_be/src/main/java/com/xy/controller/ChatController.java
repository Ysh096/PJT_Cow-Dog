package com.xy.controller;

import com.xy.common.response.BaseResponseBody;
import com.xy.repository.MemberRepository;
import com.xy.service.ChatRoomJoinService;
import com.xy.service.MemberService;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/cowdog/chat")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class ChatController {
    private final SimpMessagingTemplate simpMessagingTemplate;
	@Autowired
	MemberService memSer;
	
	@Autowired
	MemberRepository memRepo;
	
    @Autowired
    ChatRoomJoinService	chatRoomJoinService;
    
    
    //채팅방 개설
    @PostMapping("/newChat")
    public ResponseEntity<? extends BaseResponseBody> newChat(@RequestBody Map<String, String> map){
    	Long chatRoomId = chatRoomJoinService.newRoom(map.get("user1"),map.get("user2"));
    	
        if ( chatRoomId != 0L) {
            // 성공 시 생성된 방 Id 리턴
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, Long.toString(chatRoomId)));
        }
        return ResponseEntity.status(500).body(BaseResponseBody.of(500, "FAIL"));
    }
    
//    //채팅방 개설
//    @PostMapping("/newChat")
//    public ResponseEntity<? extends BaseResponseBody> newChat(@RequestParam("receiver") String user1, @RequestParam("sender") String user2){
//    	System.out.println("axios 성공");
//    	Long chatRoomId = chatRoomJoinService.newRoom(user1,user2);
//    	System.out.println("채팅방 생성 성공");
//    	
//    	if ( chatRoomId != 0L) {
//    		// 성공 시 생성된 방 Id 리턴
//    		return ResponseEntity.status(200).body(BaseResponseBody.of(200, Long.toString(chatRoomId)));
//    	}
//    	else {
//    		return ResponseEntity.status(500).body(BaseResponseBody.of(500, "FAIL"));
//    	}
////    	return "redirect:/personalChat/" + chatRoomId; String return 일때의 라인
//    }
}