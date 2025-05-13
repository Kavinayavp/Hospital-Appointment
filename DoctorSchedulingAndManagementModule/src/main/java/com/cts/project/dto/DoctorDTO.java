package com.cts.project.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorDTO {

	private Long doctorId;

	@NotBlank(message = "Doctor name is required")
	private String doctorName;

	@NotBlank(message = "Specialization is required")
	private String specialization;

	@Min(value = 0, message = "Experience must be positive")
	private int experience;

	@Pattern(regexp = "^\\d{10}$", message = "Contact number must be a 10-digit number")
	@NotBlank(message = "Contact number is required")
	private String contactNumber;

	@Email(message = "Invalid email")
	private String email;

	@NotEmpty(message = "Available days are required")
	private List<String> availableDays;

	@Pattern(regexp = "^([0-9]{2}:[0-9]{2} [APM]{2} - [0-9]{2}:[0-9]{2} [APM]{2})$", message = "Available time must be in format HH:MM AM/PM - HH:MM AM/PM")
	@NotBlank(message = "Available time is required")
	private String availableTime;
}