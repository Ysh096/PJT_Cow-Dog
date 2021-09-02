package com.xy.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.type.TimestampType;

import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
//@ToString
public class Article {
	
	@Id
	@Column(name = "articleNo")
	@GeneratedValue
	private Long articleNo;//pk
	
	private String writer;
	private Long memberId;

	@JsonManagedReference
	@OneToMany(mappedBy = "articleno", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Article_Comments> comments;// 이 게시물에 댓글들
	private String file_path;
	private String title;
	private String imgFullPath;
	private String content;
	private Timestamp regtime;
	private String image;
	private String tags;

	// @ManyToMany(mappedBy = "members")
	// private List<Member> members = new ArrayList<>(); // 다대다 관계
	@JsonManagedReference
	@ManyToMany(mappedBy = "likeArticles", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Member> likeMembers = new ArrayList<>(); // 다대다 관계
	
	

}
