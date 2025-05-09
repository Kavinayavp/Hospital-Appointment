package com.cts.project.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.project.dto.PatientResponseDTO;

@FeignClient(name = "PATIENTREGISTRATIONANDPROFILEMANAGEMENTMODULE", url = "http://localhost:8081/patients")
public interface PatientClient {
    @GetMapping("/getPatientById/{patientId}")
    PatientResponseDTO getPatientById(@PathVariable Long patientId);
}