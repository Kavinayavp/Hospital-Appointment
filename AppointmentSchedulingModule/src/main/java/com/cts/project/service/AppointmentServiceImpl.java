package com.cts.project.service;

import com.cts.project.dto.AppointmentDTO;
import com.cts.project.dto.DoctorResponseDTO;
import com.cts.project.exception.AppointmentNotFoundException;
import com.cts.project.exception.UnauthorizedAccessException;
import com.cts.project.feignclient.DoctorClient;
import com.cts.project.feignclient.PatientClient;
import com.cts.project.model.Appointment;
import com.cts.project.repository.AppointmentRepository;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentServiceImpl.class);


    private final AppointmentRepository appointmentRepository;
    private final PatientClient patientClient;
    private final DoctorClient doctorClient;

    /**
     * Books a new appointment for a patient.
     * Validates doctor availability before proceeding with the booking.
     */
    @Override
    public String bookAppointment(AppointmentDTO dto) {
        try {
            LOGGER.info("Attempting to book appointment for Patient ID: " + dto.getPatientId());

            // Fetch doctor details using Feign Client
            DoctorResponseDTO doctorResponse = doctorClient.getDoctorById(dto.getDoctorId());
            if (doctorResponse == null) {
                throw new AppointmentNotFoundException("Doctor not found with ID: " + dto.getDoctorId());
            }

            // Validate doctor's available days
            String appointmentDay = LocalDate.parse(dto.getAppointmentDate()).getDayOfWeek().name();
            if (!doctorResponse.getAvailableDays().stream().map(String::toUpperCase).toList()
                    .contains(appointmentDay)) {
                LOGGER.warn("Doctor not available on " + appointmentDay);
                return "Doctor is not available on " + appointmentDay;
            }

            // Proceed with booking
            Appointment appointment = new Appointment();
            appointment.setDoctorId(dto.getDoctorId());
            appointment.setDoctorName(dto.getDoctorName());
            appointment.setPatientId(dto.getPatientId());
            appointment.setPatientName(dto.getPatientName());
            appointment.setAppointmentDate(dto.getAppointmentDate());
            appointment.setAppointmentTime(dto.getAppointmentTime());
            appointment.setStatus("BOOKED");

            appointmentRepository.save(appointment);
            LOGGER.info("Appointment booked successfully for Patient ID: " + dto.getPatientId());
            return "Appointment booked successfully!";
        } catch (FeignException.NotFound e) {
            LOGGER.error("Doctor not found with ID: " + dto.getDoctorId());
            return "Doctor not found with ID: " + dto.getDoctorId();
        } catch (Exception e) {
            LOGGER.error("Error fetching doctor details: " + e.getMessage());
            return "Error fetching doctor details: " + e.getMessage();
        }
    }

    /**
     * Updates an existing appointment for a patient.
     * Ensures patient authorization before proceeding with the update.
     */
    @Override
    public AppointmentDTO updateAppointment(Long appointmentId, Long patientId, AppointmentDTO dto) {
        LOGGER.info("Updating appointment for Appointment ID: " + appointmentId);

        if (appointmentId == null || patientId == null) {
            throw new IllegalArgumentException("Appointment ID and Patient ID must not be null");
        }

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID: " + appointmentId));

        // Validate patient authorization
        if (!appointment.getPatientId().equals(patientId)) {
            LOGGER.error("Unauthorized attempt to update appointment ID: " + appointmentId);
            throw new UnauthorizedAccessException("Unauthorized: Cannot update another patient's appointment.");
        }

        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(dto.getAppointmentTime());

        if ("CANCEL".equalsIgnoreCase(dto.getStatus())) {
            appointment.setStatus("CANCELLED");
            LOGGER.info("Appointment cancelled successfully.");
        } else if ("RESCHEDULE".equalsIgnoreCase(dto.getStatus())) {
            appointment.setStatus("RESCHEDULED");
            LOGGER.info("Appointment rescheduled successfully.");
        } else {
            appointment.setStatus("BOOKED");
        }

        appointmentRepository.save(appointment);
        LOGGER.info("Appointment updated successfully for Appointment ID: " + appointmentId);
        return mapToDTO(appointment);
    }

    /**
     * Fetches an appointment by its ID.
     */
    @Override
    public AppointmentDTO getAppointmentById(Long appointmentId) {
        LOGGER.info("Fetching appointment details for Appointment ID: " + appointmentId);

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID: " + appointmentId));

        return mapToDTO(appointment);
    }

    /**
     * Retrieves all appointments for a specific patient.
     */
    @Override
    public List<AppointmentDTO> getAppointmentsByPatientId(Long patientId) {
        LOGGER.info("Fetching appointments for Patient ID: " + patientId);
        
        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);
        return appointments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    /**
     * Deletes an appointment for a patient.
     * Ensures patient authorization before deletion.
     */
    @Override
    public boolean deleteAppointment(Long appointmentId, Long patientId) {
        LOGGER.info("Attempting to delete appointment with ID: " + appointmentId);

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID: " + appointmentId));

        // Validate patient authorization
        if (!appointment.getPatientId().equals(patientId)) {
            LOGGER.error("Unauthorized attempt to delete appointment ID: " + appointmentId);
            throw new UnauthorizedAccessException("Unauthorized: Cannot delete another patient's appointment.");
        }

        appointmentRepository.delete(appointment);
        LOGGER.info("Appointment deleted successfully.");
        return true;
    }

    /**
     * Fetches an appointment by doctor and patient ID.
     */
    @Override
    public Appointment findByDoctorIdAndPatientId(Long doctorId, Long patientId) {
        LOGGER.info("Fetching appointment for Doctor ID: " + doctorId + " and Patient ID: " + patientId);

        return appointmentRepository.findByDoctorIdAndPatientId(doctorId, patientId)
                .orElseThrow(() -> new EntityNotFoundException("No appointment found for Doctor ID " + doctorId + " and Patient ID " + patientId));
    }

    /**
     * Maps an Appointment entity to AppointmentDTO.
     */
    private AppointmentDTO mapToDTO(Appointment appointment) {
        return AppointmentDTO.builder()
                .appointmentId(appointment.getAppointmentId())
                .doctorId(appointment.getDoctorId())
                .doctorName(appointment.getDoctorName())
                .patientId(appointment.getPatientId())
                .patientName(appointment.getPatientName())
                .appointmentDate(appointment.getAppointmentDate())
                .appointmentTime(appointment.getAppointmentTime())
                .status(appointment.getStatus())
                .build();
    }
}
