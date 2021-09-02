package com.xy.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xy.api.request.UserReportPostReq;
import com.xy.common.response.BaseResponseBody;
import com.xy.entity.Notice;
import com.xy.entity.UserReport;
import com.xy.service.UserReportService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/cowdog/user-report")
@CrossOrigin(origins="http://localhost:8083")
public class UserReportController {

	@Autowired
	UserReportService userReportService;
	
	@PostMapping("") // user-report에 post 요청
	@ApiOperation(value = "유저 신고", notes = "유저를 신고한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseBody> create(
			@RequestBody UserReportPostReq request) {
		System.out.println(request);
		if(userReportService.createUserReport(request).equals("FAIL")) {
			return ResponseEntity.status(200).body(BaseResponseBody.of(404, "FAIL"));
		}
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "SUCCESS"));
	}
	
	@GetMapping("") // user-report에 get 요청
	public List<UserReport> get() {
		return userReportService.getUserReportList();
	}
	
	@DeleteMapping("/{user_report_no}") // user_report_no 신고 delete 요청
	public List<UserReport> deleteReport(@PathVariable("user_report_no") Long userReportNo) {
		userReportService.deleteUserReport(userReportNo);
		return userReportService.getUserReportList();
	}
	
	@DeleteMapping("/user/{user_id}") // user delete 요청
	public List<UserReport> deleteUser(
			@PathVariable("user_id") String userId
			) {
		userReportService.deleteReportedUser(userId);
		return userReportService.getUserReportList();
	}
	@PatchMapping("/user/{user_id}")
	public List<UserReport> suspendUser(
			@PathVariable("user_id") String userId
			) {
		userReportService.suspendReportedUser(userId);
		return userReportService.getUserReportList();
	}
	
}
