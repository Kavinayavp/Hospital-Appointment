package com.cts.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.cts.project.dto.PatientDTO;
import com.cts.project.service.PatientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private PatientService patientService;

    /** Saves patient details */
    @PostMapping("/save")
    public String savePatient(@Valid @RequestBody PatientDTO dto) {
        LOGGER.info("Attempting to save patient details: {}", dto.getEmail());
        String response = patientService.savePatient(dto);
        LOGGER.info("Patient details saved successfully for: {}", dto.getEmail());
        return response;
    }

    /** Updates patient details (Only self-access allowed) */
    @PutMapping("/update/{id}")
    public String updatePatient(@PathVariable Long id, @Valid @RequestBody PatientDTO dto) {
        LOGGER.info("Attempting to update patient details for ID: {}", id);
        String response = patientService.updatePatient(id, dto);
        LOGGER.info("Patient details updated successfully for ID: {}", id);
        return response;
    }

    /** Retrieves all patients */
    @GetMapping("/getallpatients")
    public List<PatientDTO> getAllPatients() {
        LOGGER.info("Fetching all patients.");
        return patientService.getAllPatients();
    }

    /** Retrieves patient details */
    @GetMapping("/getpatientbyid/{id}")
    public PatientDTO getPatientById(@PathVariable Long id) {
        LOGGER.info("Fetching patient details for ID: {}", id);
        return patientService.getPatientById(id);
    }

    /** Deletes patient account */
    @DeleteMapping("/deletepatient/{id}")
    public String deletePatient(@PathVariable Long id) {
        LOGGER.info("Attempting to delete patient details for ID: {}", id);
        String response = patientService.deletePatient(id);
        LOGGER.info("Patient details deleted successfully for ID: {}", id);
        return response;
    }
}
