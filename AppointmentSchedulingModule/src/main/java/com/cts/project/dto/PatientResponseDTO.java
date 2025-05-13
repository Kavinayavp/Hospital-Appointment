package com.cts.project.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponseDTO {
	private Long patientId;
	private String patientName;
	private String contactNumber;
	private String email;
}