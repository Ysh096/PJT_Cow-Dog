package com.xy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 로그인 요청
 */
@Getter
@Setter
@ApiModel("MemberLoginPostReq")
public class MemberLoginPostReq {
	@ApiModelProperty(name="유저 ID", example="아이디")
	String id;
	@ApiModelProperty(name="유저 Password", example="비번")
	String password;
}
