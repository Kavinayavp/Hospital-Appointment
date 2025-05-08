package com.cts.project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.project.dto.PatientDTO;
import com.cts.project.exception.ResourceNotFoundException;
import com.cts.project.model.Patient;
import com.cts.project.repository.PatientRepository;

@Service
public class PatientServiceImpl implements PatientService {
 
    @Autowired
    private PatientRepository patientRepository;
 
    @Override
    public PatientDTO savePatient(PatientDTO dto) {
        Patient patient = toEntity(dto);
        return toDTO(patientRepository.save(patient));
    }
 
    @Override
    public PatientDTO updatePatient(Long id, PatientDTO dto) {
        Patient existing = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
 
        existing.setPatientName(dto.getPatientName());
        existing.setDateOfBirth(dto.getDateOfBirth());
        existing.setAge(dto.getAge());
        existing.setGender(dto.getGender());
        existing.setBloodGroup(dto.getBloodGroup());
        existing.setGuardianName(dto.getGuardianName());
        existing.setContactNumber(dto.getContactNumber());
        existing.setEmail(dto.getEmail());
        existing.setAddress(dto.getAddress());
        existing.setMedicalHistory(dto.getMedicalHistory());
 
        return toDTO(patientRepository.save(existing));
    }
 
    @Override
    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }
 
    @Override
    public PatientDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
        return toDTO(patient);
    }
 
    @Override
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }
    
 
 
    private PatientDTO toDTO(Patient patient) {
        return PatientDTO.builder()
                .patientId(patient.getPatientId())
                .patientName(patient.getPatientName())
                .dateOfBirth(patient.getDateOfBirth())
                .age(patient.getAge())
                .gender(patient.getGender())
                .bloodGroup(patient.getBloodGroup())
                .guardianName(patient.getGuardianName())
                .contactNumber(patient.getContactNumber())
                .email(patient.getEmail())
                .address(patient.getAddress())
                .medicalHistory(patient.getMedicalHistory())
                .build();
    }
 
    private Patient toEntity(PatientDTO dto) {
        return Patient.builder()
                .patientId(dto.getPatientId())
                .patientName(dto.getPatientName())
                .dateOfBirth(dto.getDateOfBirth())
                .age(dto.getAge())
                .gender(dto.getGender())
                .bloodGroup(dto.getBloodGroup())
                .guardianName(dto.getGuardianName())
                .contactNumber(dto.getContactNumber())
                .email(dto.getEmail())
                .address(dto.getAddress())
                .medicalHistory(dto.getMedicalHistory())
                .build();
    }

}