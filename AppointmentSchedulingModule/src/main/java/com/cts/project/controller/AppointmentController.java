package com.cts.project.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.cts.project.dto.AppointmentDTO;
import com.cts.project.model.Appointment;
import com.cts.project.service.AppointmentService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/appointments")
@AllArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentController.class);

    /**
     * Books an appointment for a patient.
     */
    @PostMapping("/book")
    public String book(@Valid @RequestBody AppointmentDTO dto) {
        LOGGER.info("Request to book appointment for Patient ID: {}", dto.getPatientId());

        try {
            return appointmentService.bookAppointment(dto);
        } catch (Exception e) {
            LOGGER.error("Error booking appointment: {}", e.getMessage());
            return "Error booking appointment: " + e.getMessage();
        }
    }

    /**
     * Updates an existing appointment for a patient.
     */
    @PutMapping("/update/{appointmentId}/{patientId}")
    public String updateAppointment(@PathVariable Long appointmentId, @PathVariable Long patientId, @RequestBody AppointmentDTO dto) {
        LOGGER.info("Request to update appointment ID: {} for Patient ID: {}", appointmentId, patientId);

        try {
            return appointmentService.updateAppointment(appointmentId, patientId, dto).toString();
        } catch (Exception e) {
            LOGGER.error("Error updating appointment: {}", e.getMessage());
            return "Error updating appointment: " + e.getMessage();
        }
    }

    /**
     * Deletes an appointment for a patient.
     */
    @DeleteMapping("/deleteappointment/{appointmentId}/{patientId}")
    public String delete(@PathVariable Long appointmentId, @PathVariable Long patientId) {
        LOGGER.info("Request to delete appointment ID: {} for Patient ID: {}", appointmentId, patientId);

        try {
            boolean result = appointmentService.deleteAppointment(appointmentId, patientId);
            return result ? "Appointment deleted successfully." : "Appointment not found.";
        } catch (Exception e) {
            LOGGER.error("Error deleting appointment: {}", e.getMessage());
            return "Error deleting appointment: " + e.getMessage();
        }
    }

    /**
     * Retrieves an appointment by its unique ID.
     */
    @GetMapping("/getappointmentbyid/{appointmentId}")
    public AppointmentDTO getById(@PathVariable Long appointmentId) {
        LOGGER.info("Request to fetch appointment details for ID: {}", appointmentId);

        try {
            return appointmentService.getAppointmentById(appointmentId);
        } catch (Exception e) {
            LOGGER.error("Appointment not found with ID: {}", appointmentId);
            throw new RuntimeException("Appointment not found with ID: " + appointmentId);
        }
    }

    /**
     * Retrieves all appointments for a specific patient.
     */
    @GetMapping("/getappointmentbypatientid/{patientId}")
    public List<AppointmentDTO> getByPatientId(@PathVariable Long patientId) {
        LOGGER.info("Request to fetch appointments for Patient ID: {}", patientId);
        return appointmentService.getAppointmentsByPatientId(patientId);
    }


    /**
     * Handles general exceptions gracefully.
     */
    @ExceptionHandler(Exception.class)
    public String handleGlobalException(Exception ex) {
        LOGGER.error("Unhandled exception occurred: {}", ex.getMessage());
        return "An error occurred: " + ex.getMessage();
    }
}
