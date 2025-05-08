package com.cts.project.service;

import com.cts.project.dto.MedicalHistoryDTO;
import com.cts.project.exception.MedicalHistoryNotFoundException;
import com.cts.project.model.MedicalHistory;
import com.cts.project.repository.MedicalHistoryRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.util.List;
import java.util.stream.Collectors;
 
@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class MedicalHistoryServiceImpl implements MedicalHistoryService {
	@Autowired
    private MedicalHistoryRepository repository;
 
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
    public MedicalHistoryDTO saveMedicalHistory(MedicalHistoryDTO dto) {
        MedicalHistory history = repository.save(mapToEntity(dto));
        return mapToDTO(history);
    }
 
    @Override
    public MedicalHistoryDTO updateMedicalHistory(Long id, MedicalHistoryDTO dto) {
        MedicalHistory existing = repository.findById(id)
                .orElseThrow(() -> new MedicalHistoryNotFoundException("History not found"));
        existing.setIllness(dto.getIllness());
        existing.setTreatment(dto.getTreatment());
        existing.setDoctorName(dto.getDoctorName());
        existing.setVisitDate(dto.getVisitDate());
        existing.setPrescription(dto.getPrescription());
        
        return mapToDTO(repository.save(existing));
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
 
 