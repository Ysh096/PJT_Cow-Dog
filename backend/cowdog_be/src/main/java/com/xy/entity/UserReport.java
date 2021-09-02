package com.xy.entity;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserReport {
	
	@Id @GeneratedValue
	private Long userReportNo;
	
	private String reportedId;//누가 신고 대상인지
	private String title;
	private String content;
	private Timestamp regtime;
	private String image;
}
