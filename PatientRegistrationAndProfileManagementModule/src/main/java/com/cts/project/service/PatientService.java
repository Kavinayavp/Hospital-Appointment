package com.cts.project.service;

import java.util.List;
import com.cts.project.dto.PatientDTO;

public interface PatientService {
	String savePatient(PatientDTO dto);

	String updatePatient(Long id, PatientDTO dto);

	List<PatientDTO> getAllPatients();

	PatientDTO getPatientById(Long id);

	String deletePatient(Long id);
}