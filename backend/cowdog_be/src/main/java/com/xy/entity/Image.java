package com.xy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Image {
	@Id@GeneratedValue
	@Column(name="imageID")
	private Long id;
	
	private String file_path;
	private String title;
	private String imgFullPath;

}
