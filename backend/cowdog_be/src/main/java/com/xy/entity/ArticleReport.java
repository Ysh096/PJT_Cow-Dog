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
public class ArticleReport {
	
	@Id @GeneratedValue
	private Long ArticleReportNo; // 신고 게시물 순서 넘버링
	
	private String reportedArticleNo;//어떤 게시글이 신고 대상인지
	private String title;
	private String content;
	private Timestamp regtime;
	private String articleUrl;
}
