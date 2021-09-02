package com.xy.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Article_Comments {//댓글 엔터티
	@Id
	@GeneratedValue
	Long no;//댓글 번호 겸 pk

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="ariticleno")
	private Article articleno;//이 댓글이 달린 글 번호
	private String id;//작성자
	private String regtime;//작성일
	private String content;//내용
}
