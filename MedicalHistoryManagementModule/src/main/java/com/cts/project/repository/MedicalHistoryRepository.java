package com.cts.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cts.project.model.MedicalHistory;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {
    
    List<MedicalHistory> findByPatientId(Long patientId);
    Optional<List<MedicalHistory>> findOptionalByPatientId(Long patientId);
}

