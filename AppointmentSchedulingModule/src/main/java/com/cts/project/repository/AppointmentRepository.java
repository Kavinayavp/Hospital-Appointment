package com.cts.project.repository;

import com.cts.project.model.Appointment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	Logger LOGGER = LoggerFactory.getLogger(AppointmentRepository.class);

	/**
	 * Retrieves all appointments for a specific patient.
	 * 
	 * @param patientId The ID of the patient.
	 * @return List of appointments for the given patient.
	 */
	List<Appointment> findByPatientId(Long patientId);

	/**
	 * Finds an appointment by its unique ID.
	 * 
	 * @param id The unique ID of the appointment.
	 * @return Optional containing the found appointment or empty if not found.
	 */
	default Optional<Appointment> findByAppointmentId(Long id) {
		LOGGER.info("Fetching appointment details for Appointment ID: {}", id);
		Optional<Appointment> appointment = findById(id);

		if (appointment.isEmpty()) {
			LOGGER.error("No appointment found for ID: {}", id);
		}
		return appointment;
	}
}
