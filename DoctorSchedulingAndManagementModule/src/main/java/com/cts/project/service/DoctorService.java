package com.cts.project.service;

import java.util.List;

import com.cts.project.dto.DoctorDTO;


public interface DoctorService {
	DoctorDTO saveDoctor(DoctorDTO doctorDTO);

	DoctorDTO updateDoctor(Long doctorId, DoctorDTO doctorDTO);

	DoctorDTO getDoctorById(Long doctorId);

	void deleteDoctor(Long doctorId);

	List<DoctorDTO> getAllDoctors();

	DoctorDTO getDoctorAvailability(String specialization);
}