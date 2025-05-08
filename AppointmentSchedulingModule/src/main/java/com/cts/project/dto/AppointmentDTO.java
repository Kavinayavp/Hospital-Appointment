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
 
    @NotNull(message = "Doctor ID is required")
    private Long doctorId;
 
    @NotBlank(message = "Appointment date is required")
    private String appointmentDate;
 
    @NotBlank(message = "Appointment time is required") 
    private String appointmentTime;
 
    @Pattern(regexp = "Scheduled|Cancelled|Completed", message = "Status must be Scheduled, Cancelled, or Completed")
    private String status;
}
 