package com.cts.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "AppointmentDetails")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Appointment date must be in YYYY-MM-DD format")
    private String appointmentDate;

    @NotBlank(message = "Appointment time is required")
    @Pattern(regexp = "^([0-9]{2}:[0-9]{2} [APM]{2})$", message = "Appointment time must be in format HH:MM AM/PM")
    private String appointmentTime;

    private String status;
}
