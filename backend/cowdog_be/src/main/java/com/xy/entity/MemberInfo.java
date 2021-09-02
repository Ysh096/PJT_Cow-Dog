package com.xy.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class MemberInfo {
	
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String age;
	private String gender;
	private boolean smoking;
	private String alcohol;
	private String religion;
	private String personality;
	private String mbti;
	private String mymbti;
	private String hobby;
	private double latitude;
	private double longitude;
	private int distance;
	private String address;
	
	
	

}
