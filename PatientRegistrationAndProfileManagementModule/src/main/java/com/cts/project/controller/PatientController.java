package com.cts.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.cts.project.dto.DoctorDTO;
import com.cts.project.dto.PatientDTO;
import com.cts.project.exception.ResourceNotFoundException;
import com.cts.project.exception.UnauthorizedAccessException;
import com.cts.project.service.PatientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/patients")
public class PatientController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PatientController.class);

	@Autowired
	private PatientService patientService;

	/** Saves patient details */
	@PostMapping("/save")
	public String savePatient(@Valid @RequestBody PatientDTO dto) {
		LOGGER.info("Attempting to save patient details: {}", dto.getEmail());
		return patientService.savePatient(dto);
	}

	/** Updates patient details */
	@PutMapping("/update/{id}")
	public String updatePatient(@PathVariable Long id, @Valid @RequestBody PatientDTO dto) {
	    LOGGER.info("Attempting to update patient details for ID: {}", id);

	    try {
	        return patientService.updatePatient(id, dto);
	    } catch (UnauthorizedAccessException e) {
	        LOGGER.error("❌ Unauthorized update attempt for patient ID: {}", id);
	        return "Unauthorized: You cannot update another patient's details.";
	    } catch (ResourceNotFoundException e) {
	        LOGGER.error("❌ Patient not found: {}", id);
	        return "Patient not found with ID: " + id;
	    } catch (Exception e) {
	        LOGGER.error("❌ Error updating patient details: {}", e.getMessage());
	        return "Failed to update patient details.";
	    }
	}


	/** Retrieves all patients */
	@GetMapping("/getallpatients")
	public List<PatientDTO> getAllPatients() {
		LOGGER.info("Fetching all patients.");
		return patientService.getAllPatients();
	}

	/** Retrieves patient details */
	@GetMapping("/getpatientbyid/{id}")
	public PatientDTO getPatientById(@PathVariable Long id) {
		LOGGER.info("Fetching patient details for ID: {}", id);
		return patientService.getPatientById(id);
	}


	/** Deletes a patient by their unique ID. */
	@DeleteMapping("/deletepatient/{id}")
	public String deletePatient(
	        @PathVariable Long id,
	        @RequestHeader("requestingPatientId") Long requestingPatientId) { // ✅ Authorization Header
	    LOGGER.info("Request to delete patient with ID: {}", id);

	    try {
	        return patientService.deletePatient(id, requestingPatientId);
	    } catch (UnauthorizedAccessException e) {
	        LOGGER.error("❌ Unauthorized deletion attempt for patient ID: {}", id);
	        return "Unauthorized: You cannot delete another patient's profile.";
	    } catch (ResourceNotFoundException e) {
	        LOGGER.error("❌ Patient not found: {}", id);
	        return "Patient not found with ID: " + id;
	    } catch (Exception e) {
	        LOGGER.error("❌ Error deleting patient details: {}", e.getMessage());
	        return "Failed to delete patient details.";
	    }
	}
	
	/** Retrieves doctor availability based on specialization. */
	@GetMapping("/doctors/availability/{specialization}")
	public List<DoctorDTO> getDoctorAvailability(@PathVariable String specialization) {
	    LOGGER.info("Patient requesting doctor availability for specialization: {}", specialization);
	    return patientService.getDoctorAvailability(specialization);
	}

}
