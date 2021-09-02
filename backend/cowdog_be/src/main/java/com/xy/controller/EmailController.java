package com.xy.controller;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xy.entity.Member;
import com.xy.repository.MemberRepository;
import com.xy.service.MemberService;

@RequestMapping("/cowdog/EmailAuthentication")
@RestController
public class EmailController {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	MemberService memSer;
	@Autowired
	MemberRepository memRepo;
	
	
	public static String getRamdomPassword(int len) {
		char[] charSet = new char[] { '0', '1', '3', '5', '6', '7', '8', '9', 'A', 'B', 'D', 'E', 'F',
				'G', 'H', 'I', 'J', 'L', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z','a','b',
				'c','d','e','f','g','h','i','k','l','m','p','q','r','s','u','w','y','z','!','@','#','$',
				'^','&','*','+','$','%','!','~','^','$'};
		int idx = 0;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < len; i++) {
			idx = (int) (charSet.length * Math.random());
//			System.out.println("idx :::: " + idx);
			sb.append(charSet[idx]);
		}
		return sb.toString();
	}
	@PutMapping("/searchPassword")
	public String sendEmaliAction(@RequestBody Map<String, String> map) {
		System.out.println("비빌번호 찾기~~~~~");
		System.out.println(map.toString());
		String USERNAME = (String) map.get("id");
		String EMAIL = (String) map.get("email");
		String PASSWORD = getRamdomPassword(16);
		System.out.println(PASSWORD);
		System.out.println(USERNAME);
		Member mem=memSer.getMemberByMemberId(USERNAME);
		if(!mem.getEmail().equals(EMAIL)) {
			return "NOT_EXISTS_EMAIL";
		}
		
		
		try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true, "UTF-8");
             
            messageHelper.setSubject(USERNAME+"님 비밀번호 초기화 메일입니다.");
            messageHelper.setText("비밀번호는 "+PASSWORD+" 입니다.");
            messageHelper.setTo(EMAIL);
            msg.setRecipients(MimeMessage.RecipientType.TO , InternetAddress.parse(EMAIL));
            mailSender.send(msg);
             
        }catch(MessagingException e) {
            System.out.println("MessagingException");
            e.printStackTrace();
        }
		
		
		mem.setPassword(passwordEncoder.encode(PASSWORD));
		if(memRepo.save(mem)!=null) {
			return "SUCCESS";
		}
		
		
		return "FAIL";
	}

	
	
	
	
	
	

}
