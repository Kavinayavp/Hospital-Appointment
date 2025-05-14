package com.cts.project.service;

import com.cts.project.dto.NotificationDTO;

public interface NotificationService {
	NotificationDTO sendNotification(NotificationDTO dto);

	String notifyPatientAboutAppointment(Long appointmentId);
	
	public String notifyDoctorAboutAppointment(Long appointmentId);


}