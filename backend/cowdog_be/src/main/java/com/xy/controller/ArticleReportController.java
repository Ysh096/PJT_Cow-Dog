package com.xy.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xy.api.request.ArticleReportPostReq;
import com.xy.common.response.BaseResponseBody;
import com.xy.entity.Article;
import com.xy.entity.ArticleReport;
import com.xy.entity.UserReport;
import com.xy.service.ArticleReportService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/cowdog/article-report")
@CrossOrigin(origins="http://localhost:8083")
public class ArticleReportController {

	@Autowired
	ArticleReportService articleReportService;
	
	@PostMapping("") // '/user-report'에 post 요청
	@ApiOperation(value = "게시글 신고", notes = "게시글을 신고한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseBody> create(
			@RequestBody ArticleReportPostReq request) {
		System.out.println(request);
		if(articleReportService.createArticleReport(request).equals("FAIL")) {
			return ResponseEntity.status(200).body(BaseResponseBody.of(404, "FAIL"));
		}
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "SUCCESS"));
	}
	
	@GetMapping("") // article-report에 get 요청
	public List<ArticleReport> get() {
		return articleReportService.getArticleReportList();
	}
	
	@DeleteMapping("/{article_report_no}")
	public List<ArticleReport> delete(@PathVariable("article_report_no") Long articleReportNo) {
		articleReportService.deleteArticleReport(articleReportNo);
		return articleReportService.getArticleReportList();
	}
	
	@DeleteMapping("/reported-article/{reported_article_no}/{reported_article_long_no}/{reported_article_article_no}")// reported article delete 요청
	public List<ArticleReport> deleteReportedArticle(
			@PathVariable("reported_article_no") String reportedArticleNo,
			@PathVariable("reported_article_long_no") Long reportedArticleLongNo,
			@PathVariable("reported_article_article_no") Article reportedArticleArticleNo) {
		articleReportService.deleteReportedArticle(reportedArticleNo, reportedArticleLongNo, reportedArticleArticleNo);
		return articleReportService.getArticleReportList();
	}
}
