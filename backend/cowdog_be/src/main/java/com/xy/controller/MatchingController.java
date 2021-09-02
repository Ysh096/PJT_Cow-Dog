package com.xy.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xy.api.response.DistanceMatchingUserGetRes;
import com.xy.api.response.MemberLoginPostRes;
import com.xy.api.response.RandomMatchtingUserGetRes;
import com.xy.common.response.BaseResponseBody;
import com.xy.entity.DistanceMatchingResult;
import com.xy.entity.Member;
import com.xy.repository.MemberRepository;
import com.xy.service.MemberService;

@RestController
@RequestMapping("/cowdog/matching")
public class MatchingController {

	@Autowired
	MemberService memSer;
	@Autowired
	MemberRepository memRepo;

	// 랜덤 매칭
	@GetMapping("/randomMatching")
	public ResponseEntity<? extends BaseResponseBody> RandomMatching(@RequestParam("id") long id) {

		List<Member> list = memSer.getLoginUser(id);
		System.out.println(list.toString());
		int size = list.size();
		List<Member> randomList = new ArrayList<Member>();
		Random rand = new Random();
		int a[] = new int[size];// 로그인한 사람이 3명 이상이 아니면 에러라서 일단 크기를 현재 로그인 한 유저 수로 했다
		for (int i = 0; i < size; i++) {
			a[i] = rand.nextInt(size);
			System.out.println("sdf");
			for (int j = 0; j < i; j++) {
				if (a[i] == a[j]) {
					i--;
				}
			}
		}
		for (int value : a) {
			randomList.add(list.get(value));
		}
		System.out.println(randomList.toString());
		return ResponseEntity.ok(RandomMatchtingUserGetRes.of(200, "SUCCESS", randomList));
	}

	// 거리 매칭~~~
	@GetMapping("/distanceMatching")
	public ResponseEntity<? extends BaseResponseBody> DistanceMatching(@RequestParam("id") long id,
			@RequestParam("dist") int dist) {

		System.out.println(dist);
		Member USER = memRepo.getById(id);
		List<Member> loginUsers = memSer.getLoginUser(id);
		List<Member> loginUsersResult = new ArrayList<Member>();
		if (USER.getMemberinfo().getGender().equals("남자")) {
			for (int i = 0; i < loginUsers.size(); i++) {
				if (loginUsers.get(i).getMemberinfo().getGender().equals("여자")) {// 로그인 한 유저중에 여자 유저만 골라서
					loginUsersResult.add(loginUsers.get(i));
				}
			}
		} else {
			for (int i = 0; i < loginUsers.size(); i++) {
				if (loginUsers.get(i).getMemberinfo().getGender().equals("남자")) {// 로그인 한 유저중에 남자 유저만 골라서
					loginUsersResult.add(loginUsers.get(i));
				}
			}
		}

		System.out.println(loginUsers.toString());

		List<DistanceMatchingResult> resultList = new ArrayList<DistanceMatchingResult>();

		for (int i = 0; i < loginUsersResult.size(); i++) {
			double resultDist = distance(USER.getMemberinfo().getLatitude(), USER.getMemberinfo().getLongitude(),
					loginUsersResult.get(i).getMemberinfo().getLatitude(),
					loginUsersResult.get(i).getMemberinfo().getLongitude());
			System.out.println(resultDist + " km");
			if ((int) resultDist < dist) {
				DistanceMatchingResult R = new DistanceMatchingResult(loginUsersResult.get(i).getId(),
						loginUsersResult.get(i).getMemberid(), loginUsersResult.get(i).getMemberinfo(),
						loginUsersResult.get(i).getFile_path(), (int) resultDist);
				resultList.add(R);
			}
		}

		System.out.println(resultList.toString());

		return ResponseEntity.ok(DistanceMatchingUserGetRes.of(200, "SUCCESS", resultList));

	}

	// 추천 매칭
	@GetMapping("/alcohol")
	public ResponseEntity<? extends BaseResponseBody> Alcohol(@RequestParam("id") long id) {

		System.out.println("주량 같은 사람~~~~~~~~~~~~~~~~~");
		List<Member> LoginUsers = memSer.getLoginUser(id);// 나 이외에 로그인한 유저를 반환
		Member MY = memSer.getMemberById(id);
		System.out.println(LoginUsers.toString());
		List<Member> resultList = new ArrayList<Member>();
		List<Member> loginUsersResult = new ArrayList<Member>();
		if (MY.getMemberinfo().getGender().equals("남자")) {
			for (int i = 0; i < LoginUsers.size(); i++) {
				if (LoginUsers.get(i).getMemberinfo().getGender().equals("여자")) {// 로그인 한 유저중에 여자 유저만 골라서
					loginUsersResult.add(LoginUsers.get(i));
				}
			}
		} else {
			for (int i = 0; i < LoginUsers.size(); i++) {
				if (LoginUsers.get(i).getMemberinfo().getGender().equals("남자")) {// 로그인 한 유저중에 남자 유저만 골라서
					loginUsersResult.add(LoginUsers.get(i));
				}
			}
		}

		for (int i = 0; i < loginUsersResult.size(); i++) {
			if (MY.getMemberinfo().getAlcohol().equals(loginUsersResult.get(i).getMemberinfo().getAlcohol())) {
				resultList.add(loginUsersResult.get(i));
			}
		}
		System.out.println(resultList.toString());
		return ResponseEntity.ok(RandomMatchtingUserGetRes.of(200, "SUCCESS", resultList));
	}

