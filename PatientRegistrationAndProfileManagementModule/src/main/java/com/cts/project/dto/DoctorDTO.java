package com.cts.project.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorDTO {
    private Long doctorId;
    private String doctorName;
    private String specialization;
    private int experience;
    private String contactNumber;
    private String email;
    private List<String> availableDays;
    private String availableTime;
}
