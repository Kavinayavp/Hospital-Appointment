package com.cts.project.service;

import com.cts.project.dto.AppointmentDTO;
import com.cts.project.exception.AppointmentNotFoundException;
import com.cts.project.model.Appointment;
import com.cts.project.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
 
import java.util.List;
import java.util.stream.Collectors;
 
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
 
    private final AppointmentRepository repository;
 
    private AppointmentDTO mapToDTO(Appointment appointment) {
        return AppointmentDTO.builder()
                .appointmentId(appointment.getAppointmentId())
                .patientId(appointment.getPatientId())
                .doctorId(appointment.getDoctorId())
                .appointmentDate(appointment.getAppointmentDate())
                .appointmentTime(appointment.getAppointmentTime())
                .status(appointment.getStatus())
                .build();
    }
 
    private Appointment mapToEntity(AppointmentDTO dto) {
        return Appointment.builder()
                .appointmentId(dto.getAppointmentId())
                .patientId(dto.getPatientId())
                .doctorId(dto.getDoctorId())
                .appointmentDate(dto.getAppointmentDate())
                .appointmentTime(dto.getAppointmentTime())
                .status(dto.getStatus())
                .build();
    }
 
    @Override
    public AppointmentDTO bookAppointment(AppointmentDTO dto) {
        // Check if the doctor is available at the given date and time
        boolean isAvailable = repository.findByDoctorIdAndAppointmentDateAndAppointmentTime(
                dto.getDoctorId(), dto.getAppointmentDate(), dto.getAppointmentTime()).isEmpty();
 
        if (!isAvailable) {
            throw new IllegalArgumentException("Doctor is not available at the selected date and time.");
        }
 
        Appointment appointment = mapToEntity(dto);
        return mapToDTO(repository.save(appointment));
    }
 
    @Override
    public AppointmentDTO updateAppointment(Long id, AppointmentDTO dto) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found"));
 
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setDoctorId(dto.getDoctorId());
        appointment.setStatus(dto.getStatus());
 
        return mapToDTO(repository.save(appointment));
    }
 
    @Override
    public AppointmentDTO getAppointmentById(Long id) {
        return repository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found"));
    }
 
    @Override
    public List<AppointmentDTO> getAppointmentsByPatientId(Long patientId) {
        return repository.findByPatientId(patientId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    @Override
    public void deleteAppointment(Long id) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with id: " + id));
        repository.delete(appointment);
    }
}
 