	@GetMapping("/sameHobby")
	public ResponseEntity<? extends BaseResponseBody> SameHobby(@RequestParam("id") long id) {
		System.out.println("취미가 비슷한 사람~~~~~~~~~~~~~~~~~");
		List<Member> LoginUsers = memSer.getLoginUser(id);// 나 이외에 로그인한 유저를 반환
		System.out.println(LoginUsers.toString());
		Member MY = memSer.getMemberById(id);

		List<Member> loginUsersResult = new ArrayList<Member>();
		if (MY.getMemberinfo().getGender().equals("남자")) {
			for (int i = 0; i < LoginUsers.size(); i++) {
				if (LoginUsers.get(i).getMemberinfo().getGender().equals("여자")) {// 로그인 한 유저중에 여자 유저만 골라서
					loginUsersResult.add(LoginUsers.get(i));
				}
			}
		} else {
			for (int i = 0; i < LoginUsers.size(); i++) {
				if (LoginUsers.get(i).getMemberinfo().getGender().equals("남자")) {// 로그인 한 유저중에 남자 유저만 골라서
					loginUsersResult.add(LoginUsers.get(i));
				}
			}
		}

		List<Member> resultList = new ArrayList<Member>();
		ArrayList<String> myHobbyList = new ArrayList<String>();
		String myHobby = MY.getMemberinfo().getHobby();
		myHobby = myHobby.substring(1, myHobby.length() - 1);
		System.out.println(myHobby);
		StringTokenizer st = new StringTokenizer(myHobby, ", ");
		while (st.hasMoreTokens()) {
			myHobbyList.add(st.nextToken());
		}
		for (int i = 0; i < loginUsersResult.size(); i++) {
			ArrayList<String> loginUsersHobbyList = new ArrayList<String>();
			String userHobby = loginUsersResult.get(i).getMemberinfo().getHobby();
			userHobby = userHobby.substring(1, userHobby.length() - 1);
			StringTokenizer st1 = new StringTokenizer(userHobby, ", ");
			while (st1.hasMoreTokens()) {
				loginUsersHobbyList.add(st1.nextToken());
			}

			L: for (int j = 0; j < myHobbyList.size(); j++) {
				for (int k = 0; k < loginUsersHobbyList.size(); k++) {
					if (myHobbyList.get(j).equals(loginUsersHobbyList.get(k))) {// 취미가 하나라도 같으면
						resultList.add(loginUsersResult.get(i));// 리턴 리스트에 추가 후
						break L;// 반복문 빠져나간다 그리고 다시 검색
					}
				}

			}

		}
		return ResponseEntity.ok(RandomMatchtingUserGetRes.of(200, "SUCCESS", resultList));

	}

	@GetMapping("/wantMale")
	public ResponseEntity<? extends BaseResponseBody> WantMale(@RequestParam("id") long id) {

		System.out.println("남자인 사람~~~~~~~~~");
		List<Member> LoginUsers = memSer.getLoginUser(id);// 나 이외에 로그인한 유저를 반환
		Member MY = memSer.getMemberById(id);
		System.out.println(LoginUsers.toString());
		List<Member> resultList = new ArrayList<Member>();

		for (int i = 0; i < LoginUsers.size(); i++) {
			if (LoginUsers.get(i).getMemberinfo().getGender().equals("남자")) {
				resultList.add(LoginUsers.get(i));
			}
		}
		System.out.println(resultList.toString());
		return ResponseEntity.ok(RandomMatchtingUserGetRes.of(200, "SUCCESS", resultList));

	}

	@GetMapping("/wantFemale")
	public ResponseEntity<? extends BaseResponseBody> WantFemale(@RequestParam("id") long id) {

		System.out.println("남자인 사람~~~~~~~~~");
		List<Member> LoginUsers = memSer.getLoginUser(id);// 나 이외에 로그인한 유저를 반환
		Member MY = memSer.getMemberById(id);
		System.out.println(LoginUsers.toString());
		List<Member> resultList = new ArrayList<Member>();

		for (int i = 0; i < LoginUsers.size(); i++) {
			if (LoginUsers.get(i).getMemberinfo().getGender().equals("여자")) {
				resultList.add(LoginUsers.get(i));
			}
		}
		System.out.println(resultList.toString());
		return ResponseEntity.ok(RandomMatchtingUserGetRes.of(200, "SUCCESS", resultList));
	}

