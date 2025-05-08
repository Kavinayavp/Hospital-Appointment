package com.cts.project.feignclient;

import com.cts.project.dto.PatientResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
 
@FeignClient(name = "PATIENTREGISTRATIONANDPROFILEMANAGEMENTMODULE", path="/patients")
public interface PatientClient {
 
    @GetMapping("/api/patients/{patientId}")
    PatientResponseDTO getPatientById(@PathVariable("patientId") Long patientId);
}