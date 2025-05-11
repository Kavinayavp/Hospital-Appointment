package com.cts.project.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.cts.project.dto.DoctorDTO;
import com.cts.project.service.DoctorService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/doctors")
@AllArgsConstructor
public class DoctorController {

    private final DoctorService service;
    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorController.class);

    /** Saves a new doctor in the system. */
    @PostMapping("/save")
    public String saveDoctor(@Valid @RequestBody DoctorDTO dto) {
        LOGGER.info("Request to save doctor: {}", dto.getDoctorName());
        service.saveDoctor(dto);
        return "Doctor details saved successfully.";
    }

    /** Updates an existing doctor's details. */
    @PutMapping("/update/{id}")
    public String updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorDTO dto) {
        LOGGER.info("Request to update doctor with ID: {}", id);
        service.updateDoctor(id, dto);
        return "Doctor details updated successfully.";
    }

    /** Retrieves all doctors available in the system. */
    @GetMapping("/getalldoctors")
    public List<DoctorDTO> getAllDoctors() {
        LOGGER.info("Request to fetch all doctors.");
        return service.getAllDoctors();
    }

    /** Retrieves a doctor by their unique ID. */
    @GetMapping("/getdoctorbyid/{id}")
    public DoctorDTO getDoctorById(@PathVariable Long id) {
        LOGGER.info("Request to fetch doctor details for ID: {}", id);
        return service.getDoctorById(id);
    }

    /** Deletes a doctor by their unique ID. */
    @DeleteMapping("/deletedoctor/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        LOGGER.info("Request to delete doctor with ID: {}", id);
        service.deleteDoctor(id);
        return "Doctor with ID " + id + " deleted successfully.";
    }

    /** Fetches a doctor’s availability based on specialization. */
    @GetMapping("/availability/{specialization}")
    public DoctorDTO getAvailability(@PathVariable String specialization) {
        LOGGER.info("Request to check availability for specialization: {}", specialization);
        return service.getDoctorAvailability(specialization);
    }
}
