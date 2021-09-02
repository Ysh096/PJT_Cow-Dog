package com.xy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class chatMessageReq {
	private String chatroomId;
	private String sender;
	private String receiver;
	private String message;
}
