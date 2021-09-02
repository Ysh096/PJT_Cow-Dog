package com.xy.controller;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xy.api.response.MemberListGetRes;
import com.xy.api.response.NotificationListGetRes;
import com.xy.api.response.NotificationResultGetRes;
import com.xy.common.response.BaseResponseBody;
import com.xy.entity.Member;
import com.xy.entity.Notification;
import com.xy.entity.NotificationResult;
import com.xy.repository.NotificationRepository;
import com.xy.service.MemberServiceImpl;

@RestController
@RequestMapping("/cowdog/notificaion")
public class NotificationController {
	
	
	@Autowired
	NotificationRepository notiRepo;
	
	@Autowired
	MemberServiceImpl memSer;
	
	
	@GetMapping("/getNotification")
	public ResponseEntity<? extends BaseResponseBody> getNotification(@RequestParam("id") long id){
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!알림 메시지 온거 알려줘~!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		List<Notification> list=notiRepo.findAll();
		List<Notification> notificationList=new ArrayList<Notification>();
		
		
		for(int i=0; i<list.size(); i++) {
			if(list.get(i).getReceiver()==id && !list.get(i).isFlag()) {//지금 로그인 한 사람 이면서 그 사람이 아직 안읽은 메시지 
				notificationList.add(list.get(i));
			}
		}
		System.out.println("알림 메시지:          "+ notificationList.toString());
		
		List<NotificationResult> resultList=new ArrayList<>();
		
		
		for(int i=0; i<notificationList.size(); i++) {
			resultList.add(new NotificationResult(memSer.getMemberById(notificationList.get(i).getSender()),notificationList.get(i).getNotificaionNo()));
		}
		
		
		
		return ResponseEntity.ok(NotificationResultGetRes.of(200, "SUCCESS",resultList));
		
		
		
		
		
		
	}
	
	@GetMapping("/checkNotification")
	public ResponseEntity<? extends BaseResponseBody> checkNotification(@RequestParam("id") long id){
		
		Notification noti=notiRepo.getById(id);
		
		noti.setFlag(true);
		notiRepo.save(noti);
		
		return ResponseEntity.ok(BaseResponseBody.of(200, "SUCCESS"));
		
	}
	
	
	
	
	
	

}
