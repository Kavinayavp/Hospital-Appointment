package com.cts.project.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.cts.project.dto.DoctorResponseDTO;
import com.cts.project.dto.MedicalHistoryDTO;
import com.cts.project.exception.MedicalHistoryNotFoundException;
import com.cts.project.exception.UnauthorizedAccessException;
import com.cts.project.feignclient.AppointmentClient;
import com.cts.project.feignclient.DoctorClient;
import com.cts.project.model.Appointment;
import com.cts.project.model.MedicalHistory;
import com.cts.project.repository.MedicalHistoryRepository;
import feign.FeignException;
import lombok.AllArgsConstructor;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

    private final MedicalHistoryRepository repository;
    private final DoctorClient doctorClient;
    private final AppointmentClient appointmentClient; // ✅ Validate doctor-patient relation
    private static final Logger LOGGER = Logger.getLogger(MedicalHistoryServiceImpl.class.getName());

    @Override
    public MedicalHistoryDTO saveMedicalHistory(MedicalHistoryDTO dto) {
        if (dto.getDoctorId() == null || dto.getPatientId() == null) {
            throw new IllegalArgumentException("Patient ID and Doctor ID are required.");
        }

        try {
            // ✅ Check if doctor actually attended this patient
            Appointment appointment = appointmentClient.getAppointmentByDoctorIdAndPatientId(dto.getDoctorId(), dto.getPatientId());

            if (appointment == null) {
                throw new UnauthorizedAccessException("Doctor with ID " + dto.getDoctorId() + " did not attend patient with ID " + dto.getPatientId());
            }
        } catch (FeignException.NotFound e) {
            throw new UnauthorizedAccessException("No appointment found for Doctor ID " + dto.getDoctorId() + " and Patient ID " + dto.getPatientId());
        }

        // ✅ Fetch doctor details using Feign Client
        DoctorResponseDTO doctor = doctorClient.getDoctorById(dto.getDoctorId());
        if (doctor == null) {
            throw new RuntimeException("Doctor not found with ID: " + dto.getDoctorId());
        }

        dto.setDoctorName(doctor.getDoctorName()); // ✅ Set doctor name before saving
        MedicalHistory history = repository.save(mapToEntity(dto));
        LOGGER.info("Medical history saved successfully for patient ID: " + dto.getPatientId());
        return mapToDTO(history);
    }

    @Override
    public MedicalHistoryDTO updateMedicalHistory(Long id, MedicalHistoryDTO dto) {
        MedicalHistory existing = repository.findById(id)
                .orElseThrow(() -> new MedicalHistoryNotFoundException("History not found"));

        try {
            // ✅ Ensure only attending doctor updates history
            Appointment appointment = appointmentClient.getAppointmentByDoctorIdAndPatientId(dto.getDoctorId(), dto.getPatientId());

            if (appointment == null) {
                throw new UnauthorizedAccessException("Doctor with ID " + dto.getDoctorId() + " did not attend patient with ID " + dto.getPatientId());
            }
        } catch (FeignException.NotFound e) {
            throw new UnauthorizedAccessException("No appointment found for Doctor ID " + dto.getDoctorId() + " and Patient ID " + dto.getPatientId());
        }

        DoctorResponseDTO doctor = doctorClient.getDoctorById(dto.getDoctorId());
        if (doctor == null) {
            throw new RuntimeException("Doctor not found with ID: " + dto.getDoctorId());
        }

        dto.setDoctorName(doctor.getDoctorName()); // ✅ Set doctor name before saving
        existing.setIllness(dto.getIllness());
        existing.setTreatment(dto.getTreatment());
        existing.setDoctorName(dto.getDoctorName());
        existing.setVisitDate(dto.getVisitDate());
        existing.setPrescription(dto.getPrescription());

        LOGGER.info("Medical history updated successfully for patient ID: " + dto.getPatientId());
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
        List<MedicalHistory> histories = repository.findByPatientId(patientId);
        LOGGER.info("Fetched " + histories.size() + " medical history records for patient ID: " + patientId);
        return histories.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public String deleteMedicalHistory(Long id) {
        if (!repository.existsById(id)) {
            throw new MedicalHistoryNotFoundException("History not found for ID: " + id);
        }

        repository.deleteById(id);
        LOGGER.info("Medical history deleted for ID: " + id);
        return "Medical history with ID " + id + " deleted successfully.";
    }

    @Override
    public List<MedicalHistoryDTO> getAllMedicalHistory() {
        List<MedicalHistory> histories = repository.findAll();
        LOGGER.info("Fetched all medical history records: " + histories.size());
        return histories.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}
