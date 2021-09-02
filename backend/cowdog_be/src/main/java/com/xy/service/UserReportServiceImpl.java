package com.xy.service;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.xy.api.request.UserReportPostReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xy.entity.Member;
import com.xy.entity.UserReport;
import com.xy.repository.MemberRepository;
import com.xy.repository.UserReportRepository;

@Service
public class UserReportServiceImpl implements UserReportService{ // NoticeService를 상속받아 내용을 적을 것
	
	@Autowired
	UserReportRepository userReportRepo; //NoticeRepository를 가져와서 noticeRepo로 이름지음
	@Autowired
	MemberRepository memberRepo;
	
	@PersistenceContext
	EntityManager em;
	
	@Override
	public String createUserReport(UserReportPostReq request) {
		System.out.println(request);
		
		UserReport newUserReport = new UserReport();
		// title
		newUserReport.setTitle(request.getTitle());
		// content
		newUserReport.setContent(request.getContent());
		// regtime
		Timestamp date_now = new Timestamp(System.currentTimeMillis());
		newUserReport.setRegtime(date_now);
		// member_id
		newUserReport.setReportedId(request.getReportedId());
		// image 추가
		if(userReportRepo.save(newUserReport)!=null) {
			return "SUCCESS";
		}
		return "FAIL";
	}

	@Override
	public List<UserReport> getUserReportList() {
	    return userReportRepo.findAll();
	}
	
	// 유저 신고 삭제(유저 삭제가 아님)
	@Override
	public void deleteUserReport(Long userReportNo) {
		userReportRepo.deleteById(userReportNo);
	}
	
	// 유저 삭제(유저 삭제 후 유저 관련 신고도 삭제)
	@Override
	public void deleteReportedUser(String userId) {
//		String jpql = "select from Member m where m.id=:userLongId";
//		List<Member> resultList = em.createQuery(jpql, Member.class).setParameter("userLongId", userLongId).getResultList();
//		resultList.
		
		
		
		
		
		userReportRepo.deleteByReportedId(userId); // userId와 관련된 userReport 모두 삭제
		Member mem=memberRepo.getBymemberid(userId);
		
		
		memberRepo.deleteById(mem.getId());
	}
	@Transactional
	@Override
	public int suspendReportedUser(String userId) {
		Member mem=memberRepo.getBymemberid(userId);
		userReportRepo.deleteByReportedId(userId); // userId와 관련된 userReport 모두 삭제
		String jpql="update Member m SET m.issuspended=true where m.id=:userLongId";
		Query query = em.createQuery(jpql).setParameter("userLongId", mem.getId());
		int rows = query.executeUpdate();
		
		return rows;
	}
}
