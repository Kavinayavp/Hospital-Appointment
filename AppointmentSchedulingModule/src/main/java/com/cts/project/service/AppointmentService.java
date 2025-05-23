package com.cts.project.service;

import java.util.List;
import com.cts.project.dto.AppointmentDTO;

public interface AppointmentService {

	// Book an appointment
	String bookAppointment(AppointmentDTO dto);

	// Fetch appointment by ID
	AppointmentDTO getAppointmentById(Long appointmentId);

	// Fetch all appointments for a specific patient
	List<AppointmentDTO> getAppointmentsByPatientId(Long patientId);

	// Update an appointment and return the updated object
	String updateAppointment(Long appointmentId, Long patientId, AppointmentDTO dto);

	// Delete an appointment and return success status
	boolean deleteAppointment(Long appointmentId, Long patientId);

	String notifyDoctorAboutAppointment(Long appointmentId);
}
