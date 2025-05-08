package com.cts.project.dto;
import jakarta.validation.constraints.*;
import lombok.*;
 
import java.util.List;
 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorDTO {
 
    private Long doctorId;
 
    @NotBlank(message = "Doctor name is required")
    private String doctorName;
 
    @NotBlank(message = "Specialization is required")
    private String specialization;
 
    @Min(value = 0, message = "Experience must be positive")
    private int experience;
 
    @NotBlank(message = "Contact number is required")
    private String contactNumber;
 
    @Email(message = "Invalid email")
    private String email;
 
    @NotEmpty(message = "Available days are required")
    private List<String> availableDays;
 
    @NotBlank(message = "Available time is required")
    private String availableTime;
}