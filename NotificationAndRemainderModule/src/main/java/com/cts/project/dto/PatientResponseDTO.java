package com.cts.project.dto;

import lombok.*;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponseDTO {
    private Long patientId;
    private String patientName;
    private String email;
    private String contactNumber;
}