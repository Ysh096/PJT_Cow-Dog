package com.xy.api.response;

import java.util.List;

import com.xy.common.response.BaseResponseBody;
import com.xy.entity.DistanceMatchingResult;
import com.xy.entity.Member;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("DistanceMatchingUserGetRes")
public class DistanceMatchingUserGetRes extends BaseResponseBody {
	
	List<DistanceMatchingResult> list;
	
	
	
	
	public static DistanceMatchingUserGetRes of(Integer statusCode, String message, List<DistanceMatchingResult> list) {
		DistanceMatchingUserGetRes res = new DistanceMatchingUserGetRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setList(list);
		return res;
	}

}
