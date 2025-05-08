package com.cts.project.dto;


import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {

   private Long notificationId;

   @NotNull(message = "Patient ID is required")
   private Long patientId;

   @NotBlank(message = "Message is required")
   private String message;

   private LocalDateTime timestamp;
}
