package com.cts.project.feignclient;

import com.cts.project.dto.PatientResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PATIENTREGISTRATIONANDPROFILEMANAGEMENTMODULE", url = "http://localhost:8081/patients")
public interface PatientClient {
	@GetMapping("/getpatientbyid/{id}")
	public PatientResponseDTO getPatientById(@PathVariable Long id);

	@GetMapping("/exists/{id}")
	public boolean existsById(@PathVariable("id") Long patientId);

}