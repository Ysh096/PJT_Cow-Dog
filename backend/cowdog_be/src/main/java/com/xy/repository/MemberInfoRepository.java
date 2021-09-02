package com.xy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xy.entity.MemberInfo;


@Repository
public interface MemberInfoRepository extends JpaRepository<MemberInfo, Long>{
	

}
