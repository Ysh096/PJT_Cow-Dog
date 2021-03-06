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
	@ApiOperation(value = "?????? ??????", notes = "<strong>???????????? ????????????</strong>??? ?????? ???????????? ??????.")
	@ApiResponses({ @ApiResponse(code = 200, message = "??????"), @ApiResponse(code = 401, message = "?????? ??????"),
			@ApiResponse(code = 404, message = "????????? ??????"), @ApiResponse(code = 500, message = "?????? ??????") })
	public ResponseEntity<? extends BaseResponseBody> register(@RequestBody HashMap<String, Object> map) {
		System.out.println(map);
		
		
		

		if (memSer.registerMember(map).equals("FAIL")) {
			return ResponseEntity.status(200).body(BaseResponseBody.of(404, "FAIL"));
		}

		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "SUCCESS"));
	}
	
	@PostMapping("/profileImgaeUpload")
	public void execWrite(Image image, MultipartFile files,String userId) throws IOException {
		System.out.println("????????? ????????? ?????????");
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
	@ApiOperation(value = "?????????", notes = "<strong>???????????? ????????????</strong>??? ?????? ????????? ??????.")
	@ApiResponses({ @ApiResponse(code = 200, message = "??????", response = BaseResponseBody.class),
			@ApiResponse(code = 401, message = "?????? ??????", response = BaseResponseBody.class),
			@ApiResponse(code = 404, message = "????????? ??????", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "?????? ??????", response = BaseResponseBody.class) })
	public ResponseEntity<? extends BaseResponseBody> login(
			@RequestBody @ApiParam(value = "????????? ??????", required = true) MemberLoginPostReq loginInfo) throws Exception{
		String userId = loginInfo.getId();
		String password = loginInfo.getPassword();
		System.out.println(userId);
		System.out.println(password);
		Member mem = memSer.getMemberByMemberId(userId);

		

		if (mem == null) {
			System.out.println("?????? ??????");
			return ResponseEntity.status(404).body(BaseResponseBody.of(404, "NOT_EXISTS_USER"));
		}
		if(mem.isIssuspended()) {
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "ISSUSPENDED"));//?????? ???????????? ?????????
		}
		// ????????? ????????? ??????????????? ????????? ???????????? ??? ????????? ????????? ????????? ???????????? ??????????????? ????????? ??????.(????????? ?????????????????? ?????? ??????)
		if (passwordEncoder.matches(password, mem.getPassword())) {
			// ????????? ??????????????? ?????? ??????, ????????? ???????????? ??????.
			System.out.println("????????? ??????");
			mem.setLogin(true);//?????????~
			memRepo.save(mem);
			long id=mem.getId();
			return ResponseEntity.ok(MemberLoginPostRes.of(200, "SUCESS", jwtUtil.generateToken(userId),id));
		}
		// ???????????? ?????? ??????????????? ??????, ????????? ????????? ??????.
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "PASSWORD_INCORRECT"));
	}

	@PostMapping("/confirmId")
	public ResponseEntity<String> confirmUserId(@RequestBody String userId) {

		System.out.println(userId);
		if (memSer.confirmUserId(userId)) {
			System.out.println("????????? ??????");
			return ResponseEntity.status(200).body("NOT_EXISTS_USERID");
		}
		System.out.println("????????? ??????.");
		return ResponseEntity.status(200).body("EXIST_USERID");
	}

	@PostMapping("/confirmNickname")
	public ResponseEntity<String> confirmNickname(@RequestBody String Nickname) {

		System.out.println(Nickname);
		if (memSer.confirmNickname(Nickname)) {
			System.out.println("????????? ??????");
			return ResponseEntity.status(200).body("NOT_EXISTS_NICKNAME");
		}
		System.out.println("????????? ??????.");
		return ResponseEntity.status(200).body("EXIST_NICKNAME");
	}
	
	
	
	@GetMapping("/mypage")
	public Member getUserInfo(@RequestParam("userId") int id) {
		System.out.println("?????? ???????????? ?????? ?????? ??????~~");
		System.out.println(id);
		Member mem=memSer.getMemberById(id);
		System.out.println(mem.toString());
		return mem;
	}
	
	@GetMapping("/getOppInfo")
	public Member getOppInfo(@RequestParam("userId") String id) {
		System.out.println("?????? ???????????? ?????? ?????? ??????~~");
		System.out.println(id);
		Member mem=memRepo.getBymemberid(id);
		System.out.println(mem.toString());
		return mem;
	}

	
	@PutMapping("/changePassword")
	public String getMemberPassword(@RequestBody Map<String, String> map) {
		System.out.println("???????????? ?????????~~~");
		System.out.println(map.toString());
		Member mem=memSer.getMemberById(Long.parseLong(map.get("id")));
		if(!passwordEncoder.matches(map.get("curPassword"), mem.getPassword())) {//?????? ?????? ??????????????? ????????????
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
		
		
		System.out.println("????????????!!!!!!!!!!!!!!!!!!!!   "+id);
		
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

        	// ?????? ?????? ???????????? ?????? ??????????????????? ????????? ??????
        	List<Article> likeArticles = member.getLikeArticles();
        	for (int i = 0; i < likeArticles.size(); i++) {
            		if (likeArticles.get(i).getArticleNo() == article.getArticleNo()) {
                	likeArticles.remove(i);
                	memRepo.save(member);
                	return ResponseEntity.status(200).body(BaseResponseBody.of(200, "DELETE"));
            	}
        	}

        	// ?????? ???????????? ????????? ?????? ???????????? ??????, ???????????? ????????? ???????????? ????????? ManyToMany ????????? ??????
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
        	// ????????? ?????? ???????????? ????????? ???????????? ??????

        	List<Article> likeArticles = member.getLikeArticles();
        	System.out.println("????????? ?????????!!!!!" + likeArticles);
        	String result = "NO";
        	for (int i = 0; i < likeArticles.size(); i++) {
            	if (likeArticles.get(i).getArticleNo() == articleNo) {
                	result = "YES";
            	}
        	}

        	return result;
    	}
	
	

}