	@GetMapping("/nonSmoking")
	public ResponseEntity<? extends BaseResponseBody> NonSmoking(@RequestParam("id") long id) {

		System.out.println("비흡연자~~~~~~~~~~~");
		List<Member> LoginUsers = memSer.getLoginUser(id);// 나 이외에 로그인한 유저를 반환
		Member MY = memSer.getMemberById(id);
		System.out.println(LoginUsers.toString());
		List<Member> resultList = new ArrayList<Member>();

		List<Member> loginUsersResult = new ArrayList<Member>();
		if (MY.getMemberinfo().getGender().equals("남자")) {
			for (int i = 0; i < LoginUsers.size(); i++) {
				if (LoginUsers.get(i).getMemberinfo().getGender().equals("여자")) {// 로그인 한 유저중에 여자 유저만 골라서
					loginUsersResult.add(LoginUsers.get(i));
				}
			}
		} else {
			for (int i = 0; i < LoginUsers.size(); i++) {
				if (LoginUsers.get(i).getMemberinfo().getGender().equals("남자")) {// 로그인 한 유저중에 남자 유저만 골라서
					loginUsersResult.add(LoginUsers.get(i));
				}
			}
		}

		for (int i = 0; i < loginUsersResult.size(); i++) {
			if (!loginUsersResult.get(i).getMemberinfo().isSmoking()) {
				resultList.add(loginUsersResult.get(i));
			}
		}
		System.out.println(resultList.toString());
		return ResponseEntity.ok(RandomMatchtingUserGetRes.of(200, "SUCCESS", resultList));
	}

	@GetMapping("/sameReligion")
	public ResponseEntity<? extends BaseResponseBody> SameReligion(@RequestParam("id") long id) {

		System.out.println("종교가 같은사람~~~~~~~~~~~");
		List<Member> LoginUsers = memSer.getLoginUser(id);// 나 이외에 로그인한 유저를 반환
		Member MY = memSer.getMemberById(id);
		System.out.println(LoginUsers.toString());
		List<Member> resultList = new ArrayList<Member>();

		List<Member> loginUsersResult = new ArrayList<Member>();
		if (MY.getMemberinfo().getGender().equals("남자")) {
			for (int i = 0; i < LoginUsers.size(); i++) {
				if (LoginUsers.get(i).getMemberinfo().getGender().equals("여자")) {// 로그인 한 유저중에 여자 유저만 골라서
					loginUsersResult.add(LoginUsers.get(i));
				}
			}
		} else {
			for (int i = 0; i < LoginUsers.size(); i++) {
				if (LoginUsers.get(i).getMemberinfo().getGender().equals("남자")) {// 로그인 한 유저중에 남자 유저만 골라서
					loginUsersResult.add(LoginUsers.get(i));
				}
			}
		}

		for (int i = 0; i < loginUsersResult.size(); i++) {
			if (MY.getMemberinfo().getReligion().equals(loginUsersResult.get(i).getMemberinfo().getReligion())) {
				resultList.add(loginUsersResult.get(i));
			}
		}
		System.out.println(resultList.toString());
		return ResponseEntity.ok(RandomMatchtingUserGetRes.of(200, "SUCCESS", resultList));
	}

	@GetMapping("/sameMbti")
	public ResponseEntity<? extends BaseResponseBody> SameMbti(@RequestParam("id") long id) {

		System.out.println("선호하는 mbti~~~~~~~~~~~~~~~~~");
		List<Member> LoginUsers = memSer.getLoginUser(id);// 나 이외에 로그인한 유저를 반환
		System.out.println(LoginUsers.toString());
		Member MY = memSer.getMemberById(id);

		List<Member> resultList = new ArrayList<Member>();// 내가 선호하는 mbti를 가지고 있는 사람들
		ArrayList<String> myPreferredMbtiList = new ArrayList<String>();// 내가 선호하는 mbti 리스트

		String myPreferredMbti = MY.getMemberinfo().getMbti();
		myPreferredMbti = myPreferredMbti.substring(1, myPreferredMbti.length() - 1);
		System.out.println(myPreferredMbti);
		StringTokenizer st = new StringTokenizer(myPreferredMbti, ", ");
		while (st.hasMoreTokens()) {
			myPreferredMbtiList.add(st.nextToken());
		}

		List<Member> loginUsersResult = new ArrayList<Member>();
		if (MY.getMemberinfo().getGender().equals("남자")) {
			for (int i = 0; i < LoginUsers.size(); i++) {
				if (LoginUsers.get(i).getMemberinfo().getGender().equals("여자")) {// 로그인 한 유저중에 여자 유저만 골라서
					loginUsersResult.add(LoginUsers.get(i));
				}
			}
		} else {
			for (int i = 0; i < LoginUsers.size(); i++) {
				if (LoginUsers.get(i).getMemberinfo().getGender().equals("남자")) {// 로그인 한 유저중에 남자 유저만 골라서
					loginUsersResult.add(LoginUsers.get(i));
				}
			}
		}

		for (int i = 0; i < loginUsersResult.size(); i++) {
			for (int j = 0; j < myPreferredMbtiList.size(); j++) {
				if (loginUsersResult.get(i).getMemberinfo().getMymbti().equals(myPreferredMbtiList.get(j))) {
					resultList.add(loginUsersResult.get(i));
				}
			}

		}
		return ResponseEntity.ok(RandomMatchtingUserGetRes.of(200, "SUCCESS", resultList));
	}

