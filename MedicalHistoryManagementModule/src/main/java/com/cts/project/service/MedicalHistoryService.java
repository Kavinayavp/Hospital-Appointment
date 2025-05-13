package com.cts.project.service;

import com.cts.project.dto.MedicalHistoryDTO;

import java.util.List;

public interface MedicalHistoryService {
	MedicalHistoryDTO saveMedicalHistory(MedicalHistoryDTO dto);

	MedicalHistoryDTO updateMedicalHistory(Long id, MedicalHistoryDTO dto);

	List<MedicalHistoryDTO> getMedicalHistoryByPatientId(Long patientId);

	List<MedicalHistoryDTO> getAllMedicalHistory();

	String deleteMedicalHistory(Long id);
}
