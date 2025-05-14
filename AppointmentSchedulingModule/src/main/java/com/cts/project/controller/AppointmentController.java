package com.cts.project.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.cts.project.dto.AppointmentDTO;
import com.cts.project.exception.AppointmentNotFoundException;
import com.cts.project.service.AppointmentService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * REST Controller for managing appointments.
 */
@RestController
@RequestMapping("/appointments")
@AllArgsConstructor
public class AppointmentController {

	private final AppointmentService appointmentService;
	private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentController.class);

	/** Books an appointment */
	@PostMapping("/book")
	public String book(@Valid @RequestBody AppointmentDTO dto) {
		LOGGER.info("Request to book an appointment");
		return appointmentService.bookAppointment(dto);
	}

	/** Updates an existing appointment */
	@PutMapping("/update/{appointmentId}/{patientId}")
	public String updateAppointment(@PathVariable Long appointmentId, @PathVariable Long patientId,
			@Valid @RequestBody AppointmentDTO dto) {
		LOGGER.info("Request to update an appointment");
		return appointmentService.updateAppointment(appointmentId, patientId, dto);
	}

	/** Deletes an appointment */
	@DeleteMapping("/deleteappointment/{appointmentId}/{patientId}")
	public String delete(@PathVariable Long appointmentId, @PathVariable Long patientId) {
		LOGGER.info("Request to delete an appointment");

		try {
			boolean result = appointmentService.deleteAppointment(appointmentId, patientId);
			return result ? "Appointment deleted successfully." : "Appointment not found.";
		} catch (Exception e) {
			LOGGER.error("Error deleting appointment: {}", e.getMessage());
			return "Error deleting appointment: " + e.getMessage();
		}
	}

	/** Retrieves an appointment by its unique ID */
	@GetMapping("/getappointmentbyid/{appointmentId}")
	public AppointmentDTO getById(@PathVariable Long appointmentId) {
		LOGGER.info("Request to fetch an appointment");

		try {
			return appointmentService.getAppointmentById(appointmentId);
		} catch (Exception e) {
			LOGGER.error("Appointment not found");
			throw new AppointmentNotFoundException("Appointment not found");
		}
	}

	/** Retrieves all appointments for a specific patient */
	@GetMapping("/getappointmentbypatientid/{patientId}")
	public List<AppointmentDTO> getByPatientId(@PathVariable Long patientId) {
		LOGGER.info("Request to fetch appointments");

		try {
			return appointmentService.getAppointmentsByPatientId(patientId);
		} catch (Exception e) {
			LOGGER.error("Patient not found");
			throw new AppointmentNotFoundException("Patient not found");
		}
	}
	
	@GetMapping("/appointments/{appointmentId}/notify")
	public String notifyAppointment(@PathVariable Long appointmentId) {
	    return appointmentService.notifyDoctorAboutAppointment(appointmentId);
	}

}
