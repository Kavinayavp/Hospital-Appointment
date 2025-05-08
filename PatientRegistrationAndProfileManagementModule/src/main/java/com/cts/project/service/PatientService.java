package com.cts.project.service;

import java.util.List;

import com.cts.project.dto.PatientDTO;
 
public interface PatientService {
    PatientDTO savePatient(PatientDTO dto);
    PatientDTO updatePatient(Long id, PatientDTO dto);
    List<PatientDTO> getAllPatients();
    PatientDTO getPatientById(Long id);
    void deletePatient(Long id);
}