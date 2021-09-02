package com.xy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xy.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{
	

}
