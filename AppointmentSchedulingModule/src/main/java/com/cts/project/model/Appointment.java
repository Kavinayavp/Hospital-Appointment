package com.cts.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @NotNull(message = "Doctor ID is required")
    private Long doctorId;

    @NotBlank(message = "Appointment date is required")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Appointment date must be in YYYY-MM-DD format")
    private String appointmentDate;

    @NotBlank(message = "Appointment time is required")
    @Pattern(regexp = "^([0-9]{2}:[0-9]{2} [APM]{2})$", message = "Appointment time must be in format HH:MM AM/PM")
    private String appointmentTime;

    private String status;
}
