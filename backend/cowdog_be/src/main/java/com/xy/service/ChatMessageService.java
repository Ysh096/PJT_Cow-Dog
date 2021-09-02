package com.xy.service;

import java.util.List;

import com.xy.entity.ChatMessage;
import com.xy.entity.Follow;

public interface ChatMessageService {
	public ChatMessage save(ChatMessage chatMessage);
	public List<ChatMessage> getChatMessagesByChatroomId(long id);
	
//	public Follow save(Follow follow);
//	public List<Follow> getMemberId(long id);
}
