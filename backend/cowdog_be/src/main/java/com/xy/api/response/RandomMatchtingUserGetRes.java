package com.xy.api.response;

import java.util.List;

import com.xy.common.response.BaseResponseBody;
import com.xy.entity.Member;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("RandomMatchtingUserGetRes")
public class RandomMatchtingUserGetRes extends BaseResponseBody {
	
	List<Member> list;
	
	
	
	
	public static RandomMatchtingUserGetRes of(Integer statusCode, String message, List<Member> list) {
		RandomMatchtingUserGetRes res = new RandomMatchtingUserGetRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setList(list);
		return res;
	}

}
