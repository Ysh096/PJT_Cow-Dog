package com.xy.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DistanceMatchingResult {

	
	
	private Long id;
	private String memberid;;
	private MemberInfo memberinfo;
	private String file_path;
	private int dist;

}
