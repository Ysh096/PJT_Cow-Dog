package com.xy.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.xy.entity.Member;
import com.xy.entity.MemberInfo;
import com.xy.repository.MemberInfoRepository;
import com.xy.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	MemberRepository memRepo;
	
	@Autowired
	MemberInfoRepository memInfoRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	 @PersistenceContext
	    EntityManager em;
	@Override
	public String registerMember(Map member) {
		System.out.println(member);
		MemberInfo newMemberInfo=new MemberInfo();
		
		newMemberInfo.setAddress((String)member.get("address"));
		newMemberInfo.setAge((String)member.get("age"));
		newMemberInfo.setAlcohol(member.get("alcohol").toString());
		System.out.println(member.get("distance"));
		newMemberInfo.setDistance((Integer)member.get("distance"));
		newMemberInfo.setGender(member.get("gender").toString());
		newMemberInfo.setHobby(member.get("hobby").toString());
		newMemberInfo.setMbti(member.get("mbti").toString());
		newMemberInfo.setMymbti(member.get("mymbti").toString().toUpperCase());
		newMemberInfo.setLatitude((double)member.get("latitude"));
		newMemberInfo.setLongitude((double)member.get("longitude"));
		newMemberInfo.setPersonality(member.get("personality").toString());
		newMemberInfo.setReligion(member.get("religion").toString());
		boolean smokingCheck=true;
		if(member.get("smoking").toString().equals("[false]")) {
			smokingCheck=false;
		}
		newMemberInfo.setSmoking(smokingCheck);
		
		
		Member newMember=new Member();
		newMember.setEmail(member.get("email").toString());
		newMember.setIssuspended(false);
		newMember.setMemberid(member.get("id").toString());
		newMember.setMemberinfo(newMemberInfo);
		newMember.setNickname(member.get("nickname").toString());
		newMember.setLogin(false);
		Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다
		String stringDatetime = new SimpleDateFormat("yyyy.MM.dd").format(date_now);
//		SimpleDateFormat fourteen_format = new SimpleDateFormat("yyyy-MM-dd-HH"); 
		newMember.setOnlinetime(stringDatetime);
		newMember.setPassword(passwordEncoder.encode(member.get("password").toString()));//패스워드 인코드
		newMember.setManager(false);
		newMember.setMatching(false);
		
		
		
		
		if(memInfoRepo.save(newMemberInfo)!=null && memRepo.save(newMember)!=null) {
			return "SUCCESS";
		}
		
		
		
		return "FAIL";
	}

	@Override
	public Member getMemberByMemberId(String id) {
		
		System.out.println(memRepo.existsBymemberid(id));
		if(memRepo.existsBymemberid(id)) {
			return memRepo.getBymemberid(id);
		}
		
		
		return null;
	}

	@Override
	public boolean confirmUserId(String userId) {
		
		
		if(memRepo.existsBymemberid(userId)) {
			return false;
		}
		
		
		return true;
	}

	@Override
	public boolean confirmNickname(String userNickname) {

		if(memRepo.existsBynickname(userNickname)) {
			return false;
		}
		return true;
	}

	@Override
	public Member getMemberById(long id) {
		
		if(memRepo.existsById(id)) {
			return memRepo.getById(id);
		}
		
		
		return null;
	}

	@Override
	public void deleteMemberById(long id) {
		
		memRepo.deleteById(id);
		
	}

	
	//로그인한 유저만 가져오기
	@Override
	public List<Member> getLoginUser(long id) {
		System.out.println(id);
		
		//자신 빼고 지금 로그인한 유저 쿼리
		String jpql="select m from Member as m where m.login='1' and m.id is not :id and m.Matching='0'";
		TypedQuery<Member> query=em.createQuery(jpql,Member.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
	
	
	

}
