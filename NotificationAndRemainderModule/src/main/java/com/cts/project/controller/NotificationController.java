package com.cts.project.controller;

import com.cts.project.dto.NotificationDTO;
import com.cts.project.service.NotificationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
@AllArgsConstructor
public class NotificationController {

	private final NotificationService service;
	private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

	@PostMapping("/send")
	public NotificationDTO send(@Valid @RequestBody NotificationDTO dto) {
		logger.info("Sending notification: {}", dto);
		return service.sendNotification(dto);
	}

	@GetMapping("/appointments/{appointmentId}/notifyPatient")
	public String notifyPatient(@PathVariable Long appointmentId) {
		logger.info("Sending patient notification for Appointment ID: {}", appointmentId);
	    return service.notifyPatientAboutAppointment(appointmentId);
	}


	@GetMapping("/appointments/{appointmentId}/notifyDoctor")
	public String notifyDoctor(@PathVariable Long appointmentId) {
		logger.info("Sending doctor notification for Appointment ID: {}", appointmentId);
	    return service.notifyDoctorAboutAppointment(appointmentId);
	}


}
