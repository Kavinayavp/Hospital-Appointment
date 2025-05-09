package com.cts.project.service;
import java.util.List;

import com.cts.project.dto.AppointmentDTO;
 
public interface AppointmentService {
    String bookAppointment(AppointmentDTO dto);
    AppointmentDTO updateAppointment(Long id, AppointmentDTO dto);
    AppointmentDTO getAppointmentById(Long id);
    List<AppointmentDTO> getAppointmentsByPatientId(Long patientId);
    void deleteAppointment(Long id);
}