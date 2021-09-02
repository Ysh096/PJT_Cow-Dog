package com.xy.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xy.entity.ChatRoom;
import com.xy.entity.ChatRoomJoin;
import com.xy.entity.Member;
import com.xy.repository.ChatRoomJoinRepository;
import com.xy.repository.ChatRoomRepository;

@Service
public class ChatRoomJoinServiceImpl implements ChatRoomJoinService{

	@Autowired
	ChatRoomRepository chatRoomRepository;
	
	@Autowired
	ChatRoomJoinRepository chatRoomJoinRepository;
	
	@Autowired
	MemberService memberService;
	
	@Override
	public Long newRoom(String user1, String user2) {
		//두 유저가 모두 포함되는 방이 있는지 검사.
		//존재하는 경우 Long id (primary key) 리턴
		//존재하지 않는 경우 방 생성 후 Long id 리턴
	    Long ret = check(user1,user2);
	    if(ret != 0){
	        return ret;
	    }
	    ChatRoom chatRoom = new ChatRoom();
	    ChatRoom newChatRoom = chatRoomRepository.save(chatRoom);
	    if(user1.equals(user2)){
	        //나 자신과의 채팅은 한명만 존재
	        createRoom(user1,newChatRoom);
	    }
	    else{
	        //두명 다 입장
	        createRoom(user1,newChatRoom);
	        createRoom(user2,newChatRoom);
	    }
	    return newChatRoom.getId();
	}

	@Override
	public Long check(String user1, String user2) {
	    Member userFirst = memberService.getMemberByMemberId(user1);
	    List<ChatRoomJoin> listFirst = chatRoomJoinRepository.findChatRoomJoinByMember(userFirst);
	    Set<ChatRoom> setFirst = new HashSet<>();
	    for(ChatRoomJoin chatRoomJoin : listFirst){
	        setFirst.add(chatRoomJoin.getChatRoom());
	    }
	    Member userSecond = memberService.getMemberByMemberId(user2);
	    List<ChatRoomJoin> listSecond = chatRoomJoinRepository.findChatRoomJoinByMember(userSecond);
	    for(ChatRoomJoin chatRoomJoin : listSecond){
	        if(setFirst.contains(chatRoomJoin.getChatRoom())){
	            return chatRoomJoin.getChatRoom().getId();
	        }
	    }
	    return 0L;
	}
	
	@Override
	public void createRoom(String member_id, ChatRoom chatRoom){
	    ChatRoomJoin chatRoomJoin = new ChatRoomJoin();
	    chatRoomJoin.setChatRoom(chatRoom);
	    chatRoomJoin.setMember(memberService.getMemberByMemberId(member_id));
	    chatRoomJoinRepository.save(chatRoomJoin);
	}

}
