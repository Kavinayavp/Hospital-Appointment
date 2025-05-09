package com.cts.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponseDTO {
    private Long appointmentId;
    private Long patientId;
    private Long doctorId;
    private String appointmentDate;
    private String appointmentTime;
    private String status;
}