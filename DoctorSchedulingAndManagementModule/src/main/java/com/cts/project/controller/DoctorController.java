package com.cts.project.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.project.dto.DoctorDTO;
import com.cts.project.service.DoctorService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/doctors")
@AllArgsConstructor
public class DoctorController {

	private DoctorService service;

	@PostMapping("/save")
	public String saveDoctor(@Valid @RequestBody DoctorDTO dto) {
		service.saveDoctor(dto);
		return "Doctor details saved successfully";
	}

	@PutMapping("/update/{id}")
	public String updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorDTO dto) {
	    service.updateDoctor(id, dto);
	    return "Doctor details updated successfully";
	}

	@GetMapping("/getalldoctors")
	public List<DoctorDTO> getAllDoctors() {
		return service.getAllDoctors();
	}

	@GetMapping("/getdoctorbyid/{id}")
	public DoctorDTO getDoctorById(@PathVariable Long id) {
		return service.getDoctorById(id);
	}

	@DeleteMapping("/deletedoctor/{id}")
	public String deleteDoctor(@PathVariable Long id) {
		service.deleteDoctor(id);
		return "Doctor with ID " + id + " deleted successfully.";
	}

	@GetMapping("/availability/{specialization}")
	public DoctorDTO getAvailability(@PathVariable String specialization) {
		return service.getDoctorAvailability(specialization);
	}
	

  
}