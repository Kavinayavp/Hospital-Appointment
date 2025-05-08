package com.cts.project.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.project.model.Patient;
 
public interface PatientRepository extends JpaRepository<Patient, Long> {

	
}