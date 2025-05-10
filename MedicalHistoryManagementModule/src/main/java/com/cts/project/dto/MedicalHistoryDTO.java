package com.cts.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalHistoryDTO {
 
    private Long historyId;
    
    private Long doctorId;
    
    @NotNull(message = "Patient ID is required")
    private Long patientId;
    
    @NotBlank(message = "Patient name is required")
    @Size(max = 50, message = "Patient name should not exceed 50 characters")
    private String patientName;
 
    @NotBlank(message = "Illness is required")
    private String illness;
 
    private String treatment;
 
    private String doctorName;
 
    private String visitDate;
 
    private String prescription;
}
 
 

 