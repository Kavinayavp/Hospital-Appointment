package com.cts.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "DoctorDetails")
public class Doctor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long doctorId;

	@NotBlank(message = "Doctor name is required")
	@Size(max = 50, message = "Doctor name should not exceed 50 characters")
	private String doctorName;

	@NotBlank(message = "Specialization is required")
	@Size(max = 50, message = "Specialization should not exceed 50 characters")
	private String specialization;

	@Min(value = 0, message = "Experience cannot be negative")
	@Max(value = 60, message = "Experience cannot exceed 60 years")
	private int experience;

	@Pattern(regexp = "^\\d{10}$", message = "Contact number must be a 10-digit number")
	private String contactNumber;

	@Email(message = "Invalid email format")
	private String email;

	@ElementCollection
	@NotEmpty(message = "Available days cannot be empty")
	private List<String> availableDays;

	@Pattern(regexp = "^([0-9]{2}:[0-9]{2} [APM]{2} - [0-9]{2}:[0-9]{2} [APM]{2})$", message = "Available time must be in format HH:MM AM/PM - HH:MM AM/PM")
	private String availableTime;
}
