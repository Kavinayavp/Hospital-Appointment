package com.cts.project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.project.dto.DoctorResponseDTO;
import com.cts.project.dto.MedicalHistoryDTO;
import com.cts.project.exception.MedicalHistoryNotFoundException;
import com.cts.project.feignclient.DoctorClient;
import com.cts.project.model.MedicalHistory;
import com.cts.project.repository.MedicalHistoryRepository;

import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
 
@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class MedicalHistoryServiceImpl implements MedicalHistoryService {
	
    private MedicalHistoryRepository repository;
    
	 @Autowired
	    private DoctorClient doctorClient;
	 
	 @Override
	 public MedicalHistoryDTO saveMedicalHistory(MedicalHistoryDTO dto) {
	     try {
	         // Fetch doctor details using Feign Client
	         DoctorResponseDTO doctor = doctorClient.getDoctorById(dto.getDoctorId());
	         if (doctor == null) {
	             throw new RuntimeException("Doctor not found with ID: " + dto.getDoctorId());
	         }

	         dto.setDoctorName(doctor.getDoctorName()); // Set Doctor Name in DTO
	         MedicalHistory history = repository.save(mapToEntity(dto));
	         return mapToDTO(history);
	     } catch (FeignException.NotFound e) { // Catch Feign client 404 errors
	         throw new RuntimeException("Doctor not found with ID: " + dto.getDoctorId());
	     } catch (Exception e) {
	         throw new RuntimeException("Error fetching doctor details: " + e.getMessage());
	     }
	 }
	    @Override
	    public MedicalHistoryDTO updateMedicalHistory(Long id, MedicalHistoryDTO dto) {
	        MedicalHistory existing = repository.findById(id)
	                .orElseThrow(() -> new MedicalHistoryNotFoundException("History not found"));

	        DoctorResponseDTO doctor = doctorClient.getDoctorById(dto.getDoctorId());
	        if (doctor == null) {
	            throw new RuntimeException("Doctor not found with ID: " + dto.getDoctorId());
	        }

	        dto.setDoctorName(doctor.getDoctorName()); // Update Doctor Name in DTO
	        existing.setIllness(dto.getIllness());
	        existing.setTreatment(dto.getTreatment());
	        existing.setDoctorName(dto.getDoctorName());
	        existing.setVisitDate(dto.getVisitDate());
	        existing.setPrescription(dto.getPrescription());

	        return mapToDTO(repository.save(existing));
	    }

	
    private MedicalHistoryDTO mapToDTO(MedicalHistory history) {
        return MedicalHistoryDTO.builder()
                .historyId(history.getHistoryId())
                .patientId(history.getPatientId())
                .illness(history.getIllness())
                .treatment(history.getTreatment())
                .doctorName(history.getDoctorName())
                .visitDate(history.getVisitDate())
                .prescription(history.getPrescription())
                .build();
    }
 
    private MedicalHistory mapToEntity(MedicalHistoryDTO dto) {
        return MedicalHistory.builder()
                .historyId(dto.getHistoryId())
                .patientId(dto.getPatientId())
                .illness(dto.getIllness())
                .treatment(dto.getTreatment())
                .doctorName(dto.getDoctorName())
                .visitDate(dto.getVisitDate())
                .prescription(dto.getPrescription())
                .build();
    }
 
   
    @Override
    public List<MedicalHistoryDTO> getMedicalHistoryByPatientId(Long patientId) {
        return repository.findByPatientId(patientId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }
 
    @Override
    public void deleteMedicalHistory(Long id) {
        if (!repository.existsById(id)) {
            throw new MedicalHistoryNotFoundException("History not found");
        }
        repository.deleteById(id);
    }

	@Override
	public List<MedicalHistoryDTO> getAllMedicalHistory() {
	List<MedicalHistory> histories=repository.findAll();
		return histories.stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	
}
 
 