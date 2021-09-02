package com.xy.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xy.entity.ChatMessage;
import com.xy.repository.ChatMessageRepository;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

	@Autowired
	ChatMessageRepository chatMessageRepo;
	@PersistenceContext
	EntityManager em;
	
	@Override
	public ChatMessage save(ChatMessage chatMessage) {
		return chatMessageRepo.save(chatMessage);
	}

	@Override
	public List<ChatMessage> getChatMessagesByChatroomId(long id) {
		String jpql="select * from ChatMessage as cm where cm.room_id=:id";
		TypedQuery<ChatMessage> query=em.createQuery(jpql,ChatMessage.class);
		query.setParameter("id", id);
		
		return query.getResultList();
	}

}
