package com.xy.auth;

import org.springframework.stereotype.Service;

import com.xy.repository.MemberRepository;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.xy.entity.Member;
@Service
public class MemberDetailsService implements UserDetailsService{

	@Autowired
	MemberRepository memRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		
		
		Member mem=memRepo.getBymemberid(id);
		
		return new org.springframework.security.core.userdetails.User(mem.getMemberid(), mem.getPassword(), new ArrayList<>());

	}

}
