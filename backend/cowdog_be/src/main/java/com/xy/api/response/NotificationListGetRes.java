package com.xy.api.response;

import java.util.List;

import com.xy.common.response.BaseResponseBody;
import com.xy.entity.Notification;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("NotificationListGetRes")
public class NotificationListGetRes extends BaseResponseBody {
	
	List<Notification> list;
	
	
	
	
	public static NotificationListGetRes of(Integer statusCode, String message, List<Notification> list) {
		NotificationListGetRes res = new NotificationListGetRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setList(list);
		return res;
	}

}
