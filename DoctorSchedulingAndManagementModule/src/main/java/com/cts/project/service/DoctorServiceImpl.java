package com.cts.project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.project.dto.DoctorDTO;
import com.cts.project.exception.DoctorNotFoundException;
import com.cts.project.model.Doctor;
import com.cts.project.repository.DoctorRepository;

@Service
public class DoctorServiceImpl implements DoctorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorServiceImpl.class);

    @Autowired
    private DoctorRepository doctorRepository;

    /**
     * Maps a Doctor entity to a DoctorDTO object.
     */
    private DoctorDTO mapToDTO(Doctor doctor) {
        LOGGER.debug("Mapping Doctor entity to DTO for Doctor ID: {}", doctor.getDoctorId());
        return DoctorDTO.builder()
                .doctorId(doctor.getDoctorId())
                .doctorName(doctor.getDoctorName())
                .specialization(doctor.getSpecialization())
                .experience(doctor.getExperience())
                .contactNumber(doctor.getContactNumber())
                .email(doctor.getEmail())
                .availableDays(doctor.getAvailableDays())
                .availableTime(doctor.getAvailableTime())
                .build();
    }

    /**
     * Maps a DoctorDTO object to a Doctor entity.
     */
    private Doctor mapToEntity(DoctorDTO dto) {
        LOGGER.debug("Mapping Doctor DTO to entity for Doctor ID: {}", dto.getDoctorId());
        return Doctor.builder()
                .doctorId(dto.getDoctorId())
                .doctorName(dto.getDoctorName())
                .specialization(dto.getSpecialization())
                .experience(dto.getExperience())
                .contactNumber(dto.getContactNumber())
                .email(dto.getEmail())
                .availableDays(dto.getAvailableDays())
                .availableTime(dto.getAvailableTime())
                .build();
    }

    /**
     * Saves a new doctor in the database.
     */
    @Override
    public DoctorDTO saveDoctor(DoctorDTO dto) {
        LOGGER.info("Saving new Doctor with name: {}", dto.getDoctorName());
        return mapToDTO(doctorRepository.save(mapToEntity(dto)));
    }

    /**
     * Updates an existing doctor's details.
     */
    @Override
    public DoctorDTO updateDoctor(Long doctorId, DoctorDTO dto) {
        LOGGER.info("Updating Doctor with ID: {}", doctorId);
        LOGGER.info("Received Update Request: {}", dto); // ✅ Log incoming request

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with id " + doctorId));

        // ✅ Validate Available Time Format
        if (!dto.getAvailableTime().matches("^([0-9]{2}:[0-9]{2} (AM|PM) - [0-9]{2}:[0-9]{2} (AM|PM))$")) {
            throw new IllegalArgumentException("Invalid availableTime format! Must be HH:MM AM/PM - HH:MM AM/PM.");
        }

        doctor.setDoctorName(dto.getDoctorName());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setExperience(dto.getExperience());
        doctor.setContactNumber(dto.getContactNumber());
        doctor.setEmail(dto.getEmail());
        doctor.setAvailableDays(dto.getAvailableDays());
        doctor.setAvailableTime(dto.getAvailableTime());

        try {
            doctorRepository.save(doctor);
            LOGGER.info("Doctor updated successfully for ID: {}", doctorId);
            return mapToDTO(doctor);
        } catch (Exception e) {
            LOGGER.error("Error updating doctor details: {}", e.getMessage());
            throw new RuntimeException("Failed to update doctor details. Please check the input data.");
        }
    }

    /**
     * Retrieves a list of all doctors in the system.
     */
    @Override
    public List<DoctorDTO> getAllDoctors() {
        LOGGER.info("Fetching all doctors from database.");
        return doctorRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    /**
     * Fetches a doctor's details by their unique ID.
     */
    @Override
    public DoctorDTO getDoctorById(Long doctorId) {
        LOGGER.info("Fetching Doctor details for ID: {}", doctorId);
        return doctorRepository.findById(doctorId)
                .map(this::mapToDTO)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with id " + doctorId));
    }

    /**
     * Deletes a doctor by their unique ID.
     */
    @Override
    public void deleteDoctor(Long doctorId) {
        LOGGER.info("Deleting Doctor with ID: {}", doctorId);

        if (!doctorRepository.existsById(doctorId)) {
            LOGGER.error("Doctor not found with ID: {}", doctorId);
            throw new DoctorNotFoundException("Doctor not found with id " + doctorId);
        }

        doctorRepository.deleteById(doctorId);
        LOGGER.info("Doctor deleted successfully.");
    }

    /**
     * Fetches a doctor's availability based on specialization.
     */
    @Override
    public List<DoctorDTO> getDoctorAvailability(String specialization) {
        LOGGER.info("Fetching Doctor availability for specialization: {}", specialization);

        List<Doctor> doctors = doctorRepository.findBySpecialization(specialization);

        if (doctors.isEmpty()) {
            throw new DoctorNotFoundException("No doctors found for specialization: " + specialization);
        }

        return doctors.stream().map(doctor -> DoctorDTO.builder()
                .doctorId(doctor.getDoctorId())
                .doctorName(doctor.getDoctorName())
                .specialization(doctor.getSpecialization())
                .experience(doctor.getExperience())
                .contactNumber(doctor.getContactNumber())
                .email(doctor.getEmail())
                .availableDays(doctor.getAvailableDays())
                .availableTime(doctor.getAvailableTime())
                .build()).toList();
    }

}
