/*
 * package com.cts.project.service;
 * 
 * import java.util.List; import java.util.stream.Collectors; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Service; import org.slf4j.Logger; import
 * org.slf4j.LoggerFactory; import com.cts.project.dto.PatientDTO; import
 * com.cts.project.exception.ResourceNotFoundException; import
 * com.cts.project.model.Patient; import
 * com.cts.project.repository.PatientRepository;
 * 
 * @Service public class PatientServiceImpl implements PatientService {
 * 
 * private static final Logger LOGGER =
 * LoggerFactory.getLogger(PatientServiceImpl.class);
 * 
 * @Autowired private PatientRepository patientRepository;
 * 
 *//** Saves patient details and logs success message */
/*
 * @Override public String savePatient(PatientDTO dto) { Patient patient =
 * toEntity(dto); patientRepository.save(patient);
 * LOGGER.info("Patient details saved successfully for: {}", dto.getEmail());
 * return "Patient details saved successfully!"; }
 * 
 *//** Updates patient details and logs success message */
/*
 * @Override public String updatePatient(Long id, PatientDTO dto) { Patient
 * existing = patientRepository.findById(id) .orElseThrow(() -> new
 * ResourceNotFoundException("Patient not found with id: " + id));
 * 
 * existing.setPatientName(dto.getPatientName());
 * existing.setDateOfBirth(dto.getDateOfBirth()); existing.setAge(dto.getAge());
 * existing.setGender(dto.getGender());
 * existing.setBloodGroup(dto.getBloodGroup());
 * existing.setGuardianName(dto.getGuardianName());
 * existing.setContactNumber(dto.getContactNumber());
 * existing.setEmail(dto.getEmail()); existing.setAddress(dto.getAddress());
 * existing.setMedicalHistory(dto.getMedicalHistory());
 * 
 * patientRepository.save(existing);
 * LOGGER.info("Patient details updated successfully for ID: {}", id); return
 * "Patient details updated successfully!"; }
 * 
 *//** Retrieves all patients */
/*
 * @Override public List<PatientDTO> getAllPatients() {
 * LOGGER.info("Fetching all patients."); return
 * patientRepository.findAll().stream().map(this::toDTO).collect(Collectors.
 * toList()); }
 * 
 *//** Retrieves patient details */
/*
 * @Override public PatientDTO getPatientById(Long id) {
 * LOGGER.info("Fetching patient details for ID: {}", id); Patient patient =
 * patientRepository.findById(id) .orElseThrow(() -> new
 * ResourceNotFoundException("Patient not found with id: " + id)); return
 * toDTO(patient); }
 * 
 *//** Deletes patient details */
/*
 * @Override public String deletePatient(Long id) { if
 * (!patientRepository.existsById(id)) { throw new
 * ResourceNotFoundException("Oops! We couldn't find a patient with ID: " + id +
 * ". Please check and try again."); }
 * 
 * patientRepository.deleteById(id);
 * LOGGER.info("Patient details deleted successfully for ID: {}", id); return
 * "Patient details deleted successfully!"; }
 * 
 *//** Converts Patient entity to DTO */
/*
 * private PatientDTO toDTO(Patient patient) { return PatientDTO.builder()
 * .patientId(patient.getPatientId()) .patientName(patient.getPatientName())
 * .dateOfBirth(patient.getDateOfBirth()) .age(patient.getAge())
 * .gender(patient.getGender()) .bloodGroup(patient.getBloodGroup())
 * .guardianName(patient.getGuardianName())
 * .contactNumber(patient.getContactNumber()) .email(patient.getEmail())
 * .address(patient.getAddress()) .medicalHistory(patient.getMedicalHistory())
 * .build(); }
 * 
 *//** Converts DTO to Patient entity *//*
											 * private Patient toEntity(PatientDTO dto) { return Patient.builder()
											 * .patientId(dto.getPatientId()) .patientName(dto.getPatientName())
											 * .dateOfBirth(dto.getDateOfBirth()) .age(dto.getAge())
											 * .gender(dto.getGender()) .bloodGroup(dto.getBloodGroup())
											 * .guardianName(dto.getGuardianName()) .contactNumber(dto.getContactNumber())
											 * .email(dto.getEmail()) .address(dto.getAddress())
											 * .medicalHistory(dto.getMedicalHistory()) .build(); } }
											 */

