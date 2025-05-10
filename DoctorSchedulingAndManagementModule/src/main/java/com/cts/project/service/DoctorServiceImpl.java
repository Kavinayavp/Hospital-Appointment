package com.cts.project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.project.dto.DoctorDTO;
import com.cts.project.exception.DoctorNotFoundException;
import com.cts.project.model.Doctor;
import com.cts.project.repository.DoctorRepository;

@Service
public class DoctorServiceImpl implements DoctorService {
	@Autowired
	private DoctorRepository doctorRepository;

	private DoctorDTO mapToDTO(Doctor doctor) {
		return DoctorDTO.builder().doctorId(doctor.getDoctorId()).doctorName(doctor.getDoctorName())
				.specialization(doctor.getSpecialization()).experience(doctor.getExperience())
				.contactNumber(doctor.getContactNumber()).email(doctor.getEmail())
				.availableDays(doctor.getAvailableDays()).availableTime(doctor.getAvailableTime()).build();
	}

	private Doctor mapToEntity(DoctorDTO dto) {
		return Doctor.builder().doctorId(dto.getDoctorId()).doctorName(dto.getDoctorName())
				.specialization(dto.getSpecialization()).experience(dto.getExperience())
				.contactNumber(dto.getContactNumber()).email(dto.getEmail()).availableDays(dto.getAvailableDays())
				.availableTime(dto.getAvailableTime()).build();
	}

	@Override
	public DoctorDTO saveDoctor(DoctorDTO dto) {
		return mapToDTO(doctorRepository.save(mapToEntity(dto)));
	}

	@Override
	public DoctorDTO updateDoctor(Long doctorId, DoctorDTO dto) {
		Doctor doctor = doctorRepository.findById(doctorId)
				.orElseThrow(() -> new DoctorNotFoundException("Doctor not found with id " + doctorId));

		doctor.setDoctorName(dto.getDoctorName());
		doctor.setSpecialization(dto.getSpecialization());
		doctor.setExperience(dto.getExperience());
		doctor.setContactNumber(dto.getContactNumber());
		doctor.setEmail(dto.getEmail());
		doctor.setAvailableDays(dto.getAvailableDays());
		doctor.setAvailableTime(dto.getAvailableTime());

		return mapToDTO(doctorRepository.save(doctor));
	}

	@Override
	public List<DoctorDTO> getAllDoctors() {
		return doctorRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public DoctorDTO getDoctorById(Long doctorId) {
		return doctorRepository.findById(doctorId).map(this::mapToDTO)
				.orElseThrow(() -> new DoctorNotFoundException("Doctor not found with id " + doctorId));

	}

	@Override
	public void deleteDoctor(Long doctorId) {
		if (!doctorRepository.existsById(doctorId)) {
			throw new DoctorNotFoundException("Doctor not found with id " + doctorId);
		}
		doctorRepository.deleteById(doctorId);
	}

	@Override
	public DoctorDTO getDoctorAvailability(String specialization) {
		Doctor doctor = doctorRepository.findBySpecialization(specialization).orElseThrow(
				() -> new DoctorNotFoundException("Doctor not found with specialization " + specialization));
		return DoctorDTO.builder().doctorId(doctor.getDoctorId()).doctorName(doctor.getDoctorName())
				.specialization(doctor.getSpecialization()).experience(doctor.getExperience())
				.contactNumber(doctor.getContactNumber()).email(doctor.getEmail())
				.availableDays(doctor.getAvailableDays()).availableTime(doctor.getAvailableTime()).build();
	}

}