package com.cts.project.service;

import com.cts.project.dto.NotificationDTO;
import java.util.List;
 
public interface NotificationService {
    NotificationDTO sendNotification(NotificationDTO dto);
	String notifyAppointmentStatus(Long appointmentId);
}