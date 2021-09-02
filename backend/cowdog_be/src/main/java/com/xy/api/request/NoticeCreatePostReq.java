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
@ApiModel("NoticeCreateRequest")
public class NoticeCreatePostReq {
	@ApiModelProperty(name="title", example="cow&dog 사용 안내")
	String title;
	@ApiModelProperty(name="content", example="즐겁게 사용해주세요.")
	String content;
}