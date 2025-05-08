package com.cts.project.service;

import com.cts.project.dto.AppointmentDTO;
import com.cts.project.dto.PatientResponseDTO;
import com.cts.project.exception.AppointmentNotFoundException;
import com.cts.project.feignclient.PatientClient;
import com.cts.project.model.Appointment;
import com.cts.project.repository.AppointmentRepository;

import lombok.AllArgsConstructor;


import org.springframework.stereotype.Service;
 
import java.util.List;
import java.util.stream.Collectors;
 
@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
 
   
    private AppointmentRepository appointmentRepository;

    private PatientClient patientClient;
 
    @Override
    public AppointmentDTO bookAppointment(AppointmentDTO dto) {
        PatientResponseDTO patient = patientClient.getPatientById(dto.getPatientId());
        if (patient == null) {
            throw new AppointmentNotFoundException("Patient not found with ID: " + dto.getPatientId());
        }
 
        Appointment appointment = new Appointment();
        appointment.setDoctorId(dto.getDoctorId());
        appointment.setPatientId(dto.getPatientId());
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setStatus("BOOKED");
 
        Appointment saved = appointmentRepository.save(appointment);
        return mapToDTO(saved);
    }
 
    @Override
    public AppointmentDTO updateAppointment(Long id, AppointmentDTO dto) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID: " + id));
 
        boolean rescheduled = !appointment.getAppointmentDate().equals(dto.getAppointmentDate()) ||
                              !appointment.getAppointmentTime().equals(dto.getAppointmentTime());
 
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(dto.getAppointmentTime());
 
        if (dto.getStatus() != null) {
            appointment.setStatus(dto.getStatus());
        } else if (rescheduled) {
            appointment.setStatus("RESCHEDULED");
        }
 
        Appointment updated = appointmentRepository.save(appointment);
        return mapToDTO(updated);
    }
 
    @Override
    public AppointmentDTO getAppointmentById(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID: " + appointmentId));
        return mapToDTO(appointment);
    }
 
    @Override
    public List<AppointmentDTO> getAppointmentsByPatientId(Long patientId) {
        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);
        return appointments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
 
    @Override
    public void deleteAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID: " + appointmentId));
        appointmentRepository.delete(appointment);
    }
 
    private AppointmentDTO mapToDTO(Appointment appointment) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setAppointmentId(appointment.getAppointmentId());
        dto.setDoctorId(appointment.getDoctorId());
        dto.setPatientId(appointment.getPatientId());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setAppointmentTime(appointment.getAppointmentTime());
        dto.setStatus(appointment.getStatus());
        return dto;
    }


}
 