package com.cts.project.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientDTO {
	private Long patientId;
	@NotBlank(message = "Patient name is required")
	private String patientName;
	@NotNull(message = "Date of birth is required")
	private LocalDate dateOfBirth;
	@Min(value = 0, message = "Age must be non-negative")
	private int age;
	@NotBlank(message = "Gender is required")
	private String gender;
	@NotBlank(message = "Blood group is required")
	private String bloodGroup;
	private String guardianName;
	@Pattern(regexp = "\\d{10}", message = "Contact number must be 10 digits")
	private String contactNumber;
	@Email(message = "Invalid email format")
	private String email;
	private String address;
	private String medicalHistory;
}