package com.xy.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Notice {
	
	
	@Id@GeneratedValue
	private Long noticeNo;
	
	private String title;
	private String content;
	private Timestamp regtime;
	private String image;
}
