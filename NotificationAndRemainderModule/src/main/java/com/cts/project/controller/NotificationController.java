package com.cts.project.controller;

import com.cts.project.dto.NotificationDTO;
import com.cts.project.service.NotificationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/getnotificationbypatientid/{patientId}")
    public List<NotificationDTO> getByPatient(@PathVariable Long patientId) {
        logger.info("Fetching notifications for Patient ID: {}", patientId);
        return service.getNotificationsByPatientId(patientId);
    }

    @GetMapping("/appointments/{appointmentId}/notify")
    public String notifyAppointment(@PathVariable Long appointmentId) {
        logger.info("Sending appointment notification for Appointment ID: {}", appointmentId);
        return service.notifyAppointmentStatus(appointmentId);
    }

}
