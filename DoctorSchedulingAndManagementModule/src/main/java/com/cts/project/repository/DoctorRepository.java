package com.cts.project.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.project.model.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

	/** ✅ Return a list of doctors for the given specialization */
	List<Doctor> findBySpecialization(String specialization);
}
