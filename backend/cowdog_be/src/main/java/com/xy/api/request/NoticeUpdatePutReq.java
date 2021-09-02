package com.xy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@ApiModel("NoticeUpdateRequest")
public class NoticeUpdatePutReq {
	@ApiModelProperty(name="noticeNo", example="3")
	Long noticeNo;
	@ApiModelProperty(name="title", example="cow&dog 사용 안내")
	String title;
	@ApiModelProperty(name="content", example="즐겁게 사용해주세요.")
	String content;
}