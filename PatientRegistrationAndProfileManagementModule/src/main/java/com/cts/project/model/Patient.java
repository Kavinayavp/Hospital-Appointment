package com.cts.project.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "PatientDetails")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;

    @NotBlank(message = "Patient name is required")
    @Size(max = 50, message = "Patient name should not exceed 50 characters")
    private String patientName;
     
    @Past(message = "Date of Birth must be in the past")
    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @Min(value = 0, message = "Age cannot be negative")
    @Max(value = 100, message = "Age cannot exceed 100 years")
    private int age;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "Male|Female|Other", message = "Gender must be Male, Female, or Other")
    private String gender;

    @NotBlank(message = "Blood group is required")
    @Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Invalid blood group format")
    private String bloodGroup;

    @Size(max = 50, message = "Guardian name should not exceed 50 characters")
    private String guardianName;

    @Pattern(regexp = "^\\d{10}$", message = "Contact number must be a 10-digit number")
    private String contactNumber;

    @Email(message = "Invalid email format")
    private String email;

    @Size(max = 255, message = "Address should not exceed 255 characters")
    private String address;

    @Size(max = 500, message = "Medical history should not exceed 500 characters")
    private String medicalHistory;
}
