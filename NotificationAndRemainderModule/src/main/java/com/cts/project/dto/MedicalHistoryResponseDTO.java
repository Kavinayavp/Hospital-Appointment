package com.cts.project.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalHistoryResponseDTO {
    private Long historyId;
    private Long patientId;
    private String illness;
    private String treatment;
    private String doctorName;
    private String visitDate;
    private String prescription;
}