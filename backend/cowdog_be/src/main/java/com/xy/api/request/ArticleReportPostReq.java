package com.xy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserReportPostReq")
public class ArticleReportPostReq {
	@ApiModelProperty(name="신고 게시글 Number", example="3")
	String reportedArticleNo;
	@ApiModelProperty(name="title", example="신고 제목")
	String title;
	@ApiModelProperty(name="content", example="신고 내용")
	String content;
}