package com.cts.project.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.project.dto.DoctorResponseDTO;

@FeignClient(name = "DOCTORSCHEDULINGANDMANAGEMENTMODULE", url = "http://localhost:8083/doctors")
public interface DoctorClient {

	@GetMapping("/getdoctorbyid/{id}")
	public DoctorResponseDTO getDoctorById(@PathVariable Long id);

	@GetMapping("/availability/{specialization}")
	public DoctorResponseDTO getAvailability(@PathVariable String specialization);

}
