package com.xy.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xy.entity.Member;
import com.xy.entity.Notification;
import com.xy.repository.MemberRepository;
import com.xy.repository.NotificationRepository;
import com.xy.service.FollowService;
import com.xy.service.FollowServiceImpl;
import com.xy.service.MemberService;
import com.amazonaws.services.datapipeline.model.Query;
import com.xy.api.response.DistanceMatchingUserGetRes;
import com.xy.api.response.MemberListGetRes;
import com.xy.common.response.BaseResponseBody;
import com.xy.entity.EachOther;
import com.xy.entity.Follow;

@RestController
@RequestMapping("/cowdog/like")
public class LikeController {
	@Autowired
	MemberService memSer;
	@Autowired
	MemberRepository memRepo;
	@Autowired
	NotificationRepository notiRepo;
	@Autowired
	FollowServiceImpl followSer;
	
	@GetMapping("/AmIFollowed")
	public List<Follow> AmIFollowed(@RequestParam("id") long id) {
		System.out.println("내가 누구를 팔로우 했지?");
		return followSer.amIFollowed(id);
	}
	
	@PostMapping("/follow")
	public List<Follow> Follow(@RequestBody Map<String, String> map) {
		
		System.out.println(map.toString());
		
		long From=Long.parseLong(map.get("followerid").toString());//팔로우를 하는사람
		Member to=memSer.getMemberByMemberId(map.get("memberid"));//팔로우를 당하는사람
		Follow follow=new Follow();
		Notification notification=new Notification();
		notification.setFlag(false);
		notification.setSender(From);
		notification.setReceiver(to.getId());
		notiRepo.save(notification);
		
		follow.setMember_id(to.getId());
		follow.setFollower_id(From);
		if(followSer.save(follow)!=null) {
			
			return AmIFollowed(From);
		}
		return AmIFollowed(From);
	}
	
	
	@GetMapping("/getFollowUsers")
	public ResponseEntity<? extends BaseResponseBody> getFollowUsers(@RequestParam("id") long id) {
		
		System.out.println("나 팔로우 한 사람 내놔~~~");
		System.out.println(id);//로그인 한사람
		List<EachOther> followUsers= new ArrayList<EachOther>();
		followUsers=followSer.getFollowerMemberId(id);//로그인 한 사람을 팔로우 하는 사람들
		
		System.out.println(followUsers.toString());
		
		List<Member> follower=new ArrayList<Member>();
		
		for(int i=0; i<followUsers.size(); i++) {
			if(memSer.getMemberById(followUsers.get(i).getFollower_Id())!=null) {
				follower.add(memSer.getMemberById(followUsers.get(i).getFollower_Id()));
			}
		}
		
		return ResponseEntity.ok(MemberListGetRes.of(200, "SUCCESS",follower));

	}
	@GetMapping("/getFollowEachOther")
	public ResponseEntity<? extends BaseResponseBody> getFollowEachOther(@RequestParam("id") long id) {
		
		System.out.println("나랑 맞팔로우 한 사람 내놔~~~");
		System.out.println(id);//로그인 한사람
		List<EachOther> getFollowEachOther= new ArrayList<EachOther>();
		getFollowEachOther=followSer.getFollowEachOther(id);//로그인 한 사람과 맞팔로우 하는 사람들
		
		System.out.println(getFollowEachOther.toString());
		
		List<Member> resultLIst=new ArrayList<Member>();
		
		for(int i=0; i<getFollowEachOther.size(); i++) {
			if(memSer.getMemberById(getFollowEachOther.get(i).getFollower_Id())!=null) {
				resultLIst.add(memSer.getMemberById(getFollowEachOther.get(i).getFollower_Id()));
			}
		}
		
		System.out.println(resultLIst.toString());
		return ResponseEntity.ok(MemberListGetRes.of(200, "SUCCESS",resultLIst));

	}
	
	@DeleteMapping("/follow/{member_id}/{follow_id}")//member_id ->카드에 있는 사람,, follow_id -> 로그인 한 사람
	public List<Follow> Unfollow(@PathVariable("member_id") long memberid, @PathVariable("follow_id") long followid) {
		System.out.println("삭제 시작합니다?");
		followSer.unFollow(memberid, followid);
		return followSer.amIFollowed(followid);
	}
	
}