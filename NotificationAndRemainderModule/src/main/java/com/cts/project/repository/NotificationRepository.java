package com.cts.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.project.model.Notification;

import java.util.List;
 
public interface NotificationRepository extends JpaRepository<Notification, Long> {
   
}
 