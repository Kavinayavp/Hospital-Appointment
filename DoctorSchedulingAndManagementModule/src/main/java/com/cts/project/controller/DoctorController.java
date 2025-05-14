package com.cts.project.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.cts.project.dto.DoctorDTO;
import com.cts.project.exception.DoctorNotFoundException;
import com.cts.project.exception.UnauthorizedAccessException;
import com.cts.project.service.DoctorService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/doctors")
@AllArgsConstructor
public class DoctorController {

	private final DoctorService service;
	private static final Logger LOGGER = LoggerFactory.getLogger(DoctorController.class);

	/** Saves a new doctor in the system. */
	@PostMapping("/save")
	public String saveDoctor(@Valid @RequestBody DoctorDTO dto) {
		LOGGER.info("Request to save doctor: {}", dto.getDoctorName());
		service.saveDoctor(dto);
		return "Doctor details saved successfully.";
	}

	/** Updates an existing doctor's details. */
	@PutMapping("/update/{id}")
	public String updateDoctor(
	        @PathVariable Long id,
	        @RequestHeader("requestingDoctorId") Long requestingDoctorId, // ✅ Authorization header
	        @Valid @RequestBody DoctorDTO dto) {
	    LOGGER.info("Request to update doctor with ID: {}", id);
	    
	    try {
	        service.updateDoctor(id, requestingDoctorId, dto);
	        return "Doctor details updated successfully.";
	    } catch (UnauthorizedAccessException e) {
	        LOGGER.error("Unauthorized update attempt for doctor ID: {}", id);
	        return "Unauthorized: Cannot update another doctor's details.";
	    } catch (DoctorNotFoundException e) {
	        LOGGER.error("Doctor not found: {}", id);
	        return "Doctor not found with ID: " + id;
	    } catch (Exception e) {
	        LOGGER.error("Error updating doctor details: {}", e.getMessage());
	        return "Failed to update doctor details.";
	    }
	}

	/** Retrieves all doctors available in the system. */
	@GetMapping("/getalldoctors")
	public List<DoctorDTO> getAllDoctors() {
		LOGGER.info("Request to fetch all doctors.");
		return service.getAllDoctors();
	}

	/** Retrieves a doctor by their unique ID. */
	@GetMapping("/getdoctorbyid/{id}")
	public DoctorDTO getDoctorById(@PathVariable Long id) {
		LOGGER.info("Request to fetch doctor details for ID: {}", id);
		return service.getDoctorById(id);
	}


	/** Deletes a doctor by their unique ID. */
	@DeleteMapping("/deletedoctor/{id}")
	public String deleteDoctor(
	        @PathVariable Long id,
	        @RequestHeader("requestingDoctorId") Long requestingDoctorId) { // ✅ Authorization Header
	    LOGGER.info("Request to delete doctor with ID: {}", id);

	    try {
	        service.deleteDoctor(id, requestingDoctorId);
	        return "Doctor with ID " + id + " deleted successfully.";
	    } catch (UnauthorizedAccessException e) {
	        LOGGER.error("Unauthorized deletion attempt for doctor ID: {}", id);
	        return "Unauthorized: You cannot delete another doctor's profile.";
	    } catch (DoctorNotFoundException e) {
	        LOGGER.error("Doctor not found: {}", id);
	        return "Doctor not found with ID: " + id;
	    } catch (Exception e) {
	        LOGGER.error("Error deleting doctor details: {}", e.getMessage());
	        return "Failed to delete doctor details.";
	    }
	}


	/** Fetches a doctor’s availability based on specialization. */
	@GetMapping("/availability/{specialization}")
	public List<DoctorDTO> getDoctorAvailability(@PathVariable String specialization) {
	    LOGGER.info("Fetching Doctor availability for specialization: {}", specialization);
	    return service.getDoctorAvailability(specialization);
	}

	@GetMapping("/appointments/{appointmentId}/notifyDoctor")
	public String notifyDoctor(@PathVariable Long appointmentId) {
	    LOGGER.info("Notifying doctor about appointment ID: {}", appointmentId);
	    return service.notifyDoctorAboutAppointment(appointmentId);
	}

}