	@GetMapping("/samePersonality")
	public ResponseEntity<? extends BaseResponseBody> SamePersonality(@RequestParam("id") long id) {

		System.out.println("성격이 비슷한 사람~~~~~~~~~~~~~~~~~");
		List<Member> LoginUsers = memSer.getLoginUser(id);// 나 이외에 로그인한 유저를 반환
		System.out.println(LoginUsers.toString());

		Member MY = memSer.getMemberById(id);// 현재 로그인 한 사람
		List<Member> resultList = new ArrayList<Member>();// 성격 비슷한 사람들 리스트
		ArrayList<String> myPersonalityList = new ArrayList<String>();

		List<Member> loginUsersResult = new ArrayList<Member>();
		if (MY.getMemberinfo().getGender().equals("남자")) {
			for (int i = 0; i < LoginUsers.size(); i++) {
				if (LoginUsers.get(i).getMemberinfo().getGender().equals("여자")) {// 로그인 한 유저중에 여자 유저만 골라서
					loginUsersResult.add(LoginUsers.get(i));
				}
			}
		} else {
			for (int i = 0; i < LoginUsers.size(); i++) {
				if (LoginUsers.get(i).getMemberinfo().getGender().equals("남자")) {// 로그인 한 유저중에 남자 유저만 골라서
					loginUsersResult.add(LoginUsers.get(i));
				}
			}
		}

		String myPersonality = MY.getMemberinfo().getPersonality();
		myPersonality = myPersonality.substring(1, myPersonality.length() - 1);// 앞뒤 [ ] 없애기
		System.out.println(myPersonality);

		StringTokenizer st = new StringTokenizer(myPersonality, ", ");// , 구분자
		while (st.hasMoreTokens()) {
			myPersonalityList.add(st.nextToken());
		}
		for (int i = 0; i < loginUsersResult.size(); i++) {
			ArrayList<String> loginUsersPersonalityList = new ArrayList<String>();
			String userPersonality = loginUsersResult.get(i).getMemberinfo().getPersonality();
			userPersonality = userPersonality.substring(1, userPersonality.length() - 1);
			StringTokenizer st1 = new StringTokenizer(userPersonality, ", ");
			while (st1.hasMoreTokens()) {
				loginUsersPersonalityList.add(st1.nextToken());
			}

			L: for (int j = 0; j < myPersonalityList.size(); j++) {
				for (int k = 0; k < loginUsersPersonalityList.size(); k++) {
					if (myPersonalityList.get(j).equals(loginUsersPersonalityList.get(k))) {// 취미가 하나라도 같으면
						resultList.add(loginUsersResult.get(i));// 리턴 리스트에 추가 후
						break L;// 반복문 빠져나간다 그리고 다시 검색
					}
				}

			}

		}
		return ResponseEntity.ok(RandomMatchtingUserGetRes.of(200, "SUCCESS", resultList));
	}

	@PatchMapping("/isMatching/{from}/{to}/{message}")
	public void isMatching(@PathVariable("from") String from, @PathVariable("to") String to,
			@PathVariable("message") String message) {
		if (message.equals("liveOn")) {
			Member Sender = memRepo.getBymemberid(from);
			Member Receiver = memRepo.getBymemberid(to);
			Sender.setMatching(true);
			Receiver.setMatching(true);
			memRepo.save(Sender);
			memRepo.save(Receiver);
		}else {
			Member Sender = memRepo.getBymemberid(from);
			Member Receiver = memRepo.getBymemberid(to);
			Sender.setMatching(false);
			Receiver.setMatching(false);
			memRepo.save(Sender);
			memRepo.save(Receiver);
		}

	}

	private static double distance(double lat1, double lon1, double lat2, double lon2) {

		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344;

		return (dist);
	}

	// This function converts decimal degrees to radians
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	// This function converts radians to decimal degrees
	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

}
