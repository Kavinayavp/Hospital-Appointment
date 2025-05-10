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
 
@Service
@AllArgsConstructor
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

    private final MedicalHistoryRepository repository;  // ✅ Use 'final' to ensure correct injection

    @Autowired
    private final DoctorClient doctorClient; // ✅ Ensure this is correctly autowired
    
    @Override
    public MedicalHistoryDTO saveMedicalHistory(MedicalHistoryDTO dto) {
        try {
            if (dto.getDoctorId() == null) {
                throw new IllegalArgumentException("Doctor ID is required.");
            }

            DoctorResponseDTO doctor = doctorClient.getDoctorById(dto.getDoctorId());
            if (doctor == null) {
                throw new RuntimeException("Doctor not found with ID: " + dto.getDoctorId());
            }

            dto.setDoctorName(doctor.getDoctorName()); // ✅ Set doctor name before saving
            MedicalHistory history = repository.save(mapToEntity(dto));
            return mapToDTO(history);
        } catch (FeignException.NotFound e) {
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
                .patientName(history.getPatientName())
                .illness(history.getIllness())
                .treatment(history.getTreatment())
                .doctorId(history.getDoctorId())
                .doctorName(history.getDoctorName())
                .visitDate(history.getVisitDate())
                .prescription(history.getPrescription())
                .build();
    }
 
    private MedicalHistory mapToEntity(MedicalHistoryDTO dto) {
        return MedicalHistory.builder()
                .historyId(dto.getHistoryId())
                .patientId(dto.getPatientId())
                .patientName(dto.getPatientName())
                .illness(dto.getIllness())
                .treatment(dto.getTreatment())
                .doctorId(dto.getDoctorId())
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
    public String deleteMedicalHistory(Long id) {
        if (!repository.existsById(id)) {
            throw new MedicalHistoryNotFoundException("History not found for ID: " + id);
        }
        
        repository.deleteById(id);
        return "Medical history with ID " + id + " deleted successfully.";
    }


	@Override
	public List<MedicalHistoryDTO> getAllMedicalHistory() {
	List<MedicalHistory> histories=repository.findAll();
		return histories.stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	
}
 
 