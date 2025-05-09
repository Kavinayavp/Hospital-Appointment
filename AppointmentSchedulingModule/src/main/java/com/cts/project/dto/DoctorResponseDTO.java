package com.cts.project.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResponseDTO {
    private Long doctorId;
    private String doctorName;
    private List<String> availableDays;
    private String availableTime;
}
