package com.xy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/*
 * notice create에 필요한 request body 정의
 * */
@Getter
@Setter
@ApiModel("UserReportPostReq")
public class UserReportPostReq {
	@ApiModelProperty(name="신고id", example="3")
	String reportedId;
	@ApiModelProperty(name="title", example="신고 제목")
	String title;
	@ApiModelProperty(name="content", example="신고 내용")
	String content;
	// 이미지 추가 필수
//	@ApiModelProperty(name="image", example="image server url")
//	String image;
}