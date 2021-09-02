package com.xy.service;

import com.xy.entity.ChatRoom;

public interface ChatRoomJoinService {
	public Long newRoom(String user1, String user2);
	public Long check(String user1,String user2);
	public void createRoom(String user, ChatRoom chatRoom);
}
