package com.xy.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xy.entity.Article;
import com.xy.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xy.S3.S3SServiceImpl;
import com.xy.S3.S3Service;
import com.xy.api.request.MemberLoginPostReq;
import com.xy.api.response.MemberLoginPostRes;
import com.xy.common.response.BaseResponseBody;
import com.xy.entity.Image;
import com.xy.entity.Member;
import com.xy.entity.MemberInfo;
import com.xy.repository.MemberRepository;
import com.xy.service.ImageService;
import com.xy.service.MemberService;
import com.xy.auth.JwtUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/cowdog/mem")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class MemberController {

	@Autowired
	MemberService memSer;
	@Autowired
	ArticleService articleSer;
	@Autowired
	S3SServiceImpl s3sSer;
	@Autowired
	ImageService imgaeSer;
	@Autowired
	MemberRepository memRepo;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;


	@PostMapping("/register")
	@ApiOperation(value = "회원 가입", notes = "<strong>아이디와 패스워드</strong>를 통해 회원가입 한다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공"), @ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"), @ApiResponse(code = 500, message = "서버 오류") })
	public ResponseEntity<? extends BaseResponseBody> register(@RequestBody HashMap<String, Object> map) {
		System.out.println(map);
		
		
		

		if (memSer.registerMember(map).equals("FAIL")) {
			return ResponseEntity.status(200).body(BaseResponseBody.of(404, "FAIL"));
		}

		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "SUCCESS"));
	}
	
	@PostMapping("/profileImgaeUpload")
	public void execWrite(Image image, MultipartFile files,String userId) throws IOException {
		System.out.println("여기는 이미지 업로드");
		System.out.println(image.toString());
	    System.out.println(files);
	    System.out.println(userId);
	    // for (int i = 0; i < files.length; i++) {
	    String imgPath = s3sSer.upload(image.getFile_path(), files);
	    image.setFile_path("https://" + "d2ukkf315368dk.cloudfront.net" + "/" + imgPath);
	    // }
	    imgaeSer.upload(image,userId);
	  }
	
	@GetMapping("/getImageList")
	public List<Image> getImageList(){
		return imgaeSer.getImageList();
	}
	
	
	@PostMapping("/login")
	@ApiOperation(value = "로그인", notes = "<strong>아이디와 패스워드</strong>를 통해 로그인 한다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = BaseResponseBody.class),
			@ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
			@ApiResponse(code = 404, message = "사용자 없음", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class) })
	public ResponseEntity<? extends BaseResponseBody> login(
			@RequestBody @ApiParam(value = "로그인 정보", required = true) MemberLoginPostReq loginInfo) throws Exception{
		String userId = loginInfo.getId();
		String password = loginInfo.getPassword();
		System.out.println(userId);
		System.out.println(password);
		Member mem = memSer.getMemberByMemberId(userId);

		

		if (mem == null) {
			System.out.println("회원 읎어");
			return ResponseEntity.status(404).body(BaseResponseBody.of(404, "NOT_EXISTS_USER"));
		}
		if(mem.isIssuspended()) {
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "ISSUSPENDED"));//계정 정지되어 있으면
		}
		// 로그인 요청한 유저로부터 입력된 패스워드 와 디비에 저장된 유저의 암호화된 패스워드가 같은지 확인.(유효한 패스워드인지 여부 확인)
		if (passwordEncoder.matches(password, mem.getPassword())) {
			// 유효한 패스워드가 맞는 경우, 로그인 성공으로 응답.
			System.out.println("로그인 성공");
			mem.setLogin(true);//로그인~
			memRepo.save(mem);
			long id=mem.getId();
			return ResponseEntity.ok(MemberLoginPostRes.of(200, "SUCESS", jwtUtil.generateToken(userId),id));
		}
		// 유효하지 않는 패스워드인 경우, 로그인 실패로 응답.
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "PASSWORD_INCORRECT"));
	}

	@PostMapping("/confirmId")
	public ResponseEntity<String> confirmUserId(@RequestBody String userId) {

		System.out.println(userId);
		if (memSer.confirmUserId(userId)) {
			System.out.println("아이디 없다");
			return ResponseEntity.status(200).body("NOT_EXISTS_USERID");
		}
		System.out.println("아이디 있다.");
		return ResponseEntity.status(200).body("EXIST_USERID");
	}

	@PostMapping("/confirmNickname")
	public ResponseEntity<String> confirmNickname(@RequestBody String Nickname) {

		System.out.println(Nickname);
		if (memSer.confirmNickname(Nickname)) {
			System.out.println("닉네임 없다");
			return ResponseEntity.status(200).body("NOT_EXISTS_NICKNAME");
		}
		System.out.println("닉네임 있다.");
		return ResponseEntity.status(200).body("EXIST_NICKNAME");
	}
	
	
	
	@GetMapping("/mypage")
	public Member getUserInfo(@RequestParam("userId") int id) {
		System.out.println("현재 로그인한 유저 정보 내놔~~");
		System.out.println(id);
		Member mem=memSer.getMemberById(id);
		System.out.println(mem.toString());
		return mem;
	}
	
	@GetMapping("/getOppInfo")
	public Member getOppInfo(@RequestParam("userId") String id) {
		System.out.println("현재 로그인한 유저 정보 내놔~~");
		System.out.println(id);
		Member mem=memRepo.getBymemberid(id);
		System.out.println(mem.toString());
		return mem;
	}

	
	@PutMapping("/changePassword")
	public String getMemberPassword(@RequestBody Map<String, String> map) {
		System.out.println("비밀번호 바꾸기~~~");
		System.out.println(map.toString());
		Member mem=memSer.getMemberById(Long.parseLong(map.get("id")));
		if(!passwordEncoder.matches(map.get("curPassword"), mem.getPassword())) {//만약 현재 비밀번호가 틀렸다면
			return "FAIL";
		}
		
		
		mem.setPassword(passwordEncoder.encode(map.get("newPassword")));
		memRepo.save(mem);
		System.out.println(mem.toString());
		return "SUCCESS";
	}
	
	
	@DeleteMapping("/deleteMember")
	public String deleteMember(@RequestParam("id") long id) {
		System.out.println(id);
		
		try {
			memSer.deleteMemberById(id);
		} catch (Exception e) {
			return "FAIL";
		}
		
		
		return "SUCCESS";
	}
	
	@GetMapping("/logout")
	public String logout(@RequestParam("id") long id) {
		
		
		System.out.println("로그아우!!!!!!!!!!!!!!!!!!!!   "+id);
		
		try {
			Member mem=memSer.getMemberById(id);
			mem.setLogin(false);
			memRepo.save(mem);
			
		} catch (Exception e) {
			return "FAIL";
		}
		return "SUCCESS";
	}
	


	@GetMapping("/likeArticle")
    	public ResponseEntity<? extends BaseResponseBody> like(@RequestParam Map<String, String> map) {
        	long id = Long.parseLong(map.get("id"));
        	long articleNo = Long.parseLong(map.get("articleNo"));

        	Article article = articleSer.findArticleByArticleNo(articleNo);
        	Member member = memSer.getMemberById(id);

        	// 만약 이미 좋아요를 누른 게시글이라면? 좋아요 취소
        	List<Article> likeArticles = member.getLikeArticles();
        	for (int i = 0; i < likeArticles.size(); i++) {
            		if (likeArticles.get(i).getArticleNo() == article.getArticleNo()) {
                	likeArticles.remove(i);
                	memRepo.save(member);
                	return ResponseEntity.status(200).body(BaseResponseBody.of(200, "DELETE"));
            	}
        	}

        	// 아직 좋아요를 누르지 않은 게시글인 경우, 좋아요를 클릭한 게시글을 회원의 ManyToMany 컬럼에 추가
        	member.getLikeArticles().add(article);
        	memRepo.save(member);

        	return ResponseEntity.status(200).body(BaseResponseBody.of(200, "SUCCESS"));
    	}

    	@GetMapping("/likeArticleCheck")
    	public String likeCheck(@RequestParam Map<String, String> map) {
        	long id = Long.parseLong(map.get("id"));
        	long articleNo = Long.parseLong(map.get("articleNo"));

        	Article article = articleSer.findArticleByArticleNo(articleNo);
        	Member member = memSer.getMemberById(id);
        	// 회원이 해당 게시글을 좋아요 눌렀는지 확인

        	List<Article> likeArticles = member.getLikeArticles();
        	System.out.println("좋아요 정보는!!!!!" + likeArticles);
        	String result = "NO";
        	for (int i = 0; i < likeArticles.size(); i++) {
            	if (likeArticles.get(i).getArticleNo() == articleNo) {
                	result = "YES";
            	}
        	}

        	return result;
    	}
	
	

}
