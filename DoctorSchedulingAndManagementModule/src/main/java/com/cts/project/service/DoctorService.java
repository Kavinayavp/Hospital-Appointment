package com.cts.project.service;

import java.util.List;
import com.cts.project.dto.DoctorDTO;

/**
 * Service interface for managing doctor-related operations.
 */
public interface DoctorService {

	/** Saves a new doctor in the system. */
	DoctorDTO saveDoctor(DoctorDTO doctorDTO);

	/** Updates an existing doctor's details. */
	DoctorDTO updateDoctor(Long doctorId, DoctorDTO doctorDTO);

	/** Retrieves a doctor's details by their unique ID. */
	DoctorDTO getDoctorById(Long doctorId);

	/** Deletes a doctor from the system by their unique ID. */
	void deleteDoctor(Long doctorId);

	/** Retrieves all doctors available in the system. */
	List<DoctorDTO> getAllDoctors();

	/** Fetches a doctor's availability based on their specialization. */
	List<DoctorDTO> getDoctorAvailability(String specialization);
}
