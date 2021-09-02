package com.xy.service;
import java.sql.Timestamp;
import java.util.List;

import com.xy.api.request.NoticeCreatePostReq;
import com.xy.api.request.NoticeUpdatePutReq;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xy.entity.Notice;
import com.xy.repository.NoticeRepository;

@Service
public class NoticeServiceImpl implements NoticeService{ // NoticeService를 상속받아 내용을 적을 것
	
	@Autowired
	NoticeRepository noticeRepo; //NoticeRepository를 가져와서 noticeRepo로 이름지음
	
	
	@Override
	public String createNotice(NoticeCreatePostReq noticereq) { // MemberService에 선언한 변수의 내용을 override
		System.out.println(noticereq);
	
		Notice newNotice=new Notice();
		// title
		newNotice.setTitle(noticereq.getTitle());
		// content
		newNotice.setContent(noticereq.getContent());
		// Timestamp로 시간 구해서 set
		Timestamp date_now = new Timestamp(System.currentTimeMillis());
		newNotice.setRegtime(date_now);
		// image 곧 수정
//		newNotice.setImage(noticereq.getImage());
		
		if(noticeRepo.save(newNotice)!=null) {
			return "SUCCESS";
		}
		return "FAIL";
	}
	
	@Override
	public List<Notice> getNoticeList() {
	    return noticeRepo.findAll();
	}
	
	@Override
	public void deleteNotice(Long noticeNo) {
		noticeRepo.deleteById(noticeNo);
	}
	
	@Override
	public void updateNotice(NoticeUpdatePutReq noticeUpdateReq) {
		Optional<Notice> notice = noticeRepo.findById(noticeUpdateReq.getNoticeNo());
		notice.ifPresent(update -> {
			update.setNoticeNo(noticeUpdateReq.getNoticeNo());
			update.setTitle(noticeUpdateReq.getTitle());
			update.setContent(noticeUpdateReq.getContent());
			// 이미지 수정
			// 저장
			noticeRepo.save(update);
		});
	}
}
