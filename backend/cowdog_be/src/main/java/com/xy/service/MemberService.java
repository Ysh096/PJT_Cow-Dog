package com.xy.service;

import java.util.List;
import java.util.Map;

import com.xy.entity.Member;
import com.xy.entity.MemberInfo;

public interface MemberService {
	public String registerMember(Map member);
	public Member getMemberByMemberId(String id);
	public boolean confirmUserId(String userId);
	public boolean confirmNickname(String userNickname);
	public Member getMemberById(long id);
	public void deleteMemberById(long id);
	public List<Member> getLoginUser(long id);
}
