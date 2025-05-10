package com.cts.project.dto;

import jakarta.validation.constraints.*;
import lombok.*;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentDTO {
 
    private Long appointmentId;
 
    @NotNull(message = "Patient ID is required")
    private Long patientId;
    
    @NotBlank(message = "Patient name is required")
    @Size(max = 50, message = "Patient name should not exceed 50 characters")
    private String patientName;
 
    @NotNull(message = "Doctor ID is required")
    private Long doctorId;
    
    @NotBlank(message = "Doctor name is required")
    @Size(max = 50, message = "Doctor name should not exceed 50 characters")
    private String doctorName;
 
    @NotBlank(message = "Appointment date is required")
    private String appointmentDate;
 
    @NotBlank(message = "Appointment time is required") 
    private String appointmentTime;
 
    private String status; 
    
}
 