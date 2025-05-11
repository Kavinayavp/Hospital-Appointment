package com.cts.project.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cts.project.dto.AppointmentResponseDTO;
import com.cts.project.dto.NotificationDTO;
import com.cts.project.dto.PatientResponseDTO;
import com.cts.project.exception.NotificationNotFoundException;
import com.cts.project.feignclient.AppointmentClient;
import com.cts.project.feignclient.MedicalHistoryClient;
import com.cts.project.feignclient.PatientClient;
import com.cts.project.model.Notification;
import com.cts.project.repository.NotificationRepository;

import feign.FeignException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {
 
    private final NotificationRepository repository;
    private final PatientClient patientClient;
    private final AppointmentClient appointmentClient;
    private final MedicalHistoryClient medicalHistoryClient;
    
    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    public String notifyAppointmentStatus(Long appointmentId) {
        try {
            AppointmentResponseDTO appointment = appointmentClient.getAppointmentById(appointmentId);
            String message = switch (appointment.getStatus().toUpperCase()) {
                case "BOOKED" -> "Your appointment has been successfully booked for " + appointment.getAppointmentDate() + " at " + appointment.getAppointmentTime() + ".";
                case "RESCHEDULED" -> "Your appointment has been rescheduled to " + appointment.getAppointmentDate() + " at " + appointment.getAppointmentTime() + ".";
                case "CANCELLED" -> "Your appointment has been cancelled.";
                default -> "Your appointment status is updated.";
            };

            sendNotificationToPatient(appointment.getPatientId(), message);
            logger.info("Notification sent for appointment ID: {}", appointmentId);
            return "Notification sent successfully for Appointment ID: " + appointmentId;
        } catch (FeignException.NotFound e) {
            logger.error("Appointment not found with ID: {}", appointmentId);
            return "Appointment not found with ID: " + appointmentId;
        } catch (Exception e) {
            logger.error("Error fetching appointment details: {}", e.getMessage());
            return "Error fetching appointment details: " + e.getMessage();
        }
    }

   

    public String sendNotificationToPatient(Long patientId, String message) {
        try {
            PatientResponseDTO patient = patientClient.getPatientById(patientId);
            Notification notification = new Notification();
            notification.setPatientId(patientId);
            notification.setMessage("Hi " + patient.getPatientName() + ", " + message);
            notification.setTimestamp(LocalDateTime.now());

            Notification savedNotification = repository.save(notification); // ✅ Store notification
            logger.info("✅ Notification stored in DB: {}", savedNotification); // ✅ Log saved notification
            return "Notification sent successfully to Patient ID: " + patientId;
        } catch (FeignException.NotFound e) {
            logger.error("❌ Patient not found with ID: {}", patientId);
            return "Patient not found with ID: " + patientId;
        } catch (Exception e) {
            logger.error("❌ Error while sending notification: {}", e.getMessage());
            return "Error while sending notification: " + e.getMessage();
        }
    }

    /** ✅ Send Notification Manually **/
    @Override
    public NotificationDTO sendNotification(NotificationDTO dto) {
        Notification notification = mapToEntity(dto);
        Notification savedNotification = repository.save(notification);
        return mapToDTO(savedNotification);
    }

    /** ✅ Get Notifications by Patient ID **/
    @Override
    public List<NotificationDTO> getNotificationsByPatientId(Long patientId) {
        List<Notification> notifications = repository.findByPatientId(patientId);
        if (notifications.isEmpty()) {
            throw new NotificationNotFoundException("No notifications found for Patient ID: " + patientId);
        }
        return notifications.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    /** ✅ Helper Methods for DTO Mapping **/
    private NotificationDTO mapToDTO(Notification notification) {
        return NotificationDTO.builder()
                .notificationId(notification.getNotificationId())
                .patientId(notification.getPatientId())
                .message(notification.getMessage())
                .timestamp(notification.getTimestamp())
                .build();
    }
 
    private Notification mapToEntity(NotificationDTO dto) {
        return Notification.builder()
                .notificationId(dto.getNotificationId())
                .patientId(dto.getPatientId())
                .message(dto.getMessage())
                .timestamp(dto.getTimestamp() != null ? dto.getTimestamp() : LocalDateTime.now())
                .build();
    }
}
