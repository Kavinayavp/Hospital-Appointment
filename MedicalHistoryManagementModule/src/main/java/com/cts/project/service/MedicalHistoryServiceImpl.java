package com.cts.project.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.cts.project.dto.MedicalHistoryDTO;
import com.cts.project.exception.MedicalHistoryNotFoundException;
import com.cts.project.model.MedicalHistory;
import com.cts.project.repository.MedicalHistoryRepository;
import lombok.AllArgsConstructor;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

    private final MedicalHistoryRepository repository;
    private static final Logger LOGGER = Logger.getLogger(MedicalHistoryServiceImpl.class.getName());

    /** ✅ Save medical history without appointment validation */
    @Override
    public MedicalHistoryDTO saveMedicalHistory(MedicalHistoryDTO dto) {
        MedicalHistory history = repository.save(mapToEntity(dto));
        LOGGER.info("Medical history saved successfully for patient ID: " + dto.getPatientId());
        return mapToDTO(history);
    }

    /** ✅ Update medical history just using historyId */
    @Override
    public MedicalHistoryDTO updateMedicalHistory(Long historyId, MedicalHistoryDTO dto) {
        List<MedicalHistory> existingRecords = repository.findByHistoryId(historyId);

        if (existingRecords.isEmpty()) {
            throw new MedicalHistoryNotFoundException("No medical history found for history ID: " + historyId);
        }

        // ✅ Update all records for the given history ID
        for (MedicalHistory history : existingRecords) {

//        	history.setHistoryId(dto.getHistoryId());
        	history.setPatientId(dto.getPatientId());
        	history.setDoctorId(dto.getDoctorId());
        	history.setDoctorName(dto.getDoctorName());
            history.setPatientName(dto.getPatientName());
            history.setIllness(dto.getIllness());
            history.setTreatment(dto.getTreatment());
            history.setVisitDate(dto.getVisitDate());
            history.setPrescription(dto.getPrescription());
            repository.save(history);
        }

        LOGGER.info("Medical history updated successfully for history ID: " + historyId);
        return mapToDTO(existingRecords.get(0)); // ✅ Return first updated record as reference
    }

    private MedicalHistoryDTO mapToDTO(MedicalHistory history) {
        return MedicalHistoryDTO.builder()
                .historyId(history.getHistoryId())
                .patientId(history.getPatientId())
                .doctorId(history.getDoctorId())
                .doctorName(history.getDoctorName())
                .patientName(history.getPatientName())
                .illness(history.getIllness())
                .treatment(history.getTreatment())
                .visitDate(history.getVisitDate())
                .prescription(history.getPrescription())
                .build();
    }

    private MedicalHistory mapToEntity(MedicalHistoryDTO dto) {
        return MedicalHistory.builder()
                .historyId(dto.getHistoryId())
                .patientId(dto.getPatientId())
                .doctorId(dto.getDoctorId())
                .doctorName(dto.getDoctorName())
                .patientName(dto.getPatientName())
                .illness(dto.getIllness())
                .treatment(dto.getTreatment())
                .visitDate(dto.getVisitDate())
                .prescription(dto.getPrescription())
                .build();
    }

    /** ✅ Fetch medical history by patient ID */
    @Override
    public List<MedicalHistoryDTO> getMedicalHistoryByPatientId(Long patientId) {
        List<MedicalHistory> histories = repository.findByPatientId(patientId);
        LOGGER.info("Fetched " + histories.size() + " medical history records for patient ID: " + patientId);
        return histories.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    /** ✅ Delete medical history by ID */
    @Override
    public String deleteMedicalHistory(Long id) {
        if (!repository.existsById(id)) {
            throw new MedicalHistoryNotFoundException("History not found for ID: " + id);
        }

        repository.deleteById(id);
        LOGGER.info("Medical history deleted for ID: " + id);
        return "Medical history with ID " + id + " deleted successfully.";
    }

    /** ✅ Fetch all medical history records */
    @Override
    public List<MedicalHistoryDTO> getAllMedicalHistory() {
        List<MedicalHistory> histories = repository.findAll();
        LOGGER.info("Fetched all medical history records: " + histories.size());
        return histories.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}
