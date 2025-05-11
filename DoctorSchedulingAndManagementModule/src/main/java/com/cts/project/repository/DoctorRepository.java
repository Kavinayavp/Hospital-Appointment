package com.cts.project.repository;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cts.project.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Logger LOGGER = LoggerFactory.getLogger(DoctorRepository.class);

    /** Retrieves a doctor based on their specialization. */
    default Optional<Doctor> findBySpecialization(String specialization) {
        LOGGER.info("Fetching doctor details for specialization: {}", specialization);
        Optional<Doctor> doctor = findBySpecialization(specialization);

        if (doctor.isEmpty()) {
            LOGGER.error("No doctor found with specialization: {}", specialization);
        }

        return doctor;
    }
}
