package com.cts.project.service;

import com.cts.project.dto.NotificationDTO;
import com.cts.project.exception.NotificationNotFoundException;
import com.cts.project.model.Notification;
import com.cts.project.repository.NotificationRepository;
import com.cts.project.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
 
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
 
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
 
    private final NotificationRepository repository;
 
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
 
    @Override
    public NotificationDTO sendNotification(NotificationDTO dto) {
        Notification notification = mapToEntity(dto);
        return mapToDTO(repository.save(notification));
    }
 
    @Override
    public List<NotificationDTO> getNotificationsByPatientId(Long patientId) {
        List<Notification> notifications = repository.findByPatientId(patientId);
        if (notifications.isEmpty()) {
            throw new NotificationNotFoundException("No notifications found for patient ID: " + patientId);
        }
        return notifications.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}
 