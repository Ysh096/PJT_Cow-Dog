package com.xy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xy.entity.Follow;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

	
}
