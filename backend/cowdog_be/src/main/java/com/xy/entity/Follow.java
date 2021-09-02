package com.xy.entity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Follow {
	
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private long id;
	
	
	private long member_id;// 사용자 id
	private long follower_id;// 사용자를 팔로우 하는 사람들
	
	
}
