package com.xy.api.response;

import java.util.List;

import com.xy.common.response.BaseResponseBody;
import com.xy.entity.Notification;
import com.xy.entity.NotificationResult;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("NotificationResultGetRes")
public class NotificationResultGetRes extends BaseResponseBody {
	
	List<NotificationResult> list;
	
	
	
	
	public static NotificationResultGetRes of(Integer statusCode, String message, List<NotificationResult> list) {
		NotificationResultGetRes res = new NotificationResultGetRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setList(list);
		return res;
	}

}
