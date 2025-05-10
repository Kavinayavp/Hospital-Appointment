package com.cts.project.service;
import java.util.List;

import com.cts.project.dto.AppointmentDTO;
 
public interface AppointmentService {
    String bookAppointment(AppointmentDTO dto);
    AppointmentDTO getAppointmentById(Long id);
    List<AppointmentDTO> getAppointmentsByPatientId(Long patientId);
	String updateAppointment(Long appointmentId, Long patientId, AppointmentDTO dto);
	String deleteAppointment(Long appointmentId, Long patientId);
}