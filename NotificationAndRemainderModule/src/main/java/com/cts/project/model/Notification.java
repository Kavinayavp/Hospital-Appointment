package com.cts.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "NotificationDetails")
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long notificationId;

	@NotNull(message = "Patient ID is required")
	private Long patientId;

	@NotBlank(message = "Message content cannot be empty")
	@Size(max = 500, message = "Message should not exceed 500 characters")
	private String message;

	@PastOrPresent(message = "Timestamp must be in the past or present")
	private LocalDateTime timestamp;
}
