package com.cts.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalHistoryDTO {
 
    private Long historyId;
 
    @NotNull(message = "Patient ID is required")
    private Long patientId;
 
    @NotBlank(message = "Illness is required")
    private String illness;
 
    private String treatment;
 
    private String doctorName;
 
    private String visitDate;
 
    private String prescription;
}
 
 

 