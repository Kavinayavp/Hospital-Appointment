package com.cts.project.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorResponseDTO {
    private Long doctorId;
    private String doctorName;
    private String specialization;
    private int experience;
    private String contactNumber;
    private String email;
    private List<String> availableDays;
    private String availableTime;
}
