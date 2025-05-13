package com.cts.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.project.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
