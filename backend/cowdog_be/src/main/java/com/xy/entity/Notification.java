package com.xy.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Notification {
	
	@Id@GeneratedValue(strategy = GenerationType.AUTO)
	private Long notificaionNo;
	
	
	private long sender;
	private long receiver;
	
	private boolean flag;
	
	
	
}