package com.cts.project.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cts.project.dto.PatientDTO;
import com.cts.project.exception.ResourceNotFoundException;
import com.cts.project.model.Patient;
import com.cts.project.repository.PatientRepository;
import jakarta.validation.ConstraintViolationException;

@Service
public class PatientServiceImpl implements PatientService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PatientServiceImpl.class);

	@Autowired
	private PatientRepository patientRepository;

	/** Saves patient details and logs success message */
	@Override
	public String savePatient(PatientDTO dto) {
		try {
			Patient patient = toEntity(dto);
			patientRepository.save(patient);
			LOGGER.info("✅ Patient details saved successfully for: {}", dto.getEmail());
			return "Patient details saved successfully!";
		} catch (ConstraintViolationException ex) {
			LOGGER.error("❌ Validation Error - {}", ex.getMessage());
			throw new IllegalArgumentException("Invalid patient details. " + extractValidationMessages(ex));
		}
	}

	/** Updates patient details and logs success message */
	@Override
	public String updatePatient(Long id, PatientDTO dto) {
		try {
			Patient existing = patientRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("❌ Patient not found with ID: " + id));

			existing.setPatientName(dto.getPatientName());
			existing.setDateOfBirth(dto.getDateOfBirth());
			existing.setAge(dto.getAge());
			existing.setGender(dto.getGender());
			existing.setBloodGroup(dto.getBloodGroup());
			existing.setGuardianName(dto.getGuardianName());
			existing.setContactNumber(dto.getContactNumber());
			existing.setEmail(dto.getEmail());
			existing.setAddress(dto.getAddress());
			existing.setMedicalHistory(dto.getMedicalHistory());

			patientRepository.save(existing);
			LOGGER.info("✅ Patient details updated successfully for ID: {}", id);
			return "Patient details updated successfully!";
		} catch (ConstraintViolationException ex) {
			LOGGER.error("❌ Validation Error - {}", ex.getMessage());
			throw new IllegalArgumentException("Invalid patient details. " + extractValidationMessages(ex));
		}
	}

	/** Extracts readable validation error messages */
	private String extractValidationMessages(ConstraintViolationException ex) {
		return ex.getConstraintViolations().stream()
				.map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
				.collect(Collectors.joining(", "));
	}

	/** Retrieves all patients */
	@Override
	public List<PatientDTO> getAllPatients() {
		LOGGER.info("📌 Fetching all patients.");
		return patientRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	/** Retrieves patient details */
	@Override
	public PatientDTO getPatientById(Long id) {
		LOGGER.info("🔍 Fetching patient details for ID: {}", id);
		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("❌ Patient not found with ID: " + id));
		return toDTO(patient);
	}

	/** Deletes patient details */
	@Override
	public String deletePatient(Long id) {
		if (!patientRepository.existsById(id)) {
			throw new ResourceNotFoundException("❌ Patient not found with ID: " + id);
		}

		patientRepository.deleteById(id);
		LOGGER.info("🗑️ Patient details deleted successfully for ID: {}", id);
		return "Patient details deleted successfully!";
	}

	/** Converts Patient entity to DTO */
	private PatientDTO toDTO(Patient patient) {
		return PatientDTO.builder().patientId(patient.getPatientId()).patientName(patient.getPatientName())
				.dateOfBirth(patient.getDateOfBirth()).age(patient.getAge()).gender(patient.getGender())
				.bloodGroup(patient.getBloodGroup()).guardianName(patient.getGuardianName())
				.contactNumber(patient.getContactNumber()).email(patient.getEmail()).address(patient.getAddress())
				.medicalHistory(patient.getMedicalHistory()).build();
	}

	/** Converts DTO to Patient entity */
	private Patient toEntity(PatientDTO dto) {
		return Patient.builder().patientId(dto.getPatientId()).patientName(dto.getPatientName())
				.dateOfBirth(dto.getDateOfBirth()).age(dto.getAge()).gender(dto.getGender())
				.bloodGroup(dto.getBloodGroup()).guardianName(dto.getGuardianName())
				.contactNumber(dto.getContactNumber()).email(dto.getEmail()).address(dto.getAddress())
				.medicalHistory(dto.getMedicalHistory()).build();
	}
}
