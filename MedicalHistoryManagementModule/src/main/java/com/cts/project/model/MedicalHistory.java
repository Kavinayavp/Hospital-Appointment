package com.cts.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "MedicalHistoryDetails")
public class MedicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @NotBlank(message = "Illness is required")
    @Size(max = 100, message = "Illness should not exceed 100 characters")
    private String illness;

    @Size(max = 500, message = "Treatment description should not exceed 500 characters")
    private String treatment;

    @NotBlank(message = "Doctor name is required")
    @Size(max = 50, message = "Doctor name should not exceed 50 characters")
    private String doctorName;

    @NotNull(message = "Visit date is required")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date should be in format YYYY-MM-DD")
    private String visitDate;

    @Size(max = 500, message = "Prescription should not exceed 500 characters")
    private String prescription;
}
