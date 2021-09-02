package com.xy.repository;

import javax.persistence.NamedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xy.entity.Image;

@Repository
public interface ImageRepository extends  JpaRepository<Image, Long>{
	
}
